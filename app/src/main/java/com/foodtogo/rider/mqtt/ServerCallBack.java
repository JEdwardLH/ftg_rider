package com.foodtogo.rider.mqtt;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;


/**
 * The interface Server call back.
 */
public interface ServerCallBack {

    /**
     * On success.
     *
     * @param mqttAndroidClient the mqtt android client
     */
    void onSuccess(MqttAndroidClient mqttAndroidClient);

    /**
     * On failure.
     *
     * @param message the message
     */
    void onFailure(String message);

    /**
     * On message received.
     *
     * @param topic       the topic
     * @param mqttMessage the mqtt message
     */
    void onMessageReceived(String topic, MqttMessage mqttMessage);

    /**
     * Connection lost.
     *
     * @param error the error
     */
    void connectionLost(String error);

    /**
     * Reconneted.
     *
     * @param mqttAndroidClient the mqtt android client
     */
    void reconneted(MqttAndroidClient mqttAndroidClient);


}
