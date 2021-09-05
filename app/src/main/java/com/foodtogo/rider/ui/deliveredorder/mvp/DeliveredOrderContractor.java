package com.foodtogo.rider.ui.deliveredorder.mvp;

import com.foodtogo.rider.BaseView;
import com.foodtogo.rider.model.BaseResponse;
import com.foodtogo.rider.model.deliveredorders.DeliveredOrderRequest;
import com.foodtogo.rider.model.deliveredorders.DeliveredResponse;
import com.foodtogo.rider.model.deliveredorders.NewOrderList;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * The interface Delivered order contractor.
 */
public interface DeliveredOrderContractor {
    /**
     * The interface View.
     */
    interface View extends BaseView {
        /**
         * Load delivered orders.
         *
         * @param orderList the order list
         */
        void loadDeliveredOrders(List<NewOrderList> orderList);


        /**
         * Show search loading.
         */
        void showSearchLoading();

        /**
         * Hide search loading.
         */
        void hideSearchLoading();

    }

    /**
     * The interface Presenter.
     */
    interface Presenter {
        /**
         * Gets delivered orders.
         *
         * @param fromDate   the from date
         * @param toDate     the to date
         * @param pageNo     the page no
         * @param searchText the search text
         */
        void getDeliveredOrders(String fromDate, String toDate, String pageNo, String searchText);

        /**
         * Delivered order success.
         *
         * @param response the response
         */
        void deliveredOrderSuccess(BaseResponse<DeliveredResponse> response);

        /**
         * Delivered order error.
         *
         * @param msg the msg
         */
        void deliveredOrderError(String msg);

        /**
         * Close.
         */
        void close();
        void loggedInByOtherError(String msg);
    }

    /**
     * The interface Modal.
     */
    interface Modal {
        /**
         * Request delivered orders disposable.
         *
         * @param request the request
         * @return the disposable
         */
        Disposable requestDeliveredOrders(DeliveredOrderRequest request);

        /**
         * Close.
         */
        void close();
    }
}
