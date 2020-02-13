package com.lyz.entity;

import lombok.Data;

/**
 *  @Author: liyuzhan
 *  @classDesp： 败家指数实体类
 *  @Date: 2020/2/13 21:34
 *  @Email: 1031759184@qq.com
 */
@Data
public class ProdigalEntity {
    /**
     * 败家指数得分
     * 区段：0-20、20-50、50-70、70-80、80-90、90-100
     */
    private String prodigalScore;
    /**
     * 数量
     */
    private String count;
    /**
     * 分组
     */
    private String groupField;
}
