package com.lyz.entity;

import lombok.Data;

/**
 * @Author: liyuzhan
 * @classDesp： 手机运营商实体类
 * @Date: 2020/1/29 10:46
 * @Email: 1031759184@qq.com
 */
@Data
public class CarrierEntity {
    /**
     * 运营商
     */
    private String carrier;
    /**
     * 数量
     */
    private Long count;
    /**
     * 分组
     */
    private String groupField;
}
