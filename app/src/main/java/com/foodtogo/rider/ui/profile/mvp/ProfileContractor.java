package com.foodtogo.rider.ui.profile.mvp;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.foodtogo.rider.BaseView;
import com.foodtogo.rider.data.rest.ApiInterface;
import com.foodtogo.rider.model.changepassword.ChangePasswordResponse;
import com.foodtogo.rider.model.profile.ProfileResponse;
import com.foodtogo.rider.model.profiledetails.ProfileDetailResponse;


/**
 * The interface Profile contractor.
 */
public interface ProfileContractor {

    /**
     * The interface View.
     */
    interface View extends BaseView {
        /**
         * Show snack bar.
         *
         * @param msg the msg
         */
        void showSnackBar(int msg);

        /**
         * Profile response.
         *
         * @param profileResponse the profile response
         * @param msg             the msg
         */
        void profileResponse(ProfileResponse profileResponse, String msg);

        /**
         * Change password response.
         *
         * @param response the response
         * @param msg      the msg
         */
        void changePasswordResponse(ChangePasswordResponse response, String msg);

        /**
         * Profile details.
         *
         * @param response the response
         * @param msg      the msg
         */
        void profileDetails(ProfileDetailResponse response, String msg);

        /**
         * Show otp popup.
         */
        void showOtpPopup();

        /**
         * Profile with otp success.
         */
        void profileWithOtpSuccess();


    }

    /**
     * The interface Presenter.
     */
    interface Presenter {

        /**
         * Update profile.
         *
         * @param fName           the f name
         * @param lName           the l name
         * @param availableStatus the available status
         * @param vehicleType     the vehicle type
         * @param orderLimit      the order limit
         * @param phone           the phone
         * @param email           the email
         */
        void updateProfile(String fName, String lName, boolean availableStatus, String vehicleType, String orderLimit,
                           String phone, String email,String addressName,String licenseName);

        /**
         * Profile update success.
         *
         * @param profileResponse the profile response
         * @param msg             the msg
         */
        void profileUpdateSuccess(ProfileResponse profileResponse, String msg);

        /**
         * Profile update with otp success.
         *
         * @param msg the msg
         */
        void profileUpdateWithOtpSuccess(String msg);

        /**
         * Change password success.
         *
         * @param response the response
         * @param msg      the msg
         */
        void changePasswordSuccess(ChangePasswordResponse response, String msg);

        /**
         * Profile detail success.
         *
         * @param response the response
         * @param msg      the msg
         */
        void profileDetailSuccess(ProfileDetailResponse response, String msg);

        /**
         * Change password validation.
         *
         * @param layout      the layout
         * @param oldPassword the old password
         * @param newPassword the new password
         */
        void changePasswordValidation(ConstraintLayout layout, String oldPassword, String newPassword);

        /**
         * Profile error.
         *
         * @param error the error
         */
        void profileError(String error);

        /**
         * Profile error.
         *
         * @param error the error
         */
        void profileError(int error);

        /**
         * Change password request.
         *
         * @param oldPassword the old password
         * @param newPassword the new password
         */
        void changePasswordRequest(String oldPassword, String newPassword);

        /**
         * Gets profile data.
         */
        void getProfileData();

        /**
         * Response time string.
         *
         * @param responseTime the response time
         * @return the string
         */
        String responseTime(String responseTime);

        /**
         * Check and update otp.
         *
         * @param layout          the layout
         * @param otp             the otp
         * @param fName           the f name
         * @param lName           the l name
         * @param availableStatus the available status
         * @param vehicleType     the vehicle type
         * @param orderLimit      the order limit
         * @param phone           the phone
         * @param email           the email
         */
        void checkAndUpdateOtp(ConstraintLayout layout, String otp, String fName, String lName, boolean availableStatus,
                               String vehicleType, String orderLimit,
                               String phone, String email);

        /**
         * Close.
         */
        void close();
        void loggedInByAnotherError(String msg);
    }

    /**
     * The interface Model.
     */
    interface Model {
        /**
         * Request profile update.
         *
         * @param apiInterface    the api interface
         * @param lan             the lan
         * @param fName           the f name
         * @param lName           the l name
         * @param availableStatus the available status
         * @param vehicleType     the vehicle type
         * @param orderLimit      the order limit
         * @param phone           the phone
         * @param email           the email
         * @param location        the location
         * @param latitude        the latitude
         * @param longitude       the longitude
         * @param imageUri        the image uri
         * @param license         the license
         * @param address_proof   the address proof
         */
        void requestProfileUpdate(ApiInterface apiInterface, String lan, String fName, String lName, boolean availableStatus, String vehicleType, String orderLimit,
                                  String phone, String email, String location, String latitude, String longitude, String imageUri, String license, String address_proof);

        /**
         * Request change password.
         *
         * @param apiInterface the api interface
         * @param lan          the lan
         * @param oldPassword  the old password
         * @param newPassword  the new password
         */
        void requestChangePassword(ApiInterface apiInterface, String lan, String oldPassword, String newPassword);

        /**
         * Request profile data.
         *
         * @param apiInterface the api interface
         * @param lan          the lan
         */
        void requestProfileData(ApiInterface apiInterface, String lan);

        /**
         * Request profile update with otp.
         *
         * @param apiInterface    the api interface
         * @param lan             the lan
         * @param fName           the f name
         * @param lName           the l name
         * @param availableStatus the available status
         * @param vehicleType     the vehicle type
         * @param orderLimit      the order limit
         * @param phone           the phone
         * @param email           the email
         * @param location        the location
         * @param latitude        the latitude
         * @param longitude       the longitude
         * @param imageUri        the image uri
         * @param otp             the otp
         */
        void requestProfileUpdateWithOtp(ApiInterface apiInterface, String lan, String fName, String lName, boolean availableStatus, String vehicleType, String orderLimit,
                                         String phone, String email, String location, String latitude, String longitude, String imageUri, String otp);

        /**
         * Close.
         */
        void close();

    }
}
