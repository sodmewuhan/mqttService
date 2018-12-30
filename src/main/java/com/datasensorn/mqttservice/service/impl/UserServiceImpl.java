package com.datasensorn.mqttservice.service.impl;

import com.datasensorn.mqttservice.controller.model.UserInfoDTO;
import com.datasensorn.mqttservice.model.biz.UserInfo;
import com.datasensorn.mqttservice.model.biz.mapper.UserInfoMapper;
import com.datasensorn.mqttservice.service.UserService;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public boolean logon(String userName, String password) {
        Assert.notNull(userName, "parameter userName is empty");
        Assert.notNull(password, "password phone is empty");

        LOGGER.info("begin check user login the parameter is the userName :" + userName +
                " and password :" + password);
        UserInfo user = new UserInfo();
        user.setUsername(userName);
        user.setPassword(password);
        int ret = userInfoMapper.checkUserLogin(user);
        return ret > 0 ?  true : false;
    }

    @Override
    public UserInfo findUserByUserName(String userName) {
        Assert.notNull(userName, "parameter userName is empty");
        return userInfoMapper.findUserByUserName(userName);
    }

    @Override
    public boolean updateUserInfo(UserInfoDTO userInfoDTO) throws Exception{
        Assert.notNull(userInfoDTO,"参数对象不能为空");
        Assert.notNull(userInfoDTO.getId(),"更新对象编号不能为空");

        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfo,userInfoDTO);
        userInfoMapper.updateByPrimaryKeySelective(userInfo);
        LOGGER.info("用户数据更新成功");
        return true;
    }
}
