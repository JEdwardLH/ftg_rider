package com.foodtogo.rider.model.splash;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Splash response.
 */
public class SplashResponse {
    @SerializedName("splash_screen_android")
    @Expose
    private String splashScreenAndroid;
    @SerializedName("splash_screen_ios")
    @Expose
    private String splashScreenIos;
    @SerializedName("logo_android")
    @Expose
    private String logoAndroid;
    @SerializedName("login_logo_ios")
    @Expose
    private String loginLogoIos;
    @SerializedName("signup_logo_ios")
    @Expose
    private String signupLogoIos;
    @SerializedName("forgot_password_logo_ios")
    @Expose
    private String forgotPasswordLogoIos;

    /**
     * Gets splash screen android.
     *
     * @return the splash screen android
     */
    public String getSplashScreenAndroid() {
        return splashScreenAndroid;
    }

    /**
     * Sets splash screen android.
     *
     * @param splashScreenAndroid the splash screen android
     */
    public void setSplashScreenAndroid(String splashScreenAndroid) {
        this.splashScreenAndroid = splashScreenAndroid;
    }

    /**
     * Gets splash screen ios.
     *
     * @return the splash screen ios
     */
    public String getSplashScreenIos() {
        return splashScreenIos;
    }

    /**
     * Sets splash screen ios.
     *
     * @param splashScreenIos the splash screen ios
     */
    public void setSplashScreenIos(String splashScreenIos) {
        this.splashScreenIos = splashScreenIos;
    }

    /**
     * Gets logo android.
     *
     * @return the logo android
     */
    public String getLogoAndroid() {
        return logoAndroid;
    }

    /**
     * Sets logo android.
     *
     * @param logoAndroid the logo android
     */
    public void setLogoAndroid(String logoAndroid) {
        this.logoAndroid = logoAndroid;
    }

    /**
     * Gets login logo ios.
     *
     * @return the login logo ios
     */
    public String getLoginLogoIos() {
        return loginLogoIos;
    }

    /**
     * Sets login logo ios.
     *
     * @param loginLogoIos the login logo ios
     */
    public void setLoginLogoIos(String loginLogoIos) {
        this.loginLogoIos = loginLogoIos;
    }

    /**
     * Gets signup logo ios.
     *
     * @return the signup logo ios
     */
    public String getSignupLogoIos() {
        return signupLogoIos;
    }

    /**
     * Sets signup logo ios.
     *
     * @param signupLogoIos the signup logo ios
     */
    public void setSignupLogoIos(String signupLogoIos) {
        this.signupLogoIos = signupLogoIos;
    }

    /**
     * Gets forgot password logo ios.
     *
     * @return the forgot password logo ios
     */
    public String getForgotPasswordLogoIos() {
        return forgotPasswordLogoIos;
    }

    /**
     * Sets forgot password logo ios.
     *
     * @param forgotPasswordLogoIos the forgot password logo ios
     */
    public void setForgotPasswordLogoIos(String forgotPasswordLogoIos) {
        this.forgotPasswordLogoIos = forgotPasswordLogoIos;
    }
}
