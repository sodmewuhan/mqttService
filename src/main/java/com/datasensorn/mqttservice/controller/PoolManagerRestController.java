package com.datasensorn.mqttservice.controller;

import com.datasensorn.mqttservice.model.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 池塘管理
 */
@RestController
@RequestMapping(value="/app/poolManager")
public class PoolManagerRestController {

    /**
     * 添加池塘
     * @return
     */
    public Result addPool() {
        return null;
    }

    /**
     * 删除池塘
     * @return
     */
    public Result delPool() {
        return null;
    }


}
