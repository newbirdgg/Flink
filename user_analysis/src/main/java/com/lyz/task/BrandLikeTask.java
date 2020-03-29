package com.lyz.task;

import com.lyz.kafka.KafkaEvent;
import com.lyz.kafka.KafkaEventSchema;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;

/**
 *  @Author: liyuzhan
 *  @classDesp： 品牌偏好任务执行
 *  @Date: 2020/3/25 15:44
 *  @Email: 1031759184@qq.com
 */
public class BrandLikeTask {
    public static void main(String[] args) throws Exception {
        // parse input arguments
        args = new String[]{"--input-topic","scanProductLog","--bootstrap.servers","192.168.153.178:9092","--zookeeper.connect","192.168.153.178:2181","--group.id","lyz"};
        final ParameterTool parameterTool = ParameterTool.fromArgs(args);

//		if (parameterTool.getNumberOfParameters() < 5) {
//			System.out.println("Missing parameters!\n" +
//					"Usage: Kafka --input-topic <topic> --output-topic <topic> " +
//					"--bootstrap.servers <kafka brokers> " +
//					"--zookeeper.connect <zk quorum> --group.id <some id>");
//			return;
//		}

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.getConfig().disableSysoutLogging();
        env.getConfig().setRestartStrategy(RestartStrategies.fixedDelayRestart(4, 10000));
        // create a checkpoint every 5 seconds
        env.enableCheckpointing(5000);
        // make parameters available in the web interface
        env.getConfig().setGlobalJobParameters(parameterTool);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        DataStream<KafkaEvent> input = env
                .addSource(
                        new FlinkKafkaConsumer010<>(
                                parameterTool.getRequired("input-topic"),
                                new KafkaEventSchema(),
                                parameterTool.getProperties())
                                .assignTimestampsAndWatermarks(new CustomWatermarkExtractor()));
        DataStream<test> testmap = input.map();

        DataStream<NewUser> reduce = newusermap.keyBy("groupbyfield").timeWindowAll(Time.seconds(2)).reduce(new testReduce());

        testreduce.addSink(new testSink());

        env.execute("test analy");
	}

    /**
     * A custom {@link AssignerWithPeriodicWatermarks}, that simply assumes that the input stream
     * records are strictly ascending.
    */
    private static class CustomWatermarkExtractor implements AssignerWithPeriodicWatermarks<KafkaEvent> {

        private static final long serialVersionUID = -742759155861320823L;

        private long currentTimestamp = Long.MIN_VALUE;

        @Override
        public long extractTimestamp(KafkaEvent event, long previousElementTimestamp) {
            // the inputs are assumed to be of format (message,timestamp)
            this.currentTimestamp = event.getTimestamp();
            return event.getTimestamp();
        }

        @Nullable
        @Override
        public Watermark getCurrentWatermark() {
            return new Watermark(currentTimestamp == Long.MIN_VALUE ? Long.MIN_VALUE : currentTimestamp - 1);
        }
    }


}
