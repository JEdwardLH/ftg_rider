package com.foodtogo.rider.ui.payment.mvp;

import com.foodtogo.rider.BaseView;
import com.foodtogo.rider.model.payment.PaymentConversionRequest;
import com.foodtogo.rider.model.payment.PaymentRequest;

/**
 * The interface Payment contractor.
 */
public interface PaymentContractor {
    /**
     * The interface View.
     */
    interface View extends BaseView {
        /**
         * Show pay success.
         *
         * @param msg the msg
         */
        void showPaySuccess(String msg);

        /**
         * Parse pay pal intent.
         *
         * @param amount the amount
         */
        void parsePayPalIntent(String amount);
    }

    /**
     * The interface Presenter.
     */
    interface Presenter {
        /**
         * Close.
         */
        void close();

        /**
         * Pay amount.
         *
         * @param transactionId the transaction id
         * @param amount        the amount
         * @param currency      the currency
         * @param payType       the pay type
         */
        void payAmount(String transactionId, String amount, String currency, String payType);

        /**
         * Paid amount success.
         *
         * @param msg the msg
         */
        void paidAmountSuccess(String msg);

        /**
         * Pay error.
         *
         * @param msg the msg
         */
        void payError(String msg);

        /**
         * Pay error.
         *
         * @param msg the msg
         */
        void payError(int msg);

        /**
         * Amount conversion.
         *
         * @param amount   the amount
         * @param currency the currency
         */
        void amountConversion(String amount, String currency);

        /**
         * Amount conversion success.
         *
         * @param amount the amount
         */
        void amountConversionSuccess(String amount);

        /**
         * Amount conversion error.
         *
         * @param msg the msg
         */
        void amountConversionError(int msg);
        void loggedInByAnotherError(String msg);

    }

    /**
     * The interface Modal.
     */
    interface Modal {
        /**
         * Request to pay.
         *
         * @param request the request
         */
        void requestToPay(PaymentRequest request);

        /**
         * Request to currency conversion.
         *
         * @param request the request
         */
        void requestToCurrencyConversion(PaymentConversionRequest request);

        /**
         * Close.
         */
        void close();
    }
}
