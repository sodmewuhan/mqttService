package com.datasensorn.mqttservice.service;

import com.datasensorn.mqttservice.model.biz.UserInfo;

/**
 * 用户服务
 */
public interface UserService {

    /**
     * 用户登录
     * @param accountName
     * @param password    对password解密
     */
    public boolean logon(String accountName ,String password);

    /**
     * 根据用户注册的电话，得到用户信息
     * @return
     */
    public UserInfo findUserByPhone(String phone);
}
