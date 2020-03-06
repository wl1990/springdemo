package com.bd.test.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FlinkTest {
    public static void main(String[] args) throws Exception {
        ExecutorService exec=Executors.newCachedThreadPool();
        StreamExecutionEnvironment env=StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> text=env.socketTextStream("localhost",9000,"\n");
        DataStream<Tuple2<String,Integer>> wordCounts=text.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {
                for(String word:value.split("\\s")){
                    out.collect(Tuple2.of(word,1));
                }
            }
        });
        DataStream<Tuple2<String,Integer>> windowCounts=wordCounts.keyBy(0).timeWindow(Time.seconds(5)).sum(1);
        windowCounts.print().setParallelism(1);
        env.execute("socket window wordcount");

    }
}
