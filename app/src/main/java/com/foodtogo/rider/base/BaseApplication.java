package com.foodtogo.rider.base;

import android.app.Application;
import android.content.Context;

import android.util.Log;

import androidx.multidex.MultiDex;

import com.foodtogo.rider.base.mvp.BaseContractor;
import com.foodtogo.rider.base.mvp.BasePresenter;
import com.foodtogo.rider.data.source.AppDataSource;
import com.foodtogo.rider.data.source.AppRepository;
import com.foodtogo.rider.data.source.sharedpreference.AppPreferenceDataSource;
import com.foodtogo.rider.mqtt.MQTTServerClient;
import com.foodtogo.rider.mqtt.ServerCallBack;
import com.foodtogo.rider.util.AppUtils;
import com.foodtogo.rider.util.NetworkUtils;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;


import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static com.foodtogo.rider.base.AppConstants.EXCEPTION;


/**
 * The type Base application.
 */
public class BaseApplication extends Application implements ServerCallBack, BaseContractor.View {

    private static Context context;
    private static MqttAndroidClient mqttAndroidClient = null;
    private static MQTTServerClient mqttServerClient = null;
    /**
     * The App repository.
     */
    protected AppRepository appRepository;
    /**
     * The App data source.
     */
    protected AppDataSource appDataSource;
    private Timer timer;
    public static final long EVERY_30_SEC = 1000 * 30;
    static BaseApplication instance;
    BasePresenter presenter;

    public static BaseApplication getInstance(){
        return instance;
    }
    String currentLat="";
    String currentLng="";
    public static void publish(String lat, String lng) {
        instance.currentLat = lat;
        instance.currentLng = lng;

        if (instance.appRepository.isLoggedIn()) {
            JSONObject jsonObjectLive = new JSONObject();
            try {
                jsonObjectLive.put("lat", lat);
                jsonObjectLive.put("lng", lng);
                jsonObjectLive.put("delivery_boy_id", instance.appRepository.getUserId());
                jsonObjectLive.put("delivery_user_name", instance.appRepository.getUserName());
                System.out.println("EDWARD");
                BaseApplication.getMQTTServerClient().publish(BaseApplication.getMQTTClient(), "delivery", jsonObjectLive.toString());

            } catch (Exception e) {

            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        instance = this;
        appDataSource = new AppPreferenceDataSource(this);
        appRepository = new AppRepository(appDataSource);
        startMQTT();
        presenter=new BasePresenter(this);
    }

    /**
     * Gets context.
     *
     * @return the context
     */
    public static Context getContext() {
        return context;
    }

    /**
     * Gets mqtt client.
     *
     * @return the mqtt client
     */
    public static MqttAndroidClient getMQTTClient() {
        return mqttAndroidClient;
    }

    /**
     * Gets mqtt server client.
     *
     * @return the mqtt server client
     */
    public static MQTTServerClient getMQTTServerClient() {
        return mqttServerClient;
    }

    @Override
    public void onSuccess(MqttAndroidClient _mqttAndroidClient) {
        mqttAndroidClient = _mqttAndroidClient;
    }

    @Override
    public void onFailure(String message) {
        stopTimer();
        timer = new Timer();
        timer.schedule(new LocationTimerTask(), EVERY_30_SEC, EVERY_30_SEC);
    }

    @Override
    public void onMessageReceived(String topic, MqttMessage mqttMessage) {


    }

    @Override
    public void connectionLost(String error) {
        stopTimer();
        timer = new Timer();
        timer.schedule(new LocationTimerTask(), EVERY_30_SEC, EVERY_30_SEC);
    }

    @Override
    public void reconneted(MqttAndroidClient _mqttAndroidClient) {
        mqttAndroidClient = _mqttAndroidClient;
    }


    /**
     * Stop mqtt.
     */
    public void stopMQTT() {
        try {
            if (mqttAndroidClient != null) {
                mqttAndroidClient.unregisterResources();
                mqttAndroidClient.close();
            }
        } catch (Exception e) {
            Log.e(EXCEPTION, e.toString());
        }
    }

    private void startMQTT() {
        mqttServerClient = new MQTTServerClient();
        mqttServerClient.mqttCallBack(this);
        mqttServerClient.clientConnect(this);
    }

    @Override
    public void showError(String msg) {
        //SAFELY IGNORE
    }

    @Override
    public void showError(int msg) {
        //SAFELY IGNORE
    }

    private class LocationTimerTask extends TimerTask {
        @Override
        public void run() {
            if (instance.appRepository.isLoggedIn()) {
                if (NetworkUtils.isNetworkConnected(context))
                    if(!instance.currentLat.equals("") && !instance.currentLng.equals("")){
                        presenter.postLocation(instance.currentLat, instance.currentLng, appRepository.getLanguage());

                    }
                   /* else {
                        Toast.makeText(context, context.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }*/
            }

        }
    }

    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }




}
