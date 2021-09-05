package com.foodtogo.rider.ui.dashboard.mvp;

import com.foodtogo.rider.R;
import com.foodtogo.rider.base.BaseApplication;
import com.foodtogo.rider.data.rest.ApiInterface;
import com.foodtogo.rider.data.source.AppRepository;
import com.foodtogo.rider.model.BaseResponse;
import com.foodtogo.rider.model.Request;
import com.foodtogo.rider.model.dashboard.DashboardResponse;
import com.foodtogo.rider.model.signout.SignOutResponse;
import com.foodtogo.rider.model.signout.SignoutRequest;
import com.foodtogo.rider.util.NetworkUtils;
import com.foodtogo.rider.util.PreferenceUtils;
import com.foodtogo.rider.util.ResourceUtils;
import com.foodtogo.rider.util.ViewUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;

import static com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys.FCM_TOKEN;

/**
 * The type Dashboard model.
 */
public class DashboardModel implements DashboardContractor.Model {
    private DashboardPresenter mPresenter;
    private AppRepository mPreference;
    private CompositeDisposable disposable = new CompositeDisposable();

    /**
     * Instantiates a new Dashboard model.
     *
     * @param mPresenter  the m presenter
     * @param mPreference the m preference
     */
    public DashboardModel(DashboardPresenter mPresenter, AppRepository mPreference) {
        this.mPresenter = mPresenter;
        this.mPreference = mPreference;
    }

    @Override
    public void requestDashboard(ApiInterface apiInterface, Request request) {

        disposable.add(apiInterface.dashboard(request).subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<BaseResponse<DashboardResponse>>() {
            @Override
            public void onSuccess(BaseResponse<DashboardResponse> baseResponse) {
                mPresenter.dashboardSuccess(baseResponse);
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    mPresenter.dashboardError(NetworkUtils.getErrorMessage(responseBody));
                } else if (e instanceof SocketTimeoutException) {
                    mPresenter.dashboardError(ResourceUtils.getString(R.string.time_out_error));
                } else if (e instanceof IOException) {
                    mPresenter.dashboardError(ResourceUtils.getString(R.string.network_error));
                } else {
                    mPresenter.dashboardError(e.getMessage());
                }
            }
        }));
    }


    /**
     *  This EMPTY function use for cronjob
     */
    void requestOrdersAutoAllocation(ApiInterface apiInterface) {
        Call<ResponseBody> dataCall = apiInterface.forcedOrderAutoAllocation();
        dataCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }


    @Override
    public void requestSignOut(ApiInterface apiInterface) {
        SignoutRequest signoutRequest = new SignoutRequest();
        signoutRequest.setLang(mPreference.getLanguage());
        signoutRequest.setToken(mPreference.getOAuthKey());
        signoutRequest.setAndr_fcm_id(PreferenceUtils.readString(BaseApplication.getContext(),FCM_TOKEN,""));
        signoutRequest.setAndr_device_id(ViewUtils.getDeviceId());
        signoutRequest.setType("android");
        disposable.add(apiInterface.signOut(signoutRequest).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                subscribeWith(new DisposableSingleObserver<BaseResponse<SignOutResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<SignOutResponse> baseResponse) {
                        mPresenter.exitApp(baseResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody responseBody = ((HttpException) e).response().errorBody();
                            mPresenter.errorSignout(NetworkUtils.getErrorMessage(responseBody));
                        } else if (e instanceof SocketTimeoutException) {
                            mPresenter.errorSignout(ResourceUtils.getString(R.string.time_out_error));
                        } else if (e instanceof IOException) {
                            mPresenter.errorSignout(ResourceUtils.getString(R.string.network_error));
                        } else {
                            mPresenter.errorSignout(e.getMessage());
                        }
                    }
                }));
    }

    @Override
    public void close() {
        disposable.dispose();
    }


}
