package com.datasensorn.mqttservice.controller;

import com.datasensorn.mqttservice.Utils.ResultGenerator;
import com.datasensorn.mqttservice.model.Result;
import com.datasensorn.mqttservice.model.biz.User;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理控制类
 */
@RestController
@RequestMapping(value="/app/user")
public class UserRestController {

    /**
     * 新用户注册
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result register(@RequestBody User user) {

        ResultGenerator resultGenerator = new ResultGenerator();
        return resultGenerator.genSuccessResult();
    }
}
