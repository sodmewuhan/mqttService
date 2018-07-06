package com.datasensorn.mqttservice.service.impl;

import com.datasensorn.mqttservice.Utils.Constant;
import com.datasensorn.mqttservice.model.biz.ChartPoint;
import com.datasensorn.mqttservice.model.biz.ChartSerial;
import com.datasensorn.mqttservice.service.ChartSarvice;
import org.apache.commons.lang3.StringUtils;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.influxdb.InfluxDBTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ChartSarviceImpl implements ChartSarvice {

    private final static Logger LOGGER = LoggerFactory.getLogger(ChartSarviceImpl.class);

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    // 查询influxdb数据库的数据
    @Autowired
    private InfluxDBTemplate<Point> influxDBTemplate;


    @Override
    public ChartSerial getChartSerial(String boxId,String deviceId, int days) {
        // 建立数据库的实例
        influxDBTemplate.createDatabase();

        StringBuffer command = new StringBuffer();
        command.append("SELECT * FROM fish WHERE deviceId = ")
                .append(deviceId)
                .append(" and boxid = ").append("\'").append(boxId).append("\'")
                .append(" and time > now() - ")
                .append(String.valueOf(days * 24*60))
                .append("m");

        ChartSerial chartSerial = new ChartSerial();
        Query query = new Query(command.toString(),Constant.INFLUXDB_NAME);

        LOGGER.info("the database is " + query.getDatabase());

        QueryResult results = influxDBTemplate.query(query);
        if (results != null && results.getResults()!=null && !results.getResults().isEmpty()) {
            //结果组装
            List<QueryResult.Result> resultList = results.getResults();
            QueryResult.Result result = resultList.get(0);
            if (result != null && result.getSeries()!=null) {
                List<List<Object>> values = result.getSeries().get(0).getValues();
                try {
                    for (int i=0; i < values.size(); i++) {
                        List<Object> objects = values.get(i);
                        ChartPoint chartPoint = new ChartPoint();
                        //设置时间
                        String date = objects.get(0)==null ? "" : objects.get(0).toString();
                        if (!StringUtils.isEmpty(date)) {
                            chartPoint.setTime(sdf.parse(date));
                        } else {
                            continue;
                        }
                        // 设置设备编号
                        String boxid = objects.get(1)==null ? "" : objects.get(1).toString();
                        if (!StringUtils.isEmpty(boxid)) {
                            chartPoint.setBoxid(boxid);
                        }
                        // 设置探头
                        String deviceid = objects.get(2)==null ? "" : objects.get(2).toString();
                        if (!StringUtils.isEmpty(deviceid)) {
                            chartPoint.setDeviceId(deviceid);
                        }
                        //设置值
                        String value = objects.get(3) == null ? "" : objects.get(3).toString();
                        if (!StringUtils.isEmpty(value)) {
                            chartPoint.setValue(Float.valueOf(value));
                        }
                        chartSerial.getPoints().add(chartPoint);
                    }
                } catch (Exception e) {
                    LOGGER.error("parse data error ",e.getCause());
                }
            }
        }
        return chartSerial;
    }
}