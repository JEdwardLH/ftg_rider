package com.foodtogo.rider.ui.deliveredorder.mvp;

import android.content.Context;

import com.foodtogo.rider.model.BaseResponse;
import com.foodtogo.rider.model.deliveredorders.DeliveredOrderRequest;
import com.foodtogo.rider.model.deliveredorders.DeliveredResponse;
import com.foodtogo.rider.util.PreferenceUtils;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

import static com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys.LANGUAGE;

/**
 * The type Delivered order presenter.
 */
public class DeliveredOrderPresenter implements DeliveredOrderContractor.Presenter {
    private DeliveredOrderModal modal;
    private DeliveredOrderContractor.View mView;
    private Context context;

    /**
     * Instantiates a new Delivered order presenter.
     *
     * @param mView   the m view
     * @param context the context
     */
    public DeliveredOrderPresenter(DeliveredOrderContractor.View mView, Context context) {
        modal = new DeliveredOrderModal(this, context);
        this.mView = mView;
        this.context = context;
    }

    /**
     * The Is loading first.
     */
    boolean isLoadingFirst = true;


    /**
     * The Disposable.
     */
    Disposable disposable;


    @Override
    public void getDeliveredOrders(String fromDate, String toDate, String pageNo, String searchText) {
        DeliveredOrderRequest request = new DeliveredOrderRequest();
        request.setLang(PreferenceUtils.readString(context, LANGUAGE, "en"));
        request.setFromDate(fromDate);
        request.setToDate(toDate);
        request.setPageNo(pageNo);
        request.setSearchText(searchText);
        if (pageNo.equals("1")) {
            if (isLoadingFirst) {
                mView.showLoadingView();
            } else {
                mView.showSearchLoading();
            }
        }

        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();
        disposable = modal.requestDeliveredOrders(request);
    }

    @Override
    public void deliveredOrderSuccess(BaseResponse<DeliveredResponse> response) {

        mView.hideLoadingView();
        mView.hideSearchLoading();

        if (response.isTokenExpired()) {
            mView.showTokenExpired(response.getMessage());
        } else if (response.isSuccess()) {
            isLoadingFirst = false;
            mView.loadDeliveredOrders(response.isNoItem()
                    ? new ArrayList<>()
                    : response.getData().getNewOrderList());
        } else {
            mView.showError(response.getMessage());
        }
    }

    @Override
    public void deliveredOrderError(String msg) {

        if (isLoadingFirst) {
            mView.hideLoadingView();
        } else {
            mView.hideSearchLoading();
        }

        mView.loadDeliveredOrders(null);
    }

    @Override
    public void close() {
        modal.close();
    }

    @Override
    public void loggedInByOtherError(String msg) {
        mView.hideLoadingView();
        mView.hideSearchLoading();
        mView.showLoggedInByAnother(msg);
    }
}
