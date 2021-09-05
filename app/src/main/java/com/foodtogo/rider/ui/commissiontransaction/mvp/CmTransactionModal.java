package com.foodtogo.rider.ui.commissiontransaction.mvp;

import com.foodtogo.rider.R;
import com.foodtogo.rider.data.rest.ApiClient;
import com.foodtogo.rider.data.rest.ApiInterface;
import com.foodtogo.rider.model.BaseResponse;
import com.foodtogo.rider.model.commission_transaction.TransactionRequest;
import com.foodtogo.rider.model.commission_transaction.TransactionResponse;
import com.foodtogo.rider.util.NetworkUtils;
import com.foodtogo.rider.util.ResourceUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * The type Cm transaction modal.
 */
public class CmTransactionModal implements CmTransactionContractor.Modal {
    private CmTransactionPresenter presenter;
    private ApiInterface apiInterface;
    private CompositeDisposable disposable = new CompositeDisposable();

    /**
     * Instantiates a new Cm transaction modal.
     *
     * @param presenter the presenter
     */
    public CmTransactionModal(CmTransactionPresenter presenter) {
        this.presenter = presenter;
        this.apiInterface = ApiClient.getApiInterface();
    }

    @Override
    public void requestTransactionDetails(TransactionRequest request) {
        disposable.add(apiInterface.getCommissionTransaction(request).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<BaseResponse<TransactionResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<TransactionResponse> baseResponse) {
                      if(baseResponse.getCode()==401){
                            presenter.loggedInByOtherError(baseResponse.getMessage());
                        }else{
                          presenter.showSuccess(baseResponse);
                      }

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody responseBody = ((HttpException) e).response().errorBody();
                            presenter.transactionError(NetworkUtils.getErrorMessage(responseBody));
                        } else if (e instanceof SocketTimeoutException) {
                            presenter.transactionError(ResourceUtils.getString(R.string.time_out_error));
                        } else if (e instanceof IOException) {
                            presenter.transactionError(ResourceUtils.getString(R.string.network_error));
                        } else {
                            presenter.transactionError(e.getMessage());
                        }
                    }
                }));
    }

    @Override
    public void close() {
        disposable.dispose();
    }
}
