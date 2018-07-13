package com.datasensorn.mqttservice.controller;

import com.datasensorn.mqttservice.Utils.ResultGenerator;
import com.datasensorn.mqttservice.model.Result;
import com.datasensorn.mqttservice.model.biz.User;
import com.datasensorn.mqttservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理控制类
 */
@RestController
@RequestMapping(value="/api/user")
public class UserRestController {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    private UserService userService;

    /**
     * 新用户注册
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result register(@RequestBody User user) {

        userService.registerUser(user);

        ResultGenerator resultGenerator = new ResultGenerator();
        return resultGenerator.genSuccessResult();
    }

    /**
     * 用户登录
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(String phone,String password) {

        boolean retn = userService.logon(phone,password);
        LOGGER.info(String.valueOf(retn));
        ResultGenerator resultGenerator = new ResultGenerator();

        return resultGenerator.genSuccessResult(retn);

    }
}
