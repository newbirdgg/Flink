package com.lyz.map;

import com.lyz.entity.EmailEntity;
import com.lyz.util.EmailUtils;
import com.lyz.util.HBaseUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;
/**
 *  @Author: liyuzhan
 *  @classDesp： 邮箱运营商map
 *  @Date: 2020/2/13 19:42
 *  @Email: 1031759184@qq.com
 */
public class EmailMap implements MapFunction<String, EmailEntity> {
    @Override
    public EmailEntity map(String param) throws Exception {
        if (StringUtils.isBlank(param)) {
            return null;
        }
        String[] userInfos = param.split(",");
        String email = userInfos[5];
        String emailType = EmailUtils.getEmailTypeBy(email);
        String tableName = "userflaginfo";
        String rowKey = "userid";
        String familyName = "baseinfo";
        String column = "emailinfo";
        HBaseUtils.putData(tableName,rowKey,familyName,column,emailType);
        EmailEntity emailEntity = new EmailEntity();
        String groupField = "emailinfo=="+emailType;
        emailEntity.setEmailType(emailType);
        emailEntity.setCount(1L);
        emailEntity.setGroupField(groupField);

        return emailEntity;
    }
}
