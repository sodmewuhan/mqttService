package com.datasensorn.mqttservice.Utils;

import com.datasensorn.mqttservice.model.Result;

/**
 * 返回结果生成
 */
public class ResultGenerator {

    private final static String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public Result genSuccessResult() {
        Result ret = new Result();
        ret.setCode(ResultCode.SUCCESS.code);
        ret.setMessage(DEFAULT_SUCCESS_MESSAGE);

        return ret;
    }

    public Result genSuccessResult(Object data) {
        Result ret = new Result();
        ret.setCode(ResultCode.SUCCESS.code);
        ret.setMessage(DEFAULT_SUCCESS_MESSAGE);
        ret.setData(data);

        return ret;
    }

    public Result genFailResult(String message) {
        Result ret = new Result();
        ret.setCode(ResultCode.FAIL.code);

        return ret;
    }
}
