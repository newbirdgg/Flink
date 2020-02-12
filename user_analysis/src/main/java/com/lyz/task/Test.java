package com.lyz.task;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;

/**
 * @Author: liyuzhan
 * @classDesp： 测试用例
 * @copyright： 山东亿云
 * @Date: 2019/10/31
 */
public class Test {
    public static void main(String[] args) {
        final ParameterTool param = ParameterTool.fromArgs(args);

        //建立环境
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        //让param在web接口中可用
        env.getConfig().setGlobalJobParameters(param);

        //获取input数据
        DataSet<String> text = env.readTextFile(param.get("input"));
        DataSet map = text.flatMap(null);
        DataSet reduce = map.groupBy("groupbyfield").reduce(null);
        try {
            env.execute("test");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
