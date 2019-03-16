package com.datasensorn.mqttservice.influxdb;

import com.datasensorn.mqttservice.Utils.Constant;
import com.datasensorn.mqttservice.conf.InfluxDBSettingConfig;
import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class InfluxDBUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(InfluxDBUtil.class);

    private InfluxDB influxDB = null;

    @Autowired
    InfluxDBSettingConfig influxDBSettingConfig;

    @PostConstruct
    public void InfluxDBCreate() {
        influxDB = InfluxDBFactory.connect(influxDBSettingConfig.getInfluxdbURL(),
                influxDBSettingConfig.getUsername(),influxDBSettingConfig.getPassword());
        influxDB.setDatabase(influxDBSettingConfig.getDatabase());
        // Flush every 2000 Points, at least every 100ms
        influxDB.enableBatch(BatchOptions.DEFAULTS.actions(2000).flushDuration(100));
        Pong pong = influxDB.ping();
        if (pong != null) {
            LOGGER.info("******************the inflxdb haved connected.");
        }
    }

    public InfluxDB getInfluxDB() {
        return influxDB;
    }


    /**
     * 查询数据
     *
     * @param command
     * @return QueryResult
     */
    public QueryResult query(String command) {
        return influxDB.query(new Query(command, influxDBSettingConfig.getDatabase()));
    }

    /**
     * 插入数据
     *
     */
    public void insert(String measurement, Map<String, String> tags, Map<String, Object> fields) {
        Point.Builder builder = Point.measurement(measurement);
        builder.tag(tags);
        builder.fields(fields);
        // 写入数据库
        influxDB.write(builder.build());
        LOGGER.info("save to influxDB success,the deviceid is " + fields.get(Constant.INFLUXDB_COL_DEVICEID));
    }
}
