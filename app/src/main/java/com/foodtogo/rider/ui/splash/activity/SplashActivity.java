package com.foodtogo.rider.ui.splash.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;

// import com.google.firebase.FirebaseApp;
import com.foodtogo.rider.util.AppUtils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.foodtogo.rider.BuildConfig;
import com.foodtogo.rider.R;
import com.foodtogo.rider.data.source.AppDataSource;
import com.foodtogo.rider.data.source.AppRepository;
import com.foodtogo.rider.data.source.sharedpreference.AppPreferenceDataSource;
import com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys;
import com.foodtogo.rider.ui.dashboard.activity.DashboardActivity;
import com.foodtogo.rider.ui.splash.mvp.SplashContractor;
import com.foodtogo.rider.ui.splash.mvp.SplashPresenter;
import com.foodtogo.rider.util.PreferenceUtils;


/**
 * The type Splash activity.
 */
public class SplashActivity extends AppCompatActivity implements PreferenceKeys, SplashContractor.View {
    /**
     * The Splash presenter.
     */
    SplashPresenter splashPresenter;
    /**
     * The constant CHANNEL_ID.
     */
    final static String CHANNEL_ID = "channel_01";
    private static String ARG_OPEN = "arg_open";

    private AppRepository appRepository;
    private AppDataSource appDataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        appDataSource = new AppPreferenceDataSource(this);
        appRepository = new AppRepository(appDataSource);
        splashPresenter = new SplashPresenter(this, appRepository, getApplicationContext());
        // FirebaseApp.initializeApp(getApplicationContext());

        if(getIntent()!=null && getIntent().getStringExtra("body")!=null){
            Intent intent = new Intent(this, DashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(ARG_OPEN, "1");
            startActivity(intent);
            finish();
        }else{
            permissionRequest();
        }
        AppUtils.updateLocale(appRepository.getLanguage(), this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        splashPresenter.close();
    }

    @Override
    public void moveToNextPage(Intent intent) {
        PreferenceUtils.writeString(SplashActivity.this, LANGUAGE, "en");
        startActivity(intent);
        finish();
    }



    private void permissionRequest() {
        // Requesting ACCESS_FINE_LOCATION using Dexter library
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        splashPresenter.splashDelay();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            openSettings();
                        } else {
                            permissionRequest();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}


