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

import java.util.Date;
import java.util.List;

@Service
public class ChartSarviceImpl implements ChartSarvice {

    private final static Logger LOGGER = LoggerFactory.getLogger(ChartSarviceImpl.class);

    // 查询influxdb数据库的数据
    @Autowired
    private InfluxDBTemplate<Point> influxDBTemplate;


    @Override
    public ChartSerial getChartSerial(String boxId,String deviceId, Date begin, Date end) {
        StringBuffer command = new StringBuffer();
        command.append("SELECT * FROM fish WHERE deviceId = ")
                .append(deviceId)
                .append(" and time > now() - ")
                .append(String.valueOf(24*60))
                .append("m");


        ChartSerial chartSerial = new ChartSerial();
        Query query = new Query(command.toString(),Constant.INFLUXDB_NAME);

        LOGGER.info("the database is " + query.getDatabase());
        //query.

        QueryResult results = influxDBTemplate.query(query);
        if (results != null && results.getResults()!=null && !results.getResults().isEmpty()) {

            ChartSerial chartSerial1 = new ChartSerial();

            //结果组装
            List<QueryResult.Result> resultList = results.getResults();
            QueryResult.Result result = resultList.get(0);
            List<List<Object>> values = result.getSeries().get(0).getValues();
            for (int i=0; i < values.size(); i++) {
                List<Object> objects = values.get(i);
                ChartPoint chartPoint = new ChartPoint();
                //设置时间
                String date = objects.get(0)==null ? "" : objects.get(0).toString();
                if (!StringUtils.isEmpty(date)) {
                    chartPoint.setTime(new Date((date)));
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
                chartSerial1.getPoints().add(chartPoint);

                LOGGER.info("开始获取数据");
            }
        }
        return chartSerial;
    }
}
