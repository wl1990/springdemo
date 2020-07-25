package com.bd.test.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.text.SimpleDateFormat;

/**
 * 去重窗口统计
 * 未触发滚动窗口
 * 触发后清除窗口数据
 * win 启动socket nc -l -p 9500
 */
public class FlinkWinDistinctCountTest {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env=StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        DataStream<String> dataStream=env.socketTextStream("localhost",9500,"\n");
        SingleOutputStreamOperator<Tuple2<String,String>> convetStream=dataStream.flatMap(new FlatMapFunction<String,Tuple2<String,String>>()
        {
            @Override
            public void flatMap(String value, Collector<Tuple2<String, String>> out) throws Exception {
                String[] words=value.split("\\s");
                System.out.println("value = [" + words[0] + "], out = [" + words[1] + "]");
                out.collect(new Tuple2<>(words[0],words[1]));
            }
        }).setParallelism(1)
                .name("convert map")
                .assignTimestampsAndWatermarks(new AscendingTimestampExtractor<Tuple2<String,String>>(){
                    @Override
                    public long extractAscendingTimestamp(Tuple2<String, String> element) {
                        return System.currentTimeMillis();
                    }
                });
        int wsize=2;
        DataStream<String> outstream=convetStream.keyBy(0).window(SlidingEventTimeWindows.of(Time.minutes(wsize), Time.seconds(wsize*30)))
                .trigger(MyDistinctCountTrigger.create(5,2))
                .process(new ProcessWindowFunction<Tuple2<String, String>, String, Tuple, TimeWindow>() {
                    @Override
                    public void process(Tuple tuple, Context context, Iterable<Tuple2<String, String>> elements, Collector<String> out) throws Exception {
                        String key=tuple.getField(0);
                        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
                        System.out.println("key="+key+"]time="+sdf.format(System.currentTimeMillis()));
                        out.collect(key);
                    }
                }).setParallelism(1);
        outstream.print().setParallelism(1);
        env.execute("distinct_window");

    }
}
