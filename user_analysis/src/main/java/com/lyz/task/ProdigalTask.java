package com.lyz.task;

import com.lyz.entity.EmailEntity;
import com.lyz.entity.ProdigalEntity;
import com.lyz.map.EmailMap;
import com.lyz.map.ProdigalMap;
import com.lyz.reduce.EmailReduce;
import com.lyz.reduce.ProdigalReduce;
import com.lyz.util.DateUtils;
import com.lyz.util.HBaseUtils;
import com.lyz.util.MongoUtils;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;
import org.bson.Document;
import sun.java2d.pipe.SpanShapeRenderer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: liyuzhan
 * @classDesp： 败家指数任务执行
 * @Date: 2020/2/13 21:33
 * @Email: 1031759184@qq.com
 */
public class ProdigalTask {
    public static void main(String[] args) {
        final ParameterTool param = ParameterTool.fromArgs(args);
        //建立环境

        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        //让param在web接口中可用

        env.getConfig().setGlobalJobParameters(param);
        //获取input数据
        DataSet<String> text = env.readTextFile(param.get("input"));
        DataSet<ProdigalEntity> mapResult = text.map(new ProdigalMap());
        DataSet<ProdigalEntity> reduceResult = mapResult.groupBy("groupField").reduce(new ProdigalReduce());
        try {
            List<ProdigalEntity> resultList = reduceResult.collect();
            for (ProdigalEntity prodigal : resultList) {
                String userId = prodigal.getUserId();
                List<ProdigalEntity> list = prodigal.getList();
                list.sort((o1, o2) -> {
                    String createTime1 = o1.getCreateTime();
                    String createTime2 = o2.getCreateTime();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Date time1 = new Date();
                    Date time2 = new Date();
                    try {
                        time1 = dateFormat.parse(createTime1);
                        time2 = dateFormat.parse(createTime2);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    return time1.compareTo(time2);
                });
                ProdigalEntity before = null;
                Map<Integer, Integer> frequencyMap = new HashMap<>(16);
                double maxAmount = 0d;
                double sum = 0d;
                for (ProdigalEntity pro : list) {
                    if (before == null) {
                        before = pro;
                    } else {
                        //计算购买频率
                        String beforeCreateTime = before.getCreateTime();
                        String endCreateTime = pro.getCreateTime();
                        int days = DateUtils.getBetweenByStartAndEnd(beforeCreateTime, endCreateTime, "yyyy-MM-dd hh:mm:ss");
                        int beforeDay = frequencyMap.get(days) == null ? 0 : frequencyMap.get(days);
                        frequencyMap.put(days, ++beforeDay);
                        //计算最大金额
                        String totalAmountString = pro.getTotalAmount();
                        double totalAmount = Double.parseDouble(totalAmountString);
                        if (totalAmount > maxAmount) {
                            maxAmount = totalAmount;
                        }
                        //计算平均值
                        sum += totalAmount;
                        before = pro;
                    }
                }
                double averageAmount = sum / list.size();
                int totalDays = 0;
                Set<Map.Entry<Integer, Integer>> entries = frequencyMap.entrySet();
                for (Map.Entry<Integer, Integer> entry : entries) {
                    Integer frequencyDays = entry.getKey();
                    Integer count = entry.getValue();
                    totalDays += frequencyDays * count;
                }
                //平均天数
                int averageDays = totalDays / list.size();

                //败家指数= 支付金额平均值*0.3、最大金额*0.3、下单频率*0.4
                //支付金额平均值30分(0-20 5，20-60 10,60-100 20,100-150 30，150-200 40，200-250 60,250-350 70，350-450 80，450-600 90 ，600+ 100)，
                // 最大支付金额30分（0-20 5 ，20-60 10,60-200 30，200-500 60,500-700 80,700+ 100），
                // 下单频率30分（0-5 100，5-10 90 ，10-30 70,30-60 60,60-80 40，80-100 20,100+ 10）
                int averageAmountScore = 0;
                if (averageAmount>0&&averageAmount<=20){
                    averageAmountScore = 5;
                }else if (averageAmount>20&&averageAmount<=60){
                    averageAmountScore = 10;
                }else if (averageAmount>60&&averageAmount<=100){
                    averageAmountScore = 20;
                }else if (averageAmount>100&&averageAmount<=150){
                    averageAmountScore = 30;
                }else if (averageAmount>15.&&averageAmount<=200){
                    averageAmountScore = 40;
                }else if (averageAmount>200&&averageAmount<=250){
                    averageAmountScore = 60;
                }else if (averageAmount>250&&averageAmount<=350){
                    averageAmountScore = 70;
                }else if (averageAmount>350&&averageAmount<=450){
                    averageAmountScore = 80;
                }else if (averageAmount>450&&averageAmount<=600){
                    averageAmountScore = 90;
                }else if (averageAmount>600){
                    averageAmountScore = 100;
                }

                int maxAmountScore = 0;
                if (maxAmount>0&&maxAmount<=20){
                    maxAmountScore = 5;
                }else if (maxAmount>20&&maxAmount<=60){
                    maxAmountScore = 10;
                }else if (maxAmount>60&&maxAmount<=200){
                    maxAmountScore = 30;
                }else if (maxAmount>200&&maxAmount<=500){
                    maxAmountScore = 60;
                }else if (maxAmount>500&&maxAmount<=700){
                    maxAmountScore = 80;
                }else if (maxAmount>700){
                    maxAmountScore = 100;
                }

                int averageDaysScore = 0;

                if (averageDays>0&&averageDays<=5){
                    averageDaysScore = 100;
                }else if (averageDays>5&&averageDays<=10){
                    averageDaysScore = 90;
                }else if (averageDays>10&&averageDays<=30){
                    averageDaysScore = 70;
                }else if (averageDays>30&&averageDays<=60){
                    averageDaysScore = 60;
                }else if (averageDays>60&&averageDays<=80){
                    averageDaysScore = 40;
                }else if (averageDays>80&&averageDays<=100){
                    averageDaysScore = 20;
                }else if (averageDays>100){
                    averageDaysScore = 10;
                }

                double totalScore = (averageAmountScore/100.0)*30+(maxAmountScore/100.0)*30+(averageDaysScore/100.0)*40;
                String tableName = "userflaginfo";
                String rowKey = "userid";
                String familyName = "baseinfo";
                //年代
                String column = "prodigalscore";
                HBaseUtils.putData(tableName,rowKey,familyName,column,totalScore+"");

               }
            env.execute("prodigalscore analysis");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
