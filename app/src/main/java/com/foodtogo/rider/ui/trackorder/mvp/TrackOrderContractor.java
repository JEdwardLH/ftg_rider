package com.foodtogo.rider.ui.trackorder.mvp;


import com.foodtogo.rider.BaseView;
import com.foodtogo.rider.model.directions.DirectionResponse;
import com.foodtogo.rider.model.sendOtp.SendOtpRequest;
import com.foodtogo.rider.model.trackOrder.TrackOrderRequest;
import com.foodtogo.rider.model.trackOrder.TrackOrderResponse;
import com.foodtogo.rider.model.trackOrder.UpdateStatusRequest;
import com.google.android.gms.maps.model.LatLng;

/**
 * The interface Track order contractor.
 */
public interface TrackOrderContractor {
    /**
     * The interface View.
     */
    interface View extends BaseView {
        /**
         * Bind track order values.
         *
         * @param response the response
         */
        void bindTrackOrderValues(TrackOrderResponse response);



        /**
         * Sent location updates.
         *
         * @param locationData the location data
         */
// void updateTime(String resultJson);
        void sentLocationUpdates(String locationData);

        /**
         * On otp success send mqtt.
         */
        void onOtpSuccessSendMqtt();

        /**
         * Order failed.
         *
         * @param msg the msg
         */
        void orderFailed(String msg);
        void showDirection(DirectionResponse directionResponse);
        void directionAPIError(String error);

        void updateMqttStatus(String status);
    }

    /**
     * The interface Presenter.
     */
    interface Presenter {
        /**
         * Call phone.
         *
         * @param phone the phone
         */
        void callPhone(String phone);

        /**
         * Gets track order.
         *
         * @param storeId the store id
         * @param orderId the order id
         */
        void getTrackOrder(String storeId, String orderId);

        /**
         * Track order success.
         *
         * @param response the response
         */
        void trackOrderSuccess(TrackOrderResponse response);

        /**
         * Update order success.
         *
         * @param msg the msg
         */
        void updateOrderSuccess(String msg);

        /**
         * Track order error.
         *
         * @param msg the msg
         */
        void trackOrderError(String msg);

        /**
         * Track order error.
         *
         * @param msg the msg
         */
        void trackOrderError(int msg);



        /**
         * Close.
         */
// void downloadTimeTaskCall(LatLng origin,LatLng dest);
        void close();

        /**
         * Change status dialog.
         *
         * @param storeId  the store id
         * @param orderId  the order id
         * @param endLoc   the end loc
         * @param cusId    the cus id
         * @param cusPhone the cus phone
         * @param status   the status
         */
        void changeStatusDialog(String storeId, String orderId, LatLng endLoc, String cusId, String cusPhone,int status,String cusEmail);

        /**
         * Convert miles to km double.
         *
         * @param distanceInMiles the distance in miles
         * @return the double
         */
        double convertMilesToKm(double distanceInMiles);

        /**
         * Otp success.
         *
         * @param msg the msg
         */
        void otpSuccess(String msg);

        /**
         * Order failed.
         *
         * @param msg the msg
         */
        void orderFailed(String msg);

        void requestPolyLineData(LatLng originDriver, LatLng destCustomer, LatLng restaurant,boolean showProgress);
        void requestPolyLineWithoutWayPoint(LatLng originDriver, LatLng destCustomer);
        void onDirectionResponse(DirectionResponse directionResponse);
        void directionAPIError(String error);
        void loggedInByAnotherError(String msg);

        void updateMqtt(String status);
    }

    /**
     * The interface Modal.
     */
    interface Modal {

        void requestTrackOrder(TrackOrderRequest request);

        void updateStatus(UpdateStatusRequest request);

        void requestToSendOtp(SendOtpRequest request);
        void close();
        void requestPolyLineData(LatLng originDriver, LatLng destCustomer, LatLng restaurant);

        void requestPolyLineWithoutWayPoints(LatLng originDriver,LatLng destCustomer);
    }
}
