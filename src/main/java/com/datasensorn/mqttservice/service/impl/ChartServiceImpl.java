package com.datasensorn.mqttservice.service.impl;

import com.datasensorn.mqttservice.conf.InfluxDBSettingConfig;
import com.datasensorn.mqttservice.influxdb.InfluxDBUtil;
import com.datasensorn.mqttservice.model.Request.ChartRequest;
import com.datasensorn.mqttservice.model.biz.AxisDatas;
import com.datasensorn.mqttservice.model.biz.ChartSerial;
import com.datasensorn.mqttservice.service.ChartService;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Service
public class ChartServiceImpl implements ChartService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ChartServiceImpl.class);

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    @Autowired
    private InfluxDBUtil influxDBUtil;

    InfluxDBSettingConfig influxDBSettingConfig;

    private static final String DEVICE_ID = "0";

    private static final Integer TEN = 10;
    @Override
    public AxisDatas getChartSerial(ChartRequest chartRequest) {
        // 建立数据库的实例
        InfluxDB influxDB = influxDBUtil.getInfluxDB();

        StringBuffer command = new StringBuffer();
        Long queryDate = getQueryDate(chartRequest.getDays());  // 查询时间前面推行多长时间
        command.append("SELECT * FROM fish WHERE deviceId = ")
                .append(chartRequest.getDeviceId())
                .append(" and boxid = ").append("\'").append(chartRequest.getBoxId()).append("\'")
                .append(" and time > now() - ")
                .append(queryDate)
                .append("ms");

        ChartSerial chartSerial = new ChartSerial();
        Query query = new Query(command.toString(),influxDBSettingConfig.getDatabase());

        LOGGER.info("the database is " + query.getDatabase());

        AxisDatas datas = new AxisDatas();
        datas.setTitle("溶氧量");

        QueryResult results = influxDB.query(query);
        if (results != null && results.getResults()!=null && !results.getResults().isEmpty()) {
            //结果组装
            List<QueryResult.Result> resultList = results.getResults();
            QueryResult.Result result = resultList.get(0);
            if (result != null && result.getSeries()!=null) {
                List<List<Object>> values = result.getSeries().get(0).getValues();
                try {
                    for (int i=0; i < values.size(); i++) {
                        List<Object> objects = values.get(i);
                        //设置时间
                        String date = objects.get(0)==null ? "" : objects.get(0).toString();
                        if (!org.springframework.util.StringUtils.isEmpty(date)) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(sdf.parse(date));
                            calendar.add(Calendar.HOUR_OF_DAY,8);
                            String title = calendar.get(Calendar.DAY_OF_MONTH) + "日" +
                                    calendar.get(Calendar.HOUR_OF_DAY)  + "点" + calendar.get(Calendar.MINUTE) + "分" ;
                            datas.getxAxis().add(title);
                        } else {
                            continue;
                        }

                        //设置值
                        String value = objects.get(3) == null ? "" : objects.get(3).toString();
                        if (!org.springframework.util.StringUtils.isEmpty(value)) {
                            //TODO 值要除以10
                            datas.getyAxis().add(Float.valueOf(value) / TEN);
                        }
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

        Calendar end = Calendar.getInstance();  // 当前时间
        Calendar begin = Calendar.getInstance();
        if (day == 1) {
            begin.set(Calendar.HOUR_OF_DAY,0);
            begin.set(Calendar.MINUTE,0);
        } else if (day == 2) {
            // 查询昨天的记录
            begin.add(Calendar.DAY_OF_MONTH,-1);
            begin.set(Calendar.HOUR_OF_DAY,0);
            begin.set(Calendar.MINUTE,0);
        } else if (day == 3) {
            // 查询前天的记录
            begin.add(Calendar.DAY_OF_MONTH,-2);
            begin.set(Calendar.HOUR_OF_DAY,0);
            begin.set(Calendar.MINUTE,0);
        }
        return (end.getTimeInMillis() - begin.getTimeInMillis());
    }
}

