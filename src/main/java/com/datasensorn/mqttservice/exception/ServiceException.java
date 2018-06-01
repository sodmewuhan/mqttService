package com.datasensorn.mqttservice.exception;

/**
 * 业务类抛出异常处理
 */
public class ServiceException extends Exception {

    private String code; //错误码

    private String message; //错误消息

    public ServiceException(String message, String code) {

        this.code = code;
        this.message = message;
    }

    public ServiceException(String code, String message, Throwable t) {
        super();
        this.code = code;
        this.message = message;
    }

    public ServiceException(String message) {
        super(message);
        this.message = message;
    }

    public ServiceException(String message, Throwable t) {
        super(message, t);
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ServiceException [code=" + code + ", message=" + message + "]";
    }
}
