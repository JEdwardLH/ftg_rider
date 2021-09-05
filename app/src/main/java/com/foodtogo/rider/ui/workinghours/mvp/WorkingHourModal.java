package com.foodtogo.rider.ui.workinghours.mvp;

import android.content.Context;

import com.foodtogo.rider.R;
import com.foodtogo.rider.data.rest.ApiClient;
import com.foodtogo.rider.data.rest.ApiInterface;
import com.foodtogo.rider.model.BaseResponse;
import com.foodtogo.rider.model.Request;
import com.foodtogo.rider.model.CommonResponse;
import com.foodtogo.rider.model.workinghours.WorkingHoursRequest;
import com.foodtogo.rider.model.workinghours.getworkinghour.GetWorkingHourResponse;
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
 * The type Working hour modal.
 */
public class WorkingHourModal implements WorkingHourContractor.Model {
    private WorkingHourPresenter mPresenter;
    private ApiInterface apiInterface;
    private CompositeDisposable disposable = new CompositeDisposable();

    /**
     * Instantiates a new Working hour modal.
     *
     * @param presenter the presenter
     * @param context   the context
     */
    WorkingHourModal(WorkingHourPresenter presenter, Context context) {
        mPresenter = presenter;
        apiInterface = ApiClient.getApiInterface();
    }

    @Override
    public void requestToUpload(WorkingHoursRequest request) {
        disposable.add(apiInterface.updateWorkingHour(request).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<BaseResponse<CommonResponse>>() {
            @Override
            public void onSuccess(BaseResponse<CommonResponse> baseResponse) {
                if (baseResponse.getCode() == 200) {
                    mPresenter.updateSuccess(baseResponse.getMessage());
                } else if(baseResponse.getCode()==401){
                    mPresenter.loggedInByOtherError(baseResponse.getMessage());
                }else {
                    mPresenter.WorkingHourError(baseResponse.getMessage());
                    if (baseResponse.getCode() == 400 && baseResponse.getMessage().equals(CommonStrings.TOKEN_EXPIRED)) {
                        mPresenter.WorkingHourError(baseResponse.getCode());
                    }

                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    mPresenter.WorkingHourError(NetworkUtils.getErrorMessage(responseBody));
                } else if (e instanceof SocketTimeoutException) {
                    mPresenter.WorkingHourError(R.string.time_out_error);
                } else if (e instanceof IOException) {
                    mPresenter.WorkingHourError(R.string.network_error);
                } else {
                    mPresenter.WorkingHourError(e.getMessage());
                }
            }
        }));
    }

    @Override
    public void requestToGetWorkingHours(Request request) {
        disposable.add(apiInterface.getWorkingHour(request).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<BaseResponse<GetWorkingHourResponse>>() {
            @Override
            public void onSuccess(BaseResponse<GetWorkingHourResponse> baseResponse) {
                if (baseResponse.getCode() == 200) {
                    mPresenter.getDataSuccess(baseResponse.getData(), baseResponse.getMessage());
                }else if(baseResponse.getCode()==401){
                    mPresenter.loggedInByOtherError(baseResponse.getMessage());
                } else {
                    mPresenter.WorkingHourError(baseResponse.getMessage());
                    if (baseResponse.getCode() == 400 && baseResponse.getMessage().equals(CommonStrings.TOKEN_EXPIRED)) {
                        mPresenter.WorkingHourError(baseResponse.getCode());
                    }

                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    mPresenter.WorkingHourError(NetworkUtils.getErrorMessage(responseBody));
                } else if (e instanceof SocketTimeoutException) {
                    mPresenter.WorkingHourError(R.string.time_out_error);
                } else if (e instanceof IOException) {
                    mPresenter.WorkingHourError(R.string.network_error);
                } else {
                    mPresenter.WorkingHourError(e.getMessage());
                }
            }
        }));
    }

    @Override
    public void close() {
        disposable.dispose();
    }
}
