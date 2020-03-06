package com.bd.test.flink;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.java.io.PojoCsvInputFormat;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple1;
import org.apache.flink.api.java.typeutils.PojoTypeInfo;
import org.apache.flink.api.java.typeutils.TypeExtractor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import javax.swing.*;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HotItems {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env= StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        AssignerWithPeriodicWatermarks assignerWithPeriodicWatermarks=null;

        String dataPath="UserBehavior.csv";
        URL fileUrl=HotItems.class.getClassLoader().getResource(dataPath);
        Path filePath= Path.fromLocalFile(new File(fileUrl.toURI()));
        PojoTypeInfo<UserBehavior> pojoTypeInfo= (PojoTypeInfo<UserBehavior>) TypeExtractor.createTypeInfo(UserBehavior.class);
        String[] fieldOrder = new String[]{"userId", "itemId", "categoryId", "behavior",
                "timestamp"};
        PojoCsvInputFormat<UserBehavior> csvInputFormat=new PojoCsvInputFormat<UserBehavior>(filePath,pojoTypeInfo,fieldOrder);
        DataStream<UserBehavior> dataSource=env.createInput(csvInputFormat,pojoTypeInfo);

        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        DataStream<UserBehavior> timedData=dataSource.assignTimestampsAndWatermarks(new AscendingTimestampExtractor<UserBehavior>() {
            @Override
            public long extractAscendingTimestamp(UserBehavior userBehavior) {
                return userBehavior.timestamp*1000;
            }
        });

        DataStream<UserBehavior> pvData=timedData.filter(new FilterFunction<UserBehavior>() {
            public boolean filter(UserBehavior userBehavior) throws Exception {
                return userBehavior.behavior.equals("pv");
            }
        });

        DataStream<ItemViewCount> windowedData=pvData.keyBy("itemId")
                .timeWindow(Time.minutes(60),Time.minutes(5))
                .aggregate(new CountAgg(), new WindowResultFunction());

        DataStream<String> topItems=windowedData.keyBy("windowEnd")
                .process(new TopNHotItems(3));
        topItems.print();
        env.execute("Hot Items Job");

    }



    public static class TopNHotItems extends KeyedProcessFunction<Tuple,
                ItemViewCount, String> {
        private final int topSize;
        public TopNHotItems(int topSize) {
            this.topSize = topSize;
        }
        private ListState<ItemViewCount> itemState;
        @Override
        public void open(Configuration parameters) throws Exception {
            super.open(parameters);
            ListStateDescriptor<ItemViewCount> itemsStateDesc=new ListStateDescriptor<ItemViewCount>("itemState-state",ItemViewCount.class);
            itemState=getRuntimeContext().getListState(itemsStateDesc);
        }
        @Override
        public void processElement(ItemViewCount input, Context context, Collector<String> collector) throws Exception {
            itemState.add(input);
            context.timerService().registerEventTimeTimer(input.windowEnd+1);
        }
        @Override
        public void onTimer(long timestamp, OnTimerContext ctx, Collector<String> out) throws Exception {
            List<ItemViewCount> allItems=new ArrayList<ItemViewCount>();
            for (ItemViewCount item : itemState.get()) {
                allItems.add(item);
            }
            itemState.clear();
            allItems.sort(new Comparator<ItemViewCount>() {
                public int compare(ItemViewCount o1, ItemViewCount o2) {
                    return (int)(o2.viewCount-o1.viewCount);
                }
            });
            // 将排名信息格式化成 String, 便于打印
            StringBuilder result = new StringBuilder();
            result.append("====================================\n");
            result.append("时间: ").append(new Timestamp(timestamp-1)).append("\n");
            for (int i=0;i<topSize;i++) {
                ItemViewCount currentItem = allItems.get(i);
                result.append("No").append(i).append(":")
                        .append(" 商品ID=").append(currentItem.itemId)
                        .append(" 浏览量=").append(currentItem.viewCount)
                        .append("\n");
            }
            result.append("====================================\n\n");
            out.collect(result.toString());
        }

    }


    public static class CountAgg implements AggregateFunction<UserBehavior,Long,Long> {
        public Long createAccumulator() {
            return 0L;
        }

        public Long add(UserBehavior userBehavior, Long acc) {
            return acc+1;
        }

        public Long getResult(Long accumulator) {
            return accumulator;
        }

        public Long merge(Long a, Long b) {
            return a+b;
        }
    }

    public static class WindowResultFunction implements WindowFunction<Long,ItemViewCount,Tuple,TimeWindow> {

        public void apply(Tuple key, TimeWindow timeWindow, Iterable<Long> iterable, Collector<ItemViewCount> collector) throws Exception {
            Long itemId=((Tuple1<Long>)key).f0;
            Long count=iterable.iterator().next();
            collector.collect(ItemViewCount.of(itemId, timeWindow.getEnd(), count));
        }
    }

    /** 商品点击量(窗口操作的输出类型) */
    public static class ItemViewCount {
        public long itemId; // 商品ID
        public long windowEnd; // 窗口结束时间戳
        public long viewCount; // 商品的点击量
        public static ItemViewCount of(long itemId, long windowEnd, long viewCount) {
            ItemViewCount result = new ItemViewCount();
            result.itemId = itemId;
            result.windowEnd = windowEnd;
            result.viewCount = viewCount;
            return result;
        }
    }
}
