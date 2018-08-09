package com.datasensorn.mqttservice.controller;

import com.datasensorn.mqttservice.Utils.ResultGenerator;
import com.datasensorn.mqttservice.model.Result;
import com.datasensorn.mqttservice.model.biz.PoolCurrentStatus;
import com.datasensorn.mqttservice.service.PoolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 池塘管理
 */
@RestController
@RequestMapping(value="/api/pool/")
public class PoolManagerRestController {

    private final static Logger LOGGER = LoggerFactory.getLogger(PoolManagerRestController.class);

    @Autowired
    PoolService poolService;

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

    /**
     * 池塘的当前状态
     * @return
     */
    @PostMapping("status/{boxid}")
    public Result getCurrentStatus(@PathVariable("boxid") String boxid) {

        LOGGER.info("Get the boxid is " + boxid);

        ResultGenerator resultGenerator = new ResultGenerator();
        PoolCurrentStatus poolCurrentStatus = poolService.getCurrentStatus(boxid);

        return resultGenerator.genSuccessResult(poolCurrentStatus);
    }
}
