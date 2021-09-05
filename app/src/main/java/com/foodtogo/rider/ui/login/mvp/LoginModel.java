package com.foodtogo.rider.ui.login.mvp;


import android.content.Context;

import com.foodtogo.rider.R;
import com.foodtogo.rider.data.rest.ApiClient;
import com.foodtogo.rider.data.rest.ApiInterface;
import com.foodtogo.rider.model.BaseResponse;
import com.foodtogo.rider.model.geocodeaddress.GeoCodeAddress;
import com.foodtogo.rider.model.login.LoginRequest;
import com.foodtogo.rider.model.login.LoginResponse;
import com.foodtogo.rider.util.NetworkUtils;
import com.foodtogo.rider.util.ViewUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * The type Login model.
 */
public class LoginModel implements LoginContractor.Model {


    private LoginPresenter mPresenter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private ApiInterface apiInterface;
    private Context mContext;

    /**
     * Instantiates a new Login model.
     *
     * @param presenter the presenter
     */
    LoginModel(LoginPresenter presenter,Context context) {
        mPresenter = presenter;
        apiInterface = ApiClient.getApiInterface();
        this.mContext=context;
    }


    @Override
    public void requestLogin(String email, String password, String lan, String fcmToken, String latitude, String longitude, String address) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword(password);
        loginRequest.setEmail(email);
        loginRequest.setLanguage(lan);
        loginRequest.setAndr_fcm_id(fcmToken);
        loginRequest.setLatitude(latitude);
        loginRequest.setLongitude(longitude);
        loginRequest.setLocation(address);
        loginRequest.setType("android");
        loginRequest.setAndr_device_id(ViewUtils.getDeviceId());
        disposable.add(apiInterface.signIn(loginRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<LoginResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<LoginResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.loginSuccess(baseResponse.getData(), baseResponse.getMessage());
                        } else {
                            mPresenter.loginError(baseResponse.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody responseBody = ((HttpException) e).response().errorBody();
                            mPresenter.loginError(NetworkUtils.getErrorMessage(responseBody));
                        } else if (e instanceof SocketTimeoutException) {
                            mPresenter.loginError(R.string.time_out_error);
                        } else if (e instanceof IOException) {
                            mPresenter.loginError(R.string.network_error);
                        } else {
                            mPresenter.loginError(e.getMessage());
                        }

                    }
                }));
    }

    @Override
    public void requestAddress(String latitude, String longitude) {
        String location = latitude + "," + longitude;
        disposable.add(apiInterface
                .getAddress(location, mContext.getString(R.string.direction_api_key))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<GeoCodeAddress>() {
                    @Override
                    public void onSuccess(GeoCodeAddress geoCodeAddress) {
                        if (geoCodeAddress.getStatus().equals("OK")) {
                            mPresenter.onGeoCodeAddress(geoCodeAddress);
                        } else {
                            mPresenter.onGeoCodeAddressError(geoCodeAddress.getStatus());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody responseBody = ((HttpException) e).response().errorBody();
                            mPresenter.loginError(NetworkUtils.getErrorMessage(responseBody));
                        } else if (e instanceof SocketTimeoutException) {
                            mPresenter.loginError(R.string.time_out_error);
                        } else if (e instanceof IOException) {
                            mPresenter.loginError(R.string.network_error);
                        } else {
                            mPresenter.loginError(e.getMessage());
                        }

                    }
                }));
    }

    @Override
    public void close() {
        disposable.dispose();
    }
}
