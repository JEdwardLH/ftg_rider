package com.foodtogo.rider.ui.processingorder.mvp;

import com.foodtogo.rider.BaseView;
import com.foodtogo.rider.model.BaseResponse;
import com.foodtogo.rider.model.neworders.OrderNew;
import com.foodtogo.rider.model.orders.OrderRequest;
import com.foodtogo.rider.model.processingorders.ProcessingOrderResponse;

import java.util.List;

/**
 * The interface Processing order contractor.
 */
public interface ProcessingOrderContractor {
    /**
     * The interface View.
     */
    interface View extends BaseView {
        /**
         * Load orders.
         *
         * @param response the response
         */
        void loadOrders(List<OrderNew> response);
    }

    /**
     * The interface Presenter.
     */
    interface Presenter {
        /**
         * Gets processing orders.
         *
         * @param pageCount the page count
         */
        void getProcessingOrders(String pageCount);

        /**
         * Processing order success.
         *
         * @param response the response
         */
        void processingOrderSuccess(BaseResponse<ProcessingOrderResponse> response);

        /**
         * Processing order error.
         *
         * @param error the error
         */
        void processingOrderError(String error);

        /**
         * Close.
         */
        void close();
        void loggedInByAnotherError(String msg);
        void callPhone(String phone);
    }

    /**
     * The interface Modal.
     */
    interface Modal {
        /**
         * Request processing order.
         *
         * @param request the request
         */
        void requestProcessingOrder(OrderRequest request);

        /**
         * Close.
         */
        void close();
    }
}
