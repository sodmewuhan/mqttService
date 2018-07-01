package com.datasensorn.mqttservice.service.impl;

import com.datasensorn.mqttservice.Utils.Constant;
import com.datasensorn.mqttservice.model.biz.ChartSerial;
import com.datasensorn.mqttservice.service.ChartSarvice;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.influxdb.InfluxDBTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChartSarviceImpl implements ChartSarvice {

    // 查询influxdb数据库的数据
    @Autowired
    private InfluxDBTemplate<Point> influxDBTemplate;

    @Override
    public ChartSerial getChartSerial(String boxId,String deviceId, Date begin, Date end) {
        StringBuffer command = new StringBuffer();
        command.append("SELECT * FROM \"fish\" WHERE deviceId = ")
                .append(deviceId)
                .append(" and time >= ");


        ChartSerial chartSerial = new ChartSerial();
        Query query = new Query(command.toString(),Constant.INFLUXDB_NAME);
        //query.

        QueryResult results = influxDBTemplate.query(query);
        if (results != null && results.getResults()!=null && !results.getResults().isEmpty()) {
            //结果组装
            List<QueryResult.Result> resultList = results.getResults();
            for (int i=0; i< resultList.size(); i++) {
                QueryResult.Result result = resultList.get(i);
                result.getSeries().get(0).getTags().get(Constant.INFLUXDB_COL_DEVICEID);
            }
        }
        return chartSerial;
    }
}
