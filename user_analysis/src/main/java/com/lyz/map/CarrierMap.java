package com.lyz.map;

import com.lyz.entity.CarrierEntity;
import com.lyz.util.CarrierUtils;
import com.lyz.util.DateUtils;
import com.lyz.util.HBaseUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;

/**
 *  @Author: liyuzhan
 *  @classDesp： 手机运营商map
 *  @Date: 2020/1/29 10:47
 *  @Email: 1031759184@qq.com
 */
public class CarrierMap implements MapFunction<String, CarrierEntity> {
    @Override
    public CarrierEntity map(String param) throws Exception {
        if (StringUtils.isBlank(param)) {
            return null;
        }
        String[] userInfos = param.split(",");
        String phoneNum = userInfos[4];
        String carrierType = CarrierUtils.getCarrierByPhone(phoneNum);
        String tableName = "userflaginfo";
        String rowKey = "userid";
        String familyName = "baseinfo";
        //年代
        String column = "carrierbase";
        HBaseUtils.putData(tableName,rowKey,familyName,column,carrierType);
        CarrierEntity carrier = new CarrierEntity();
        String groupField = "carrierbase=="+carrierType;
        carrier.setCarrier(carrierType);
        carrier.setCount(1L);
        carrier.setGroupField(groupField);
        return carrier;
    }
}
