package com.foodtogo.rider.ui.commissiontracking.mvp;

import com.foodtogo.rider.BaseView;
import com.foodtogo.rider.model.Request;
import com.foodtogo.rider.model.commissiontracking.CommissionTrackingResponse;
import com.foodtogo.rider.model.commissiontracking.PayRequest;

/**
 * The interface Cm tracking contractor.
 */
public interface CmTrackingContractor {
    /**
     * The interface View.
     */
    interface View extends BaseView {
        /**
         * Show cm tracking data.
         *
         * @param response the response
         */
        void showCmTrackingData(CommissionTrackingResponse response);

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
         * Close.
         */
        void close();

        /**
         * Gets cm tracking details.
         */
        void getCmTrackingDetails();

        /**
         * Cm tracking success.
         *
         * @param response the response
         */
        void cmTrackingSuccess(CommissionTrackingResponse response);

        /**
         * Cm tracking error.
         *
         * @param msg the msg
         */
        void cmTrackingError(String msg);

        /**
         * Cm tracking error.
         *
         * @param msg the msg
         */
        void cmTrackingError(int msg);

        /**
         * Retry clicked.
         */
        void retryClicked();

        /**
         * Pay request success.
         *
         * @param msg the msg
         */
        void payRequestSuccess(String msg);

        /**
         * Request to receive pay.
         *
         * @param amount the amount
         */
        void requestToReceivePay(String amount);

        void loggedInByOtherError(String msg);

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
         * Request cm tracking details.
         *
         * @param request the request
         */
        void requestCmTrackingDetails(Request request);

        /**
         * Request to pay.
         *
         * @param request the request
         */
        void requestToPay(PayRequest request);
    }
}
