package com.lyz.utils;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * @Author: liyuzhan
 * @classDesp：
 * @Date: 2020/3/25 14:50
 * @Email: 1031759184@qq.com
 */
public class ReadProperties {
    public final static Config CONFIG = ConfigFactory.load("lyz.properties");

    public static String getKey(String key) {
        return CONFIG.getString(key).trim();
    }

}
