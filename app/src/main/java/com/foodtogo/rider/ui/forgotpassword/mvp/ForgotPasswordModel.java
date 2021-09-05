package com.foodtogo.rider.ui.forgotpassword.mvp;

import com.foodtogo.rider.R;
import com.foodtogo.rider.data.rest.ApiInterface;
import com.foodtogo.rider.model.BaseResponse;
import com.foodtogo.rider.model.forgotpassword.ForgotPasswordRequest;
import com.foodtogo.rider.model.forgotpassword.ForgotPasswordResponse;
import com.foodtogo.rider.util.NetworkUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * The type Forgot password model.
 */
public class ForgotPasswordModel implements ForgotPasswordContractor.Model {

    private ForgotPasswordPresenter presenter;
    private CompositeDisposable disposable = new CompositeDisposable();

    /**
     * Instantiates a new Forgot password model.
     *
     * @param forgotPasswordPresenter the forgot password presenter
     */
    public ForgotPasswordModel(ForgotPasswordPresenter forgotPasswordPresenter) {
        this.presenter = forgotPasswordPresenter;
    }

    @Override
    public void requestForgotPassword(ApiInterface apiInterface, String email, String lan) {
        ForgotPasswordRequest forgotRequest = new ForgotPasswordRequest();
        forgotRequest.setDelivery_email(email);
        forgotRequest.setLang(lan);
        disposable.add(apiInterface.
                forgotPassword(forgotRequest).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribeWith(new DisposableSingleObserver<BaseResponse<ForgotPasswordResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<ForgotPasswordResponse> forgotPasswordResponse) {
                        if (forgotPasswordResponse.getCode() == 200) {
                            presenter.emailSentSuccess(forgotPasswordResponse.getData());
                        } else if(forgotPasswordResponse.getCode()==401){
                            presenter.loggedInByOtherError(forgotPasswordResponse.getMessage());
                        }else{
                            presenter.forgotPasswordError(forgotPasswordResponse.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody responseBody = ((HttpException) e).response().errorBody();
                            presenter.forgotPasswordError(NetworkUtils.getErrorMessage(responseBody));
                        } else if (e instanceof SocketTimeoutException) {
                            presenter.forgotPasswordError(R.string.time_out_error);
                        } else if (e instanceof IOException) {
                            presenter.forgotPasswordError(R.string.network_error);
                        } else {
                            presenter.forgotPasswordError(e.getMessage());
                        }
                    }

                }));

    }

    @Override
    public void close() {
        disposable.dispose();
    }
}
