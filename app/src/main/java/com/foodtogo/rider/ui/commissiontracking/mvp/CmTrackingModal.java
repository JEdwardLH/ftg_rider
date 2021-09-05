package com.foodtogo.rider.ui.commissiontracking.mvp;

import android.content.Context;

import com.foodtogo.rider.R;
import com.foodtogo.rider.data.rest.ApiClient;
import com.foodtogo.rider.data.rest.ApiInterface;
import com.foodtogo.rider.model.BaseResponse;
import com.foodtogo.rider.model.Request;
import com.foodtogo.rider.model.commissiontracking.CommissionTrackingResponse;
import com.foodtogo.rider.model.commissiontracking.PayRequest;
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
 * The type Cm tracking modal.
 */
public class CmTrackingModal implements CmTrackingContractor.Modal {
    private CmTrackingPresenter presenter;
    private ApiInterface apiInterface;
    private CompositeDisposable disposable = new CompositeDisposable();

    /**
     * Instantiates a new Cm tracking modal.
     *
     * @param presenter the presenter
     * @param context   the context
     */
    public CmTrackingModal(CmTrackingPresenter presenter, Context context) {
        this.presenter = presenter;
        this.apiInterface = ApiClient.getApiInterface();
    }


    @Override
    public void requestCmTrackingDetails(Request request) {
        disposable.add(apiInterface.getCommissionTracking(request).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<BaseResponse<CommissionTrackingResponse>>() {
            @Override
            public void onSuccess(BaseResponse<CommissionTrackingResponse> baseResponse) {
                try {
                    if (baseResponse.getCode() == 200) {
                        presenter.cmTrackingSuccess(baseResponse.getData());
                    } else {
                        if (baseResponse.getCode() == 400 && baseResponse.getMessage().equals(CommonStrings.TOKEN_EXPIRED)) {
                            presenter.cmTrackingError(baseResponse.getCode());
                        }else if(baseResponse.getCode()==401){
                            presenter.loggedInByOtherError(baseResponse.getMessage());
                        }else{
                            presenter.cmTrackingError(baseResponse.getMessage());
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
                    presenter.cmTrackingError(NetworkUtils.getErrorMessage(responseBody));
                } else if (e instanceof SocketTimeoutException) {
                    presenter.cmTrackingError(R.string.time_out_error);
                } else if (e instanceof IOException) {
                    presenter.cmTrackingError(R.string.network_error);
                } else {
                    presenter.cmTrackingError(e.getMessage());
                }
            }
        }));
    }

    @Override
    public void requestToPay(PayRequest request) {
        disposable.add(apiInterface.paymentRequest(request).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse baseResponse) {
                try {
                    if (baseResponse.getCode() == 200) {
                        presenter.payRequestSuccess(baseResponse.getMessage());
                    } else {
                        presenter.cmTrackingError(baseResponse.getMessage());
                        if (baseResponse.getCode() == 400 && baseResponse.getMessage().equals(CommonStrings.TOKEN_EXPIRED)) {
                            presenter.cmTrackingError(baseResponse.getCode());
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
                    presenter.cmTrackingError(NetworkUtils.getErrorMessage(responseBody));
                } else if (e instanceof SocketTimeoutException) {
                    presenter.cmTrackingError(R.string.time_out_error);
                } else if (e instanceof IOException) {
                    presenter.cmTrackingError(R.string.network_error);
                } else {
                    presenter.cmTrackingError(e.getMessage());
                }
            }
        }));
    }

    @Override
    public void close() {
        disposable.dispose();
    }
}
