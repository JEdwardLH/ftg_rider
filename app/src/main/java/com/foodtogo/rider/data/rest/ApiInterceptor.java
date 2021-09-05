package com.foodtogo.rider.data.rest;

import androidx.annotation.NonNull;

import com.foodtogo.rider.base.BaseApplication;
import com.foodtogo.rider.data.source.sharedpreference.AppPreferenceDataSource;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;


/**
 * The type Api interceptor.
 */
public class ApiInterceptor implements Interceptor {

    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String HEADER_ACCEPT = "Accept";
    private static final String AUTHORIZATION = "Authorization";
    private static final String TOKEN_KEY = "Bearer";

    private AppPreferenceDataSource mAppPreference;

    /**
     * Instantiates a new Api interceptor.
     */
    public ApiInterceptor() {
        mAppPreference = new AppPreferenceDataSource(BaseApplication.getContext());
    }


    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        String mToken = !mAppPreference.getOAuthKey().equals("") ? TOKEN_KEY + mAppPreference.getOAuthKey() : "";
        Request chainRequest = chain.request();
        Builder builder = chainRequest.newBuilder();
        builder.header(HEADER_CONTENT_TYPE, "application/json");
        builder.header(HEADER_ACCEPT, "application/json");
        builder.header(AUTHORIZATION, mToken);
        Request request = builder.build();
        return chain.proceed(request);
    }
}
