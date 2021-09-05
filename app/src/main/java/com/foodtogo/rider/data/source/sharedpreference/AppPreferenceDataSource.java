package com.foodtogo.rider.data.source.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.foodtogo.rider.data.source.AppDataSource;


/**
 * The type App preference data source.
 */
public class AppPreferenceDataSource implements AppDataSource, PreferenceKeys {

    private SharedPreferences sharedPreferences;

    /**
     * Instantiates a new App preference data source.
     *
     * @param context the context
     */
    public AppPreferenceDataSource(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE);
    }

    @Override
    public void saveIsLoggedIn(boolean isLoggedIn) {
        sharedPreferences.edit().putBoolean(IS_LOGGED_IN, isLoggedIn).apply();

    }

    @Override
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

    @Override
    public void setUserId(String userId) {
        sharedPreferences.edit().putString(USER_ID, userId).apply();
    }

    @Override
    public String getUserId() {
        return sharedPreferences.getString(USER_ID, "0");
    }

    @Override
    public void setOAuthKey(String oAuthKey) {
        sharedPreferences.edit().putString(OAUTH_KEY, oAuthKey).apply();
    }

    @Override
    public String getOAuthKey() {
        return sharedPreferences.getString(OAUTH_KEY, "");
    }

    @Override
    public void setLanguage(String lan) {
        sharedPreferences.edit().putString(LANGUAGE, lan).apply();
    }

    @Override
    public String getLanguage() {
        return sharedPreferences.getString(LANGUAGE, "en");
    }

    @Override
    public void saveUserName(String name) {
        sharedPreferences.edit().putString(USERNAME, name).apply();
    }

    @Override
    public String getUserName() {
        return sharedPreferences.getString(USERNAME, "user");
    }

    @Override
    public void saveUserEmail(String email) {
        sharedPreferences.edit().putString(USEREMAIL, email).apply();
    }

    @Override
    public String getUserEmail() {
        return sharedPreferences.getString(USEREMAIL, "Email");
    }

    @Override
    public void setWorkingStatus(String workingStatus) {
        sharedPreferences.edit().putString(WORKING_HOUR, workingStatus).apply();
    }

    @Override
    public String getWorkingHours() {
        return sharedPreferences.getString(WORKING_HOUR, "WorkHour");
    }

    @Override
    public void setUploadDocumentStatus(String uploadDocumentStatus) {
        sharedPreferences.edit().putString(DOCUMENT_STATUS, uploadDocumentStatus).apply();
    }

    @Override
    public String getUploadDocumentStatus() {
        return sharedPreferences.getString(DOCUMENT_STATUS, "document");
    }

    @Override
    public void setAppLogo(String appLogo) {
        sharedPreferences.edit().putString(APP_LOGO, appLogo).apply();
    }

    @Override
    public String getAppLogo() {
        return sharedPreferences.getString(APP_LOGO, "");
    }

    @Override
    public void setIsInActiveDayInfoShown(boolean isShown) {
       sharedPreferences.edit().putBoolean(IN_ACTIVE_DAY_SHOWN,isShown).apply();
    }

    @Override
    public boolean getIsInActiveDayInfoShown() {
        return sharedPreferences.getBoolean(IN_ACTIVE_DAY_SHOWN,false);
    }

    @Override
    public void setIsShownInfoDate(String date) {
        sharedPreferences.edit().putString(INFO_SHOWN_DATE,date).apply();
    }

    @Override
    public String getIsShownInfoDate() {
        return sharedPreferences.getString(INFO_SHOWN_DATE,"");
    }
}
