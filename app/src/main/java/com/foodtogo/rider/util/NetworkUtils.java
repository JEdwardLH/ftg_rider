package com.foodtogo.rider.util;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;

import org.json.JSONObject;

import okhttp3.ResponseBody;


/**
 * The type Network utils.
 */
public class NetworkUtils {

    private static final String MESSAGE = "message";

    /**
     * Is network connected boolean.
     *
     * @param context the context
     * @return the boolean
     */
    public static boolean isNetworkConnected(Context context) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= Build.VERSION_CODES.M) {
            final ConnectivityManager connectivityManager = (ConnectivityManager) context.
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connectivityManager != null;
            final Network network = connectivityManager.getActiveNetwork();
            final NetworkCapabilities capabilities = connectivityManager
                    .getNetworkCapabilities(network);
            return capabilities != null
                    && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
        } else {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connectivityManager != null;
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
    }


    /**
     * Gets error message.
     *
     * @param responseBody the response body
     * @return the error message
     */
    public static String getErrorMessage(ResponseBody responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            return jsonObject.getString(MESSAGE);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Is sim support boolean.
     *
     * @param context the context
     * @return the boolean
     */
    public static boolean isSimSupport(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return !(tm.getSimState() == TelephonyManager.SIM_STATE_ABSENT);
    }


}