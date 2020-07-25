package com.bd.test.flink;

import org.apache.flink.api.java.io.PojoCsvInputFormat;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.typeutils.PojoTypeInfo;
import org.apache.flink.api.java.typeutils.TypeExtractor;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 每个process 都能接受全部的source数据
 */
public class FlinkMulProcessTest {

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
        final Map<String,Long> map=new HashMap<String,Long>();
        List<SingleOutputStreamOperator<String>> ruleTriggerStreamList = new ArrayList<>();
        for(int i=0;i<10;i++){
            final String suff=String.valueOf(i);
            SingleOutputStreamOperator<String> triggerStream=dataSource.process(new ProcessFunction<UserBehavior, String>() {
                long count;
                @Override
                public void processElement(UserBehavior userBehavior, Context context, Collector<String> collector) throws Exception {
                    count++;
                    collector.collect("count="+count+"]context="+suff);
                }
            }).setParallelism(1).name("sub param");
            ruleTriggerStreamList.add(triggerStream);
        }
        System.out.println("end flink ruleTriggerStreamList  size="+ruleTriggerStreamList.size());
        ruleTriggerStreamList.forEach(triggerStream->{
            triggerStream.print();
        });
        env.execute("mul process");
    }
}
