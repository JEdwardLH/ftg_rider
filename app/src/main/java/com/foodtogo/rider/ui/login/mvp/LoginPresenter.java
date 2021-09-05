package com.foodtogo.rider.ui.login.mvp;

import android.content.Context;
import android.util.Patterns;

import com.foodtogo.rider.data.rest.ApiInterface;
import com.foodtogo.rider.data.source.AppRepository;
import com.foodtogo.rider.model.geocodeaddress.GeoCodeAddress;
import com.foodtogo.rider.model.login.LoginResponse;


/**
 * The type Login presenter.
 */
public class LoginPresenter implements LoginContractor.Presenter {

    private LoginContractor.View mView;
    private LoginModel model;
    private AppRepository appRepository;
    private ApiInterface apiInterface;
    private Context context;

    /**
     * Instantiates a new Login presenter.
     *
     * @param view           the view
     * @param appRepository1 the app repository 1
     * @param mContext       the m context
     */
    public LoginPresenter(LoginContractor.View view, AppRepository appRepository1, Context mContext) {
        mView = view;
        this.appRepository = appRepository1;
        this.context = mContext;
        model = new LoginModel(this,context);

    }


    @Override
    public void loginButtonClicked(String email, String password, String lan, String fcmToken, String latitude, String longitude, String address) {
        if (email.length() == 0) {
            mView.showEmailEmptyError();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mView.showNotValidEmailError();
        } else if (password.length() == 0) {
            mView.showPasswordEmptyError();
        } else {
            model.requestLogin(email, password, lan, fcmToken, latitude, longitude, address);
        }
    }

    @Override
    public void loginValidation(String email, String password, String lan, String fcmToken, String latitude, String longitude, String address) {
        if (email.length() == 0) {
            mView.showEmailEmptyError();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mView.showNotValidEmailError();
        } else if (password.length() == 0) {
            mView.showPasswordEmptyError();
        } else {
            mView.validationSuccess();
        }
    }


    @Override
    public void loginSuccess(LoginResponse loginResponse, String msg) {
        mView.hideLoadingView();
        appRepository.saveIsLoggedIn(true);
        appRepository.setOAuthKey(loginResponse.getToken());
        appRepository.saveUserEmail(loginResponse.getDeliveryPersonEmail());
        appRepository.saveUserName(loginResponse.getDeliveryPersonName());
        appRepository.setUserId(String.valueOf(loginResponse.getDeliveryPersonId()));
        appRepository.setWorkingStatus(loginResponse.getWorkingStatus());
        appRepository.setUploadDocumentStatus(loginResponse.getUploadDocumentStatus());
        mView.showLoginResponse(loginResponse, msg);
    }

    @Override
    public void loginError(String error) {
        mView.hideLoadingView();
        mView.showError(error);
    }

    @Override
    public void loginError(int error) {
        mView.hideLoadingView();
        mView.showError(error);
    }

    @Override
    public void requestAddress(String lat, String longitude) {
        mView.showLoadingView();
        model.requestAddress(lat, longitude);
    }


    @Override
    public void onGeoCodeAddress(GeoCodeAddress geoCodeAddress) {
        mView.showGeoCodeAddress(geoCodeAddress);
    }

    @Override
    public void onGeoCodeAddressError(String error) {
        mView.showGeoCodeAddressError(error);
    }

    @Override
    public void close() {
        model.close();
    }
}
