package com.lyz.map;

import com.lyz.entity.YearBaseEntity;
import com.lyz.util.DateUtils;
import com.lyz.util.HBaseUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;

/**
 * @Author: liyuzhan
 * @classDesp： 年代标签map获取
 * @Date: 2019/12/31 9:43
 * @Email: 1031759184@qq.com
 */
public class YearBaseMap implements MapFunction<String, YearBaseEntity> {

    @Override
    public YearBaseEntity map(String param) throws Exception {
        if (StringUtils.isBlank(param)) {
            return null;
        }
        String[] userInfos = param.split(",");
        String userId = userInfos[0];
        String userName = userInfos[1];
        String userPassword = userInfos[2];
        String sex = userInfos[3];
        String phoneNum = userInfos[4];
        String email = userInfos[5];
        String age = userInfos[6];
        String idCard = userInfos[7];
        String registerTime = userInfos[8];
        //终端类型
        String userType = userInfos[9];

        //插入年代数据
        String yearBaseType = DateUtils.getYearBaseByAge(age);
        String tableName = "userflaginfo";
        String rowKey = "userid";
        String familyName = "baseinfo";
        //年代
        String column = "yearbase";
        HBaseUtils.putData(tableName,rowKey,familyName,column,yearBaseType);
        YearBaseEntity yearBase = new YearBaseEntity();
        String groupField = "yearbase=="+yearBaseType;
        yearBase.setYearType(yearBaseType);
        yearBase.setCount(1L);
        yearBase.setGroupField(groupField);
        return yearBase;
    }
}
