package com.bd.test.flink;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.state.AggregatingState;
import org.apache.flink.api.common.state.AggregatingStateDescriptor;
import org.apache.flink.api.common.state.StateTtlConfig;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.api.common.typeutils.base.ListSerializer;
import org.apache.flink.api.common.typeutils.base.StringSerializer;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.windowing.triggers.Trigger;
import org.apache.flink.streaming.api.windowing.triggers.TriggerResult;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MyDistinctCountTrigger extends Trigger<Tuple2<String,String>,TimeWindow> {
    private int threadhold;
    private Time lateness;
    private long ttl;
    private AggregatingStateDescriptor<Tuple2<String,String>,List<String>,Integer> stateDesc;
    private MyDistinctCountTrigger(int threadhold, long ttl){
        this.threadhold=threadhold;
        this.ttl=ttl;
        stateDesc=new AggregatingStateDescriptor<>("distinctCount",new DistinctCount(),new ListSerializer<>(StringSerializer.INSTANCE));
        StateTtlConfig ttlConfig=StateTtlConfig.newBuilder(Time.minutes(this.ttl))
                .setUpdateType(StateTtlConfig.UpdateType.OnCreateAndWrite)
                .setStateVisibility(StateTtlConfig.StateVisibility.NeverReturnExpired)
                .build();
        stateDesc.enableTimeToLive(ttlConfig);
    }

    public static MyDistinctCountTrigger create(int threadhold, long ttl){
        return new MyDistinctCountTrigger(threadhold,ttl);
    }

    @Override
    public TriggerResult onElement(Tuple2<String, String> element, long timestamp, TimeWindow timeWindow, TriggerContext triggerContext) throws Exception {
        if(timeWindow.maxTimestamp()<=triggerContext.getCurrentWatermark()){
            System.out.println("maxTimestamp()<=CurrentWatermark");
            return TriggerResult.PURGE;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        triggerContext.registerEventTimeTimer(timeWindow.maxTimestamp());
        System.out.println("onElement maxTimestamp="+sdf.format(timeWindow.maxTimestamp())
                +"]start="+sdf.format(timeWindow.getStart())
                +"]end="+sdf.format(timeWindow.getEnd())
                +"]CurrentWatermark="+sdf.format(triggerContext.getCurrentWatermark()<0 ? 0:triggerContext.getCurrentWatermark())+"]timestamp="+sdf.format(timestamp));
        AggregatingState<Tuple2<String, String>, Integer> count = triggerContext.getPartitionedState(stateDesc);
        count.add(element);
        //清理触发前的聚合数据 原始数据
        if(count.get().intValue()>=threadhold){
            System.out.println("bigger threadhold timestamp="+sdf.format(timestamp));
            count.clear();
            return TriggerResult.FIRE_AND_PURGE;
        }
        return TriggerResult.CONTINUE;
    }

    @Override
    public TriggerResult onProcessingTime(long timestamp, TimeWindow timeWindow, TriggerContext triggerContext) throws Exception {
        return TriggerResult.CONTINUE;
    }

    @Override
    public TriggerResult onEventTime(long timestamp, TimeWindow timeWindow, TriggerContext triggerContext) throws Exception {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        System.out.println("onEventTime timeWindow.maxTimestamp="+sdf.format(timeWindow.maxTimestamp())
                +"]start="+sdf.format(timeWindow.getStart())
                +"]end="+sdf.format(timeWindow.getEnd())
                +"]CurrentWatermark="+sdf.format(triggerContext.getCurrentWatermark()<0 ? 0:triggerContext.getCurrentWatermark())+"]timestamp="+sdf.format(timestamp));
        return timestamp == timeWindow.maxTimestamp() ?
                TriggerResult.PURGE :
                TriggerResult.CONTINUE;
    }

    @Override
    public void clear(TimeWindow timeWindow, TriggerContext triggerContext) throws Exception {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        System.out.println("clear timeWindow.maxTimestamp="+sdf.format(timeWindow.maxTimestamp())
                +"]start="+sdf.format(timeWindow.getStart())
                +"]end="+sdf.format(timeWindow.getEnd())
                +"]CurrentWatermark="+sdf.format(triggerContext.getCurrentWatermark()<0 ? 0:triggerContext.getCurrentWatermark()));
        triggerContext.getPartitionedState(stateDesc).clear();
        triggerContext.deleteEventTimeTimer(timeWindow.maxTimestamp());
    }

    @Override
    public boolean canMerge() {
        return true;
    }

    @Override
    public void onMerge(TimeWindow timeWindow, OnMergeContext ctx) throws Exception {
        ctx.mergePartitionedState(stateDesc);
    }

    @Override
    public String toString() {
        return "CountTrigger(" + threadhold + ")";
    }

    private static class DistinctCount implements AggregateFunction<Tuple2<String, String>, List<String>, Integer>{

        @Override
        public List<String> createAccumulator() {
            return new ArrayList<>();
        }

        @Override
        public List<String> add(Tuple2<String, String> value, List<String> accumulator) {
            if(!accumulator.contains(value.f1)){
                accumulator.add(value.f1);
            }
            return accumulator;
        }

        @Override
        public Integer getResult(List<String> accumulator) {
            return accumulator.size();
        }

        @Override
        public List<String> merge(List<String> a, List<String> b) {
            Set<String> set=new HashSet<>();
            set.addAll(a);
            set.addAll(b);
            return set.stream().collect(Collectors.toList());
        }
    }
}
