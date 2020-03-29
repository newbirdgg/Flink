package com.lyz.reduce;

import com.lyz.entity.ProdigalEntity;
import org.apache.flink.api.common.functions.GroupReduceFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liyuzhan
 * @classDesp： 败家指数reduce
 * @Date: 2020/3/21 19:52
 * @Email: 1031759184@qq.com
 */
public class ProdigalReduce implements ReduceFunction<ProdigalEntity> {

    @Override
    public ProdigalEntity reduce(ProdigalEntity prodigalEntity, ProdigalEntity t1) throws Exception {
        String userId = prodigalEntity.getUserId();
        List<ProdigalEntity> prodigalEntities1 = prodigalEntity.getList();
        List<ProdigalEntity> prodigalEntities2 = t1.getList();
        List<ProdigalEntity> finalList = new ArrayList<>();
        finalList.addAll(prodigalEntities1);
        finalList.addAll(prodigalEntities2);
        ProdigalEntity finalProdigal = new ProdigalEntity();
        finalProdigal.setUserId(userId);
        finalProdigal.setList(finalList);
        return finalProdigal;
    }
}
