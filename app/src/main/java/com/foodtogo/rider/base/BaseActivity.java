package com.foodtogo.rider.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
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
import com.foodtogo.rider.util.AppUtils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.foodtogo.rider.BuildConfig;
import com.foodtogo.rider.R;
import com.foodtogo.rider.customview.CenteredTitleToolbar;
import com.foodtogo.rider.data.source.AppDataSource;
import com.foodtogo.rider.data.source.AppRepository;
import com.foodtogo.rider.data.source.sharedpreference.AppPreferenceDataSource;
import com.foodtogo.rider.ui.dashboard.fragment.DashboardFragment;
import com.foodtogo.rider.ui.login.activity.Login;
import com.foodtogo.rider.ui.profile.fragment.EditProfileFragment;
import com.foodtogo.rider.util.NetworkUtils;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import butterknife.ButterKnife;

import static com.foodtogo.rider.base.AppConstants.EXCEPTION;

/**
 * The type Base activity.
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * The Context.
     */
    protected Context context;
    /**
     * The Progress dialog.
     */
    protected ProgressDialog progressDialog;
    /**
     * The App repository.
     */
    protected AppRepository appRepository;
    /**
     * The App data source.
     */
    protected AppDataSource appDataSource;
    /**
     * The Toolbar.
     */
    protected CenteredTitleToolbar toolbar;
    /**
     * The Spinner progress.
     */
    protected ProgressBar spinnerProgress;
    private static final String TAG = BaseActivity.class.getSimpleName();

    // location last updated time
    private String mLastUpdateTime;

    // location updates interval - 10sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 20000;

    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 20000;

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
     * The constant ACTION_LOCATION_BROADCAST.
     */
    public static final String ACTION_LOCATION_BROADCAST = BaseActivity.class.getName() + "LocationBroadcast";
    /**
     * The constant EXTRA_LATITUDE.
     */
    public static final String EXTRA_LATITUDE = "extra_latitude";
    /**
     * The constant EXTRA_LONGITUDE.
     */
    public static final String EXTRA_LONGITUDE = "extra_longitude";
    /**
     * The Current lat.
     */
//Current Location
    protected String currentLat;
    /**
     * The Current long.
     */
    protected String currentLong;
    /**
     * The Json object live.
     */
    JSONObject jsonObjectLive = null;

    /**
     * Gets context.
     *
     * @return the context
     */
    public Context getContext() {
        return context;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(getLayout());
        ButterKnife.bind(this);
        appDataSource = new AppPreferenceDataSource(this);
        appRepository = new AppRepository(appDataSource);
        context = this;
        jsonObjectLive = new JSONObject();
        // initialize the necessary libraries
        init();
        // restore the values from saved instance state
        restoreValuesFromBundle(savedInstanceState);
        permissionRequest();
        updateLocale(appRepository.getLanguage());
    }

    private void updateLocale(String defaultLang) {
        AppUtils.updateLocale(defaultLang, this);
        appRepository.setLanguage(defaultLang);
    }



    /**
     * Gets layout.
     *
     * @return the layout
     */
    public abstract @LayoutRes
    int getLayout();


    /**
     * Is network connected boolean.
     *
     * @return the boolean
     */
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }


    /**
     * Hide keyboard.
     */
    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            assert inputMethodManager != null;
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Show progress.
     */
    public void showProgress() {
        try {
            if (progressDialog != null) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.loading_wait));
            progressDialog.setCancelable(false);
            progressDialog.show();
        } catch (Exception e) {
            Log.e(EXCEPTION, e.toString());
        }
    }

    /**
     * Show progress loading.
     */
    public void showProgressLoading() {
        spinnerProgress = findViewById(R.id.progressBar);
        spinnerProgress.setVisibility(View.VISIBLE);

    }

    /**
     * Stop progress loading.
     */
    public void stopProgressLoading() {
        spinnerProgress.setVisibility(View.GONE);
    }


    /**
     * Hide progress.
     */
    public void hideProgress() {
        System.out.println("hideprogress");
        try {
            if (progressDialog != null)
                progressDialog.dismiss();
        } catch (Exception e) {
            Log.e(EXCEPTION, e.toString());
        }
    }

    /**
     * Sets tool bar.
     */
    public void setupToolBar() {
        toolbar = findViewById(R.id.toolbar);
        Log.d("ChangingTitle", getTitle() + "");

        setTitle(getTitle());
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
           // getSupportActionBar().setHomeButtonEnabled(true);
            toolbar.setNavigationIcon(R.drawable.ic_toolbar_foodtogo);
            //toolbar
        }
    }


    /*    public void setupToolBar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                hideKeyboard();
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Change activity.
     *
     * @param i the
     */
    public void changeActivity(Intent i) {
        startActivity(i);
    }

    /**
     * Change activity.
     *
     * @param clz the clz
     */
    public void changeActivity(Class clz) {
        Intent i = new Intent(this, clz);
        changeActivity(i);
    }

    /**
     * Change activity with string.
     *
     * @param clz  the clz
     * @param from the from
     */
    public void changeActivityWithString(Class clz, String from) {
        Intent i = new Intent(this, clz);
        i.putExtra("from", from);
        startActivity(i);
    }

    /**
     * Change activity clear back stack.
     *
     * @param clz the clz
     */
    public void changeActivityClearBackStack(Class clz) {
        Intent i = new Intent(this, clz);
        changeActivityClearBackStack(i);
    }

    /**
     * Change activity clear back stack.
     *
     * @param intent the intent
     */
    public void changeActivityClearBackStack(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        changeActivity(intent);
    }

    /**
     * Change int to string string.
     *
     * @param value the value
     * @return the string
     */
    public String changeIntToString(int value) {
        return String.valueOf(value);
    }

    /**
     * Replace fragment.
     *
     * @param container the container
     * @param fragment  the fragment
     * @param tag       the tag
     */
    public void replaceFragment(int container, Fragment fragment, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        ft.replace(container, fragment, tag);
        ft.addToBackStack(null);
        ft.commit();

        setTitle(tag);

        Log.d("ChangingTitle", tag);

        toolbar.setTitleCentered(true);

        setTheme(fragment instanceof DashboardFragment || fragment instanceof EditProfileFragment ? R.style.AppTheme_Dashboard : R.style.AppTheme_Dashboard_Black);
    }

    /**
     * Gets status bar height.
     *
     * @return the status bar height
     */
    public int getStatusBarHeight() {
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    //dialog

    /**
     * The interface Dialog listener.
     */
    public interface DialogListener {

    }

    /**
     * The interface Retry listener.
     */
    public interface RetryListener extends DialogListener {
        /**
         * On click retry.
         */
        void onClickRetry();
    }

    /**
     * The interface Login listener.
     */
    public interface LoginListener extends DialogListener {
        /**
         * On click login.
         */
        void onClickLogin();
    }

    /**
     * The constant DIALOG_INFO.
     */
    public static final int DIALOG_INFO = 0;
    /**
     * The constant DIALOG_EXIT.
     */
    public static final int DIALOG_EXIT = 1;
    /**
     * The constant DIALOG_EXIT_RETRY.
     */
    public static final int DIALOG_EXIT_RETRY = 3;
    /**
     * The constant DIALOG_TOKEN_EXPIRED.
     */
    public static final int DIALOG_TOKEN_EXPIRED = 4;

    public static final int APP_LOGGED_IN_ANOTHER_DEVICE = 5;

    /**
     * Show warning.
     *
     * @param message the message
     */
    public void showWarning(int message) {
        showError(getResources().getString(message));
    }

    /**
     * Show warning.
     *
     * @param message the message
     */
    public void showWarning(String message) {
        showDialog(DIALOG_INFO, getResources().getString(R.string.dialog_title_warning), message, null);
    }

    public void showLoggedInByAnotherInfo(String message){
        showDialog(APP_LOGGED_IN_ANOTHER_DEVICE,getResources().getString(R.string.dialog_title_connection_error),message,null);
    }

    /**
     * Show error.
     *
     * @param message the message
     */
    public void showError(int message) {
        showError(getResources().getString(message));
    }

    /**
     * Show error.
     *
     * @param message the message
     */
    public void showError(String message) {
        showDialog(DIALOG_INFO, getResources().getString(R.string.dialog_title_error), message, null);
    }

    /**
     * Show connection error.
     *
     * @param message the message
     */
    public void showConnectionError(int message) {
        showConnectionError(getResources().getString(message));
    }

    /**
     * Show connection error finish.
     *
     * @param message the message
     */
    public void showConnectionErrorFinish(int message) {
        showConnectionErrorFinish(getResources().getString(message));
    }

    /**
     * Show connection error retry.
     *
     * @param message       the message
     * @param retryListener the retry listener
     */
    public void showConnectionErrorRetry(int message, RetryListener retryListener) {
        showConnectionErrorRetry(getResources().getString(message), retryListener);
    }

    /**
     * Show token expired.
     *
     * @param string the string
     */
    public void showTokenExpired(String string) {

        appRepository.saveIsLoggedIn(false);

        showDialog(DIALOG_TOKEN_EXPIRED, getResources().getString(R.string.dialog_title_token_expired), string, (LoginListener) () -> changeActivityClearBackStack(Login.class));
    }

    /**
     * Show token expired.
     *
     * @param string the string
     */
    public void showTokenExpired(int string) {
        showTokenExpired(getResources().getString(string));
    }

    /**
     * Show connection error.
     *
     * @param message the message
     */
    public void showConnectionError(String message) {
        showDialog(DIALOG_INFO, getResources().getString(R.string.dialog_title_connection_error), message, null);
    }

    /**
     * Show connection error finish.
     *
     * @param message the message
     */
    public void showConnectionErrorFinish(String message) {
        showDialog(DIALOG_EXIT, getResources().getString(R.string.dialog_title_connection_error), message, null);
    }

    /**
     * Show connection error retry.
     *
     * @param message       the message
     * @param retryListener the retry listener
     */
    public void showConnectionErrorRetry(String message, RetryListener retryListener) {
        showDialog(DIALOG_EXIT_RETRY, getResources().getString(R.string.dialog_title_connection_error), message,
                retryListener);
    }

    /**
     * The Dialog.
     */
    Dialog dialog;


    private void showDialog(int dialogType, String title, String message, DialogListener retryListener) {
        //  Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        dialog = new Dialog(this, R.style.AppCompatAlertDialogStyle);

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_info, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);
        dialog.setContentView(view);

        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        TextView tvMessage = dialog.findViewById(R.id.tvMessage);
        Button btnPositive = dialog.findViewById(R.id.btnPositive);
        Button btnNegative = dialog.findViewById(R.id.btnNegative);

        tvTitle.setText(title);
        tvMessage.setText(message);

        switch (dialogType) {
            case DIALOG_INFO:
                btnNegative.setVisibility(View.GONE);
                btnPositive.setText(R.string.dialog_btn_ok);
                break;
            case DIALOG_EXIT:

            case APP_LOGGED_IN_ANOTHER_DEVICE:
                btnNegative.setVisibility(View.GONE);
                btnPositive.setText(R.string.dialog_btn_exit);
                break;
            case DIALOG_EXIT_RETRY:
                btnNegative.setVisibility(View.VISIBLE);
                btnPositive.setText(R.string.dialog_btn_retry);
                btnNegative.setText(R.string.dialog_btn_exit);
                break;
            case DIALOG_TOKEN_EXPIRED:
                btnNegative.setVisibility(View.GONE);
                btnPositive.setText(R.string.dialog_btn_login);
                break;
        }

        btnNegative.setTag(dialogType);
        btnPositive.setTag(dialogType);

        View.OnClickListener onClickListener = view1 -> {
            System.out.println("dialog dismissed");
            dialog.dismiss();
            if (view1.getId() == R.id.btnPositive) {
                switch ((int) view1.getTag()) {
                    case DIALOG_INFO:
                        dialog.dismiss();
                        break;
                    case DIALOG_EXIT:
                        dialog.dismiss();
                        finish();
                        break;
                    case DIALOG_EXIT_RETRY:
                        if (retryListener != null)
                            ((RetryListener) retryListener).onClickRetry();
                        break;
                    case DIALOG_TOKEN_EXPIRED:
                        if (retryListener != null)
                            ((LoginListener) retryListener).onClickLogin();
                        break;
                    case APP_LOGGED_IN_ANOTHER_DEVICE:
                        appRepository.saveIsLoggedIn(false);
                        changeActivityClearBackStack(Login.class);
                        break;
                }
            } else {
                switch ((int) view1.getTag()) {
                    case DIALOG_EXIT_RETRY:
                        dialog.dismiss();
                        finish();
                        break;
                }
            }
        };

        btnPositive.setOnClickListener(onClickListener);
        btnNegative.setOnClickListener(onClickListener);

        dialog.show();
    }

    /**
     * Show toast.
     *
     * @param message the message
     */
    public void showToast(int message) {
        showToast(getResources().getString(message));
    }

    /**
     * Show toast.
     *
     * @param message the message
     */
    public void showToast(String message) {

        showCustomSnackbar(message);
    }

    /**
     * Gets app repositry.
     *
     * @return the app repositry
     */
    public AppRepository getAppRepositry() {
        return appRepository;
    }

    /**
     * The Snackbar.
     */
    List<View> snackbar = new ArrayList<>();

    private void showCustomSnackbar(String message) {

        View customSnackbar = LayoutInflater.from(context).inflate(R.layout.custom_snackbar, null, false);
        ((TextView) customSnackbar.findViewById(R.id.text)).setText(message);

        snackbar.add(customSnackbar);
/*        customSnackbar.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //action implementation
            }
        });*/

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.format = PixelFormat.TRANSLUCENT;
        lp.gravity = Gravity.BOTTOM;
        lp.flags = WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        customSnackbar.setLayoutParams(lp);

        if (getWindowManager() != null) {
            getWindowManager().addView(customSnackbar, customSnackbar.getLayoutParams());

            Point m = new Point();
            getWindowManager().getDefaultDisplay().getSize(m);
            int childMeasureSpecWidth = ViewGroup.getChildMeasureSpec(View.MeasureSpec.makeMeasureSpec(m.x, View.MeasureSpec.EXACTLY), 0, lp.width);
            int childMeasureSpecHeight = ViewGroup.getChildMeasureSpec(View.MeasureSpec.makeMeasureSpec(m.y, View.MeasureSpec.EXACTLY), 0, lp.height);
            customSnackbar.measure(childMeasureSpecWidth, childMeasureSpecHeight);

            customSnackbar.setTranslationY(customSnackbar.getMeasuredHeight());
            customSnackbar.animate()
                    .setDuration(300)
                    .translationX(0.0f)
                    .translationY(0.0f);
        }
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                customSnackbar.animate()
                        .setDuration(300)
                        .translationX(0.0f)
                        .translationY(customSnackbar.getMeasuredHeight())
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
                                if (windowManager != null) {
                                    if (!isDestroyed) {
                                        customSnackbar.setVisibility(View.GONE);
                                        if (snackbar.contains(customSnackbar)) {
                                            windowManager.removeViewImmediate(customSnackbar);
                                            snackbar.remove(customSnackbar);
                                        }
                                    }
                                }
                            }
                        });
            }
        };
        getHandler().postDelayed(runnable, 1700);
    }

    /**
     * Gets handler.
     *
     * @return the handler
     */
    public Handler getHandler() {
        if (handler == null) handler = new Handler();
        return handler;
    }

    /**
     * The Handler.
     */
    Handler handler;

    /**
     * The Is destroyed.
     */
    boolean isDestroyed;

    @Override
    public boolean isDestroyed() {
        return isDestroyed;
    }

    @Override
    protected void onDestroy() {
        if (dialog != null) dialog.dismiss();
        dialog = null;
        if (progressDialog != null) progressDialog.dismiss();
        progressDialog = null;
        getHandler().removeCallbacksAndMessages(null);
        for (View v : snackbar) {
            getWindowManager().removeViewImmediate(v);
        }
        isDestroyed = true;
        super.onDestroy();
    }


    //Location
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
                                rae.startResolutionForResult(this, REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException sie) {
                                Log.i(TAG, "PendingIntent unable to execute request.");
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            String errorMessage = "Location settings are inadequate, and cannot be " +
                                    "fixed here. Fix in Settings.";
                            Log.e(TAG, errorMessage);
                            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
                    }

                    updateLocationUI();
                });
    }

    /**
     * Stop location updates.
     */
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
            currentLat = String.valueOf(mCurrentLocation.getLatitude());
            currentLong = String.valueOf(mCurrentLocation.getLongitude());
            sendMessageToUI(currentLat, currentLong);
        }
    }


    private void sendMessageToUI(String lat, String lng) {
        Log.d("updateLocation", "Sending info... Latitude : " + lat + "Longitude : " + lng);
        Intent intent = new Intent(ACTION_LOCATION_BROADCAST);
        intent.putExtra(EXTRA_LATITUDE, lat);
        intent.putExtra(EXTRA_LONGITUDE, lng);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        sentLocationToWeb(lat, lng);
    }

    private void sentLocationToWeb(String lat, String lng) {
        BaseApplication.publish(lat, lng);
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