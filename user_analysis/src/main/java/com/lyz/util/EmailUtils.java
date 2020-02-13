package com.lyz.util;
/**
 *  @Author: liyuzhan
 *  @classDesp： 邮箱工具类
 *  @Date: 2020/2/13 19:22
 *  @Email: 1031759184@qq.com
 */
public class EmailUtils {
    public static String getEmailTypeBy(String email){
        String emailType = Constent.OTHER_EMAIL_OPERATOR;
        if (email.contains("@163.com")||email.contains("@126.com")){
            emailType = Constent.WANGYI_EMAIL_OPERATOR;
        }else if(email.contains("@sohu.com")){
            emailType = Constent.SOUHU_EMAIL_OPERATOR;
        }else if(email.contains("@qq.com")){
            emailType = Constent.QQ_EMAIL_OPERATOR;
        }else if(email.contains("@aliyun.com")){
            emailType = Constent.ALIYUN_EMAIL_OPERATOR;
        }else if(email.contains("@sina.com")){
            emailType = Constent.XINLANG_EMAIL_OPERATOR;
        }
        return emailType;
    }
}
