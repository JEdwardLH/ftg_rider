package com.foodtogo.rider.ui.earning_reports.mvp;

import android.content.Context;

import com.foodtogo.rider.R;
import com.foodtogo.rider.data.rest.ApiClient;
import com.foodtogo.rider.data.rest.ApiInterface;
import com.foodtogo.rider.model.BaseResponse;
import com.foodtogo.rider.model.earningreports.EarningReportRequest;
import com.foodtogo.rider.model.earningreports.EarningReportsResponse;
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
 * The type Earning reports modal.
 */
public class EarningReportsModal implements EarningReportsContractor.Modal {
    private EarningReportsPresenter presenter;
    private ApiInterface apiInterface;
    private CompositeDisposable disposable = new CompositeDisposable();

    /**
     * Instantiates a new Earning reports modal.
     *
     * @param presenter the presenter
     * @param context   the context
     */
    public EarningReportsModal(EarningReportsPresenter presenter, Context context) {
        this.presenter = presenter;
        this.apiInterface = ApiClient.getApiInterface();
    }

    @Override
    public void requestEarningReport(EarningReportRequest request) {
        disposable.add(apiInterface.getEarningReports(request).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<BaseResponse<EarningReportsResponse>>() {
            @Override
            public void onSuccess(BaseResponse<EarningReportsResponse> baseResponse) {
                if (baseResponse.getCode() == 200) {
                    presenter.earningReportSuccess(baseResponse.getData());
                } else {
                    if (baseResponse.getCode() == 400 && baseResponse.getMessage().equals(CommonStrings.TOKEN_EXPIRED)) {
                        presenter.earningReportError(baseResponse.getCode());
                    }else if(baseResponse.getCode()==401){
                        presenter.loggedInByAnotherError(baseResponse.getMessage());
                    }else{
                        presenter.earningReportError(baseResponse.getMessage());
                    }

                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    presenter.earningReportError(NetworkUtils.getErrorMessage(responseBody));
                } else if (e instanceof SocketTimeoutException) {
                    presenter.earningReportError(R.string.time_out_error);
                } else if (e instanceof IOException) {
                    presenter.earningReportError(R.string.network_error);
                } else {
                    presenter.earningReportError(e.getMessage());
                }

            }
        }));
    }

    @Override
    public void close() {
        disposable.dispose();
    }
}
