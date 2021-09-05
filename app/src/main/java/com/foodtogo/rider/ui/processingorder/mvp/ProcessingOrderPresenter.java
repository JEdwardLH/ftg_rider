package com.foodtogo.rider.ui.processingorder.mvp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.foodtogo.rider.R;
import com.foodtogo.rider.model.BaseResponse;
import com.foodtogo.rider.model.orders.OrderRequest;
import com.foodtogo.rider.model.processingorders.ProcessingOrderResponse;
import com.foodtogo.rider.util.NetworkUtils;
import com.foodtogo.rider.util.PreferenceUtils;

import java.util.ArrayList;

import static com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys.LANGUAGE;

/**
 * The type Processing order presenter.
 */
public class ProcessingOrderPresenter implements ProcessingOrderContractor.Presenter {
    private ProcessingOrderModal modal;
    private ProcessingOrderContractor.View mView;
    private Context context;

    /**
     * Instantiates a new Processing order presenter.
     *
     * @param mView   the m view
     * @param context the context
     */
    public ProcessingOrderPresenter(ProcessingOrderContractor.View mView, Context context) {
        this.modal = new ProcessingOrderModal(this, context);
        this.mView = mView;
        this.context = context;
    }

    @Override
    public void getProcessingOrders(String pageCount) {
        OrderRequest request = new OrderRequest();
        request.setLang(PreferenceUtils.readString(context, LANGUAGE, "en"));
        request.setPageNo(pageCount);
        modal.requestProcessingOrder(request);
    }

    @Override
    public void processingOrderSuccess(BaseResponse<ProcessingOrderResponse> response) {
        if (response.isTokenExpired()) {
            mView.showTokenExpired(response.getMessage());
        } else if (response.isSuccess()) {
            mView.loadOrders(response.isNoItem()
                    ? new ArrayList<>()
                    : response.getData().getOrderNew());
        } else {
            mView.showWarning(response.getMessage());
        }
    }

    @Override
    public void processingOrderError(String error) {
        mView.loadOrders(null);
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

    @Override
    public void callPhone(String phone) {
        if (NetworkUtils.isSimSupport(context)) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phone));//change the number
            context.startActivity(callIntent);
        } else {
            mView.showError(context.getString(R.string.mobile_call));
        }
    }
}
