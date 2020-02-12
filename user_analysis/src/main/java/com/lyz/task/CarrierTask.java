package com.lyz.task;

import com.lyz.entity.CarrierEntity;
import com.lyz.entity.YearBaseEntity;
import com.lyz.map.CarrierMap;
import com.lyz.map.YearBaseMap;
import com.lyz.reduce.CarrierReduce;
import com.lyz.reduce.YearBaseReduce;
import com.lyz.util.MongoUtils;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;
import org.bson.Document;

import java.util.List;

/**
 * @Author: liyuzhan
 * @classDesp： 手机运营商任务执行
 * @Date: 2020/1/29 10:47
 * @Email: 1031759184@qq.com
 */
public class CarrierTask {
    public static void main(String[] args) {
        final ParameterTool param = ParameterTool.fromArgs(args);
        //建立环境

        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        //让param在web接口中可用

        env.getConfig().setGlobalJobParameters(param);
        //获取input数据
        DataSet<String> text = env.readTextFile(param.get("input"));
        DataSet<CarrierEntity> mapResult = text.map(new CarrierMap());
        DataSet<CarrierEntity> reduceResult = mapResult.groupBy("groupField").reduce(new CarrierReduce());
        try {
            List<CarrierEntity> resultList = reduceResult.collect();
            for (CarrierEntity carrier : resultList) {
                String carrierType = carrier.getCarrier();
                Long count = carrier.getCount();
                Document doc = MongoUtils.findOneBy("carrierstatics", "lyzPortrait", carrierType);
                if (null == doc) {
                    doc = new Document();
                    doc.put("info", carrierType);
                    doc.put("count", count);
                } else {
                    Long countPre = doc.getLong("count");
                    Long total = countPre + count;
                    doc.put("count", total);
                }
                MongoUtils.saveOrUpdateMongo("carrierstatics", "lyzPortrait", doc);
            }
            env.execute("carrier analysis");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
