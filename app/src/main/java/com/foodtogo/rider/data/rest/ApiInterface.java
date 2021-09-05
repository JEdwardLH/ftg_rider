package com.foodtogo.rider.data.rest;


import com.foodtogo.rider.model.BaseResponse;
import com.foodtogo.rider.model.CommonResponse;
import com.foodtogo.rider.model.changepassword.ChangePasswordRequest;
import com.foodtogo.rider.model.changepassword.ChangePasswordResponse;
import com.foodtogo.rider.model.Request;
import com.foodtogo.rider.model.commission_transaction.TransactionRequest;
import com.foodtogo.rider.model.commission_transaction.TransactionResponse;
import com.foodtogo.rider.model.commissiontracking.CommissionTrackingResponse;
import com.foodtogo.rider.model.commissiontracking.PayRequest;
import com.foodtogo.rider.model.dashboard.DashboardResponse;
import com.foodtogo.rider.model.deliveredorders.DeliveredOrderRequest;
import com.foodtogo.rider.model.deliveredorders.DeliveredResponse;
import com.foodtogo.rider.model.directions.DirectionResponse;
import com.foodtogo.rider.model.earningreports.EarningReportRequest;
import com.foodtogo.rider.model.earningreports.EarningReportsResponse;
import com.foodtogo.rider.model.forgotpassword.ForgotPasswordRequest;
import com.foodtogo.rider.model.forgotpassword.ForgotPasswordResponse;
import com.foodtogo.rider.model.geocodeaddress.GeoCodeAddress;
import com.foodtogo.rider.model.invoice.InvoiceRequest;
import com.foodtogo.rider.model.invoice.InvoiceResponse;
import com.foodtogo.rider.model.login.LoginRequest;
import com.foodtogo.rider.model.login.LoginResponse;
import com.foodtogo.rider.model.neworders.AcceptOrRejectOrderRequest;
import com.foodtogo.rider.model.neworders.NewOrdersResponse;
import com.foodtogo.rider.model.orders.OrderRequest;
import com.foodtogo.rider.model.payment.PaymentConversionResponse;
import com.foodtogo.rider.model.payment.PaymentRequest;
import com.foodtogo.rider.model.payment.PaymentConversionRequest;
import com.foodtogo.rider.model.payment_settings.PaymentSettingResponse;
import com.foodtogo.rider.model.payment_settings.UpdatePaymentRequest;
import com.foodtogo.rider.model.processingorders.ProcessingOrderResponse;
import com.foodtogo.rider.model.profile.ProfileResponse;
import com.foodtogo.rider.model.profiledetails.ProfileDetailResponse;
import com.foodtogo.rider.model.sendOtp.SendOtpRequest;
import com.foodtogo.rider.model.signout.SignOutResponse;
import com.foodtogo.rider.model.signout.SignoutRequest;
import com.foodtogo.rider.model.splash.SplashResponse;
import com.foodtogo.rider.model.trackOrder.TrackOrderRequest;
import com.foodtogo.rider.model.trackOrder.TrackOrderResponse;
import com.foodtogo.rider.model.trackOrder.UpdateLocationRequest;
import com.foodtogo.rider.model.trackOrder.UpdateStatusRequest;
import com.foodtogo.rider.model.workinghours.WorkingHoursRequest;
import com.foodtogo.rider.model.workinghours.getworkinghour.GetWorkingHourResponse;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * The interface Api interface.
 */
public interface ApiInterface {

    /**
     * Splash load single.
     *
     * @param request the request
     * @return the single
     */
    @POST(ApiEndPoints.SPLASH)
    Single<BaseResponse<SplashResponse>> splashLoad(@Body Request request);

    /**
     * Sign in single.
     *
     * @param loginRequest the login request
     * @return the single
     */
    @POST(ApiEndPoints.LOGIN)
    Single<BaseResponse<LoginResponse>> signIn(@Body LoginRequest loginRequest);

    /**
     * Dashboard single.
     *
     * @param commonRequest the common request
     * @return the single
     */
    @POST(ApiEndPoints.DASHBOARD)
    Single<BaseResponse<DashboardResponse>> dashboard(@Body Request commonRequest);

    /**
     * Sign out single.
     *
     * @param signoutRequest the signout request
     * @return the single
     */
    @POST(ApiEndPoints.SIGN_OUT)
    Single<BaseResponse<SignOutResponse>> signOut(@Body SignoutRequest signoutRequest);

    /**
     * Forgot password single.
     *
     * @param forgotPasswordRequest the forgot password request
     * @return the single
     */
    @POST(ApiEndPoints.FORGOT_PASSWORD)
    Single<BaseResponse<ForgotPasswordResponse>> forgotPassword(@Body ForgotPasswordRequest forgotPasswordRequest);

    /**
     * Change password single.
     *
     * @param changePasswordRequest the change password request
     * @return the single
     */
    @POST(ApiEndPoints.CHANGE_PASSWORD)
    Single<BaseResponse<ChangePasswordResponse>> changePassword(@Body ChangePasswordRequest changePasswordRequest);

    /**
     * Profile update single.
     *
     * @param lang               the lang
     * @param delivery_fname     the delivery fname
     * @param delivery_lname     the delivery lname
     * @param delivery_location  the delivery location
     * @param delivery_latitude  the delivery latitude
     * @param delivery_longitude the delivery longitude
     * @param delivery_status    the delivery status
     * @param delivery_vehicle   the delivery vehicle
     * @param order_limit        the order limit
     * @param delivery_email     the delivery email
     * @param delivery_phone     the delivery phone
     * @param image              the image
     * @param license            the license
     * @param addressProof       the address proof
     * @return the single
     */
    @POST(ApiEndPoints.UPDATE_PROFILE)
    @Multipart
    Single<BaseResponse<ProfileResponse>> profileUpdate(@Part("lang") RequestBody lang,
                                                        @Part("delivery_fname") RequestBody delivery_fname, @Part("delivery_lname") RequestBody delivery_lname, @Part("delivery_location") RequestBody delivery_location, @Part("delivery_latitude") RequestBody delivery_latitude,
                                                        @Part("delivery_longitude") RequestBody delivery_longitude, @Part("delivery_status") RequestBody delivery_status, @Part("delivery_vehicle") RequestBody delivery_vehicle, @Part("order_limit") RequestBody order_limit,
                                                        @Part("delivery_email") RequestBody delivery_email, @Part("delivery_phone") RequestBody delivery_phone, @Part MultipartBody.Part image, @Part MultipartBody.Part license, @Part MultipartBody.Part addressProof);

    /**
     * Profile update with otp single.
     *
     * @param lang               the lang
     * @param otp                the otp
     * @param currentOtp         the current otp
     * @param delivery_fname     the delivery fname
     * @param delivery_lname     the delivery lname
     * @param delivery_location  the delivery location
     * @param delivery_latitude  the delivery latitude
     * @param delivery_longitude the delivery longitude
     * @param delivery_status    the delivery status
     * @param delivery_vehicle   the delivery vehicle
     * @param order_limit        the order limit
     * @param delivery_email     the delivery email
     * @param delivery_phone     the delivery phone
     * @param image              the image
     * @return the single
     */
    @POST(ApiEndPoints.UPDATE_PROFILE_OTP)
    @Multipart
    Single<BaseResponse<CommonResponse>> profileUpdateWithOtp(@Part("lang") RequestBody lang, @Part("otp") RequestBody otp, @Part("current_otp") RequestBody currentOtp,
                                                              @Part("delivery_fname") RequestBody delivery_fname, @Part("delivery_lname") RequestBody delivery_lname, @Part("delivery_location") RequestBody delivery_location, @Part("delivery_latitude") RequestBody delivery_latitude,
                                                              @Part("delivery_longitude") RequestBody delivery_longitude, @Part("delivery_status") RequestBody delivery_status, @Part("delivery_vehicle") RequestBody delivery_vehicle, @Part("order_limit") RequestBody order_limit,
                                                              @Part("delivery_email") RequestBody delivery_email, @Part("delivery_phone") RequestBody delivery_phone, @Part MultipartBody.Part image);

    /**
     * Gets profile data.
     *
     * @param commonRequest the common request
     * @return the profile data
     */
    @POST(ApiEndPoints.GET_PROFILE)
    Single<BaseResponse<ProfileDetailResponse>> getProfileData(@Body Request commonRequest);

    /**
     * Update working hour single.
     *
     * @param request the request
     * @return the single
     */
    @POST(ApiEndPoints.UPDATE_WORKING_HOUR)
    Single<BaseResponse<CommonResponse>> updateWorkingHour(@Body WorkingHoursRequest request);

    /**
     * Gets working hour.
     *
     * @param request the request
     * @return the working hour
     */
    @POST(ApiEndPoints.GET_WORKING_HOUR)
    Single<BaseResponse<GetWorkingHourResponse>> getWorkingHour(@Body Request request);

    /**
     * Gets new orders.
     *
     * @param request the request
     * @return the new orders
     */
    @POST(ApiEndPoints.GET_NEW_ORDERS)
    Single<BaseResponse<NewOrdersResponse>> getNewOrders(@Body Request request);

    /**
     * Accept or reject order single.
     *
     * @param request the request
     * @return the single
     */
    @POST(ApiEndPoints.ACCEPT_REJECT_ORDER)
    Single<BaseResponse<CommonResponse>> acceptOrRejectOrder(@Body AcceptOrRejectOrderRequest request);

    /**
     * Gets processing orders.
     *
     * @param request the request
     * @return the processing orders
     */
    @POST(ApiEndPoints.GET_PROCESSING_ORDER)
    Single<BaseResponse<ProcessingOrderResponse>> getProcessingOrders(@Body OrderRequest request);

    /**
     * Gets delivered orders.
     *
     * @param request the request
     * @return the delivered orders
     */
    @POST(ApiEndPoints.GET_DELIVERED_ORDER)
    Single<BaseResponse<DeliveredResponse>> getDeliveredOrders(@Body DeliveredOrderRequest request);

    /**
     * Gets earning reports.
     *
     * @param request the request
     * @return the earning reports
     */
    @POST(ApiEndPoints.GET_EARNING_REPORTS)
    Single<BaseResponse<EarningReportsResponse>> getEarningReports(@Body EarningReportRequest request);

    /**
     * Gets invoice detail.
     *
     * @param request the request
     * @return the invoice detail
     */
    @POST(ApiEndPoints.GET_INVOICE_DETAIL)
    Single<BaseResponse<InvoiceResponse>> getInvoiceDetail(@Body InvoiceRequest request);

    /**
     * Gets track order details.
     *
     * @param request the request
     * @return the track order details
     */
    @POST(ApiEndPoints.TRACK_ORDER)
    Single<BaseResponse<TrackOrderResponse>> getTrackOrderDetails(@Body TrackOrderRequest request);

    /**
     * Update order status single.
     *
     * @param request the request
     * @return the single
     */
    @POST(ApiEndPoints.CHANGE_ORDER_STATUS)
    Single<BaseResponse<CommonResponse>> updateOrderStatus(@Body UpdateStatusRequest request);

    /**
     * Gets commission tracking.
     *
     * @param request the request
     * @return the commission tracking
     */
    @POST(ApiEndPoints.COMMISSION_TRACKING)
    Single<BaseResponse<CommissionTrackingResponse>> getCommissionTracking(@Body Request request);

    /**
     * Gets commission transaction.
     *
     * @param request the request
     * @return the commission transaction
     */
    @POST(ApiEndPoints.COMMISSION_TRANSACTION)
    Single<BaseResponse<TransactionResponse>> getCommissionTransaction(@Body TransactionRequest request);

    /**
     * Gets payment settings.
     *
     * @param request the request
     * @return the payment settings
     */
    @POST(ApiEndPoints.PAYMENT_SETTINGS)
    Single<BaseResponse<PaymentSettingResponse>> getPaymentSettings(@Body Request request);

    /**
     * Update settings single.
     *
     * @param request the request
     * @return the single
     */
    @POST(ApiEndPoints.UPDATE_SETTINGS)
    Single<BaseResponse<CommonResponse>> updateSettings(@Body UpdatePaymentRequest request);

    /**
     * Update location single.
     *
     * @param request the request
     * @return the single
     */
    @POST(ApiEndPoints.UPDATE_LOCATION)
    Single<BaseResponse<CommonResponse>> updateLocation(@Body UpdateLocationRequest request);

    /**
     * Amount conversion single.
     *
     * @param request the request
     * @return the single
     */
    @POST(ApiEndPoints.AMOUNT_CONVERSION)
    Single<BaseResponse<PaymentConversionResponse>> amountConversion(@Body PaymentConversionRequest request);

    /**
     * Payment request single.
     *
     * @param request the request
     * @return the single
     */
    @POST(ApiEndPoints.PAY_REQUEST)
    Single<BaseResponse> paymentRequest(@Body PayRequest request);

    /**
     * Payment to pay single.
     *
     * @param request the request
     * @return the single
     */
    @POST(ApiEndPoints.PAY_AMOUNT)
    Single<BaseResponse> paymentToPay(@Body PaymentRequest request);

    /**
     * Send otp single.
     *
     * @param request the request
     * @return the single
     */
    @POST(ApiEndPoints.SEND_OTP)
    Single<BaseResponse> sendOtp(@Body SendOtpRequest request);

    /**
     * Gets address.
     *
     * @param latlng the latlng
     * @param apiKey the api key
     * @return the address
     */
    @GET(ApiEndPoints.ADDRESS_API)
    Single<GeoCodeAddress> getAddress(@Query(ApiKeys.LATLNG) String latlng,
                                      @Query(ApiKeys.KEY) String apiKey);

    @GET(ApiEndPoints.DIRECTION_API)
    Single<DirectionResponse> getPolylineData(@Query(ApiKeys.ORIGIN) String origin,
                                              @Query(ApiKeys.DESTINATION) String destination,
                                              @Query(ApiKeys.WAY_POINTS) String wayPoints,
                                              @Query(ApiKeys.SENSOR) String sensor,
                                              @Query(ApiKeys.MODE) String mode,
                                              @Query(ApiKeys.KEY) String apiKey);

      @GET(ApiEndPoints.DIRECTION_API)
  Single<DirectionResponse> getPolylineDataWithoutWaypoints(@Query(ApiKeys.ORIGIN) String origin,
                                            @Query(ApiKeys.DESTINATION) String destination,
                                            @Query(ApiKeys.SENSOR) String sensor,
                                            @Query(ApiKeys.MODE) String mode,
                                            @Query(ApiKeys.KEY) String apiKey);


    /**
     * forcing trigger the cronjob to
     * push the notification for rider
     */
    @GET(ApiEndPoints.ORDERS_AUTO_ALLOCATION)
    Call<ResponseBody> forcedOrderAutoAllocation();
}
