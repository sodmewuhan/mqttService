package com.datasensorn.mqttservice.service.impl;

import com.datasensorn.mqttservice.model.biz.User;
import com.datasensorn.mqttservice.model.biz.UserMapper;
import com.datasensorn.mqttservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public void registerUser(User user) {
//        LOGGER.info("the user name is " + user.getAccountName());
        userMapper.registerUser(user);
    }

    @Override
    public boolean logon(String phone, String password) {
        return userMapper.logon(phone,password) == 0 ? false:true;
    }
}
