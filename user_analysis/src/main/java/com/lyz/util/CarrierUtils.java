package com.lyz.util;

import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * @Author: liyuzhan
 * @classDesp： 手机运营商工具类
 * @Date: 2020/1/29 10:18
 * @Email: 1031759184@qq.com
 */
public class CarrierUtils {
    /**
     * 判断手机运营商
     *
     * @param phoneNum 手机号
     * @return
     */
    public static String getCarrierByPhone(String phoneNum) {
        if (phoneNum != null && !"".equals(phoneNum.trim())) {
            if (match(Constent.CHINA_MOBILE_PATTERN, phoneNum)) {
                return Constent.MOBILE_PARTTEN;
            }
            if (match(Constent.CHINA_UNICOM_PATTERN, phoneNum)) {
                return Constent.UNICOM_PARTTEN;
            }
            if (match(Constent.CHINA_TELECOM_PATTERN, phoneNum)) {
                return Constent.TELECOM_PARTTEN;
            }
        }
        return Constent.UNKOWN_PARTTEN;
    }

    /**
     * 匹配函数
     *
     * @param regex 正则规则
     * @param tel   手机号
     * @return boolean
     */
    private static boolean match(String regex, String tel) {
        return Pattern.matches(regex, tel);
    }

}
