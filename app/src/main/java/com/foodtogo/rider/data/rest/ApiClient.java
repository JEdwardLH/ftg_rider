package com.foodtogo.rider.data.rest;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The type Api client.
 */
public class ApiClient {

    /**
     * Gets api interface.
     *
     * @return the api interface
     */
    public static ApiInterface getApiInterface() {
        if (ourInstance == null)
            ourInstance = getRetrofit().create(ApiInterface.class);
        return ourInstance;
    }

    private static ApiInterface ourInstance = null;

    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(ApiEndPoints.SERVER_ENDPOINT)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient(getHttpLoggingInterceptor()))
                .build();
    }


    private static OkHttpClient getOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(new ApiInterceptor());
        //if (BuildConfig.DEBUG) {
        builder.addInterceptor(httpLoggingInterceptor);
        //}
        return builder.build();
    }


    private static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }
}