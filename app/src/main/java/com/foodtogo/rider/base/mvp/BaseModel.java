package com.foodtogo.rider.base.mvp;


import com.foodtogo.rider.R;
import com.foodtogo.rider.data.rest.ApiClient;
import com.foodtogo.rider.data.rest.ApiInterface;
import com.foodtogo.rider.model.BaseResponse;
import com.foodtogo.rider.model.CommonResponse;
import com.foodtogo.rider.model.trackOrder.UpdateLocationRequest;
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

public class BaseModel implements BaseContractor.Model {
    private CompositeDisposable disposable = new CompositeDisposable();
    private ApiInterface apiInterface;
    private BasePresenter mPresenter;

    public BaseModel(BasePresenter presenter) {
        this.apiInterface = ApiClient.getApiInterface();
        this.mPresenter=presenter;
    }

    @Override
    public void requestToPostData(String lat, String lng, String language) {
        UpdateLocationRequest locationRequest=new UpdateLocationRequest();
        locationRequest.setDeliveryLatitude(lat);
        locationRequest.setDeliveryLongitude(lng);
        locationRequest.setLang(language);
        disposable.add(apiInterface.updateLocation(locationRequest).subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<BaseResponse<CommonResponse>>() {
            @Override
            public void onSuccess(BaseResponse<CommonResponse> baseResponse) {
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    mPresenter.showError(NetworkUtils.getErrorMessage(responseBody));
                } else if (e instanceof SocketTimeoutException) {
                    mPresenter.showError(ResourceUtils.getString(R.string.time_out_error));
                } else if (e instanceof IOException) {
                    mPresenter.showError(ResourceUtils.getString(R.string.network_error));
                } else {
                    mPresenter.showError(e.getMessage());
                }
            }
        }));
    }

    @Override
    public void close() {
        disposable.dispose();
    }
}
