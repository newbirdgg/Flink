package com.lyz.entity;

import lombok.Data;

/**
 *  @Author: liyuzhan
 *  @classDesp： 品牌偏好实体类
 *  @Date: 2020/3/25 16:21
 *  @Email: 1031759184@qq.com
 */
@Data
public class BrandLikeEntity {
    /**
     * 品牌
     */
    private String brand;
    /**
     * 数量
     */
    private long count;
}
