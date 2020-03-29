package com.lyz.map;

import com.alibaba.fastjson.JSONObject;
import com.lyz.entity.BrandLikeEntity;
import com.lyz.kafka.KafkaEvent;
import com.lyz.log.ScanProductLog;
import com.lyz.util.HBaseUtils;
import com.lyz.util.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

import java.util.HashMap;
import java.util.Map;


/**
 * @Author: liyuzhan
 * @classDesp： 品牌偏好map
 * @Date: 2020/3/25 16:19
 * @Email: 1031759184@qq.com
 */
public class BrandLikeMap implements FlatMapFunction<KafkaEvent, BrandLikeEntity> {

    @Override
    public void flatMap(KafkaEvent kafkaEvent, Collector<BrandLikeEntity> collector) throws Exception {
        String data = kafkaEvent.getWord();
        ScanProductLog scanProductLog = JSONObject.parseObject(data, ScanProductLog.class);
        int userId = scanProductLog.getUserId();
        String brand = scanProductLog.getBrand();
        String tableName = "userflaginfo";
        String rowKey = "userid";
        String familyName = "userbehavior";
        String column = "brandlist";
        String mapData = HBaseUtils.getData(tableName, rowKey, familyName, column);
        Map<String, Long> map = new HashMap<>();
        if (StringUtils.isNotBlank(mapData)) {
            map = JSONObject.parseObject(mapData, Map.class);
        }
        //获取之前的品牌偏好
        String maxPreBrand = MapUtils.getMaxByMap(map);

        long preBrand = map.get(brand) == null ? 1 : map.get(brand);
        map.put(brand, preBrand + 1);
        String finalString = JSONObject.toJSONString(map);
        HBaseUtils.putData(tableName,rowKey,familyName,column,finalString);
        String maxBrand = MapUtils.getMaxByMap(map);
        if (StringUtils.isNotBlank(maxPreBrand)&&StringUtils.isNotBlank(maxBrand)&&!maxPreBrand.equals(maxBrand)){
            BrandLikeEntity brandLike = new BrandLikeEntity();
            brandLike.setBrand(maxPreBrand);
            brandLike.setCount(-1L);
            collector.collect(brandLike);
        }
        BrandLikeEntity brandLike = new BrandLikeEntity();
        brandLike.setBrand(maxBrand);
        brandLike.setCount(1L);
        collector.collect(brandLike);

    }
}
