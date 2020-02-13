package com.lyz.reduce;

import com.lyz.entity.CarrierEntity;
import org.apache.flink.api.common.functions.ReduceFunction;

/**
 *  @Author: liyuzhan
 *  @classDesp： 手机运营商reduce
 *  @Date: 2020/1/29 11:11
 *  @Email: 1031759184@qq.com
 */
public class CarrierReduce implements ReduceFunction<CarrierEntity>{

    @Override
    public CarrierEntity reduce(CarrierEntity carrierEntity, CarrierEntity t1) throws Exception {
        String carrier = carrierEntity.getCarrier();
        Long count1 = carrierEntity.getCount();
        Long count2 = t1.getCount();

        CarrierEntity result = new CarrierEntity();
        result.setCarrier(carrier);
        result.setCount(count1+count2);
        return result;
    }
}
