package com.foodtogo.rider.ui.trackorder.mvp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;

import com.chaos.view.PinView;
import com.google.android.gms.maps.model.LatLng;
import com.foodtogo.rider.R;
import com.foodtogo.rider.data.source.AppRepository;
import com.foodtogo.rider.model.directions.DirectionResponse;
import com.foodtogo.rider.model.sendOtp.SendOtpRequest;
import com.foodtogo.rider.model.trackOrder.TrackOrderRequest;
import com.foodtogo.rider.model.trackOrder.TrackOrderResponse;
import com.foodtogo.rider.model.trackOrder.UpdateStatusRequest;
import com.foodtogo.rider.util.NetworkUtils;
import com.foodtogo.rider.util.PreferenceUtils;

import org.json.JSONException;
import org.json.JSONObject;

import static com.foodtogo.rider.base.AppConstants.COD;
import static com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys.LANGUAGE;
import static com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys.LATITUDE;
import static com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys.LONGITUDE;
import static com.foodtogo.rider.ui.trackorder.activity.TrackOrderMapsActivity.orderAmount;
import static com.foodtogo.rider.ui.trackorder.activity.TrackOrderMapsActivity.payType;
import static com.foodtogo.rider.ui.trackorder.activity.TrackOrderMapsActivity.webMqttData;
import static com.foodtogo.rider.util.CommonStrings.ORDER_ID;
import static com.foodtogo.rider.util.CommonStrings.STORE_ID;
import static com.foodtogo.rider.util.CommonStrings.TYPE;

/**
 * The type Track order presenter.
 */
public class TrackOrderPresenter implements TrackOrderContractor.Presenter {
    private TrackOrderContractor.View mView;
    private Activity context;
    private TrackOrderModal modal;
    private AppRepository appRepository;


    /**
     * Instantiates a new Track order presenter.
     *
     * @param view          the view
     * @param context       the context
     * @param appRepository the app repository
     */
    public TrackOrderPresenter(TrackOrderContractor.View view, Activity context, AppRepository appRepository) {
        this.mView = view;
        this.context = context;
        this.modal = new TrackOrderModal(this, context);
        this.appRepository = appRepository;
    }

    @Override
    public void callPhone(String phone) {
        if (NetworkUtils.isSimSupport(context)) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phone));//change the number
            context.startActivity(callIntent);
        } else {
            mView.showError(context.getString(R.string.mobile_call));
        }
    }

    @Override
    public void getTrackOrder(String storeId, String orderId) {
        TrackOrderRequest request = new TrackOrderRequest();
        request.setLang(PreferenceUtils.readString(context, LANGUAGE, "en"));
        request.setLatitude(PreferenceUtils.readString(context, LATITUDE, "0.0"));
        request.setLongitude(PreferenceUtils.readString(context, LONGITUDE, "0.0"));
        request.setOrderId(orderId);
        request.setStoreId(storeId);
        modal.requestTrackOrder(request);
    }

    @Override
    public void trackOrderSuccess(TrackOrderResponse response) {
        mView.hideLoadingView();
        mView.bindTrackOrderValues(response);
    }

    @Override
    public void updateOrderSuccess(String msg) {
        mView.showError(msg);
        getTrackOrder(PreferenceUtils.readString(context, STORE_ID, "0"), PreferenceUtils.readString(context, ORDER_ID, "0"));
    }

    @Override
    public void trackOrderError(String msg) {
        mView.hideLoadingView();
        mView.showError(msg);
    }

    @Override
    public void trackOrderError(int msg) {
        mView.hideLoadingView();
        mView.showError(msg);
    }


    private void showConfirmation(String storeId, String orderId, LatLng driverLoc, String title, String message, int status) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context, R.style.MyAlertDialogTheme);
        builder1.setTitle(title);
        builder1.setMessage(message);
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "OK",
                (dialog, id) -> {
                    dialog.cancel();
                    String changeStaus = status == 1 ? "6" : "7";
                    setRequestForStatusChange(storeId, orderId, driverLoc,  "", changeStaus);
                });

        builder1.setNegativeButton(
                "Cancel",
                (dialog, id) -> dialog.cancel());

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void showFailed(String storeId, String orderId, LatLng driverLoc, String title, int status) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context, R.style.MyAlertDialogTheme);
        LayoutInflater inflater = context.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_input, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle(title);
        dialogBuilder.setMessage(context.getResources().getString(R.string.enter_here));
        dialogBuilder.setCancelable(true);
        AppCompatEditText editText = dialogView.findViewById(R.id.editText);
        editText.setHint(context.getResources().getString(R.string.enter_here));
        dialogBuilder.setPositiveButton(
                "OK",
                (dialog, id) -> {
                    dialog.cancel();
                    if (TextUtils.isEmpty(editText.getText().toString())) {
                        Toast.makeText(context, context.getString(R.string.enter_reason), Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        setRequestForStatusChange(storeId, orderId, driverLoc, editText.getText().toString(), "9");

                    }
                });

        dialogBuilder.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

    }


    private void showOtp(String storeId, String orderId, LatLng driverLoc, String cusId, String cusPhone, String title, String cusEmail) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context, R.style.MyAlertDialogTheme);
        LayoutInflater inflater = context.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_pin_view, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle(payType.equals(COD) ? context.getString(R.string.amount_cod, orderAmount) : "");
        dialogBuilder.setMessage(title);
        dialogBuilder.setCancelable(true);
        final PinView etOtp = dialogView.findViewById(R.id.et_otp);
        etOtp.setAnimationEnable(true);
        dialogBuilder.setPositiveButton(
                "OK",
                (dialog, id) -> {
                    dialog.cancel();
                    if (etOtp.getText().toString().length() < 6) {
                        Toast.makeText(context, context.getString(R.string.enter_otp), Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        setRequestForStatusChange(storeId, orderId, driverLoc, "", "8");
                    }
                });

        dialogBuilder.setNegativeButton(
                "RESEND",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        mView.showLoadingView();
                        SendOtpRequest request = new SendOtpRequest();
                        request.setOrder_id(orderId);
                        request.setCustomer_id(cusId);
                        request.setCustomer_phone(cusPhone);
                        request.setCustomer_email(cusEmail);
                        request.setStore_id(storeId);
                        request.setLang(PreferenceUtils.readString(context, LANGUAGE, "en"));
                        modal.requestToSendOtp(request);
                    }
                });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

    }


    @Override
    public void close() {
        modal.close();
    }

    @Override
    public void changeStatusDialog(String storeId, String orderId, LatLng driverLoc, String cusId, String cusPhone, int status, String cusEmail) {
        switch (status) {
            case 1:
                showConfirmation(storeId, orderId, driverLoc, context.getResources().getString(R.string.started), context.getResources().getString(R.string.status_change), status);
                break;
            case 2:
                showConfirmation(storeId, orderId, driverLoc, context.getResources().getString(R.string.arrived), context.getResources().getString(R.string.status_change), status);
                break;
            case 3:
                mView.showLoadingView();
                SendOtpRequest request = new SendOtpRequest();
                request.setOrder_id(orderId);
                request.setCustomer_id(cusId);
                request.setCustomer_phone(cusPhone);
                request.setCustomer_email(cusEmail);
                request.setLang(PreferenceUtils.readString(context, LANGUAGE, "en"));
                request.setStore_id(storeId);
                modal.requestToSendOtp(request);
                break;
            case 4:
                showFailed(storeId, orderId, driverLoc, context.getResources().getString(R.string.failed), status);
                break;
            case 5:
                setRequestForStatusChange(storeId, orderId, driverLoc, "", "8");
                break;
        }
    }


    @Override
    public double convertMilesToKm(double distanceInMiles) {
        return distanceInMiles * 1.60934;
    }

    @Override
    public void otpSuccess(String msg) {
        mView.hideLoadingView();
        mView.onOtpSuccessSendMqtt();

    }

    @Override
    public void orderFailed(String msg) {
        mView.hideLoadingView();
        mView.orderFailed(msg);
    }

    @Override
    public void requestPolyLineData(LatLng originDriver, LatLng destCustomer, LatLng restaurant, boolean showProgress) {
        PreferenceUtils.writeString(context, LATITUDE, String.valueOf(originDriver.latitude));
        PreferenceUtils.writeString(context, LONGITUDE, String.valueOf(originDriver.longitude));
        sendLocationToMqtt(originDriver);
        modal.requestPolyLineData(originDriver, destCustomer, restaurant);
    }

    @Override
    public void requestPolyLineWithoutWayPoint(LatLng originDriver, LatLng destCustomer) {
        PreferenceUtils.writeString(context, LATITUDE, String.valueOf(originDriver.latitude));
        PreferenceUtils.writeString(context, LONGITUDE, String.valueOf(originDriver.longitude));
        sendLocationToMqtt(originDriver);
        modal.requestPolyLineWithoutWayPoints(originDriver, destCustomer);
    }

    @Override
    public void onDirectionResponse(DirectionResponse directionResponse) {
         mView.hideLoadingView();
        mView.showDirection(directionResponse);
    }

    @Override
    public void directionAPIError(String error) {
        mView.hideLoadingView();
        mView.directionAPIError(error);
    }

    @Override
    public void loggedInByAnotherError(String msg) {
        mView.hideLoadingView();
        mView.showLoggedInByAnother(msg);
    }

    @Override
    public void updateMqtt(String status) {
        mView.updateMqttStatus(status);
    }


    private void setRequestForStatusChange(String storeId, String orderId, LatLng endLoc, String reason, String status) {
        try {
            UpdateStatusRequest request = new UpdateStatusRequest();

            request.setLang(PreferenceUtils.readString(context, LANGUAGE, "en"));
            request.setStoreId(storeId);
            request.setOrderId(orderId);
            request.setReason(reason);
            request.setStatus(status);
            if (NetworkUtils.isNetworkConnected(context)) {
                mView.showLoadingView();
                modal.updateStatus(request);
            } else {
                Toast.makeText(context, context.getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {

        }

    }

    /**
     * Send location to mqtt.
     *
     * @param origin the origin
     */
   private void sendLocationToMqtt(LatLng origin) {
        JSONObject jsonObject = new JSONObject();
        JSONObject webObject = new JSONObject();
        try {

            //customer app
            jsonObject.put("lat", String.valueOf(origin.latitude));
            jsonObject.put("lng", String.valueOf(origin.longitude));
            jsonObject.put("type", TYPE);

            //webJson
            webObject.put("lat", String.valueOf(origin.latitude));
            webObject.put("lng", String.valueOf(origin.longitude));
            webObject.put("type", TYPE);
            webObject.put("delivery_boy_id", appRepository.getUserId());
            webMqttData = webObject.toString();
            mView.sentLocationUpdates(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Toast.makeText(context, TYPE, Toast.LENGTH_SHORT).show();
    }

    /**
     * Hide keyboard.
     *
     * @param view the view
     */
    public void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager)
                    context.getSystemService(Context.INPUT_METHOD_SERVICE);
            assert inputMethodManager != null;
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}
