package com.foodtogo.rider.ui.neworderfragment.mvp;

import com.google.android.gms.maps.model.LatLng;
import com.foodtogo.rider.BaseView;
import com.foodtogo.rider.model.BaseResponse;
import com.foodtogo.rider.model.Request;
import com.foodtogo.rider.model.neworders.NewOrdersResponse;
import com.foodtogo.rider.model.neworders.OrderNew;

import java.util.List;


/**
 * The interface New order contractor.
 */
public interface NewOrderContractor {
    /**
     * The interface View.
     */
    interface View extends BaseView {

        /**
         * Re load.
         */
        void reLoad();

        /**
         * Load orders.
         *
         * @param orderNew the order new
         */
        void loadOrders(List<OrderNew> orderNew);

        /**
         * Accept reject success.
         *
         * @param msg the msg
         */
        void acceptRejectSuccess(String msg);

        /**
         * Update time.
         *
         * @param result the result
         */
        void updateTime(String result);

        /**
         * Send order accept details.
         *
         * @param acceptJson the accept json
         * @param orderId    the order id
         * @param storeId    the store id
         */
        void sendOrderAcceptDetails(String acceptJson, String orderId, String storeId);

    }

    /**
     * The interface Presenter.
     */
    interface Presenter {
        /**
         * Gets new order data.
         *
         * @param pageNo the page no
         */
        void getNewOrderData(String pageNo);

        /**
         * Order success.
         *
         * @param response the response
         */
        void orderSuccess(BaseResponse<NewOrdersResponse> response);

        /**
         * Accept reject success.
         *
         * @param msg the msg
         */
        void acceptRejectSuccess(String msg,String status);

        /**
         * New order error.
         *
         * @param error the error
         */
        void newOrderError(String error);

        /**
         * Error accept reject.
         *
         * @param error the error
         */
        void errorAcceptReject(String error);

        /**
         * Accept order.
         *
         * @param status      the status
         * @param orderId     the order id
         * @param storeId     the store id
         * @param storeLatLng the store lat lng
         */
        void acceptOrder(String status, String orderId, String storeId, LatLng storeLatLng);

        /**
         * Reject order.
         *
         * @param status  the status
         * @param orderId the order id
         * @param storeId the store id
         * @param reason  the reason
         */
        void rejectOrder(String status, String orderId, String storeId, String reason);

        /**
         * Close.
         */
        void close();

        /**
         * Accept with estimated time.
         *
         * @param time the time
         */
        void acceptWithEstimatedTime(int[] time);
        void loggedInByAnother(String msg);
        void callPhone(String phone);

    }

    /**
     * The interface Model.
     */
    interface Model {
        /**
         * Request new order data.
         *
         * @param request the request
         */
        void requestNewOrderData(Request request);

        /**
         * Request to acceptor reject.
         *
         * @param status       the status
         * @param orderId      the order id
         * @param storeId      the store id
         * @param reason       the reason
         * @param estimateHour the estimate hour
         * @param min          the min
         */
        void requestToAcceptorReject(String status,
                                     String orderId,
                                     String storeId,
                                     String reason,
                                     String estimateHour,
                                     String min);

        /**
         * Close.
         */
        void close();
    }
}
