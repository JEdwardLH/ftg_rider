package com.foodtogo.rider.ui.commissiontransaction.mvp;


import com.foodtogo.rider.BaseView;
import com.foodtogo.rider.model.BaseResponse;
import com.foodtogo.rider.model.commission_transaction.TransactionList;
import com.foodtogo.rider.model.commission_transaction.TransactionRequest;
import com.foodtogo.rider.model.commission_transaction.TransactionResponse;

import java.util.List;

/**
 * The interface Cm transaction contractor.
 */
public interface CmTransactionContractor {

    /**
     * The interface View.
     */
    interface View extends BaseView {
        /**
         * Load transaction list.
         *
         * @param transactionList the transaction list
         */
        void loadTransactionList(List<TransactionList> transactionList);
    }

    /**
     * The interface Presenter.
     */
    interface Presenter {
        /**
         * Gets transaction details.
         *
         * @param page     the page
         * @param fromDate the from date
         * @param toDate   the to date
         */
        void getTransactionDetails(String page, String fromDate, String toDate);

        /**
         * Show success.
         *
         * @param response the response
         */
        void showSuccess(BaseResponse<TransactionResponse> response);

        /**
         * Transaction error.
         *
         * @param msg the msg
         */
        void transactionError(String msg);

        /**
         * Close.
         */
        void close();

        void loggedInByOtherError(String message);
    }

    /**
     * The interface Modal.
     */
    interface Modal {
        /**
         * Close.
         */
        void close();

        /**
         * Request transaction details.
         *
         * @param request the request
         */
        void requestTransactionDetails(TransactionRequest request);
    }
}
