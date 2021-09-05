package com.foodtogo.rider.ui.dashboard.mvp;

import android.content.Context;

import com.foodtogo.rider.BaseView;
import com.foodtogo.rider.data.rest.ApiInterface;
import com.foodtogo.rider.model.BaseResponse;
import com.foodtogo.rider.model.Request;
import com.foodtogo.rider.model.dashboard.DashboardResponse;

/**
 * The interface Dashboard contractor.
 */
public interface DashboardContractor {
    /**
     * The interface View.
     */
    interface View extends BaseView {

        /**
         * Show refresh view.
         */
        void showRefreshView();

        /**
         * Hide refresh view.
         */
        void hideRefreshView();

        /**
         * Load data.
         *
         * @param dashboardResponse the dashboard response
         */
        void loadData(DashboardResponse dashboardResponse);

        /**
         * Exit app on sign out.
         */
        void exitAppOnSignOut();

        /**
         * Show snack bar.
         *
         * @param msg the msg
         */
        void showSnackBar(String msg);
    }

    /**
     * The interface Presenter.
     */
    interface Presenter {

        /**
         * Start timer.
         *
         * @param context the context
         */
        void startTimer(Context context);

        /**
         * Stop timer.
         */
        void stopTimer();

        /**
         * Close.
         */
        void close();

        /**
         * Load dashboard.
         *
         * @param isRefresh the is refresh
         */
        void loadDashboard(boolean isRefresh);

        /**
         * Dashboard success.
         *
         * @param response the response
         */
        void dashboardSuccess(BaseResponse<DashboardResponse> response);

        /**
         * Dashboard error.
         *
         * @param error the error
         */
        void dashboardError(String error);

        /**
         * Error signout.
         *
         * @param error the error
         */
        void errorSignout(String error);

        /**
         * Sign out clicked.
         */
        void signOutClicked();

        /**
         * Exit app.
         *
         * @param baseResponse the base response
         */
        void exitApp(BaseResponse baseResponse);
    }

    /**
     * The interface Model.
     */
    interface Model {
        /**
         * Request dashboard.
         *
         * @param apiInterface the api interface
         * @param request      the request
         */
        void requestDashboard(ApiInterface apiInterface, Request request);

        /**
         * Request sign out.
         *
         * @param apiInterface the api interface
         */
        void requestSignOut(ApiInterface apiInterface);

        /**
         * Close.
         */
        void close();

    }
}
