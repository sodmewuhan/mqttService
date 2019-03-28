package com.datasensorn.mqttservice.service.impl;

import com.datasensorn.mqttservice.conf.InfluxDBSettingConfig;
import com.datasensorn.mqttservice.influxdb.InfluxDBUtil;
import com.datasensorn.mqttservice.model.Request.ChartRequest;
import com.datasensorn.mqttservice.model.biz.AxisData;
import com.datasensorn.mqttservice.model.biz.ChartSerial;
import com.datasensorn.mqttservice.service.ChartService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ChartServiceImpl implements ChartService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ChartServiceImpl.class);

    @Autowired
    private InfluxDBUtil influxDBUtil;

    @Autowired
    InfluxDBSettingConfig influxDBSettingConfig;

    private static final String DEVICE_ID = "0";

    private static final Integer TEN = 10;
    @Override
    public List<AxisData> getChartSerial(ChartRequest chartRequest) {
        // 建立数据库的实例
        InfluxDB influxDB = influxDBUtil.getInfluxDB();

        StringBuffer command = new StringBuffer();
        Long queryDate = getQueryDate(chartRequest.getDays());  // 查询时间前面推行多长时间
        command.append("SELECT * FROM fish WHERE deviceId = ").append("\'")
                .append(chartRequest.getDeviceId()).append("\'")
                .append(" and boxid = ").append("\'").append(chartRequest.getBoxId()).append("\'")
                .append(" and time > now() - ")
                .append(queryDate)
                .append("m")
                .append(" order by time ");

        ChartSerial chartSerial = new ChartSerial();
        Query query = new Query(command.toString(),influxDBSettingConfig.getDatabase());

        LOGGER.info("the database is " + query.getDatabase());

        List<AxisData> datas = Lists.newArrayList();
//        datas.setTitle("溶氧量");

        QueryResult results = influxDB.query(query);
        if (results != null && results.getResults()!=null && !results.getResults().isEmpty()) {
            //结果组装
            List<QueryResult.Result> resultList = results.getResults();
            QueryResult.Result result = resultList.get(0);
            if (result != null && result.getSeries()!=null) {
                List<List<Object>> values = result.getSeries().get(0).getValues();
                try {
                    for (int i=0; i < values.size(); i++) {
                        AxisData data = new AxisData();
                        List<Object> objects = values.get(i);
                        //设置时间
                        String date = objects.get(0)==null ? "" : objects.get(0).toString();
                        if (!StringUtils.isEmpty(date)) {
                            DateTime time = new DateTime(date);
                            data.setXAxis(String.valueOf(time.getMillis()));
                        } else {
                            continue;
                        }

                        //设置值
                        String value = objects.get(4) == null ? "" : objects.get(4).toString();
                        if (!StringUtils.isEmpty(value)) {
                            //TODO 值要除以1
//                            datas.getyAxis().add(Float.valueOf(value) / TEN);
                            data.setYAxis(String.valueOf((Float.valueOf(value)/ TEN)));
                        }
                        datas.add(data);
                    }
                } catch (Exception e) {
                    LOGGER.error("parse data error ",e.getCause());
                }
            }
        }


        return datas;
    }

    /**
     * 得到查询时间，用UTC时间查询，保持和inflxdb同步的时间
     */
    private Long getQueryDate(int day) {
        Long gap = 0L;

        LocalDateTime dt = LocalDateTime.now();
        // 由于influxdb采用的是UTC时间，当前时间向前推8个小时当作当前时间
        LocalDateTime end = dt.plus(-8, ChronoUnit.HOURS);
        LocalDateTime begin = end.withHour(0).withMinute(0);
        if (day == 2) {
            begin = begin.plus(-24,ChronoUnit.HOURS);
        } else if (day == 3) {
            begin = begin.plus(-48,ChronoUnit.HOURS);
        }
        gap = begin.until(end,ChronoUnit.MINUTES);
        return gap;
    }

    public static void main(String[] args) {
        Long i = 1552708463266L;
        DateTime time = new DateTime(i);
        System.out.println(time);
    }
}

