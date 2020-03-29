package com.lyz.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @Author: liyuzhan
 * @classDesp： 返回类
 * @Date: 2020/3/22 12:16
 * @Email: 1031759184@qq.com
 */
@Data
public class ResultMessage {
    /**
     * 状态 fail or success
     */
    private String status;
    /**
     * 消息内容
     */
    private String message;

    public ResultMessage() {

    }

    public ResultMessage(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public static String success(String message){
        return JSONObject.toJSONString(new ResultMessage("success",message));
    }

    public static String failure(String message){
        return JSONObject.toJSONString(new ResultMessage("failure",message));
    }


}
