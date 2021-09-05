package com.foodtogo.rider.ui.invoice.mvp;

import android.content.Context;

import com.foodtogo.rider.model.invoice.InvoiceRequest;
import com.foodtogo.rider.model.invoice.InvoiceResponse;
import com.foodtogo.rider.util.PreferenceUtils;

import static com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys.LANGUAGE;

/**
 * The type Invoice presenter.
 */
public class InvoicePresenter implements InvoiceContractor.Presenter {
    private InvoiceContractor.View mView;
    private Context context;
    private InvoiceModal modal;

    /**
     * Instantiates a new Invoice presenter.
     *
     * @param view    the view
     * @param context the context
     */
    public InvoicePresenter(InvoiceContractor.View view, Context context) {
        this.mView = view;
        this.context = context;
        this.modal = new InvoiceModal(this, context);

    }

    @Override
    public void getInvoiceDetail(String storeId, String orderId) {
        InvoiceRequest request = new InvoiceRequest();
        request.setLang(PreferenceUtils.readString(context, LANGUAGE, "en"));
        request.setOrderId(orderId);
        request.setStoreId(storeId);
        modal.requestInvoiceDetail(request);
    }

    @Override
    public void invoiceError(String msg) {
        mView.hideLoadingView();
        mView.showError(msg);
    }

    @Override
    public void invoiceError(int msg) {
        mView.hideLoadingView();
        mView.showError(msg);
    }

    @Override
    public void invoiceSuccess(InvoiceResponse response) {
        mView.hideLoadingView();
        mView.bindViewOnSuccess(response);
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
