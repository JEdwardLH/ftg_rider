/**
 *
 */
package com.foodtogo.rider.mqtt;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;


import com.foodtogo.rider.base.AppConstants;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

/**
 * The type Mqtt server client.
 */
public class MQTTServerClient implements AppConstants {

    private int TIME_OUT = 10;

    private ServerCallBack serverCallBack;

    /**
     * Mqtt call back.
     *
     * @param callback the callback
     */
    public void mqttCallBack(ServerCallBack callback) {
        serverCallBack = callback;
    }


    /**
     * Client connect.
     *
     * @param context the context
     */
    public void clientConnect(Context context) {
        String clientId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        MqttAndroidClient client = new MqttAndroidClient(context, AppConstants.MQTT_SERVER_ENDPOINT,
                clientId);
        try {
            MqttConnectOptions options = new MqttConnectOptions();
            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
            options.setConnectionTimeout(TIME_OUT);
            options.setAutomaticReconnect(true);
            IMqttToken token = client.connect(options);
            token.setActionCallback(new MQTTActionListener(client, serverCallBack));
        } catch (NullPointerException e) {
            Log.d(MQTT_CONNECTION, e.toString());
            serverCallBack.connectionLost(e.toString());
        } catch (MqttException e) {
            Log.d(MQTT_CONNECTION, e.toString());
            serverCallBack.connectionLost(e.toString());
        } catch (Exception e) {
            Log.d(MQTT_CONNECTION, e.toString());
            serverCallBack.connectionLost(e.toString());
        }
    }


    /**
     * Subscribe.
     *
     * @param mqttAndroidClient the mqtt android client
     * @param topic             the topic
     */
    public void subscribe(MqttAndroidClient mqttAndroidClient, final String topic) {
        Log.i("SUBSCRIBE", topic);
        int qos = 1;
        try {
            mqttAndroidClient.subscribe(topic, qos);
            mqttAndroidClient.setCallback(new MQTTServerCallBack(mqttAndroidClient, serverCallBack));
        } catch (MqttException e) {
            serverCallBack.connectionLost(e.toString());
        } catch (Exception e) {
            serverCallBack.connectionLost(e.toString());
        }
    }

    /**
     * Unsubscribe.
     *
     * @param mqttAndroidClient the mqtt android client
     * @param topic             the topic
     */
    public void unsubscribe(MqttAndroidClient mqttAndroidClient, final String topic) {
        Log.i("SUBSCRIBE", topic);
        try {
            mqttAndroidClient.unsubscribe(topic);
            mqttAndroidClient.setCallback(new MQTTServerCallBack(mqttAndroidClient, serverCallBack));
        } catch (MqttException e) {
            serverCallBack.connectionLost(e.toString());
        } catch (Exception e) {
            serverCallBack.connectionLost(e.toString());
        }
    }


    /**
     * Publish.
     *
     * @param mqttAndroidClient the mqtt android client
     * @param topic             the topic
     * @param body              the body
     */
    public void publish(MqttAndroidClient mqttAndroidClient, final String topic, String body) {
        Log.i("PUBLISH", topic);
        Log.i("PAYLOAD", body);
        int qos = 1;
        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = body.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            message.setRetained(false);
            mqttAndroidClient.publish(topic, message);
            mqttAndroidClient.setCallback(new MQTTServerCallBack(mqttAndroidClient, serverCallBack));
        } catch (MqttException e) {
            serverCallBack.connectionLost(e.toString());
        } catch (UnsupportedEncodingException e) {
            serverCallBack.connectionLost(e.toString());
        }
    }

}
