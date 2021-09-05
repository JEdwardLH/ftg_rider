package com.foodtogo.rider.ui.commissiontracking.mvp;

import android.content.Context;
import android.os.Handler;

import com.foodtogo.rider.model.Request;
import com.foodtogo.rider.model.commissiontracking.CommissionTrackingResponse;
import com.foodtogo.rider.model.commissiontracking.PayRequest;
import com.foodtogo.rider.util.NetworkUtils;
import com.foodtogo.rider.util.PreferenceUtils;

import static com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys.LANGUAGE;

/**
 * The type Cm tracking presenter.
 */
public class CmTrackingPresenter implements CmTrackingContractor.Presenter {
    private CmTrackingContractor.View mView;
    private Context context;
    private CmTrackingModal modal;

    /**
     * Instantiates a new Cm tracking presenter.
     *
     * @param view    the view
     * @param context the context
     */
    public CmTrackingPresenter(CmTrackingContractor.View view, Context context) {
        this.mView = view;
        this.context = context;
        this.modal = new CmTrackingModal(this, context);
    }

    @Override
    public void getCmTrackingDetails() {
        Request request = new Request();
        request.setLang(PreferenceUtils.readString(context, LANGUAGE, "en"));
        modal.requestCmTrackingDetails(request);
    }

    @Override
    public void cmTrackingSuccess(CommissionTrackingResponse response) {
        mView.hideLoadingView();
        mView.showCmTrackingData(response);
    }

    @Override
    public void cmTrackingError(String msg) {
        mView.hideLoadingView();
        mView.showError(msg);
    }

    @Override
    public void cmTrackingError(int msg) {
        mView.hideLoadingView();
        mView.showError(msg);
    }

    @Override
    public void retryClicked() {
        mView.showLoadingView();
        Handler mWaitHandler = new Handler();
        mWaitHandler.postDelayed(() -> {
            if (NetworkUtils.isNetworkConnected(context)) {
                getCmTrackingDetails();
            } else {
                mView.showNetworkLayout();
            }
            mView.hideLoadingView();
        }, 1000);  // 1sec
    }

    @Override
    public void payRequestSuccess(String msg) {
        mView.hideLoadingView();
        mView.showError(msg);
    }

    @Override
    public void requestToReceivePay(String amount) {
        PayRequest request = new PayRequest();
        request.setAmount(amount);
        request.setLang(PreferenceUtils.readString(context, LANGUAGE, "en"));
        modal.requestToPay(request);
    }

    @Override
    public void loggedInByOtherError(String msg) {
        mView.hideLoadingView();
        mView.showLoggedInByAnother(msg);
    }

    @Override
    public void close() {
        modal.close();
    }
}
