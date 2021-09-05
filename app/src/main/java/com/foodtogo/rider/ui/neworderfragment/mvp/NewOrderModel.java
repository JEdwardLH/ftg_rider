package com.foodtogo.rider.ui.neworderfragment.mvp;

import android.content.Context;

import com.foodtogo.rider.R;
import com.foodtogo.rider.data.rest.ApiClient;
import com.foodtogo.rider.data.rest.ApiInterface;
import com.foodtogo.rider.model.BaseResponse;
import com.foodtogo.rider.model.CommonResponse;
import com.foodtogo.rider.model.Request;
import com.foodtogo.rider.model.neworders.AcceptOrRejectOrderRequest;
import com.foodtogo.rider.model.neworders.NewOrdersResponse;
import com.foodtogo.rider.util.NetworkUtils;
import com.foodtogo.rider.util.PreferenceUtils;
import com.foodtogo.rider.util.ResourceUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

import static com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys.LANGUAGE;

/**
 * The type New order model.
 */
public class NewOrderModel implements NewOrderContractor.Model {
    private NewOrderPresenter presenter;
    private ApiInterface apiInterface;
    private Context context;
    private CompositeDisposable disposable = new CompositeDisposable();

    /**
     * Instantiates a new New order model.
     *
     * @param presenter the presenter
     * @param context   the context
     */
    public NewOrderModel(NewOrderPresenter presenter, Context context) {
        this.presenter = presenter;
        this.apiInterface = ApiClient.getApiInterface();
        this.context = context;
    }

    @Override
    public void requestNewOrderData(Request request) {
        disposable.add(apiInterface.getNewOrders(request).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<BaseResponse<NewOrdersResponse>>() {
            @Override
            public void onSuccess(BaseResponse<NewOrdersResponse> baseResponse) {
                if(baseResponse.getCode()==401){
                    presenter.loggedInByAnother(baseResponse.getMessage());
                }else{
                    presenter.orderSuccess(baseResponse);
                }

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    presenter.newOrderError(NetworkUtils.getErrorMessage(responseBody));
                } else if (e instanceof SocketTimeoutException) {
                    presenter.newOrderError(ResourceUtils.getString(R.string.time_out_error));
                } else if (e instanceof IOException) {
                    presenter.newOrderError(ResourceUtils.getString(R.string.network_error));
                } else {
                    presenter.newOrderError(e.getMessage());
                }

            }
        }));

    }

    @Override
    public void requestToAcceptorReject(String status, String orderId, String storeId, String reason, String estimateHour, String estimateMin) {
        AcceptOrRejectOrderRequest request = new AcceptOrRejectOrderRequest();
        request.setStatus(status);
        request.setLang(PreferenceUtils.readString(context, LANGUAGE, "en"));
        request.setOrderId(orderId);
        request.setReason(reason);
        request.setStoreId(storeId);
        request.setEstArrivalHour(estimateHour);
        request.setEstArrivalMin(estimateMin);

        disposable.add(apiInterface.acceptOrRejectOrder(request).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<BaseResponse<CommonResponse>>() {
            @Override
            public void onSuccess(BaseResponse<CommonResponse> baseResponse) {

                if(baseResponse.getCode()==200) {
                    presenter.acceptRejectSuccess(baseResponse.getMessage(),status);
                }else if(baseResponse.getCode()==401){
                    presenter.loggedInByAnother(baseResponse.getMessage());
                }else{
                    presenter.errorAcceptReject(baseResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    presenter.errorAcceptReject(NetworkUtils.getErrorMessage(responseBody));
                } else if (e instanceof SocketTimeoutException) {
                    presenter.errorAcceptReject(ResourceUtils.getString(R.string.time_out_error));
                } else if (e instanceof IOException) {
                    presenter.errorAcceptReject(ResourceUtils.getString(R.string.network_error));
                } else {
                    presenter.errorAcceptReject(e.getMessage());
                }

            }
        }));
    }

    @Override
    public void close() {
        disposable.dispose();
    }
}
