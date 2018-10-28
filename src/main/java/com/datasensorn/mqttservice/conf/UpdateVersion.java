package com.datasensorn.mqttservice.conf;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class UpdateVersion {

    @Value( "${application.storeFilePath}" )
    private String storeFilePath;
}
