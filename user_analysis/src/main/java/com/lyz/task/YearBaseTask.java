package com.lyz.task;

import com.lyz.entity.YearBaseEntity;
import com.lyz.map.YearBaseMap;
import com.lyz.reduce.YearBaseReduce;
import com.lyz.util.MongoUtils;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.MapOperator;
import org.apache.flink.api.java.operators.ReduceOperator;
import org.apache.flink.api.java.utils.ParameterTool;
import org.bson.Document;

import java.util.List;

/**
 *  @Author: liyuzhan
 *  @classDesp：用户年代标签
 *  @Date: 2019/12/31 9:33
 *  @Email: 1031759184@qq.com
 */
public class YearBaseTask {
    public static void main(String[] args) {
        final ParameterTool param = ParameterTool.fromArgs(args);
        //建立环境

        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        //让param在web接口中可用

        env.getConfig().setGlobalJobParameters(param);
        //获取input数据
        DataSet<String> text = env.readTextFile(param.get("input"));
        DataSet<YearBaseEntity> mapResult = text.map(new YearBaseMap());
        DataSet<YearBaseEntity> reduceResult = mapResult.groupBy("groupField").reduce(new YearBaseReduce());
        try {
            List<YearBaseEntity> resultList = reduceResult.collect();
            for (YearBaseEntity yearBase:resultList){
                String yearType = yearBase.getYearType();
                Long count = yearBase.getCount();
                Document doc = MongoUtils.findOneBy("yearbasestatics", "lyzPortrait", yearType);
                if (null == doc){
                    doc = new Document();
                    doc.put("info",yearType);
                    doc.put("count",count);
                }else {
                    Long countPre = doc.getLong("count");
                    Long total = countPre + count;
                    doc.put("count",total);
                }
                MongoUtils.saveOrUpdateMongo("yearbasestatics","lyzPortrait",doc);
            }
            env.execute("year base analysis");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
