package com.foodtogo.rider.ui.trackorder.activity;

import android.Manifest;

import android.animation.ValueAnimator;

import android.app.Dialog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;
import com.google.maps.android.SphericalUtil;
import com.foodtogo.rider.R;
import com.foodtogo.rider.base.BaseActivity;
import com.foodtogo.rider.base.BaseApplication;
import com.foodtogo.rider.customview.PicassoCircleTransformation;
import com.foodtogo.rider.model.directions.DirectionResponse;
import com.foodtogo.rider.model.directions.Distance;
import com.foodtogo.rider.model.directions.Duration;
import com.foodtogo.rider.model.directions.Leg;
import com.foodtogo.rider.model.directions.Route;
import com.foodtogo.rider.model.directions.Step;
import com.foodtogo.rider.model.trackOrder.CustomerDetails;
import com.foodtogo.rider.model.trackOrder.OrderStatusDetail;
import com.foodtogo.rider.model.trackOrder.StoreDetails;
import com.foodtogo.rider.model.trackOrder.TrackOrderResponse;
import com.foodtogo.rider.ui.dashboard.activity.DashboardActivity;
import com.foodtogo.rider.ui.login.activity.Login;
import com.foodtogo.rider.ui.trackorder.adapter.DriverInfoWindowAdapter;
import com.foodtogo.rider.ui.trackorder.mvp.TrackOrderContractor;
import com.foodtogo.rider.ui.trackorder.mvp.TrackOrderPresenter;
import com.foodtogo.rider.ui.workinghours.activity.WorkingHours;
import com.foodtogo.rider.util.ConversionUtils;
import com.foodtogo.rider.util.LocationFinder;
import com.foodtogo.rider.util.NetworkUtils;
import com.foodtogo.rider.util.PolyUtil;
import com.foodtogo.rider.util.PreferenceUtils;
import com.foodtogo.rider.util.ViewUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.google.android.gms.maps.model.JointType.ROUND;
import static com.foodtogo.rider.base.AppConstants.YES;
import static com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys.LATITUDE;
import static com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys.LONGITUDE;
import static com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys.USER_IMAGE;
import static com.foodtogo.rider.util.CommonStrings.DESTINATION;
import static com.foodtogo.rider.util.CommonStrings.FROM_EDIT_PROFILE;
import static com.foodtogo.rider.util.CommonStrings.ORDER_AMOUNT;
import static com.foodtogo.rider.util.CommonStrings.ORDER_ID;
import static com.foodtogo.rider.util.CommonStrings.STORE_ID;
import static com.foodtogo.rider.util.CommonStrings.TYPE;

/**
 * The type Track order maps activity.
 */
public class TrackOrderMapsActivity extends BaseActivity implements
        OnMapReadyCallback,
        TrackOrderContractor.View,
        ActivityCompat.OnRequestPermissionsResultCallback {
    /**
     * The constant startedFinish.
     */
    public static boolean startedFinish = false;
    /**
     * The constant arrivedFinish.
     */
    public static boolean arrivedFinish = false;
    private static GoogleMap mMap;
    private TrackOrderPresenter presenter;
    private String orderId = "";
    private String storeId = "";
    private double storeLatitude = 0.0;
    private double storeLongitude = 0.0;
    private double customerLatitude = 0.0;
    private double customerLongitude = 0.0;
    private double driverLatitude = 0.0;
    private double driverLongitude = 0.0;
    private String cusName = "";
    private String cusPhoneOne = "";
    private String cusPhoneTwo = "";
    private String cusAddress = "";
    private String cusId = "";
    private String cusEmail = "";
    /**
     * The constant webMqttData.
     */
    public static String webMqttData = "";
    /**
     * The Layout.
     */
    @BindView(R.id.container_layout)
    RelativeLayout layout;
    /**
     * The New order time.
     */
    @BindView(R.id.new_order_time)
    TextView newOrderTime;
    /**
     * The Accept order time.
     */
    @BindView(R.id.accept_order_time)
    TextView acceptOrderTime;
    /**
     * The Prepare order time.
     */
    @BindView(R.id.prepare_order_time)
    TextView prepareOrderTime;
    /**
     * The Dispatch order time.
     */
    @BindView(R.id.dispatch_order_time)
    TextView dispatchOrderTime;
    /**
     * The M order id.
     */
    @BindView(R.id.order_id)
    TextView mOrderId;


    @BindView(R.id.started_msg)
    TextView startedMsg;

    @BindView(R.id.arrived_msg)
    TextView arrivedMsg;
    /**
     * The M payment method.
     */
    @BindView(R.id.payment_type)
    TextView mPaymentMethod;
    /**
     * The Started layout.
     */
    @BindView(R.id.started_order_layout)
    RelativeLayout startedLayout;
    /**
     * The Btn started order.
     */
    @BindView(R.id.btn_started_order)
    Button btnStartedOrder;

    @BindView(R.id.start_lay)
    ConstraintLayout btnStartNavigation;
    /**
     * The Arrived layout.
     */
    @BindView(R.id.arrived_order_layout)
    RelativeLayout arrivedLayout;
    /**
     * The Btn arrived order.
     */
    @BindView(R.id.btn_arrived_order)
    Button btnArrivedOrder;
    /**
     * The Delivered layout.
     */
    @BindView(R.id.delivered_order_layout)
    RelativeLayout deliveredLayout;
    /**
     * The Ll delivery view.
     */
    @BindView(R.id.ll_delivery_view)
    LinearLayout llDeliveryView;

    /**
     * The Failed order layout.
     */
    @BindView(R.id.failed_order_layout)
    RelativeLayout failedOrderLayout;

    /**
     * The Profile img.
     */
    @BindView(R.id.profile_img)
    ImageView profileImg;
    /**
     * The View start.
     */
    @BindView(R.id.view_start)
    View viewStart;
    /**
     * The View arrived.
     */
    @BindView(R.id.view_arrived)
    View viewArrived;
    /**
     * The View delivered.
     */
    @BindView(R.id.view_delivered)
    View viewDelivered;


    /**
     * The constant store.
     */
    public static LatLng store;
    /**
     * The constant customer.
     */
    public static LatLng customer;
    /**
     * The constant driver.
     */
    public static LatLng driver;
    /**
     * The constant distance.
     */
    public static String distance = "";
    /**
     * The constant duration.
     */
    public static String duration = "";
    /**
     * The constant payType.
     */
    public static String payType = "";

    public static String orderAmount = "";
    /**
     * The constant driverMarker.
     */
    public static Marker driverMarker;
    /**
     * The constant customerMarker.
     */
    public static Marker customerMarker;

    public static Marker storeMarker;
    /**
     * The constant blackPolyLine.
     */
    public static Polyline blackPolyLine,
    /**
     * The Grey poly line.
     */
    greyPolyLine;
    /**
     * The constant points.
     */
    public static ArrayList points = null;
    /**
     * The constant endPosition.
     */
    public static LatLng endPosition;
    /**
     * The constant startPosition.
     */
    public static LatLng startPosition;
    /**
     * The constant index.
     */
    public static int index = 0;

    private final static int MY_PERMISSIONS_REQUEST_CALL_PHONE = 102;
    /**
     * The constant v.
     */
    public static float v;

    String errorMsg = "";
    ArrayList<LatLng> pointsLatLngArrayList;
    boolean initialRequest = true;
    boolean showDialog = false;
    int count = 0;


    /**
     * Goto track order activity intent.
     *
     * @param context the context
     * @param storeId the store id
     * @param orderId the order id
     * @return the intent
     */
    public static Intent gotoTrackOrderActivity(Context context, String storeId, String orderId, String orderAmount) {
        Intent i = new Intent(context, TrackOrderMapsActivity.class);
        i.putExtra(STORE_ID, storeId);
        i.putExtra(ORDER_ID, orderId);
        i.putExtra(ORDER_AMOUNT, orderAmount);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TYPE = "";
        setupToolBar();
        setTitle(getResources().getString(R.string.track_order));
        init();
        getIntentValues();
        apiCall();
        showProgress();
        toolbar.setTitleCentered(true);
    }


    private void getLocationUpdates() {
        LocalBroadcastManager.getInstance(this).registerReceiver(
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        String latitude = intent.getStringExtra(BaseActivity.EXTRA_LATITUDE);
                        String longitude = intent.getStringExtra(BaseActivity.EXTRA_LONGITUDE);
                        if (latitude != null && longitude != null) {
                            LocationFinder.updateUI(latitude, longitude, getApplicationContext());
                            LatLng latLng = new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
                            updateDriverLocation(latLng);
                            if (driver != latLng) {
                                driver = latLng;
                                if (TYPE.equals("ACCEPT"))
                                    presenter.requestPolyLineData(driver, customer, store, initialRequest);
                                else
                                    presenter.requestPolyLineWithoutWayPoint(driver, customer);
                            }

                        }
                    }
                }, new IntentFilter(BaseActivity.ACTION_LOCATION_BROADCAST)
        );
    }

    private void init() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        presenter = new TrackOrderPresenter(this, TrackOrderMapsActivity.this, appRepository);
    }


    private void getIntentValues() {
        if (getIntent() != null) {
            if (getIntent().getStringExtra(STORE_ID) != null && getIntent().getStringExtra(ORDER_ID) != null) {
                orderId = getIntent().getStringExtra(ORDER_ID);
                storeId = getIntent().getStringExtra(STORE_ID);
                orderAmount = getIntent().getStringExtra(ORDER_AMOUNT);

                PreferenceUtils.writeString(getApplicationContext(), STORE_ID, storeId);
                PreferenceUtils.writeString(getApplicationContext(), ORDER_ID, orderId);
            }
        }
    }

    private void apiCall() {
        if (isNetworkConnected()) {
            showProgress();
            presenter.getTrackOrder(storeId, orderId);
        } else {
            ViewUtils.showSnackBar(layout, getString(R.string.no_internet_connection));
        }
    }

    /**
     * Show user detail dialog.
     */
    @OnClick(R.id.ic_info)
    void showUserDetailDialog() {
        userPopup();
    }

    /**
     * Change status.
     */
    @OnClick(R.id.order_status)
    void changeStatus() {
        presenter.changeStatusDialog(storeId, orderId, driver, cusId, cusPhoneOne, 0, cusEmail);
    }

    /**
     * Sets start.
     */
    @OnClick(R.id.btn_started_order)
    public void setStart() {
        presenter.changeStatusDialog(storeId, orderId, driver, cusId, cusPhoneOne, 1, cusEmail);
    }

    /**
     * Sets arrived.
     */
    @OnClick(R.id.btn_arrived_order)
    public void setArrived() {
        presenter.changeStatusDialog(storeId, orderId, driver, cusId, cusPhoneOne, 2, cusEmail);
    }

    /**
     * Sent otp.
     */
    @OnClick(R.id.delivered_order_order)
    public void sentOtp() {
        presenter.changeStatusDialog(storeId, orderId, driver, cusId, cusPhoneOne, 5, cusEmail);
    }

    /**
     * Sent failed.
     */
    @OnClick(R.id.failed_order)
    public void sentFailed() {
        TYPE = "FAILED";
        presenter.changeStatusDialog(storeId, orderId, driver, cusId, cusPhoneOne, 4, cusEmail);
    }


    @Override
    public int getLayout() {
        return R.layout.track_order_container;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            presenter.callPhone(cusPhoneOne);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_track, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.actionTrack) {
            if (NetworkUtils.isNetworkConnected(context)) {
                if (TYPE.equals("STARTED") || TYPE.equals("ARRIVED")) {
                    String navigation = "google.navigation:q=" + customerLatitude + "," + customerLongitude;
                    Uri gmmIntentUri = Uri.parse(navigation);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                } else if (TYPE.equals("ACCEPT")) {
                    String navigation = "google.navigation:q=" + storeLatitude + "," + storeLongitude;
                    Uri gmmIntentUri = Uri.parse(navigation);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        updateValuesBeforeMapUpdate();
        updateMap();
    }

    private void updateValuesBeforeMapUpdate() {
        // Add a marker in Sydney and move the camera
        driver = new LatLng(driverLatitude, driverLongitude);
        store = new LatLng(storeLatitude, storeLongitude);
        customer = new LatLng(customerLatitude, customerLongitude);
        PreferenceUtils.writeString(getApplicationContext(), DESTINATION, String.valueOf(customerLatitude + ":" + customerLongitude));
    }

    private void updateMap() {
        if (driverMarker != null) {
            driverMarker.remove();
        }
        if (customerMarker != null) {
            customerMarker.remove();
        }

        if (storeMarker != null) {
            storeMarker.remove();
        }


        driverMarker = mMap.addMarker(new MarkerOptions().position(driver).title("DRIVER").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_vehicle)));
        mMap.setInfoWindowAdapter(new DriverInfoWindowAdapter(getApplicationContext()));
        storeMarker = mMap.addMarker(new MarkerOptions().position(store).title("Restaurant").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pick)));

        customerMarker = mMap.addMarker(new MarkerOptions().position(customer).title("Customer").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_drop)));
        // zoomInToCenter();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(driver));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(driver, 15.0f));
    }



    void updateAgainMap(){
        mMap.setOnMapLoadedCallback(this::updateMap);
    }

    /**
     * Update driver location.
     *
     * @param driverLoc the driver loc
     */
    public void updateDriverLocation(LatLng driverLoc) {


        /**
         * added on 1/3/2019
         * */
        if (driverMarker != null) {
            driverMarker.remove();
            driverMarker = mMap.addMarker(new MarkerOptions().position(driverLoc).title("DRIVER").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_vehicle)));
        }

        /****************/
        if (mMap != null) {
            if (driverLoc != null) {
                startBikeAnimation(driverLoc.latitude, driverLoc.longitude);
            }

        }
    }


    @Override
    public void bindTrackOrderValues(TrackOrderResponse response) {

        /*driver profile image*/
        String url = PreferenceUtils.readString(TrackOrderMapsActivity.this, USER_IMAGE, "image");
        //System.out.println("urlImage" + url);
        Picasso.get()
                .load(url)
                .resize(600, 200)
                .centerInside()
                .placeholder(R.drawable.ic_user_default)
                .error(R.drawable.ic_user_default)
                .transform(new PicassoCircleTransformation())
                .into(profileImg);

        /*store detail*/
        StoreDetails storeDetails = response.getStoreDetails();
        storeLatitude = Double.parseDouble(storeDetails.getStoreLatitude());
        storeLongitude = Double.parseDouble(storeDetails.getStoreLongitude());
        store=new LatLng(storeLatitude,storeLongitude);
        // System.out.println("storeLatitude" + storeLatitude);
        /*driver location*/
        // driverLatitude = Double.parseDouble(PreferenceUtils.readString(context, LATITUDE, "0.0"));
        // driverLongitude = Double.parseDouble(PreferenceUtils.readString(context, LONGITUDE, "0.0"));
        //driver = new LatLng(driverLatitude, driverLongitude);

        /*customer detail*/
        CustomerDetails customerDetails = response.getCustomerDetails();
        customerLatitude = Double.parseDouble(customerDetails.getCusLatitude());
        customerLongitude = Double.parseDouble(customerDetails.getCusLongitude());
        customer=new LatLng(customerLatitude,customerLongitude);
        cusName = customerDetails.getCusName();
        cusPhoneOne = customerDetails.getCusMobile1();
        cusPhoneTwo = customerDetails.getCusMobile2();
        cusAddress = customerDetails.getCusAddress();
        cusId = String.valueOf(customerDetails.getCusId());
        cusEmail = customerDetails.getCusEmail();

        /*order status details*/
        List<OrderStatusDetail> orderDetail = response.getOrderStatusDetails();
        mOrderId.setText(response.getOrderId());
        mPaymentMethod.setText(response.getPayType());
        payType = response.getPayType();
        setOrderDetails(orderDetail);

        showLoadingView();
        getLocationUpdates();
        updateAgainMap();

        //  presenter.requestPolyLineData(driver, customer, store, initialRequest);
    }


    /**
     * Sets info window text.
     *
     * @param hour the hour
     * @param min  the min
     * @return the info window text
     */
    /**
     * Sets info window text.
     *
     * @param hour the hour
     * @param min  the min
     * @return the info window text
     */
    public static String setInfoWindowText(long hour, long min) {
        String duration = "";
        if (hour > 0 && min > 0) {
            duration = hour + "h" + " " + min + "m";
        } else if (hour > 0 && min == 0) {
            duration = hour > 1 ? hour + "hours" : hour + "hour";
        } else {
            duration = min > 1 ? min + "mins" : min + "min";
        }
        return duration;
    }

    @Override
    public void sentLocationUpdates(String locationData) {
        try {
            if (BaseApplication.getMQTTClient() != null) {
                String publishTopic = "delivery/" + appRepository.getUserId();
                BaseApplication.getMQTTServerClient().publish(BaseApplication.getMQTTClient(), publishTopic, locationData);
            }
        } catch (Exception e) {
            System.out.println("Mqtt Publish error" + e);
        }
    }

    @Override
    public void onOtpSuccessSendMqtt() {
        TYPE = "SEND_OTP";
        sentLocationUpdates(sendMqTTtoUpdateStatus("otp"));
        presenter.changeStatusDialog(storeId, orderId, driver, cusId, cusPhoneOne, 5, cusEmail);
    }


    @Override
    public void showLoadingView() {
        showProgress();
    }

    @Override
    public void hideLoadingView() {
        hideProgress();
    }

    @Override
    public void showError(String message) {
        ViewUtils.showSnackBar(layout, message);
    }

    @Override
    public void showError(int message) {
        changeActivityClearBackStack(Login.class);
    }

    @Override
    public void showLoggedInByAnother(String message) {
        showLoggedInByAnotherInfo(message);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.close();
    }

    /**
     * Sets order details.
     *
     * @param orderDetails the order details
     */
    void setOrderDetails(List<OrderStatusDetail> orderDetails) {
        for (int i = 0; i < orderDetails.size(); i++) {
            if (orderDetails.get(i).getOrdStage() == 1 && orderDetails.get(i).getStageCompleted().equals(YES)) {
                newOrderTime.setText(ConversionUtils.getFormatDateTime(orderDetails.get(i).getOrdTiming()));
            } else if (orderDetails.get(i).getOrdStage() == 2 && orderDetails.get(i).getStageCompleted().equals(YES)) {
                acceptOrderTime.setText(ConversionUtils.getFormatDateTime(orderDetails.get(i).getOrdTiming()));
            } else if (orderDetails.get(i).getOrdStage() == 3 && orderDetails.get(i).getStageCompleted().equals(YES)) {
                prepareOrderTime.setText(ConversionUtils.getFormatDateTime(orderDetails.get(i).getOrdTiming()));
            } else if (orderDetails.get(i).getOrdStage() == 4 && orderDetails.get(i).getStageCompleted().equals(YES)) {
                prepareOrderTime.setText(ConversionUtils.getFormatDateTime(orderDetails.get(i).getOrdTiming()));
            } else if (orderDetails.get(i).getOrdStage() == 5 && orderDetails.get(i).getStageCompleted().equals(YES)) {
                TYPE = "ACCEPT";
                arrivedFinish = false;
                startedFinish = false;
                dispatchOrderTime.setText(ConversionUtils.getFormatDateTime(orderDetails.get(i).getOrdTiming()));
                changeOption(1);
            } else if (orderDetails.get(i).getOrdStage() == 6 && orderDetails.get(i).getStageCompleted().equals(YES)) {
                startedMsg.setText(ConversionUtils.getFormatDateTime(orderDetails.get(i).getOrdTiming()));
                startedFinish = true;
                onOrderStarted();
                changeOption(2);
            } else if (orderDetails.get(i).getOrdStage() == 7 && orderDetails.get(i).getStageCompleted().equals(YES)) {
                arrivedMsg.setText(ConversionUtils.getFormatDateTime(orderDetails.get(i).getOrdTiming()));
                arrivedFinish = true;
                startedFinish = true;
                onOrderArrived();
                changeOption(3);
            } else if (orderDetails.get(i).getOrdStage() == 8 && orderDetails.get(i).getStageCompleted().equals(YES)) {
                onOrderDelivered();
            } else if (orderDetails.get(i).getOrdStage() == 9 && orderDetails.get(i).getStageCompleted().equals(YES)) {
                onOrderFailed();
            }
        }
    }

    private void changeOption(int status) {
        btnStartedOrder.setVisibility(View.GONE);
        btnArrivedOrder.setVisibility(View.GONE);
        llDeliveryView.setVisibility(View.GONE);
        switch (status) {
            case 1:
                startedLayout.setVisibility(View.GONE);
                btnStartedOrder.setVisibility(View.VISIBLE);
                break;
            case 2:
                startedLayout.setVisibility(View.VISIBLE);
                btnStartedOrder.setVisibility(View.GONE);
                btnStartNavigation.setVisibility(View.GONE);

                arrivedLayout.setVisibility(View.GONE);
                btnArrivedOrder.setVisibility(View.VISIBLE);
                break;
            case 3:
                startedLayout.setVisibility(View.VISIBLE);
                btnStartedOrder.setVisibility(View.GONE);
                btnStartNavigation.setVisibility(View.GONE);

                arrivedLayout.setVisibility(View.VISIBLE);
                btnArrivedOrder.setVisibility(View.GONE);

                deliveredLayout.setVisibility(View.GONE);
                llDeliveryView.setVisibility(View.VISIBLE);
                break;
        }

    }

    @Override
    public void orderFailed(String msg) {
        startedLayout.setVisibility(View.VISIBLE);
        btnStartedOrder.setVisibility(View.GONE);
        btnStartNavigation.setVisibility(View.GONE);
        arrivedLayout.setVisibility(View.VISIBLE);
        btnArrivedOrder.setVisibility(View.GONE);
        deliveredLayout.setVisibility(View.GONE);
        llDeliveryView.setVisibility(View.GONE);

        failedOrderLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showDirection(DirectionResponse directionResponse) {
        List<Route> routeList = directionResponse.getRoutes();
        if (routeList.size() > 0) {
            PolylineOptions polylineOptions = null;
            if (routeList.size() > 0) {
                duration = calculateDuration(routeList.get(0).getLegs());
                distance = calculationDistance(routeList.get(0).getLegs());


                polylineOptions = getPolyDraw(directionResponse.getRoutes());
                polylineOptions.width(7);
                polylineOptions.color(Color.DKGRAY);
                polylineOptions.startCap(new SquareCap());
                polylineOptions.endCap(new SquareCap());
                polylineOptions.jointType(ROUND);
                blackPolyLine = mMap.addPolyline(polylineOptions);

            }

            // Drawing polyline in the Google Map for the i-th route
            if (polylineOptions != null) {
                mMap.addPolyline(polylineOptions);
                animatePolyLine();
            }
        } else {
            ViewUtils.showSnackBar(layout, "No Routes Found");
        }
        new Handler().postDelayed(() -> hideLoadingView(), 300);
        if (showDialog) {
            if (count == 0) {
                showWarning("No Routes Found");
                count++;
            }
        }
    }

    private PolylineOptions getPolyDraw(List<Route> routeList) {
        initialRequest = false;

        PolylineOptions polylineOptions = null;
        for (int index = 0; index < routeList.size(); index++) {
            List<Leg> legList = routeList.get(index).getLegs();
            pointsLatLngArrayList = new ArrayList<>();
            polylineOptions = new PolylineOptions();
            for (int jIndex = 0; jIndex < legList.size(); jIndex++) {
                List<Step> stepList = legList.get(jIndex).getSteps();
                if (stepList.size() > 100) {
                    showDialog = true;
                    break;
                } else {
                    for (int kIndex = 0; kIndex < stepList.size(); kIndex++) {
                        String points = stepList.get(kIndex).getPolyline().getPoints();
                        pointsLatLngArrayList.addAll(PolyUtil.decodePoly(points));
                    }
                }

            }

            polylineOptions.addAll(pointsLatLngArrayList);
        }

        return polylineOptions;
    }


    @Override
    public void directionAPIError(String error) {
        hideLoadingView();
        System.out.println("error:" + error);
        if (!error.equals("ZERO_RESULTS")) {
            ViewUtils.showSnackBar(layout, error);
        }

    }

    @Override
    public void updateMqttStatus(String status) {
        if (status.equals("6")) {
            onOrderStarted();
        } else if (status.equals("7")) {
            onOrderArrived();
        } else if (status.equals("8")) {
            onOrderDelivered();
        } else if (status.equals("9")) {
            onOrderFailed();
        }
    }


    /**
     * On order failed.
     */
    void onOrderFailed() {
        TYPE = "FAILED";
        sentLocationUpdates(sendMqTTtoUpdateStatus("9"));
        ViewUtils.showSnackBar(layout, getString(R.string.delivered_failed));
        changeActivityClearBackStack(DashboardActivity.class);
    }

    /**
     * On order delivered.
     */
    void onOrderDelivered() {
        TYPE = "DELIVERED";
        sentLocationUpdates(sendMqTTtoUpdateStatus("8"));
        deliveredLayout.setAlpha(1.0f);
        changeActivityClearBackStack(DashboardActivity.class);
    }

    /**
     * On order arrived.
     */
    void onOrderArrived() {
        TYPE = "ARRIVED";
        sentLocationUpdates(sendMqTTtoUpdateStatus("7"));
        arrivedLayout.setAlpha(1.0f);
    }

    /**
     * On order started.
     */
    void onOrderStarted() {
        TYPE = "STARTED";
        sentLocationUpdates(sendMqTTtoUpdateStatus("6"));
        startedLayout.setAlpha(1.0f);
    }


    /**
     * User popup.
     */
    void userPopup() {
        final Dialog dialog = new Dialog(TrackOrderMapsActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_user_detail);

        TextView userName = dialog.findViewById(R.id.people_name);
        LinearLayout phoneOneLay = dialog.findViewById(R.id.phone_one_layout);
        LinearLayout phoneTwoLay = dialog.findViewById(R.id.phone_two_layout);
        TextView phoneOne = dialog.findViewById(R.id.phone_one);
        TextView phoneTwo = dialog.findViewById(R.id.phone_two);
        TextView address = dialog.findViewById(R.id.address);
        TextView callUser = dialog.findViewById(R.id.call_user);
        RelativeLayout callAction = dialog.findViewById(R.id.call_action);
        ImageView close = dialog.findViewById(R.id.close);
        phoneTwoLay.setVisibility(View.GONE);
        /*set user fields*/
        userName.setText(cusName);
        callUser.setText(String.format("Call %s?", cusName));
        if (cusPhoneOne != null && cusPhoneOne.length() > 0) {
            phoneOneLay.setVisibility(View.VISIBLE);
            phoneOne.setText(cusPhoneOne);
        }
       /* if (cusPhoneTwo != null && cusPhoneTwo.length() > 0) {
            phoneTwoLay.setVisibility(View.VISIBLE);
            phoneTwo.setText(cusPhoneTwo);
        }*/
        address.setText(cusAddress);
        callAction.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(TrackOrderMapsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(TrackOrderMapsActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);

            } else {
                presenter.callPhone(cusPhoneOne);
            }

        });
        close.setOnClickListener(v -> dialog.cancel());

        dialog.show();

    }


    /**
     * Animate poly line.
     */
// animation effect for polyLine
    public void animatePolyLine() {
        driverMarker.hideInfoWindow();
        ValueAnimator animator = ValueAnimator.ofInt(0, 100);
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(animator1 -> {

            List<LatLng> latLngList = blackPolyLine.getPoints();
            int initialPointSize = latLngList.size();
            int animatedValue = (int) animator1.getAnimatedValue();
            int newPoints = (animatedValue * pointsLatLngArrayList.size()) / 100;

            if (initialPointSize < newPoints) {
                latLngList.addAll(pointsLatLngArrayList.subList(initialPointSize, newPoints));
                blackPolyLine.setPoints(latLngList);
            }
            if (duration != null && !duration.equals("")) {
                long hour = LocationFinder.convertSecondsToHour(Long.valueOf(duration));
                long min = LocationFinder.convertSecondsToMin(Long.valueOf(duration));
                System.out.println("hour&min:" + hour + ":" + min);
                driverMarker.setTitle(setInfoWindowText(hour, min));
                driverMarker.showInfoWindow();
            } else {
                System.out.print("cant get duration");
            }


        });
        animator.start();

    }


    /**
     * Start bike animation.
     *
     * @param latitude  the latitude
     * @param longitude the longitude
     */
    public static void startBikeAnimation(Double latitude, Double longitude) {
        LatLng latLng = new LatLng(latitude, longitude);
        if (driverMarker != null) {
            endPosition = latLng;
            startPosition = driverMarker.getPosition();
            double distance = SphericalUtil.computeDistanceBetween(startPosition, endPosition);
            // System.out.println("distanceBike" + distance);
            if (distance > 5) {
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
                valueAnimator.setDuration(3000);
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.addUpdateListener(valueAnimator1 -> {
                    v = valueAnimator1.getAnimatedFraction();
                    double lng = v * endPosition.longitude + (1 - v)
                            * startPosition.longitude;
                    double lat = v * endPosition.latitude + (1 - v)
                            * startPosition.latitude;
                    LatLng newPos = new LatLng(lat, lng);
                    driverMarker.setPosition(newPos);
                    driverMarker.setAnchor(0.5f, 0.5f);
                    mMap.moveCamera(CameraUpdateFactory
                            .newCameraPosition
                                    (new CameraPosition.Builder()
                                            .target(newPos)
                                            .zoom(17.0f)
                                            .build()));


                });
                valueAnimator.start();
                index = index + 1;
            }
        }
    }

    /**
     * Send mq t tto update status string.
     *
     * @param orderStatus the order status
     * @return the string
     */
    String sendMqTTtoUpdateStatus(String orderStatus) {
        JSONObject jsonObject = new JSONObject();

        try {
            //origin
            if (orderStatus.equals("9")) {
                jsonObject.put("reason", "Delivery Failed");
            } else {
                // this json for customer app
                jsonObject.put("lat", PreferenceUtils.readString(context, LATITUDE, "0.0"));
                jsonObject.put("lng", PreferenceUtils.readString(context, LONGITUDE, "0.0"));
            }
            jsonObject.put("type", TYPE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }


    private void zoomInToCenter() {
        try {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
//the include method will calculate the min and max bound.
            builder.include(driverMarker.getPosition());
            builder.include(customerMarker.getPosition());
            LatLngBounds bounds = builder.build();

            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            int padding = (int) (width * 0.15); // offset from edges of the map 10% of screen
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
            mMap.animateCamera(cu);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String calculateDuration(List<Leg> legs) {
        int oldValue = 0;
        String durationTime = "";
        for (int index = 0; index < legs.size(); index++) {
            Leg leg = legs.get(index);
            Duration duration = leg.getDuration();
            if (oldValue == 0) {
                oldValue = duration.getValue();
                durationTime = String.valueOf(duration.getValue());
            } else if (oldValue < duration.getValue()) {
                durationTime = String.valueOf(duration.getValue());
            }
        }
        return durationTime;
    }

    private String calculationDistance(List<Leg> legs) {
        Leg leg = legs.get(0);
        Distance distance1 = leg.getDistance();
        return distance1.getText();
    }


}
