package com.datasensorn.mqttservice.service.impl;

import com.datasensorn.mqttservice.influxdb.InfluxDBUtil;
import com.datasensorn.mqttservice.model.InfluxDBSettings;
import com.datasensorn.mqttservice.model.biz.PoolCurrentStatus;
import com.datasensorn.mqttservice.service.PoolService;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoolServiceImpl implements PoolService {

    private final static Logger LOGGER = LoggerFactory.getLogger(PoolServiceImpl.class);

    @Autowired
    private InfluxDBUtil influxDBUtil;

    @Autowired
    InfluxDBSettings influxDBSettings;

    @Override
    public PoolCurrentStatus getCurrentStatus(String boxid) {
        LOGGER.info("called the function getCurrentStatus ,the boxid is " + boxid);
        PoolCurrentStatus poolCurrentStatus = new PoolCurrentStatus();

        // 建立数据库的实例
        InfluxDB influxDB = influxDBUtil.getInfluxDB();
        StringBuffer command = new StringBuffer();

        command.append("SELECT * FROM fish WHERE boxid = ")
                .append("\'").append(boxid).append("\'")
                .append(" and (")
                .append(" deviceId =0 or  deviceId = 1 or deviceId = 2 or deviceId = 3" )
                .append(" )")
                .append(" order by ")
                .append(" time")
                .append(" DESC")
                .append(" limit 1");
        Query query = new Query(command.toString(),influxDBSettings.getDatabase());
        QueryResult results = influxDB.query(query);
        if (results != null && results.getResults()!=null && !results.getResults().isEmpty()) {
            //结果组装
            List<QueryResult.Result> resultList = results.getResults();
            QueryResult.Result result = resultList.get(0);
            if (result != null && result.getSeries()!=null) {
                List<List<Object>> values = result.getSeries().get(0).getValues();
                try {
                    for (int i = 0; i < values.size(); i++) {
                        List<Object> objects = values.get(i);
                        Float oxygen = Float.valueOf(objects.get(3).toString());
                        poolCurrentStatus.setOxygen(oxygen/10);
                    }
                } catch (Exception e) {
                    LOGGER.error("parse data error ",e.getCause());
                }
            }
        }
        return poolCurrentStatus;
    }
}
