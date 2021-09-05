package com.foodtogo.rider.data.rest;


/**
 * The type Api end points.
 */
public class ApiEndPoints {

    //public static final String SERVER_ENDPOINT="http://demo5.pofi5.com/api/";
    public static final String SERVER_ENDPOINT="https://foodtogodeliveryph.com/api/";
    //public static final String SERVER_ENDPOINT="http://54.80.140.43:81/hungry_now/api/";
    //public static final String SERVER_ENDPOINT="http://54.196.121.26/api/";

    /**
     * The constant SPLASH.
     */
    public static final String SPLASH = "delivery_home_page";
    /**
     * The constant LOGIN.
     */
    public static final String LOGIN = "delivery_person_login";
    /**
     * The constant DASHBOARD.
     *
     */
    public static final String DASHBOARD = "delivery/dashboard";
    /**
     * The constant SIGN_OUT.
     */
    public static final String SIGN_OUT = "delivery/delivery_logout";
    /**
     * The constant FORGOT_PASSWORD.
     */
    public static final String FORGOT_PASSWORD = "delivery_forgot_password";
    /**
     * The constant UPDATE_PROFILE.
     */
    public static final String UPDATE_PROFILE = "delivery/update_profile";
    /**
     * The constant CHANGE_PASSWORD.
     */
    public static final String CHANGE_PASSWORD = "delivery/change_password";
    /**
     * The constant GET_PROFILE.
     */
    public static final String GET_PROFILE = "delivery/delivery_profile";
    /**
     * The constant UPDATE_PROFILE_OTP.
     */
    public static final String UPDATE_PROFILE_OTP = "delivery/update_profile_withOtp";
    /**
     * The constant UPDATE_WORKING_HOUR.
     */
    public static final String UPDATE_WORKING_HOUR = "delivery/add_update_working_hours";
    /**
     * The constant GET_WORKING_HOUR.
     */
    public static final String GET_WORKING_HOUR = "delivery/view_working_hours";
    /**
     * The constant GET_NEW_ORDERS.
     */
    public static final String GET_NEW_ORDERS = "delivery/new_orders";
    /**
     * The constant ACCEPT_REJECT_ORDER.
     */
    public static final String ACCEPT_REJECT_ORDER = "delivery/accept_reject_order";
    /**
     * The constant GET_PROCESSING_ORDER.
     */
    public static final String GET_PROCESSING_ORDER = "delivery/assigned_orders";
    /**
     * The constant GET_DELIVERED_ORDER.
     */
    public static final String GET_DELIVERED_ORDER = "delivery/delivered_orders";
    /**
     * The constant GET_EARNING_REPORTS.
     */
    public static final String GET_EARNING_REPORTS = "delivery/earning_report";
    /**
     * The constant GET_INVOICE_DETAIL.
     */
    public static final String GET_INVOICE_DETAIL = "delivery/invoice_detail";
    /**
     * The constant TRACK_ORDER.
     */
    public static final String TRACK_ORDER = "delivery/order_tracking";
    /**
     * The constant CHANGE_ORDER_STATUS.
     */
    public static final String CHANGE_ORDER_STATUS = "delivery/change_order_status";
    /**
     * The constant COMMISSION_TRACKING.
     */
    public static final String COMMISSION_TRACKING = "delivery/commission_tracking";
    /**
     * The constant COMMISSION_TRANSACTION.
     */
    public static final String COMMISSION_TRANSACTION = "delivery/commission_transaction";
    /**
     * The constant PAYMENT_SETTINGS.
     */
    public static final String PAYMENT_SETTINGS = "delivery/get_payment_settings";
    /**
     * The constant UPDATE_SETTINGS.
     */
    public static final String UPDATE_SETTINGS = "delivery/update_payment_setting";
    /**
     * The constant PAY_REQUEST.
     */
    public static final String PAY_REQUEST = "delivery/pay_request";
    /**
     * The constant PAY_AMOUNT.
     */
    public static final String PAY_AMOUNT = "delivery/commission_payment";
    /**
     * The constant UPDATE_LOCATION.
     */
    public static final String UPDATE_LOCATION = "delivery/update_location";
    /**
     * The constant AMOUNT_CONVERSION.
     */
    public static final String AMOUNT_CONVERSION = "convert_currency";
    /**
     * The constant SEND_OTP.
     */
    public static final String SEND_OTP = "delivery/send_otp";
    /**
     * The Address api.
     */
    static final String ADDRESS_API = "https://maps.googleapis.com/maps/api/geocode/json";

    static final String DIRECTION_API = "https://maps.googleapis.com/maps/api/directions/json";

    /**
     * The constant ORDERS_AUTO_ALLOCATION.
     * use for cronjob
     */
    public static final String ORDERS_AUTO_ALLOCATION  = "orders-auto-allocation";

}
