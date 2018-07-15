package com.datasensorn.mqttservice.influxdb;

import com.datasensorn.mqttservice.model.InfluxDBSettings;
import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InfluxDBUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(InfluxDBUtil.class);

    private InfluxDB influxDB = null;

    @Autowired
    InfluxDBSettings influxDBSettings;

    @PostConstruct
    public void InfluxDBCreate() {
        influxDB = InfluxDBFactory.connect(influxDBSettings.getInfluxdbURL(),
                influxDBSettings.getUsername(),influxDBSettings.getPassword());
        influxDB.setDatabase(influxDBSettings.getDatabase());
        // Flush every 2000 Points, at least every 100ms
        influxDB.enableBatch(BatchOptions.DEFAULTS.actions(2000).flushDuration(100));
    }

    public InfluxDB getInfluxDB() {
        return influxDB;
    }

}
