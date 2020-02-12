package com.lyz.reduce;

import com.lyz.entity.YearBaseEntity;
import org.apache.flink.api.common.functions.ReduceFunction;

/**
 *  @Author: liyuzhan
 *  @classDesp： 年代标签reduce处理
 *  @Date: 2019/12/31 15:13
 *  @Email: 1031759184@qq.com
 */
public class YearBaseReduce implements ReduceFunction<YearBaseEntity> {

    @Override
    public YearBaseEntity reduce(YearBaseEntity yearBaseEntity, YearBaseEntity t1) throws Exception {
        String yearType = yearBaseEntity.getYearType();
        Long count1 = yearBaseEntity.getCount();
        Long count2 = t1.getCount();

        YearBaseEntity  finalYearBase = new YearBaseEntity();
        finalYearBase.setYearType(yearType);
        finalYearBase.setCount(count1+count2);
        return finalYearBase;
    }
}
