package com.foodtogo.rider.ui.earning_reports.mvp;

import com.foodtogo.rider.BaseView;
import com.foodtogo.rider.model.earningreports.EarningReportRequest;
import com.foodtogo.rider.model.earningreports.EarningReportsResponse;

/**
 * The interface Earning reports contractor.
 */
public interface EarningReportsContractor {
    /**
     * The interface View.
     */
    interface View extends BaseView {
        /**
         * Bind report response.
         *
         * @param response the response
         */
        void bindReportResponse(EarningReportsResponse response);

        /**
         * Show network layout.
         */
        void showNetworkLayout();

        /**
         * Show snack.
         *
         * @param msg the msg
         */
        void showSnack(String msg);
    }

    /**
     * The interface Presenter.
     */
    interface Presenter {
        /**
         * Gets earning report.
         *
         * @param fromDate the from date
         * @param toDate   the to date
         */
        void getEarningReport(String fromDate, String toDate);

        /**
         * Earning report success.
         *
         * @param response the response
         */
        void earningReportSuccess(EarningReportsResponse response);

        /**
         * Earning report error.
         *
         * @param msg the msg
         */
        void earningReportError(String msg);

        /**
         * Earning report error.
         *
         * @param msg the msg
         */
        void earningReportError(int msg);

        /**
         * Retry clicked.
         */
        void retryClicked();

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
         * Request earning report.
         *
         * @param request the request
         */
        void requestEarningReport(EarningReportRequest request);

        /**
         * Close.
         */
        void close();
    }
}
