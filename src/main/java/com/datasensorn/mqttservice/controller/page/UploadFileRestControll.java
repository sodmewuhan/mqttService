package com.datasensorn.mqttservice.controller.page;

import com.datasensorn.mqttservice.Utils.ResultGenerator;
import com.datasensorn.mqttservice.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping(value="/page/appVersion")
public class UploadFileRestControll {

    private final static Logger LOGGER = LoggerFactory.getLogger(UploadFileRestControll.class);
    /**
     * 上传业务
     * @return
     */
    @RequestMapping(value = "/uploadFile")
    public String uploadFile() {
        return "upload";
    }
}
