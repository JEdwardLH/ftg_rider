package com.foodtogo.rider.ui.commissiontransaction.mvp;

import android.content.Context;

import com.foodtogo.rider.model.BaseResponse;
import com.foodtogo.rider.model.commission_transaction.TransactionRequest;
import com.foodtogo.rider.model.commission_transaction.TransactionResponse;
import com.foodtogo.rider.util.PreferenceUtils;

import java.util.ArrayList;

import static com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys.LANGUAGE;

/**
 * The type Cm transaction presenter.
 */
public class CmTransactionPresenter implements CmTransactionContractor.Presenter {
    private CmTransactionContractor.View mView;
    private CmTransactionModal modal;
    private Context context;

    /**
     * Instantiates a new Cm transaction presenter.
     *
     * @param view    the view
     * @param context the context
     */
    public CmTransactionPresenter(CmTransactionContractor.View view, Context context) {
        this.mView = view;
        modal = new CmTransactionModal(this);
        this.context = context;
    }

    @Override
    public void getTransactionDetails(String page, String fromDate, String toDate) {
        TransactionRequest request = new TransactionRequest();
        request.setFromDate(fromDate);
        request.setToDate(toDate);
        request.setLang(PreferenceUtils.readString(context, LANGUAGE, "en"));
        request.setPageNo(page);
        modal.requestTransactionDetails(request);
    }

    @Override
    public void showSuccess(BaseResponse<TransactionResponse> response) {
        if (response.isTokenExpired()) {
            mView.showTokenExpired(response.getMessage());
        } else if (response.isSuccess()) {
            mView.loadTransactionList(response.isNoItem()
                    ? new ArrayList<>()
                    : response.getData().getTransactionList());
        } else {
            mView.loadTransactionList(null);
            mView.showError(response.getMessage());
        }
    }

    @Override
    public void transactionError(String msg) {
        mView.loadTransactionList(null);
    }

    @Override
    public void close() {
        modal.close();
    }

    @Override
    public void loggedInByOtherError(String message) {
        mView.hideLoadingView();
        mView.showLoggedInByAnother(message);
    }
}
