package com.lyz.entity;

import lombok.Data;

/**
 *  @Author: liyuzhan
 *  @classDesp： 邮箱运营商实体类
 *  @Date: 2020/2/13 19:41
 *  @Email: 1031759184@qq.com
 */
@Data
public class EmailEntity {
    /**
     * 邮箱运营商
     */
    private String emailType;
    /**
     * 数量
     */
    private Long count;
    /**
     * 分组
     */
    private String groupField;
}
