package com.lyz.task;

import com.lyz.entity.EmailEntity;
import com.lyz.map.EmailMap;
import com.lyz.reduce.EmailReduce;
import com.lyz.util.MongoUtils;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;
import org.bson.Document;

import java.util.List;

/**
 *  @Author: liyuzhan
 *  @classDesp： 邮箱运营商任务执行
 *  @Date: 2020/2/13 19:40
 *  @Email: 1031759184@qq.com
 */
public class EmailTask {
    public static void main(String[] args) {
        final ParameterTool param = ParameterTool.fromArgs(args);
        //建立环境

        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        //让param在web接口中可用

        env.getConfig().setGlobalJobParameters(param);
        //获取input数据
        DataSet<String> text = env.readTextFile(param.get("input"));
        DataSet<EmailEntity> mapResult = text.map(new EmailMap());
        DataSet<EmailEntity> reduceResult = mapResult.groupBy("groupField").reduce(new EmailReduce());
        try {
            List<EmailEntity> resultList = reduceResult.collect();
            for (EmailEntity email : resultList) {
                String emailType = email.getEmailType();
                Long count = email.getCount();
                Document doc = MongoUtils.findOneBy("emailstatics", "lyzPortrait", emailType);
                if (null == doc) {
                    doc = new Document();
                    doc.put("info", emailType);
                    doc.put("count", count);
                } else {
                    Long countPre = doc.getLong("count");
                    Long total = countPre + count;
                    doc.put("count", total);
                }
                MongoUtils.saveOrUpdateMongo("emailstatics", "lyzPortrait", doc);
            }
            env.execute("email analysis");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
