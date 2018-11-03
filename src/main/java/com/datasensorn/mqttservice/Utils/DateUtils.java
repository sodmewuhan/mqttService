package com.datasensorn.mqttservice.Utils;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtils {

    public static Date getUTCTime() {
        Calendar cal = Calendar.getInstance();
        //获得时区和 GMT-0 的时间差,偏移量
        int offset = cal.get(Calendar.ZONE_OFFSET);
        //获得夏令时  时差
        int dstoff = cal.get(Calendar.DST_OFFSET);
        cal.add(Calendar.MILLISECOND, -(offset + dstoff));
        return cal.getTime();
    }

    public static Date getConvertUTCToLocal() {
        Date date = getUTCTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY,8);
        return cal.getTime();
    }
}
