package com.foodtogo.rider.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;

import com.foodtogo.rider.R;
import com.foodtogo.rider.base.BaseApplication;

import java.util.Locale;


/**
 * The type App utils.
 */
public final class AppUtils {

    private AppUtils() {
        // This class is not publicly instantiable
    }

    /**
     * Open play store for app.
     *
     * @param context the context
     */
    public static void openPlayStoreForApp(Context context) {
        final String appPackageName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(context.getResources().getString(R.string.app_market_link) + appPackageName)));
        } catch (android.content.ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(context
                            .getResources()
                            .getString(R.string.app_google_play_store_link) + appPackageName)));
        }
    }
    @SuppressLint("NewApi")
    public static void updateLocale(String language, Activity activity) {
        Resources resources = activity.getResources();
        Configuration configuration = resources.getConfiguration();
        if (!configuration.locale.getLanguage().equals(language)) {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
            configuration.locale = locale;
            configuration.setLayoutDirection(locale);
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
        resources = BaseApplication.getContext().getResources();
        configuration = resources.getConfiguration();
        if (!configuration.locale.getLanguage().equals(language)) {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
            configuration.locale = locale;
            configuration.setLayoutDirection(locale);
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }

    }


}
