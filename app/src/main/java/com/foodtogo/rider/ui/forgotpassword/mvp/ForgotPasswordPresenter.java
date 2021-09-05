package com.foodtogo.rider.ui.forgotpassword.mvp;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;

import com.foodtogo.rider.data.rest.ApiClient;
import com.foodtogo.rider.data.rest.ApiInterface;
import com.foodtogo.rider.model.forgotpassword.ForgotPasswordResponse;

/**
 * The type Forgot password presenter.
 */
public class ForgotPasswordPresenter implements ForgotPasswordContractor.Presenter {
    private ForgotPasswordContractor.View mView;
    private ForgotPasswordModel model;
    private Context context;
    private ApiInterface apiInterface;

    /**
     * Instantiates a new Forgot password presenter.
     *
     * @param mView   the m view
     * @param context the context
     */
    public ForgotPasswordPresenter(ForgotPasswordContractor.View mView, Context context) {
        this.mView = mView;
        this.context = context;
        model = new ForgotPasswordModel(this);
        apiInterface = ApiClient.getApiInterface();
    }

    @Override
    public void goButtonClicked(String email, String lang) {
        if (TextUtils.isEmpty(email)) {
            mView.showEmailEmptyError();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mView.showNotValidEmailError();
        } else {
            mView.showLoadingView();
            model.requestForgotPassword(apiInterface, email, lang);
        }
    }

    @Override
    public void emailSentSuccess(ForgotPasswordResponse forgotResponse) {
        mView.hideLoadingView();
        mView.showForgotPasswordResponse(forgotResponse);
    }

    @Override
    public void forgotPasswordError(String error) {
        mView.hideLoadingView();
        mView.showError(error);
    }

    @Override
    public void forgotPasswordError(int error) {
        mView.hideLoadingView();
        mView.showError(error);
    }

    @Override
    public void close() {
        model.close();
    }

    @Override
    public void loggedInByOtherError(String msg) {
        mView.hideLoadingView();
        mView.showLoggedInByAnother(msg);
    }
}
