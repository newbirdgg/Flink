package com.lyz.util;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @Author: liyuzhan
 * @classDesp：
 * @Date: 2020/3/25 16:52
 * @Email: 1031759184@qq.com
 */
public class MapUtils {
    public static String getMaxByMap(Map<String, Long> dataMap) {
        if (dataMap.isEmpty()){
            return null;
        }
        TreeMap<Long, String> map = new TreeMap<>(Comparator.reverseOrder());
        Set<Map.Entry<String, Long>> set = dataMap.entrySet();
        for (Map.Entry<String, Long> entry : set){
            String key = entry.getKey();
            Long value = entry.getValue();
            map.put(value,key);
        }
        return map.get(map.firstKey());
    }
}
