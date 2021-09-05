package com.foodtogo.rider.ui.payment_setting.mvp;

import android.content.Context;

import com.foodtogo.rider.R;
import com.foodtogo.rider.data.rest.ApiClient;
import com.foodtogo.rider.data.rest.ApiInterface;
import com.foodtogo.rider.model.BaseResponse;
import com.foodtogo.rider.model.Request;
import com.foodtogo.rider.model.CommonResponse;
import com.foodtogo.rider.model.payment_settings.PaymentSettingResponse;
import com.foodtogo.rider.model.payment_settings.UpdatePaymentRequest;
import com.foodtogo.rider.util.CommonStrings;
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
 * The type Payment setting modal.
 */
public class PaymentSettingModal implements PaymentSettingContractor.Modal {
    private PaymentSettingPresenter presenter;
    private ApiInterface apiInterface;
    private CompositeDisposable disposable = new CompositeDisposable();

    /**
     * Instantiates a new Payment setting modal.
     *
     * @param presenter the presenter
     * @param context   the context
     */
    public PaymentSettingModal(PaymentSettingPresenter presenter, Context context) {
        this.presenter = presenter;
        this.apiInterface = ApiClient.getApiInterface();
    }

    @Override
    public void requestPaymentSettings(Request request) {
        disposable.add(apiInterface.getPaymentSettings(request).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<BaseResponse<PaymentSettingResponse>>() {
            @Override
            public void onSuccess(BaseResponse<PaymentSettingResponse> baseResponse) {
                try {
                    if (baseResponse.getCode() == 200) {
                        presenter.paymentSettingSuccess(baseResponse.getData());
                    }else if(baseResponse.getCode()==401){
                        presenter.loggedInByAnother(baseResponse.getMessage());
                    }else {
                        presenter.paymentSettingError(baseResponse.getMessage());
                        if (baseResponse.getCode() == 400 && baseResponse.getMessage().equals(CommonStrings.TOKEN_EXPIRED)) {
                            presenter.paymentSettingError(baseResponse.getCode());
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    presenter.paymentSettingError(NetworkUtils.getErrorMessage(responseBody));
                } else if (e instanceof SocketTimeoutException) {
                    presenter.paymentSettingError(R.string.time_out_error);
                } else if (e instanceof IOException) {
                    presenter.paymentSettingError(R.string.network_error);
                } else {
                    presenter.paymentSettingError(e.getMessage());
                }

            }
        }));

    }

    @Override
    public void requestToUpdateSettings(UpdatePaymentRequest request) {
        disposable.add(apiInterface.updateSettings(request).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<BaseResponse<CommonResponse>>() {
            @Override
            public void onSuccess(BaseResponse<CommonResponse> baseResponse) {
                try {
                    if (baseResponse.getCode() == 200) {
                        presenter.updateSettingSuccess(baseResponse.getMessage());
                    } else {
                        presenter.paymentSettingError(baseResponse.getMessage());
                        if (baseResponse.getCode() == 400 && baseResponse.getMessage().equals(CommonStrings.TOKEN_EXPIRED)) {
                            presenter.paymentSettingError(baseResponse.getCode());
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    presenter.paymentSettingError(NetworkUtils.getErrorMessage(responseBody));
                } else if (e instanceof SocketTimeoutException) {
                    presenter.paymentSettingError(R.string.time_out_error);
                } else if (e instanceof IOException) {
                    presenter.paymentSettingError(R.string.network_error);
                } else {
                    presenter.paymentSettingError(e.getMessage());
                }

            }
        }));
    }

    @Override
    public void close() {
        disposable.dispose();
    }

}
