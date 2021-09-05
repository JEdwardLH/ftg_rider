package com.foodtogo.rider.ui.deliveredorder.mvp;

import android.content.Context;

import com.foodtogo.rider.R;
import com.foodtogo.rider.data.rest.ApiClient;
import com.foodtogo.rider.data.rest.ApiInterface;
import com.foodtogo.rider.model.BaseResponse;
import com.foodtogo.rider.model.deliveredorders.DeliveredOrderRequest;
import com.foodtogo.rider.model.deliveredorders.DeliveredResponse;
import com.foodtogo.rider.util.NetworkUtils;
import com.foodtogo.rider.util.ResourceUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * The type Delivered order modal.
 */
public class DeliveredOrderModal implements DeliveredOrderContractor.Modal {
    /**
     * The Presenter.
     */
    DeliveredOrderPresenter presenter;
    /**
     * The Api interface.
     */
    ApiInterface apiInterface;
    private CompositeDisposable disposable = new CompositeDisposable();

    /**
     * Instantiates a new Delivered order modal.
     *
     * @param presenter the presenter
     * @param context   the context
     */
    public DeliveredOrderModal(DeliveredOrderPresenter presenter, Context context) {
        this.presenter = presenter;
        this.apiInterface = ApiClient.getApiInterface();
    }

    @Override
    public Disposable requestDeliveredOrders(DeliveredOrderRequest request) {

        DisposableSingleObserver<BaseResponse<DeliveredResponse>> d = new DisposableSingleObserver<BaseResponse<DeliveredResponse>>() {

            @Override
            public void onSuccess(BaseResponse<DeliveredResponse> stockListResponse) {
                if(stockListResponse.getCode()==401){
                    presenter.loggedInByOtherError(stockListResponse.getMessage());
                }else{
                    presenter.deliveredOrderSuccess(stockListResponse);
                }

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    presenter.deliveredOrderError(NetworkUtils.getErrorMessage(responseBody));
                } else if (e instanceof SocketTimeoutException) {
                    presenter.deliveredOrderError(ResourceUtils.getString(R.string.time_out_error));
                } else if (e instanceof IOException) {
                    presenter.deliveredOrderError(ResourceUtils.getString(R.string.network_error));
                } else {
                    presenter.deliveredOrderError(e.getMessage());
                }
            }
        };

        Single single = apiInterface
                .getDeliveredOrders(request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        SingleObserver singleObserver = single.subscribeWith(d);

        disposable.add(d);

        return d;








/*        disposable.add(apiInterface.getDeliveredOrders(request).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<BaseResponse<DeliveredResponse>>() {
            @Override
            public void onSuccess(BaseResponse<DeliveredResponse> baseResponse) {
                    presenter.deliveredOrderSuccess(baseResponse);
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    presenter.deliveredOrderError(NetworkUtils.getErrorMessage(responseBody));
                } else if (e instanceof SocketTimeoutException) {
                    presenter.deliveredOrderError(ResourceUtils.getString(R.string.time_out_error));
                } else if (e instanceof IOException) {
                    presenter.deliveredOrderError(ResourceUtils.getString(R.string.network_error));
                } else {
                    presenter.deliveredOrderError(e.getMessage());
                }
            }
        }));*/
    }

    @Override
    public void close() {
        disposable.dispose();
    }
}
