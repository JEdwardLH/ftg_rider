package com.foodtogo.rider.ui.processingorder.mvp;

import android.content.Context;

import com.foodtogo.rider.R;
import com.foodtogo.rider.data.rest.ApiClient;
import com.foodtogo.rider.data.rest.ApiInterface;
import com.foodtogo.rider.model.BaseResponse;
import com.foodtogo.rider.model.orders.OrderRequest;
import com.foodtogo.rider.model.processingorders.ProcessingOrderResponse;
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
 * The type Processing order modal.
 */
public class ProcessingOrderModal implements ProcessingOrderContractor.Modal {
    private ProcessingOrderPresenter presenter;
    private ApiInterface apiInterface;
    private CompositeDisposable disposable = new CompositeDisposable();

    /**
     * Instantiates a new Processing order modal.
     *
     * @param presenter the presenter
     * @param context   the context
     */
    public ProcessingOrderModal(ProcessingOrderPresenter presenter, Context context) {
        this.presenter = presenter;
        this.apiInterface = ApiClient.getApiInterface();
    }

    @Override
    public void requestProcessingOrder(OrderRequest request) {
        disposable.add(apiInterface.getProcessingOrders(request).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<BaseResponse<ProcessingOrderResponse>>() {
            @Override
            public void onSuccess(BaseResponse<ProcessingOrderResponse> baseResponse) {
                if(baseResponse.getCode()==401){
                    presenter.loggedInByAnotherError(baseResponse.getMessage());
                }else{
                    presenter.processingOrderSuccess(baseResponse);
                }

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    presenter.processingOrderError(NetworkUtils.getErrorMessage(responseBody));
                } else if (e instanceof SocketTimeoutException) {
                    presenter.processingOrderError(ResourceUtils.getString(R.string.time_out_error));
                } else if (e instanceof IOException) {
                    presenter.processingOrderError(ResourceUtils.getString(R.string.network_error));
                } else {
                    presenter.processingOrderError(e.getMessage());
                }

            }
        }));
    }

    @Override
    public void close() {
        disposable.dispose();
    }
}
