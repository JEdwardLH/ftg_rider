package com.foodtogo.rider.data.source;

/**
 * The interface App data source.
 */
public interface AppDataSource {

    /**
     * Save is logged in.
     *
     * @param isLoggedIn the is logged in
     */
    void saveIsLoggedIn(boolean isLoggedIn);

    /**
     * Is logged in boolean.
     *
     * @return the boolean
     */
    boolean isLoggedIn();

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    void setUserId(String userId);

    /**
     * Gets user id.
     *
     * @return the user id
     */
    String getUserId();

    /**
     * Sets o auth key.
     *
     * @param oAuthKey the o auth key
     */
    void setOAuthKey(String oAuthKey);

    /**
     * Gets o auth key.
     *
     * @return the o auth key
     */
    String getOAuthKey();

    /**
     * Sets language.
     *
     * @param lan the lan
     */
    void setLanguage(String lan);

    /**
     * Gets language.
     *
     * @return the language
     */
    String getLanguage();

    /**
     * Save user name.
     *
     * @param name the name
     */
    void saveUserName(String name);

    /**
     * Gets user name.
     *
     * @return the user name
     */
    String getUserName();

    /**
     * Save user email.
     *
     * @param mail the mail
     */
    void saveUserEmail(String mail);

    /**
     * Gets user email.
     *
     * @return the user email
     */
    String getUserEmail();

    /**
     * Sets working status.
     *
     * @param workingStatus the working status
     */
    void setWorkingStatus(String workingStatus);

    /**
     * Gets working hours.
     *
     * @return the working hours
     */
    String getWorkingHours();

    /**
     * Sets upload document status.
     *
     * @param uploadDocumentStatus the upload document status
     */
    void setUploadDocumentStatus(String uploadDocumentStatus);

    /**
     * Gets upload document status.
     *
     * @return the upload document status
     */
    String getUploadDocumentStatus();

    /**
     * Sets app logo.
     *
     * @param appLogo the app logo
     */
    void setAppLogo(String appLogo);

    /**
     * Gets app logo.
     *
     * @return the app logo
     */
    String getAppLogo();

    void setIsInActiveDayInfoShown(boolean isShown);

    boolean getIsInActiveDayInfoShown();

    void setIsShownInfoDate(String date);

    String getIsShownInfoDate();


}
