package com.lyz.map;

import com.lyz.entity.ProdigalEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liyuzhan
 * @classDesp： 败家指数map
 * @Date: 2020/2/13 21:33
 * @Email: 1031759184@qq.com
 */
public class ProdigalMap implements MapFunction<String, ProdigalEntity> {
    @Override
    public ProdigalEntity map(String param) throws Exception {
        if (StringUtils.isBlank(param)) {
            return null;
        }
        String[] orderInfos = param.split(",");
        String id = orderInfos[0];
        String userId = orderInfos[1];
        String productId = orderInfos[2];
        String productTypeId = orderInfos[3];
        String createTime = orderInfos[4];
        String payTime = orderInfos[5];
        String payStatus = orderInfos[6];
        String payType = orderInfos[7];
        String amount = orderInfos[8];
        String couponAmount = orderInfos[9];
        String totalAmount = orderInfos[10];
        String refundAmount = orderInfos[11];
        String num = orderInfos[12];

        ProdigalEntity prodigal = new ProdigalEntity();
        prodigal.setUserId(userId);
        prodigal.setCreateTime(createTime);
        prodigal.setAmount(amount);
        prodigal.setPayType(payType);
        prodigal.setPayTime(payTime);
        prodigal.setCouponAmount(couponAmount);
        prodigal.setPayStatus(payStatus);
        prodigal.setTotalAmount(totalAmount);
        prodigal.setRefundAmount(refundAmount);
        String groupField = "prodigal == " +userId;
        prodigal.setGroupField(groupField);
        List<ProdigalEntity> list = new ArrayList<>();
        list.add(prodigal);
        return prodigal;
    }
}
