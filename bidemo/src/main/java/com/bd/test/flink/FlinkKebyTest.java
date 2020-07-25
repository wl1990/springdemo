package com.bd.test.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.triggers.Trigger;
import org.apache.flink.streaming.api.windowing.triggers.TriggerResult;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.List;

public class FlinkKebyTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env= StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> text=env.socketTextStream("localhost",9500,"\n");
        DataStream<Tuple2<String,Integer>> windowCounts=text.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {
                for(String word:value.split("\\s")){
                    out.collect(Tuple2.of(word,1));
                }
            }
        }).keyBy(0).timeWindow(Time.seconds(20)).trigger(CustomTrigger.create()).sum(1);
        windowCounts.print().setParallelism(1);
        env.execute("key test");
    }

}

class CustomTrigger extends Trigger<Object, TimeWindow> {
    private static final long serialVersionUID = 1L;
    public CustomTrigger() {}

    private static int flag = 0;

    @Override
    public TriggerResult onElement(Object element, long timestamp, TimeWindow window, TriggerContext ctx){
        ctx.registerEventTimeTimer(window.maxTimestamp());
        if (flag > 4) {
            flag = 0;
            return TriggerResult.FIRE;
        }else{
            flag ++;
        }
        System.out.println("onElement: " + element);
        return TriggerResult.CONTINUE;
    }

    @Override
    public TriggerResult onEventTime(long time, TimeWindow window, TriggerContext ctx) throws Exception{
        return TriggerResult.CONTINUE;
    }

    @Override
    public TriggerResult onProcessingTime(long time, TimeWindow window, TriggerContext ctx) throws Exception {
        return TriggerResult.FIRE;
    }

    @Override
    public void clear(TimeWindow window, TriggerContext ctx) throws Exception{
        ctx.deleteProcessingTimeTimer(window.maxTimestamp());
    }

    @Override
    public String toString(){
        return "CustomTrigger";
    }

//    @Override
//    public void onMerge(TimeWindow window, OnMergeContext ctx) throws Exception {
//        long windowMaxTimestamp = window.maxTimestamp();
//        if (windowMaxTimestamp > ctx.getCurrentProcessingTime()) {
//            ctx.registerProcessingTimeTimer(windowMaxTimestamp);
//        }
//    }

    public static CustomTrigger create(){
        return new CustomTrigger();
    }
}