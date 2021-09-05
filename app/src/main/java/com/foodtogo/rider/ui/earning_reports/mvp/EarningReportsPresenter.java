package com.foodtogo.rider.ui.earning_reports.mvp;

import android.content.Context;
import android.os.Handler;

import com.foodtogo.rider.model.earningreports.EarningReportRequest;
import com.foodtogo.rider.model.earningreports.EarningReportsResponse;
import com.foodtogo.rider.util.NetworkUtils;
import com.foodtogo.rider.util.PreferenceUtils;

import static com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys.LANGUAGE;

/**
 * The type Earning reports presenter.
 */
public class EarningReportsPresenter implements EarningReportsContractor.Presenter {
    private EarningReportsModal modal;
    private EarningReportsContractor.View mView;
    private Context context;
    private Handler mWaitHandler;

    /**
     * Instantiates a new Earning reports presenter.
     *
     * @param view    the view
     * @param context the context
     */
    public EarningReportsPresenter(EarningReportsContractor.View view, Context context) {
        this.mView = view;
        this.modal = new EarningReportsModal(this, context);
        this.context = context;
    }

    @Override
    public void getEarningReport(String fromDate, String toDate) {
        EarningReportRequest request = new EarningReportRequest();
        request.setLang(PreferenceUtils.readString(context, LANGUAGE, "en"));
        request.setFromDate(fromDate);
        request.setToDate(toDate);
        modal.requestEarningReport(request);
    }

    @Override
    public void earningReportSuccess(EarningReportsResponse response) {
        mView.hideLoadingView();
        mView.bindReportResponse(response);
    }

    @Override
    public void earningReportError(String msg) {
        mView.hideLoadingView();
        mView.showError(msg);
    }

    @Override
    public void earningReportError(int msg) {
        mView.hideLoadingView();
        mView.showError(msg);
    }

    @Override
    public void retryClicked() {
        mView.showLoadingView();
        mWaitHandler = new Handler();
        mWaitHandler.postDelayed(() -> {
            if (NetworkUtils.isNetworkConnected(context)) {
                getEarningReport("", "");
            } else {
                mView.showNetworkLayout();
            }
            mView.hideLoadingView();
        }, 1000);  // 1sec
    }

    @Override
    public void close() {
        modal.close();
    }

    @Override
    public void loggedInByAnotherError(String msg) {
        mView.hideLoadingView();
        mView.showLoggedInByAnother(msg);
    }
}
