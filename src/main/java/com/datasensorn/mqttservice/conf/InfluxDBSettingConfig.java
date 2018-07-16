package com.datasensorn.mqttservice.conf;

import com.datasensorn.mqttservice.model.InfluxDBSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * influxdb 的配置信息
 */
@Configuration
public class InfluxDBSettingConfig {

    @Value("${spring.influxdb.url}")
    private String influxdbURL;

    @Value("${spring.influxdb.username}")
    private String username;

    @Value("${spring.influxdb.password}")
    private String password;

    @Value("${spring.influxdb.database}")
    private String database;

    @Value("${spring.influxdb.retentionpolicy}")
    private String retentionpolicy;

    @Value("${spring.influxdb.connecttimeout}")
    private String connecttimeout;

    @Value("${spring.influxdb.readtimeout}")
    private String readtimeout;

    @Value("${spring.influxdb.timeout}")
    private String timeout;

    @Bean
    public InfluxDBSettings influxDBSettings() {
        return new InfluxDBSettings( influxdbURL, username, password,database,
                retentionpolicy,connecttimeout,readtimeout,timeout);
    }
}
