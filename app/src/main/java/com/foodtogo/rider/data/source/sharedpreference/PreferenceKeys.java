package com.foodtogo.rider.data.source.sharedpreference;

import android.content.Context;

/**
 * The interface Preference keys.
 */
public interface PreferenceKeys {

    /**
     * The constant PREF_NAME.
     */
    String PREF_NAME = "PEOPLE_PREFERENCES";
    /**
     * The constant MODE.
     */
    int MODE = Context.MODE_PRIVATE;

    /**
     * The constant IS_LOGGED_IN.
     */
    String IS_LOGGED_IN = "IsLoggedIn";
    /**
     * The constant USER_ID.
     */
    String USER_ID = "userId";
    /**
     * The constant OAUTH_KEY.
     */
    String OAUTH_KEY = "OAuthKey";
    /**
     * The constant LANGUAGE.
     */
    String LANGUAGE = "language";
    /**
     * The constant USERNAME.
     */
    String USERNAME = "userName";
    /**
     * The constant USEREMAIL.
     */
    String USEREMAIL = "userEmail";
    /**
     * The constant WORKING_HOUR.
     */
    String WORKING_HOUR = "work_hour";
    /**
     * The constant LATITUDE.
     */
    String LATITUDE = "latitude";
    /**
     * The constant LONGITUDE.
     */
    String LONGITUDE = "longitude";
    /**
     * The constant CURRENT_CITY.
     */
    String CURRENT_CITY = "current_city";
    /**
     * The constant USER_IMAGE.
     */
    String USER_IMAGE = "user_image";
    /**
     * The constant SUN_FROM.
     */
    String SUN_FROM = "sun_from";
    /**
     * The constant SUN_TO.
     */
    String SUN_TO = "sun_to";
    /**
     * The constant MON_FROM.
     */
    String MON_FROM = "mon_from";
    /**
     * The constant MON_TO.
     */
    String MON_TO = "mon_TO";
    /**
     * The constant TUE_FROM.
     */
    String TUE_FROM = "tue_from";
    /**
     * The constant TUE_TO.
     */
    String TUE_TO = "tue_to";
    /**
     * The constant WED_FROM.
     */
    String WED_FROM = "wed_from";
    /**
     * The constant WED_TO.
     */
    String WED_TO = "wed_to";
    /**
     * The constant THU_FROM.
     */
    String THU_FROM = "thu_from";
    /**
     * The constant THU_TO.
     */
    String THU_TO = "thu_to";
    /**
     * The constant FRI_FROM.
     */
    String FRI_FROM = "fri_from";
    /**
     * The constant FRI_TO.
     */
    String FRI_TO = "fri_to";
    /**
     * The constant SAT_FROM.
     */
    String SAT_FROM = "sat_from";
    /**
     * The constant SAT_TO.
     */
    String SAT_TO = "sat_to";
    /**
     * The constant LASTLOCATION.
     */
    String LASTLOCATION = "lastUpdateOrigin";
    /**
     * The constant DRIVING_LICENSE.
     */
    String DRIVING_LICENSE = "driving_license";
    /**
     * The constant ADDRESS_PROOF.
     */
    String ADDRESS_PROOF = "address_proof";
    /**
     * The constant DOCUMENT_STATUS.
     */
    String DOCUMENT_STATUS = "document_status";
    /**
     * The constant APP_LOGO.
     */
    String APP_LOGO = "appLogo";
    String FCM_TOKEN = "fcm_token";

    String IN_ACTIVE_DAY_SHOWN = "in_active_day";
    String INFO_SHOWN_DATE = "info_shown_date";
}
