package com.lyz.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author: liyuzhan
 * @classDesp： 败家指数实体类
 * @Date: 2020/2/13 21:34
 * @Email: 1031759184@qq.com
 */
@Data
public class ProdigalEntity {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 败家指数得分
     * 区段：0-20、20-50、50-70、70-80、80-90、90-100
     */
    private String prodigalScore;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 金额
     */
    private String amount;
    /**
     * 支付类型
     */
    private String payType;
    /**
     * 支付状态 0、未支付 1、已支付 2、已退款
     */
    private String payStatus;
    /**
     * 支付时间
     */
    private String payTime;
    /**
     * 优惠金额
     */
    private String couponAmount;
    /**
     * 总金额
     */
    private String totalAmount;
    /**
     * 退款金额
     */
    private String refundAmount;

    /**
     * 数量
     */
    private String count;
    /**
     * 分组
     */
    private String groupField;
    /**
     * 败家list
     */
    private List<ProdigalEntity> list;
}
