package com.foodtogo.rider.mqtt;

import android.util.Log;

import com.foodtogo.rider.base.AppConstants;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;


/**
 * The type Mqtt action listener.
 */
public class MQTTActionListener implements IMqttActionListener, AppConstants {

    /**
     * The Mqtt android client.
     */
    public MqttAndroidClient mqttAndroidClient;
    private ServerCallBack serverCallBack;

    /**
     * Instantiates a new Mqtt action listener.
     *
     * @param client   the client
     * @param callback the callback
     */
    public MQTTActionListener(MqttAndroidClient client, ServerCallBack callback) {
        this.mqttAndroidClient = client;
        this.serverCallBack = callback;
    }

    @Override
    public void onSuccess(IMqttToken asyncActionToken) {
        Log.i(MQTT_CONNECTION, MQTT_CONNECTION_SUCCESS);
        serverCallBack.onSuccess(mqttAndroidClient);
    }

    @Override
    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
        Log.e(MQTT_CONNECTION, MQTT_CONNECTION_FAILED);
        serverCallBack.onFailure(exception.toString());
    }
}
