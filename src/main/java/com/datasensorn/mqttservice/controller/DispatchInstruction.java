package com.datasensorn.mqttservice.controller;

import com.datasensorn.mqttservice.Utils.ResultGenerator;
import com.datasensorn.mqttservice.controller.model.InstructionObject;
import com.datasensorn.mqttservice.model.Result;
import com.datasensorn.mqttservice.service.BoxInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 向盒子下发指令
 */
@RestController
@RequestMapping("/api/dispatch")
public class DispatchInstruction {

    private final static Logger LOGGER = LoggerFactory.getLogger(DispatchInstruction.class);

    @Autowired
    private BoxInfoService boxInfoService;

    @RequestMapping(value = "/instruction", method = RequestMethod.POST)
    public Result instruction(@RequestBody InstructionObject instructionObject) {

        ResultGenerator resultGenerator = new ResultGenerator();

        try {
            boxInfoService.publishMessage(instructionObject);
            return resultGenerator.genSuccessResult();
        } catch (Exception e) {
            return resultGenerator.genFailResult(e.getMessage());
        }
    }

}
