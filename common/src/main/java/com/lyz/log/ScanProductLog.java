package com.lyz.log;

import lombok.Data;

import java.io.Serializable;

/**
 *  @Author: liyuzhan
 *  @classDesp： 浏览商品日志
 *  @Date: 2020/3/21 21:23
 *  @Email: 1031759184@qq.com
 */
@Data
public class ScanProductLog implements Serializable{
    /**
     * 商品id
     */
    private int productId;
    /**
     * 商品类别id
     */
    private int productTypeId;
    /**
     * 浏览时间
     */
    private String scanTime;
    /**
     * 停留时间
     */
    private String stayTime;
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
