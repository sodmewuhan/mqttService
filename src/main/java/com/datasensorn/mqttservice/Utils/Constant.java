package com.datasensorn.mqttservice.Utils;

/**
 * 系统的常用变量
 */
public class Constant {

    /**
     * mqtt 的前缀
     */
    public static final String MQTT_PREFIX = "UP/";

    /**
     * influxdb的measurement名字
     */
    public static final String INFLUXDB_NAME = "fish";

    /**
     * influxdb 列名 ： boxid
     */
    public static final String INFLUXDB_COL_BOXID = "boxid";

    /**
     * influxdb 列名 ： deviceId
     */
    public static final String INFLUXDB_COL_DEVICEID = "deviceId";

    /**
     * influxdb 列名 ： value
     */
    public static final String INFLUXDB_COL_VALUE = "value";

    public static  final String ZERO = "0";

    public static final String ONE = "1";
}
