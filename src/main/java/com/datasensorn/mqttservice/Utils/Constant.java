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

    public static final String APP_NAME = "fishtech.apk";

    /** APP 文件大小的限制，限制在20M之内 */
    public static final Integer APP_FILELENGTH = 20 * 1024 * 1024;
}
