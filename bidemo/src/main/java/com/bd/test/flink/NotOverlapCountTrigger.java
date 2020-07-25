package com.bd.test.flink;

import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.state.ReducingState;
import org.apache.flink.api.common.state.ReducingStateDescriptor;
import org.apache.flink.api.common.state.StateTtlConfig;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.api.common.typeutils.base.LongSerializer;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.windowing.triggers.Trigger;
import org.apache.flink.streaming.api.windowing.triggers.TriggerResult;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.api.windowing.windows.Window;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangyuxin
 * 需要配合修改过的SlidingEventTimeWindows使用
 */
public class NotOverlapCountTrigger extends Trigger<Tuple2<String, String>, TimeWindow> {
    private static final long serialVersionUID = 1L;

    private final long maxCount;

    /**
     * 间隔时间：minute
     */
    private long ttl;
    private ReducingStateDescriptor<Long> stateDesc;


    private NotOverlapCountTrigger(long maxCount, long ttl) {
        this.ttl = ttl;
        this.maxCount = maxCount;
        stateDesc = new ReducingStateDescriptor<>("count", new NotOverlapCountTrigger.Sum(), LongSerializer.INSTANCE);
        StateTtlConfig ttlConfig = StateTtlConfig
                .newBuilder(Time.minutes(ttl))
                .setUpdateType(StateTtlConfig.UpdateType.OnCreateAndWrite)
                .setStateVisibility(StateTtlConfig.StateVisibility.NeverReturnExpired)
                .build();
        stateDesc.enableTimeToLive(ttlConfig);
    }

    @Override
    public TriggerResult onElement(Tuple2<String, String> element, long timestamp, TimeWindow window, Trigger.TriggerContext ctx) throws Exception {
        if (window.maxTimestamp() <= ctx.getCurrentWatermark()) {
            System.out.println("maxTimestamp()<=CurrentWatermark");
            return TriggerResult.PURGE;
        }
        ctx.registerEventTimeTimer(window.maxTimestamp());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        System.out.println("onElement maxTimestamp="+sdf.format(window.maxTimestamp())
                +"]start="+sdf.format(window.getStart())
                +"]end="+sdf.format(window.getEnd())
                +"]CurrentWatermark="+sdf.format(ctx.getCurrentWatermark()<0 ? 0:ctx.getCurrentWatermark())+"]timestamp="+sdf.format(timestamp));

        ReducingState<Long> count = ctx.getPartitionedState(stateDesc);
        //清理触发前的数据
        count.add(1L);
        if (count.get() >= maxCount) {
            System.out.println("bigger threadhold timestamp="+sdf.format(timestamp));
            count.clear();
            return TriggerResult.FIRE_AND_PURGE;
        }
        return TriggerResult.CONTINUE;
    }

    @Override
    public TriggerResult onEventTime(long time, TimeWindow window, Trigger.TriggerContext ctx) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        System.out.println("onEventTime timeWindow.maxTimestamp="+sdf.format(window.maxTimestamp())
                +"]start="+sdf.format(window.getStart())
                +"]end="+sdf.format(window.getEnd())
                +"]CurrentWatermark="+sdf.format(ctx.getCurrentWatermark()<0 ? 0:ctx.getCurrentWatermark())+"]timestamp="+sdf.format(time));

        return time == window.maxTimestamp() ?
                TriggerResult.PURGE :
                TriggerResult.CONTINUE;
    }

    @Override
    public TriggerResult onProcessingTime(long time, TimeWindow window, Trigger.TriggerContext ctx) throws Exception {
        return TriggerResult.CONTINUE;
    }

    @Override
    public void clear(TimeWindow window, Trigger.TriggerContext ctx) throws Exception {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        System.out.println("clear timeWindow.maxTimestamp="+sdf.format(window.maxTimestamp())
                +"]start="+sdf.format(window.getStart())
                +"]end="+sdf.format(window.getEnd())
                +"]CurrentWatermark="+sdf.format(ctx.getCurrentWatermark()<0 ? 0:ctx.getCurrentWatermark()));
        ctx.getPartitionedState(stateDesc).clear();
        ctx.deleteEventTimeTimer(window.maxTimestamp());
    }

    @Override
    public boolean canMerge() {
        return true;
    }

    @Override
    public void onMerge(TimeWindow window, Trigger.OnMergeContext ctx) throws Exception {
        ctx.mergePartitionedState(stateDesc);
    }

    @Override
    public String toString() {
        return "CountTrigger(" + maxCount + ")";
    }

    /**
     * Creates a trigger that fires once the number of elements in a pane reaches the given count.
     *
     * @param maxCount The count of elements at which to fire.
     */
    public static  NotOverlapCountTrigger of(long maxCount, long ttl) {
        return new NotOverlapCountTrigger(maxCount, ttl);
    }

    private static class Sum implements ReduceFunction<Long> {
        private static final long serialVersionUID = 1L;

        @Override
        public Long reduce(Long value1, Long value2) throws Exception {
            return value1 + value2;
        }

    }
}
