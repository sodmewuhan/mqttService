package com.datasensorn.mqttservice.controller;

//import com.datasensorn.mqttservice.handle.SendDataHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 向盒子下发指令
 */
@RestController
@RequestMapping("/app/dispatch")
public class DispatchInstruction {

    private final static Logger LOGGER = LoggerFactory.getLogger(DispatchInstruction.class);

//    @Autowired
//    SendDataHandler sendDataHandler;//下发指令消息服务


}
