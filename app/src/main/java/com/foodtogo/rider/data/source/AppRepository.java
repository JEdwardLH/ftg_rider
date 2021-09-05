package com.foodtogo.rider.data.source;

/**
 * The type App repository.
 */
public class AppRepository implements AppDataSource {


    private AppDataSource mSharedPrefercenseDataSource;

    /**
     * Instantiates a new App repository.
     *
     * @param sharedPreferences the shared preferences
     */
    public AppRepository(AppDataSource sharedPreferences) {
        this.mSharedPrefercenseDataSource = sharedPreferences;
    }

    @Override
    public void saveIsLoggedIn(boolean isLoggedIn) {
        mSharedPrefercenseDataSource.saveIsLoggedIn(isLoggedIn);
    }

    @Override
    public boolean isLoggedIn() {
        return mSharedPrefercenseDataSource.isLoggedIn();
    }

    @Override
    public void setUserId(String userId) {
        mSharedPrefercenseDataSource.setUserId(userId);
    }

    @Override
    public String getUserId() {
        return mSharedPrefercenseDataSource.getUserId();
    }

    @Override
    public void setOAuthKey(String oAuthKey) {
        mSharedPrefercenseDataSource.setOAuthKey(oAuthKey);
    }

    @Override
    public String getOAuthKey() {
        return mSharedPrefercenseDataSource.getOAuthKey();
    }

    @Override
    public void setLanguage(String lan) {
        mSharedPrefercenseDataSource.setLanguage(lan);
    }

    @Override
    public String getLanguage() {
        return mSharedPrefercenseDataSource.getLanguage();
    }

    @Override
    public void saveUserName(String name) {
        mSharedPrefercenseDataSource.saveUserName(name);
    }

    @Override
    public String getUserName() {
        return mSharedPrefercenseDataSource.getUserName();
    }

    @Override
    public void saveUserEmail(String email) {
        mSharedPrefercenseDataSource.saveUserEmail(email);
    }

    @Override
    public String getUserEmail() {
        return mSharedPrefercenseDataSource.getUserEmail();
    }

    @Override
    public void setWorkingStatus(String workingStatus) {
        mSharedPrefercenseDataSource.setWorkingStatus(workingStatus);
    }

    @Override
    public String getWorkingHours() {
        return mSharedPrefercenseDataSource.getWorkingHours();
    }

    @Override
    public void setUploadDocumentStatus(String uploadDocumentStatus) {
        mSharedPrefercenseDataSource.setUploadDocumentStatus(uploadDocumentStatus);
    }

    @Override
    public String getUploadDocumentStatus() {
        return mSharedPrefercenseDataSource.getUploadDocumentStatus();
    }

    @Override
    public void setAppLogo(String appLogo) {
        mSharedPrefercenseDataSource.setAppLogo(appLogo);
    }

    @Override
    public String getAppLogo() {
        return mSharedPrefercenseDataSource.getAppLogo();
    }

    @Override
    public void setIsInActiveDayInfoShown(boolean isShown) {
      mSharedPrefercenseDataSource.setIsInActiveDayInfoShown(isShown);
    }

    @Override
    public boolean getIsInActiveDayInfoShown() {
    return mSharedPrefercenseDataSource.getIsInActiveDayInfoShown();
    }

    @Override
    public void setIsShownInfoDate(String date) {
        mSharedPrefercenseDataSource.setIsShownInfoDate(date);
    }

    @Override
    public String getIsShownInfoDate() {
        return mSharedPrefercenseDataSource.getIsShownInfoDate();
    }
}
