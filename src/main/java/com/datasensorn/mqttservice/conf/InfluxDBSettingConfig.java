package com.datasensorn.mqttservice.conf;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * influxdb 的配置信息
 */
@Configuration
@Getter
public class InfluxDBSettingConfig {

    @Value("${spring.influxdb.url}")
    private String influxdbURL;

    @Value("${spring.influxdb.username}")
    private String username;

    @Value("${spring.influxdb.password}")
    private String password;

    @Value("${spring.influxdb.database}")
    private String database;

    @Value("${spring.influxdb.connecttimeout}")
    private String connecttimeout;

    @Value("${spring.influxdb.readtimeout}")
    private String readtimeout;

    @Value("${spring.influxdb.timeout}")
    private String timeout;

    @Value("${spring.influxdb.retentionpolicy}")
    private String retentionpolicy;

    @Value("${spring.influxdb.duration}")
    private String duration;

    @Value("${spring.influxdb.replicationNum}")
    private String replicationNum;
}
