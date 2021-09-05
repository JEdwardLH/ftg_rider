package com.foodtogo.rider.ui.login.mvp;

import com.foodtogo.rider.BaseView;
import com.foodtogo.rider.model.geocodeaddress.GeoCodeAddress;
import com.foodtogo.rider.model.login.LoginResponse;

/**
 * The interface Login contractor.
 */
public interface LoginContractor {

    /**
     * The interface View.
     */
    interface View extends BaseView {

        /**
         * Show email empty error.
         */
        void showEmailEmptyError();

        /**
         * Show not valid email error.
         */
        void showNotValidEmailError();

        /**
         * Show password empty error.
         */
        void showPasswordEmptyError();

        /**
         * Show login response.
         *
         * @param loginResponse the login response
         * @param msg           the msg
         */
        void showLoginResponse(LoginResponse loginResponse, String msg);

        /**
         * Show geo code address.
         *
         * @param geoCodeAddress the geo code address
         */
        void showGeoCodeAddress(GeoCodeAddress geoCodeAddress);

        /**
         * Show geo code address error.
         *
         * @param error the error
         */
        void showGeoCodeAddressError(String error);

        /**
         * Validation success.
         */
        void validationSuccess();
    }

    /**
     * The interface Presenter.
     */
    interface Presenter {

        /**
         * Login button clicked.
         *
         * @param email     the email
         * @param password  the password
         * @param lan       the lan
         * @param fcmToken  the fcm token
         * @param latitude  the latitude
         * @param longitude the longitude
         * @param address   the address
         */
        void loginButtonClicked(String email, String password, String lan, String fcmToken, String latitude, String longitude, String address);

        /**
         * Login validation.
         *
         * @param email     the email
         * @param password  the password
         * @param lan       the lan
         * @param fcmToken  the fcm token
         * @param latitude  the latitude
         * @param longitude the longitude
         * @param address   the address
         */
        void loginValidation(String email, String password, String lan, String fcmToken, String latitude, String longitude, String address);

        /**
         * Login success.
         *
         * @param loginResponse the login response
         * @param msg           the msg
         */
        void loginSuccess(LoginResponse loginResponse, String msg);

        /**
         * On geo code address.
         *
         * @param geoCodeAddress the geo code address
         */
        void onGeoCodeAddress(GeoCodeAddress geoCodeAddress);

        /**
         * On geo code address error.
         *
         * @param error the error
         */
        void onGeoCodeAddressError(String error);

        /**
         * Request address.
         *
         * @param lat       the lat
         * @param longitude the longitude
         */
        void requestAddress(String lat, String longitude);

        /**
         * Login error.
         *
         * @param error the error
         */
        void loginError(String error);

        /**
         * Login error.
         *
         * @param error the error
         */
        void loginError(int error);

        /**
         * Close.
         */
        void close();

    }

    /**
     * The interface Model.
     */
    interface Model {

        /**
         * Request address.
         *
         * @param lat       the lat
         * @param longitude the longitude
         */
        void requestAddress(String lat, String longitude);

        /**
         * Request login.
         *
         * @param email     the email
         * @param password  the password
         * @param lan       the lan
         * @param fcmToken  the fcm token
         * @param latitude  the latitude
         * @param longitude the longitude
         * @param address   the address
         */
        void requestLogin(String email, String password, String lan, String fcmToken, String latitude, String longitude, String address);

        /**
         * Close.
         */
        void close();
    }
}


