package com.foodtogo.rider.ui.invoice.mvp;

import android.content.Context;

import com.foodtogo.rider.R;
import com.foodtogo.rider.data.rest.ApiClient;
import com.foodtogo.rider.data.rest.ApiInterface;
import com.foodtogo.rider.model.BaseResponse;
import com.foodtogo.rider.model.invoice.InvoiceRequest;
import com.foodtogo.rider.model.invoice.InvoiceResponse;
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
 * The type Invoice modal.
 */
public class InvoiceModal implements InvoiceContractor.Modal {
    private InvoicePresenter presenter;
    private ApiInterface apiInterface;
    private CompositeDisposable disposable = new CompositeDisposable();

    /**
     * Instantiates a new Invoice modal.
     *
     * @param presenter the presenter
     * @param context   the context
     */
    InvoiceModal(InvoicePresenter presenter, Context context) {
        this.presenter = presenter;
        this.apiInterface = ApiClient.getApiInterface();
    }

    @Override
    public void requestInvoiceDetail(InvoiceRequest request) {
        disposable.add(apiInterface.getInvoiceDetail(request).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<InvoiceResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<InvoiceResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            presenter.invoiceSuccess(baseResponse.getData());
                        } else if(baseResponse.getCode()==401){
                            presenter.loggedInByAnotherError(baseResponse.getMessage());
                        }else {
                            presenter.invoiceError(baseResponse.getMessage());
                            if (baseResponse.getCode() == 400 && baseResponse.getMessage().equals(CommonStrings.TOKEN_EXPIRED)) {
                                presenter.invoiceError(baseResponse.getCode());
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody responseBody = ((HttpException) e).response().errorBody();
                            presenter.invoiceError(NetworkUtils.getErrorMessage(responseBody));
                        } else if (e instanceof SocketTimeoutException) {
                            presenter.invoiceError(R.string.time_out_error);
                        } else if (e instanceof IOException) {
                            presenter.invoiceError(R.string.network_error);
                        } else {
                            presenter.invoiceError(e.getMessage());
                        }
                    }
                }));
    }

    @Override
    public void close() {
        disposable.dispose();
    }
}
