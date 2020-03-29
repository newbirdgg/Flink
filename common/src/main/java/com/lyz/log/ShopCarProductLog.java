package com.lyz.log;

import lombok.Data;

import java.io.Serializable;

/**
 *  @Author: liyuzhan
 *  @classDesp： 购物车日志
 *  @Date: 2020/3/21 21:31
 *  @Email: 1031759184@qq.com
 */
@Data
public class ShopCarProductLog implements Serializable {
    /**
     * 商品id
     */
    private int productId;
    /**
     * 商品类别id
     */
    private int productTypeId;
    /**
     * 操作时间
     */
    private String operatorTime;
    /**
     * 操作类型 0：加入 1：删除
     */
    private int operatorType;
    /**
     * 用户id
     */
    private int userId;
    /**
     * 终端类型 0：PC 1:Android 2 :小程序
     */
    private int useType;
    /**
     * 用户ip
     */
    private String ip;
    /**
     * 品牌
     */
    private String brand;
}
