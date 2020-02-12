package com.lyz.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

import java.util.Map;
import java.util.Set;

/**
 * @Author: liyuzhan
 * @classDesp： Hbase工具类
 * @Date: 2019/12/31 14:43
 * @Email: 1031759184@qq.com
 */
public class HBaseUtils {
    private static Connection conn = null;

    static {
        // 创建hbase配置对象
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.rootdir", "hdfs://192.168.153.178:9000/hbase");
        //定位
        conf.set("hbase.zookeeper.quorum", "192.168.153.178");
        conf.set("hbase.client.scanner.timeout.period", "600000");
        conf.set("hbase.rpc.timeout", "600000");
        try {
            conn = ConnectionFactory.createConnection(conf);
            // 得到管理程序
            Admin admin = conn.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 插入数据，create "userflaginfo","baseinfo"
     */
    public static void put(String tableName, String rowKey, String familyName, Map<String, String> dataMap) throws Exception {
        Table table = conn.getTable(TableName.valueOf(tableName));
        // 将字符串转换成byte[]
        byte[] rowKeyByte = Bytes.toBytes(rowKey);
        Put put = new Put(rowKeyByte);
        if (dataMap != null) {
            Set<Map.Entry<String, String>> set = dataMap.entrySet();
            for (Map.Entry<String, String> entry : set) {
                String key = entry.getKey();
                Object value = entry.getValue();
                put.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(key), Bytes.toBytes(value + ""));
            }
        }
        table.put(put);
        table.close();
        System.out.println("ok");
    }

    /**
     * 取值
     */
    public static String getData(String tableName, String rowKey, String familyName, String column) throws Exception {
        Table table = conn.getTable(TableName.valueOf(tableName));
        // 将字符串转换成byte[]
        byte[] rowKeyByte = Bytes.toBytes(rowKey);
        Get get = new Get(rowKeyByte);
        Result result = table.get(get);
        byte[] resultBytes = result.getValue(familyName.getBytes(), column.getBytes());
        if (resultBytes == null) {
            return null;
        }

        return new String(resultBytes);
    }

    /**
     * 存值
     */
    public static void putData(String tableName, String rowKey, String familyName, String column, String data) throws Exception {
        Table table = conn.getTable(TableName.valueOf(tableName));
        Put put = new Put(rowKey.getBytes());
        put.addColumn(familyName.getBytes(), column.getBytes(), data.getBytes());
        table.put(put);
    }


}
