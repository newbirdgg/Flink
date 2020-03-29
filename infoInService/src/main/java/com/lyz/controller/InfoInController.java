package com.lyz.controller;

import com.alibaba.fastjson.JSONObject;
import com.lyz.entity.ResultMessage;
import com.lyz.log.AttentionProductLog;
import com.lyz.log.CollectProductLog;
import com.lyz.log.ScanProductLog;
import com.lyz.log.ShopCarProductLog;
import com.lyz.utils.ReadProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Author: liyuzhan
 * @classDesp：
 * @Date: 2020/3/22 12:12
 * @Email: 1031759184@qq.com
 */
@RestController
@RequestMapping("infoLog")
public class InfoInController {

    private final String attentionProductLogTopic = ReadProperties.getKey("attentionProductLog");
    private final String shopCarProductLogTopic = ReadProperties.getKey("shopCarProductLog");
    private final String collectProductLogTopic = ReadProperties.getKey("collectProductLog");
    private final String scanProductLogTopic = ReadProperties.getKey("scanProductLog");


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping(value = "attentionProductLog", method = RequestMethod.GET)
    public String helloWorld(HttpServletRequest req) {
        String ip = req.getRemoteAddr();
        return ResultMessage.success("hello:" + ip);
    }

    @RequestMapping(value = "receiveLog", method = RequestMethod.POST)
    public String receive(String receiveLog, HttpServletRequest req) {
        if (StringUtils.isBlank(receiveLog)) {
            return ResultMessage.failure("receive为空");
        }
        String[] receiveArr = receiveLog.split(":", 2);
        String className = receiveArr[0];
        String data = receiveArr[1];
        String resultMessage = "";

        if ("AttentionProductLog".equals(className)) {
            AttentionProductLog attentionProductLog = JSONObject.parseObject(data, AttentionProductLog.class);
            resultMessage = JSONObject.toJSONString(attentionProductLog);
            kafkaTemplate.send(attentionProductLogTopic,resultMessage+"##1##"+System.currentTimeMillis());
        } else if ("ShopCarProductLog".equals(className)) {
            ShopCarProductLog shopCarProductLog = JSONObject.parseObject(data, ShopCarProductLog.class);
            resultMessage = JSONObject.toJSONString(shopCarProductLog);
            kafkaTemplate.send(shopCarProductLogTopic,resultMessage+"##2##"+System.currentTimeMillis());
        } else if ("CollectProductLog".equals(className)) {
            CollectProductLog collectProductLog = JSONObject.parseObject(data, CollectProductLog.class);
            resultMessage = JSONObject.toJSONString(collectProductLog);
            kafkaTemplate.send(collectProductLogTopic,resultMessage+"##3##"+System.currentTimeMillis());
        } else if ("ScanProductLog".equals(className)) {
            ScanProductLog scanProductLog = JSONObject.parseObject(data, ScanProductLog.class);
            resultMessage = JSONObject.toJSONString(scanProductLog);
            kafkaTemplate.send(scanProductLogTopic,resultMessage+"##4##"+System.currentTimeMillis());
        }
        return ResultMessage.success(resultMessage);
    }
}
