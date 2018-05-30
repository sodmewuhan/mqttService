package com.datasensorn.mqttservice.Utils;

/**
 * 返回的结果编码
 */
public enum ResultCode {

    SUCCESS(200),//成功
    FAIL(400),
    UNAUTHORIZED(401),//未认证（签名错误）
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500); //服务器内部错误

    public int code;

    ResultCode(int code) {
        this.code = code;
    }
}
