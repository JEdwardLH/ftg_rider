package com.foodtogo.rider.ui.neworderfragment.mvp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.foodtogo.rider.R;
import com.foodtogo.rider.data.source.AppRepository;
import com.foodtogo.rider.model.BaseResponse;
import com.foodtogo.rider.model.Request;
import com.foodtogo.rider.model.neworders.NewOrdersResponse;
import com.foodtogo.rider.util.LocationFinder;
import com.foodtogo.rider.util.NetworkUtils;
import com.foodtogo.rider.util.PreferenceUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys.LANGUAGE;
import static com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys.LATITUDE;
import static com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys.LONGITUDE;

/**
 * The type New order presenter.
 */
public class NewOrderPresenter implements NewOrderContractor.Presenter {
    private NewOrderModel model;
    private NewOrderContractor.View mView;
    private Context context;
    private String mStatus = "";
    private String mOrderId = "";
    private String mStoreId = "";
    private AppRepository appRepository;


    /**
     * Instantiates a new New order presenter.
     *
     * @param mView         the m view
     * @param context       the context
     * @param appRepository the app repository
     */
    public NewOrderPresenter(NewOrderContractor.View mView, Context context, AppRepository appRepository) {
        this.model = new NewOrderModel(this, context);
        this.mView = mView;
        this.context = context;
        this.appRepository = appRepository;
    }

    @Override
    public void getNewOrderData(String mPage) {
        Request request = new Request();
        request.setLang(PreferenceUtils.readString(context, LANGUAGE, "en"));
        request.setPage_no(mPage);
        model.requestNewOrderData(request);
    }

    @Override
    public void orderSuccess(BaseResponse<NewOrdersResponse> response) {
        mView.hideLoadingView();
        if (response.isTokenExpired()) {
            mView.showTokenExpired(response.getMessage());
        } else if (response.isSuccess()) {
            mView.loadOrders(response.isNoItem()
                    ? new ArrayList<>()
                    : response.getData().getOrderNew());
        } else {
            mView.showError(response.getMessage());
        }
    }

    @Override
    public void acceptRejectSuccess(String msg,String mStatus) {
        String TYPE = "ASSIGNED";
        if (mStatus.equals("1")) {
            JSONObject jsonObject = new JSONObject();
            try {
                //origin
                JSONObject orderJson = new JSONObject();
                orderJson.put("order_id", mOrderId);
                orderJson.put("deliver_boy_id", appRepository.getUserId());
                jsonObject.put("data", orderJson);
                jsonObject.put("type", TYPE);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mView.sendOrderAcceptDetails(jsonObject.toString(), mOrderId, mStoreId);
        }
        //mView.hideLoadingView();
        mView.reLoad();
        mView.acceptRejectSuccess(msg);
    }

    @Override
    public void newOrderError(String error) {
        mView.loadOrders(null);
    }

    @Override
    public void errorAcceptReject(String error) {
        mView.hideLoadingView();
        mView.showConnectionError(error);
    }

    @Override
    public void acceptOrder(String status, String orderId, String storeId, LatLng storeLatLng) {
        LatLng driverLatLng = new LatLng(Double.valueOf(PreferenceUtils.readString(context, LATITUDE, "0.0")), Double.valueOf(PreferenceUtils.readString(context, LONGITUDE, "0.0")));
        String url = LocationFinder.getDistanceUrl(driverLatLng, storeLatLng, context);
        mView.showLoadingView();
        DownloadTimeTask downloadTask = new DownloadTimeTask();
        // Start downloading json data from Google Directions API
        downloadTask.execute(url);
        mStatus = status;
        mStoreId = storeId;
        mOrderId = orderId;
    }

    @Override
    public void rejectOrder(String status, String orderId, String storeId, String reason) {
        mView.showLoadingView();
        model.requestToAcceptorReject(status, orderId, storeId, reason, "", "");
    }

    @Override
    public void close() {
        model.close();
    }

    @Override
    public void acceptWithEstimatedTime(int[] time) {
        model.requestToAcceptorReject(mStatus, mOrderId, mStoreId, "", String.valueOf(time[1]), String.valueOf(time[2]));
    }

    @Override
    public void loggedInByAnother(String msg) {
        mView.hideLoadingView();
        mView.showLoggedInByAnother(msg);
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

    private class DownloadTimeTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = LocationFinder.downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            System.out.println("result" + result);
            super.onPostExecute(result);
            mView.updateTime(result);
        }
    }


}
