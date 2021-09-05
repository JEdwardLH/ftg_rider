package com.foodtogo.rider.base;

/**
 * The interface App constants.
 */
public interface AppConstants {

    String MQTT_GLOBAL_TOPIC="delivery";
    String MQTT_SERVER_ENDPOINT="tcp://13.212.61.29:1883";
    /**
     * The constant DEFAULT_LIMIT.
     */
    int DEFAULT_LIMIT = 10;

    /**
     * The constant EXCEPTION.
     */
    String EXCEPTION = "Exception";
    /**
     * The constant MQTT_EXCEPTION.
     */
    String MQTT_EXCEPTION = "MQTT EXCEPTION";
    /**
     * The constant MQTT_DELIVERY.
     */
    String MQTT_DELIVERY = "MQTT DELIVERED";
    /**
     * The constant MQTT_DELETE_CALLED.
     */
    String MQTT_DELETE_CALLED = "DELETECALLED";
    /**
     * The constant MQTT_MSG.
     */
    String MQTT_MSG = "MQTT MESSAGE";
    /**
     * The constant MQTT_CONNECTION.
     */
    String MQTT_CONNECTION = "MQTT CONNECTION";
    /**
     * The constant MQTT_TOPIC.
     */
    String MQTT_TOPIC = "MQTT TOPIC";
    /**
     * The constant MQTT_CONNECTION_SUCCESS.
     */
    String MQTT_CONNECTION_SUCCESS = "Mqtt Server Connected";
    /**
     * The constant MQTT_CONNECTION_FAILED.
     */
    String MQTT_CONNECTION_FAILED = "Mqtt not Connected";
    /**
     * The constant MQTT_CONNECTION_RECONNECT.
     */
    String MQTT_CONNECTION_RECONNECT = "Mqtt Server reconnected";
    /**
     * The constant YES.
     */
    String YES = "Yes";
    String COD = "COD";
    String NEW_ORDER = "new_order";

    /**
     * The constant SPACE.
     */
    String SPACE = " ";
    String LOCATION_UPDATE = "location_update";

}