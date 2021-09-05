package com.foodtogo.rider.ui.login.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.foodtogo.rider.BuildConfig;
import com.foodtogo.rider.R;
import com.foodtogo.rider.base.BaseActivity;
import com.foodtogo.rider.base.FragmentActivity;
import com.foodtogo.rider.data.source.sharedpreference.AppPreferenceDataSource;
import com.foodtogo.rider.model.geocodeaddress.GeoCodeAddress;
import com.foodtogo.rider.model.geocodeaddress.Result;
import com.foodtogo.rider.model.login.LoginResponse;
import com.foodtogo.rider.ui.dashboard.activity.DashboardActivity;
import com.foodtogo.rider.ui.forgotpassword.activity.ForgotPassword;
import com.foodtogo.rider.ui.login.mvp.LoginContractor;
import com.foodtogo.rider.ui.login.mvp.LoginPresenter;
import com.google.firebase.iid.FirebaseInstanceId;
import com.foodtogo.rider.ui.workinghours.activity.WorkingHours;
import com.foodtogo.rider.util.PreferenceUtils;

import java.text.DateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

import static com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys.FCM_TOKEN;
import static com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys.LATITUDE;
import static com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys.LONGITUDE;


/**
 * The type Login.
 */
public class Login extends BaseActivity implements LoginContractor.View {

    /**
     * The Et email address.
     */
    @BindView(R.id.edt_name)
    EditText etEmailAddress;
    /**
     * The Et password.
     */
    @BindView(R.id.edt_password)
    EditText etPassword;

    /**
     * The Login presenter.
     */
    LoginPresenter loginPresenter;
    /**
     * The M app preference.
     */
    AppPreferenceDataSource mAppPreference;
    /**
     * The Fcm token.
     */
    String fcmToken = "";

    private static final String TAG = Login.class.getSimpleName();

    // location last updated time
    private String mLastUpdateTime;

    // location updates interval - 10sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;

    private static final int REQUEST_CHECK_SETTINGS = 100;


    // bunch of location related apis
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;

    // boolean flag to toggle the ui
    private Boolean mRequestingLocationUpdates;

    /**
     * The Current lat.
     */
//Current Location
    String currentLat;
    /**
     * The Current long.
     */
    String currentLong;
    /**
     * The Address.
     */
    String address = "";
    /**
     * The Bundle.
     */
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            // setupToolBar();
            FirebaseApp.initializeApp(this);
            loginPresenter = new LoginPresenter(this, appRepository, getApplicationContext());
            mAppPreference = new AppPreferenceDataSource(getApplicationContext());
            FirebaseInstanceId.getInstance()
                    .getInstanceId()
                    .addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        fcmToken = task.getResult().getToken();
                        Log.i(TAG, "fireBaseRegister: " + fcmToken);
                        PreferenceUtils.writeString(Login.this,FCM_TOKEN,fcmToken);
                    });

            // initialize the necessary libraries
            init();
            // restore the values from saved instance state
            restoreValuesFromBundle(savedInstanceState);
            permissionRequest();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }


    /**
     * Login.
     */
    @OnClick(R.id.btn_login)
    public void login() {
        if (isNetworkConnected()) {
            if (currentLat != null) {
                loginPresenter.loginValidation(etEmailAddress.getText().toString().trim(), etPassword.getText().toString().trim(),
                        mAppPreference.getLanguage(), fcmToken, currentLat, currentLong, address);

            } else {
                showToast(R.string.location_getting_failed);
                // initialize the necessary libraries
                init();
                permissionRequest();
            }
        } else {
            showToast(R.string.no_internet_connection);
        }

    }

    /**
     * Forgot password.
     */
    @OnClick(R.id.tv_forgot_password)
    public void forgotPassword() {
        changeActivity(ForgotPassword.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.close();
    }


    @Override
    public void showEmailEmptyError() {
        showToast(R.string.warning_empty_email);
    }

    @Override
    public void showNotValidEmailError() {
        showToast(R.string.warning_invalid_email);
    }

    @Override
    public void showPasswordEmptyError() {
        showToast(R.string.warning_empty_password);
    }

    @Override
    public void showLoginResponse(LoginResponse loginResponse, String msg) {
        hideKeyboard();
        showToast(msg);
        if (loginResponse.getWorkingStatus().equals("Updated") && loginResponse.getUploadDocumentStatus().equals("Updated")) {
            changeActivityClearBackStack(DashboardActivity.class);
        } else {
            if (!loginResponse.getWorkingStatus().equals("Updated")) {
                changeActivityClearBackStack(WorkingHours.class);
            } else {
                changeActivityClearBackStack(FragmentActivity.createIntent(this, FragmentActivity.OPEN_PROFILE));
            }
        }
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
        showToast(message);
    }

    @Override
    public void showError(int message) {
        showToast(message);
    }

    @Override
    public void showLoggedInByAnother(String message) {
        //not used here
    }

    @Override
    public void showGeoCodeAddress(GeoCodeAddress geoCodeAddress) {
        if (geoCodeAddress.getResults().size() > 0) {
            Result result = geoCodeAddress.getResults().get(0);
            address = result.getFormattedAddress();
            loginPresenter.loginButtonClicked(etEmailAddress.getText().toString().trim(), etPassword.getText().toString().trim(),
                    mAppPreference.getLanguage(), fcmToken, currentLat, currentLong, address);
        } else {
            showToast("Failed to get address");
        }
    }

    @Override
    public void showGeoCodeAddressError(String error) {
        showToast(error);
    }

    @Override
    public void validationSuccess() {
        loginPresenter.requestAddress(currentLat, currentLong);
    }

    private void init() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                mCurrentLocation = locationResult.getLastLocation();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
                updateLocationUI();
            }
        };

        mRequestingLocationUpdates = false;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();

    }

    private void restoreValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("is_requesting_updates")) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean("is_requesting_updates");
            }

            if (savedInstanceState.containsKey("last_known_location")) {
                mCurrentLocation = savedInstanceState.getParcelable("last_known_location");
            }

            if (savedInstanceState.containsKey("last_updated_on")) {
                mLastUpdateTime = savedInstanceState.getString("last_updated_on");
            }
        }

        updateLocationUI();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("is_requesting_updates", mRequestingLocationUpdates);
        outState.putParcelable("last_known_location", mCurrentLocation);
        outState.putString("last_updated_on", mLastUpdateTime);
    }

    private void startLocationUpdates() {
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");
                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());

                        updateLocationUI();
                    }
                })
                .addOnFailureListener(this, e -> {
                    int statusCode = ((ApiException) e).getStatusCode();
                    switch (statusCode) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                    "location settings ");
                            try {
                                // Show the dialog by calling startResolutionForResult(), and check the
                                // result in onActivityResult().
                                ResolvableApiException rae = (ResolvableApiException) e;
                                rae.startResolutionForResult(Login.this, REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException sie) {
                                Log.i(TAG, "PendingIntent unable to execute request.");
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            String errorMessage = "Location settings are inadequate, and cannot be " +
                                    "fixed here. Fix in Settings.";
                            Log.e(TAG, errorMessage);
                            Toast.makeText(Login.this, errorMessage, Toast.LENGTH_LONG).show();
                    }

                    updateLocationUI();
                });
    }

    public void stopLocationUpdates() {
        // Removing location updates
        mFusedLocationClient
                .removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(this, task -> {
                });
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Resuming location updates depending on button state and
        // allowed permissions
        if (mRequestingLocationUpdates && checkPermissions()) {
            startLocationUpdates();
        }

        updateLocationUI();
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    protected void onPause() {
        super.onPause();

        if (mRequestingLocationUpdates) {
            // pausing location updates
            stopLocationUpdates();
        }
    }

    private void updateLocationUI() {
        if (mCurrentLocation != null) {
            Log.i(TAG, "updateLocationUI: " + "Lat: " + mCurrentLocation.getLatitude() + ", " +
                    "Lng: " + mCurrentLocation.getLongitude());
            currentLat = String.valueOf(mCurrentLocation.getLatitude());
            currentLong = String.valueOf(mCurrentLocation.getLongitude());
            PreferenceUtils.writeString(context, LATITUDE, currentLat);
            PreferenceUtils.writeString(context, LONGITUDE, currentLong);
        }
    }

    private void permissionRequest() {
        // Requesting ACCESS_FINE_LOCATION using Dexter library
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mRequestingLocationUpdates = true;
                        startLocationUpdates();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            // open device settings when the permission is
                            // denied permanently
                            openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

}
