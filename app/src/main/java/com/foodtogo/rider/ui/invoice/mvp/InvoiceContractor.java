package com.foodtogo.rider.ui.invoice.mvp;

import com.foodtogo.rider.BaseView;
import com.foodtogo.rider.model.invoice.InvoiceRequest;
import com.foodtogo.rider.model.invoice.InvoiceResponse;

/**
 * The interface Invoice contractor.
 */
public interface InvoiceContractor {
    /**
     * The interface View.
     */
    interface View extends BaseView {
        /**
         * Bind view on success.
         *
         * @param response the response
         */
        void bindViewOnSuccess(InvoiceResponse response);

        /**
         * Show network layout.
         */
        void showNetworkLayout();
    }

    /**
     * The interface Presenter.
     */
    interface Presenter {
        /**
         * Gets invoice detail.
         *
         * @param storeId the store id
         * @param orderId the order id
         */
        void getInvoiceDetail(String storeId, String orderId);

        /**
         * Invoice error.
         *
         * @param msg the msg
         */
        void invoiceError(String msg);

        /**
         * Invoice error.
         *
         * @param msg the msg
         */
        void invoiceError(int msg);

        /**
         * Invoice success.
         *
         * @param response the response
         */
        void invoiceSuccess(InvoiceResponse response);

        /**
         * Close.
         */
        void close();

        void loggedInByAnotherError(String msg);
    }

    /**
     * The interface Modal.
     */
    interface Modal {
        /**
         * Request invoice detail.
         *
         * @param request the request
         */
        void requestInvoiceDetail(InvoiceRequest request);

        /**
         * Close.
         */
        void close();
    }
}
