package com.foodtogo.rider.ui.payment.mvp;

import android.content.Context;

import com.foodtogo.rider.R;
import com.foodtogo.rider.data.rest.ApiClient;
import com.foodtogo.rider.data.rest.ApiInterface;
import com.foodtogo.rider.model.BaseResponse;
import com.foodtogo.rider.model.payment.PaymentConversionRequest;
import com.foodtogo.rider.model.payment.PaymentConversionResponse;
import com.foodtogo.rider.model.payment.PaymentRequest;
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
 * The type Payment modal.
 */
public class PaymentModal implements PaymentContractor.Modal {

    private PaymentPresenter presenter;
    private ApiInterface apiInterface;
    private CompositeDisposable disposable = new CompositeDisposable();

    /**
     * Instantiates a new Payment modal.
     *
     * @param presenter the presenter
     * @param context   the context
     */
    public PaymentModal(PaymentPresenter presenter, Context context) {
        this.presenter = presenter;
        this.apiInterface = ApiClient.getApiInterface();
    }

    @Override
    public void requestToPay(PaymentRequest request) {
        disposable.add(apiInterface.paymentToPay(request).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse baseResponse) {
                try {
                    if (baseResponse.getCode() == 200) {
                        presenter.paidAmountSuccess(baseResponse.getMessage());
                    }else if(baseResponse.getCode()==401){
                        presenter.loggedInByAnotherError(baseResponse.getMessage());
                    } else {
                        presenter.payError(baseResponse.getMessage());
                        if (baseResponse.getCode() == 400 && baseResponse.getMessage().equals(CommonStrings.TOKEN_EXPIRED)) {
                            presenter.payError(baseResponse.getCode());
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
                    presenter.payError(NetworkUtils.getErrorMessage(responseBody));
                } else if (e instanceof SocketTimeoutException) {
                    presenter.payError(R.string.time_out_error);
                } else if (e instanceof IOException) {
                    presenter.payError(R.string.network_error);
                } else {
                    presenter.payError(e.getMessage());
                }

            }
        }));

    }

    @Override
    public void requestToCurrencyConversion(PaymentConversionRequest request) {
        disposable.add(apiInterface.amountConversion(request).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<BaseResponse<PaymentConversionResponse>>() {

            @Override
            public void onSuccess(BaseResponse<PaymentConversionResponse> baseResponse) {
                try {
                    if (baseResponse.getCode() == 200) {
                        presenter.amountConversionSuccess(baseResponse.getData().getAmount());
                    } else {
                        presenter.payError(baseResponse.getMessage());
                        if (baseResponse.getCode() == 400 && baseResponse.getMessage().equals(CommonStrings.TOKEN_EXPIRED)) {
                            presenter.payError(baseResponse.getCode());
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
                    presenter.payError(NetworkUtils.getErrorMessage(responseBody));
                } else if (e instanceof SocketTimeoutException) {
                    presenter.amountConversionError(R.string.time_out_error);
                } else if (e instanceof IOException) {
                    presenter.amountConversionError(R.string.network_error);
                } else {
                    presenter.payError(e.getMessage());
                }

            }
        }));
    }

    @Override
    public void close() {
        disposable.dispose();
    }
}
