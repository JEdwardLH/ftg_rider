package com.foodtogo.rider.ui.forgotpassword.mvp;

import com.foodtogo.rider.BaseView;
import com.foodtogo.rider.data.rest.ApiInterface;
import com.foodtogo.rider.model.forgotpassword.ForgotPasswordResponse;

/**
 * The interface Forgot password contractor.
 */
public interface ForgotPasswordContractor {
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
         * Show forgot password response.
         *
         * @param forgotResponse the forgot response
         */
        void showForgotPasswordResponse(ForgotPasswordResponse forgotResponse);
    }

    /**
     * The interface Presenter.
     */
    interface Presenter {
        /**
         * Go button clicked.
         *
         * @param email the email
         * @param lang  the lang
         */
        void goButtonClicked(String email, String lang);

        /**
         * Email sent success.
         *
         * @param forgotResponse the forgot response
         */
        void emailSentSuccess(ForgotPasswordResponse forgotResponse);

        /**
         * Forgot password error.
         *
         * @param error the error
         */
        void forgotPasswordError(String error);

        /**
         * Forgot password error.
         *
         * @param error the error
         */
        void forgotPasswordError(int error);

        /**
         * Close.
         */
        void close();
        void loggedInByOtherError(String msg);
    }

    /**
     * The interface Model.
     */
    interface Model {
        /**
         * Request forgot password.
         *
         * @param apiInterface the api interface
         * @param email        the email
         * @param lan          the lan
         */
        void requestForgotPassword(ApiInterface apiInterface, String email, String lan);

        /**
         * Close.
         */
        void close();
    }
}
