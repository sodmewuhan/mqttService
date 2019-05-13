package com.datasensorn.mqttservice.Utils;

/**
 * 系统的常用变量
 */
public class Constant {

    /**
     * mqtt 的前缀
     */
    public static final String MQTT_PREFIX = "UP/";

    public static final String MQTT_DOWN_PREFIX = "DOWN/";

    /**
     *
     */
    public static final String MQTT_DOWN_SET_STATUS_PREFIX = "a";

    /**
     * influxdb的measurement名字  池塘的水质情况
     */
    public static final String INFLUXDB_NAME_FISH = "fish";

    public static final String INFLUXDB_NAME_DEVICE = "device";

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

    // token的保存信息
    public static final String USER_NAME = "username";
    // token 的保存信息
    public static final String PHONE = "phone";
    // 认证
    public static final String AUTHORIZATION = "authorization";
}
