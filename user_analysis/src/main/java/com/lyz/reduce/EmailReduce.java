package com.lyz.reduce;

import com.lyz.entity.EmailEntity;
import org.apache.flink.api.common.functions.ReduceFunction;
/**
 *  @Author: liyuzhan
 *  @classDesp： 邮件运营商reduce
 *  @Date: 2020/2/13 20:08
 *  @Email: 1031759184@qq.com
 */
public class EmailReduce implements ReduceFunction<EmailEntity> {
    @Override
    public EmailEntity reduce(EmailEntity emailEntity, EmailEntity t1) throws Exception {
        String emailType = emailEntity.getEmailType();
        String groupField = emailEntity.getGroupField();
        Long count1 = emailEntity.getCount();
        Long count2 = t1.getCount();
        EmailEntity result = new EmailEntity();
        result.setEmailType(emailType);
        result.setCount(count1+count2);
        result.setGroupField(groupField);
        return result;
    }
}
