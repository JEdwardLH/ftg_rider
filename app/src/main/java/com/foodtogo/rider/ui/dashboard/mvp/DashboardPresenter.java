package com.foodtogo.rider.ui.dashboard.mvp;

import android.content.Context;

import com.foodtogo.rider.data.rest.ApiClient;
import com.foodtogo.rider.data.rest.ApiInterface;
import com.foodtogo.rider.data.source.AppRepository;
import com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys;
import com.foodtogo.rider.model.BaseResponse;
import com.foodtogo.rider.model.Request;
import com.foodtogo.rider.model.dashboard.DashboardResponse;

import java.util.Timer;


/**
 * The type Dashboard presenter.
 */
public class DashboardPresenter implements DashboardContractor.Presenter, PreferenceKeys {
    private DashboardContractor.View mView;
    private DashboardModel model;
    private ApiInterface apiInterface;
    /**
     * The App repository.
     */
    AppRepository appRepository;

    private Timer timer;
    /**
     * The constant EVERY_20_SEC.
     */
    public static final long EVERY_20_SEC = 1000 * 20;

    /**
     * Instantiates a new Dashboard presenter.
     *
     * @param mView       the m view
     * @param mPreference the m preference
     * @param context     the context
     */
    public DashboardPresenter(DashboardContractor.View mView, AppRepository mPreference, Context context) {
        this.mView = mView;
        this.model = new DashboardModel(this, mPreference);
        this.apiInterface = ApiClient.getApiInterface();
        this.appRepository = mPreference;
    }

    @Override
    public void close() {
        model.close();
    }

    /**
     * The Common request.
     */
    Request commonRequest;
    /**
     * The Is refresh.
     */
    boolean isRefresh;

    @Override
    public void loadDashboard(boolean isRefresh) {
        this.isRefresh = isRefresh;
        commonRequest = new Request();
        commonRequest.setLang(appRepository.getLanguage());

        // trigger the "order auto allocation" used by cronjob
        //model.requestOrdersAutoAllocation(apiInterface);

        model.requestDashboard(apiInterface, commonRequest);
    }

    @Override
    public void dashboardSuccess(BaseResponse<DashboardResponse> response) {
        if (isRefresh) {
            mView.hideRefreshView();
        } else {
            mView.hideLoadingView();
        }
        if (response.isTokenExpired()) {
            mView.showTokenExpired(response.getMessage());
        } else if (response.isSuccess()) {
            mView.loadData(response.getData());
        } else {
            mView.showConnectionErrorRetry(response.getMessage(), () -> {
                mView.showLoadingView();
                model.requestDashboard(apiInterface, commonRequest);
            });
        }
    }

    @Override
    public void dashboardError(String error) {
        if (isRefresh) {
            mView.hideRefreshView();
            mView.showError(error);
        } else {
            mView.hideLoadingView();
            mView.showConnectionErrorRetry(error, () -> {
                model.requestDashboard(apiInterface, commonRequest);
            });
        }
    }

    @Override
    public void errorSignout(String error) {
        mView.hideLoadingView();
        mView.showError(error);
    }

    @Override
    public void signOutClicked() {
        mView.showLoadingView();
        model.requestSignOut(apiInterface);
    }


    @Override
    public void exitApp(BaseResponse baseResponse) {
        mView.hideLoadingView();
        mView.exitAppOnSignOut();
    }

    @Override
    public void startTimer(Context context) {
//        this.context = context;
//        if (EVERY_20_SEC > 0) {
//            stopTimer();
//            timer = new Timer();
//            timer.schedule(new TrackTimerTask(), EVERY_20_SEC, EVERY_20_SEC);
//        }
    }

    @Override
    public void stopTimer() {
//        if (timer != null) {
//            timer.cancel();
//            timer.purge();
//        }
    }


//    private class TrackTimerTask extends TimerTask {
//
//        @Override
//        public void run() {
//            if (context instanceof Activity) {
//                ((Activity) context).runOnUiThread(() -> {
//                    if (context != null) {
//                        try {
//                            if (NetworkUtils.isNetworkConnected(context))
//                                loadDashboard(true);
//                            else
//                                mView.showSnackBar(context.getString(R.string.no_internet_connection));
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//            }
//        }
//    }


}
