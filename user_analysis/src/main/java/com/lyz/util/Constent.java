package com.lyz.util;

/**
 * @Author: liyuzhan
 * @classDesp： 用来存储常量
 * @Date: 2020/1/29 10:22
 * @Email: 1031759184@qq.com
 */
public class Constent {
    /**
     * 中国电信号码格式验证 手机段： 133,153,180,181,189,177,1700,173,199
     **/
    public static final String CHINA_TELECOM_PATTERN = "(^1(33|53|77|73|99|8[019])\\d{8}$)|(^1700\\d{7}$)";

    /**
     * 中国联通号码格式验证 手机段：130,131,132,155,156,185,186,145,176,1709
     **/
    public static final String CHINA_UNICOM_PATTERN = "(^1(3[0-2]|4[5]|5[56]|7[6]|8[56])\\d{8}$)|(^1709\\d{7}$)";

    /**
     * 中国移动号码格式验证
     * 手机段：134,135,136,137,138,139,150,151,152,157,158,159,182,183,184,187,188,147,178,1705
     **/
    public static final String CHINA_MOBILE_PATTERN = "(^1(3[4-9]|4[7]|5[0-27-9]|7[8]|8[2-478])\\d{8}$)|(^1705\\d{7}$)";

    /**
     * 手机运营商型号
     */
    public static final String UNKOWN_PARTTEN = "unkown";
    public static final String MOBILE_PARTTEN = "移动用户";
    public static final String UNICOM_PARTTEN = "联通用户";
    public static final String TELECOM_PARTTEN = "电信用户";

    /**
     * 邮箱运营商
     */
    public static final String OTHER_EMAIL_OPERATOR = "其他邮箱";
    public static final String WANGYI_EMAIL_OPERATOR = "网易";
    public static final String SOUHU_EMAIL_OPERATOR = "搜狐";
    public static final String ALIYUN_EMAIL_OPERATOR = "阿里";
    public static final String XINLANG_EMAIL_OPERATOR = "新浪";
    public static final String QQ_EMAIL_OPERATOR = "QQ";

}
