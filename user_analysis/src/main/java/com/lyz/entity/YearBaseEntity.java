package com.lyz.entity;

import lombok.Data;

/**
 *  @Author: liyuzhan
 *  @classDesp： 年代标签实体类
 *  @Date: 2019/12/31 9:45
 *  @Email: 1031759184@qq.com
 */
@Data
public class YearBaseEntity {
    /**
     * 年代类型
     */
    private String yearType;
    /**
     * 数量
     */
    private Long count;
    /**
     * 分组
     */
    private String groupField;

}
