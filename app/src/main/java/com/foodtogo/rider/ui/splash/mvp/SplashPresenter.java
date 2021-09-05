package com.foodtogo.rider.ui.splash.mvp;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.foodtogo.rider.base.FragmentActivity;
import com.foodtogo.rider.data.source.AppRepository;
import com.foodtogo.rider.ui.dashboard.activity.DashboardActivity;
import com.foodtogo.rider.ui.login.activity.Login;
import com.foodtogo.rider.ui.workinghours.activity.WorkingHours;

/**
 * The type Splash presenter.
 */
public class SplashPresenter implements SplashContractor.Presenter {
    private SplashContractor.View mView;
    private AppRepository appRepository;
    private Context context;
    /**
     * The M wait handler.
     */
    Handler mWaitHandler;
    private int SPLASH_TIMEOUT = 3000;

    /**
     * Instantiates a new Splash presenter.
     *
     * @param view          the view
     * @param appRepository the app repository
     * @param context       the context
     */
    public SplashPresenter(SplashContractor.View view, AppRepository appRepository, Context context) {
        this.mView = view;
        this.appRepository = appRepository;
        this.context = context;
    }

    @Override
    public void splashDelay() {
        mWaitHandler = new Handler();
        mWaitHandler.postDelayed(() -> {
            try {
                if (!appRepository.isLoggedIn()) {
                    mView.moveToNextPage(new Intent(context, Login.class));
                } else {
                    System.out.println("workStatus" + appRepository.getWorkingHours());
                    if (appRepository.getWorkingHours().equals("Updated") && appRepository.getUploadDocumentStatus().equals("Updated")) {
                        mView.moveToNextPage(new Intent(context, DashboardActivity.class));
                    } else {
                        if (!appRepository.getWorkingHours().equals("Updated")) {
                            mView.moveToNextPage(new Intent(context, WorkingHours.class));
                        } else {
                            mView.moveToNextPage(FragmentActivity.createIntent(context, FragmentActivity.OPEN_PROFILE));
                        }

                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, SPLASH_TIMEOUT);  // 1sec
    }

    @Override
    public void close() {
        if (mWaitHandler != null) {
            mWaitHandler.removeCallbacksAndMessages(null);
        }

    }

}
