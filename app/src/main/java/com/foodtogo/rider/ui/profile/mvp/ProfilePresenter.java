package com.foodtogo.rider.ui.profile.mvp;

import android.content.Context;
import android.net.Uri;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.text.TextUtils;

import com.foodtogo.rider.R;
import com.foodtogo.rider.data.rest.ApiClient;
import com.foodtogo.rider.data.rest.ApiInterface;
import com.foodtogo.rider.data.source.AppRepository;
import com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys;
import com.foodtogo.rider.model.changepassword.ChangePasswordResponse;
import com.foodtogo.rider.model.profile.ProfileResponse;
import com.foodtogo.rider.model.profiledetails.ProfileDetailResponse;
import com.foodtogo.rider.util.CommonStrings;
import com.foodtogo.rider.util.NetworkUtils;
import com.foodtogo.rider.util.PreferenceUtils;
import com.foodtogo.rider.util.ViewUtils;

import java.io.File;

import static com.foodtogo.rider.util.CommonStrings.LOCAL_EMAIL;
import static com.foodtogo.rider.util.CommonStrings.LOCAL_USER_NAME;


/**
 * The type Profile presenter.
 */
public class ProfilePresenter implements ProfileContractor.Presenter, PreferenceKeys {

    private Context context;
    private ProfileContractor.View mView;
    private ApiInterface apiInterface;
    private ProfileModel profileModel;
    private String lan;
    private String location;
    private String latitude;
    private String longitude;
    private String userImageUri = "";
    private AppRepository mApprepository;

    /**
     * Instantiates a new Profile presenter.
     *
     * @param mView   the m view
     * @param context the context
     */
    public ProfilePresenter(ProfileContractor.View mView, Context context, AppRepository appRepository) {
        this.mView = mView;
        this.context = context;
        this.mApprepository=appRepository;
        apiInterface = ApiClient.getApiInterface();
        profileModel = new ProfileModel(this);
        lan = PreferenceUtils.readString(context, LANGUAGE, "en");
        location = PreferenceUtils.readString(context, CURRENT_CITY, "location");
        latitude = PreferenceUtils.readString(context, LATITUDE, "0.0");
        longitude = PreferenceUtils.readString(context, LONGITUDE, "0.0");

    }


    @Override
    public void updateProfile(String fName, String lName, boolean availableStatus, String vehicleType, String orderLimit,
                              String phone, String email,String addressProofName,String licenseName) {
        if (!PreferenceUtils.readString(context, USER_IMAGE, "").equals("")) {
            String first = PreferenceUtils.readString(context, USER_IMAGE, "").substring(0, 1);
            if (!first.equals("h")) {
                userImageUri = PreferenceUtils.readString(context, USER_IMAGE, "");
            }
        }

        LOCAL_EMAIL = email;
        LOCAL_USER_NAME = String.format(context.getResources().getString(R.string.bind_charge_fare_type), fName, lName);
        validation(fName, lName, vehicleType, availableStatus, orderLimit, phone, email, location, latitude, longitude,
                userImageUri,addressProofName,licenseName);
    }

    @Override
    public void profileUpdateSuccess(ProfileResponse profileResponse, String msg) {
        if (!userImageUri.equals("")) {
            PreferenceUtils.writeString(context, USER_IMAGE, String.valueOf(Uri.fromFile(new File(userImageUri))));
        }
        mView.hideLoadingView();
        if (profileResponse.getOtp() != null) {
            mView.showOtpPopup();
        } else {
            PreferenceUtils.writeString(context, USERNAME, LOCAL_USER_NAME);
            mView.profileResponse(profileResponse, msg);
        }

    }

    @Override
    public void profileUpdateWithOtpSuccess(String msg) {
        PreferenceUtils.writeString(context, USEREMAIL, LOCAL_EMAIL);
        PreferenceUtils.writeString(context, USERNAME, LOCAL_USER_NAME);
        mView.hideLoadingView();
        // mView.showSnackBar(R.string.update_success);
        mView.profileWithOtpSuccess();
    }

    @Override
    public void changePasswordSuccess(ChangePasswordResponse response, String msg) {
        mView.hideLoadingView();
        mView.changePasswordResponse(response, msg);
    }

    @Override
    public void profileDetailSuccess(ProfileDetailResponse response, String msg) {
        mView.profileDetails(response, msg);

    }

    @Override
    public void changePasswordValidation(ConstraintLayout layout, String oldPassword, String newPassword) {
        if (!TextUtils.isEmpty(oldPassword)) {
            if (!TextUtils.isEmpty(newPassword)) {
                if (NetworkUtils.isNetworkConnected(context)) {
                    changePasswordRequest(oldPassword, newPassword);
                } else {
                    ViewUtils.showSnackBar(layout, context.getResources().getString(R.string.no_internet_connection));
                }
            } else {
                ViewUtils.showSnackBar(layout, context.getResources().getString(R.string.fill_password));
            }

        } else {
            ViewUtils.showSnackBar(layout, context.getResources().getString(R.string.fill_password));
        }

    }

    @Override
    public void profileError(String error) {
        mView.hideLoadingView();
        mView.showError(error);
    }

    @Override
    public void profileError(int error) {
        mView.hideLoadingView();
        mView.showError(error);
    }

    @Override
    public void changePasswordRequest(String oldPassword, String newPassword) {
        profileModel.requestChangePassword(apiInterface, lan, oldPassword, newPassword);
    }

    @Override
    public void getProfileData() {
        mView.showLoadingView();
        profileModel.requestProfileData(apiInterface, lan);
    }

    @Override
    public String responseTime(String responseTime) {
        String responseHour = "";
        String arr[] = responseTime.split(":");
        String hour = !arr[0].equals("00") ? arr[0] + "h" : "0h";
        String min = !arr[1].equals("00") ? arr[1] + "m" : "0m";
        if (!hour.equals("0h") && !min.equals("0m"))
            responseHour = hour + " " + min;
        else if (!hour.equals("0h"))
            responseHour = hour;
        else if (!min.equals("0m"))
            responseHour = min;

        return responseHour;
    }

    @Override
    public void checkAndUpdateOtp(ConstraintLayout layout, String otp, String fName, String lName, boolean availableStatus, String vehicleType,
                                  String orderLimit, String phone, String email) {
        userImageUri = PreferenceUtils.readString(context, USER_IMAGE, "");
        if (NetworkUtils.isNetworkConnected(context)) {
            LOCAL_EMAIL = email;
            LOCAL_USER_NAME = String.format(context.getResources().getString(R.string.bind_charge_fare_type), fName, lName);
            profileModel.requestProfileUpdateWithOtp(apiInterface, lan, fName, lName, availableStatus, vehicleType, orderLimit, phone, email,
                    location, latitude, longitude, userImageUri, otp);
        } else
            ViewUtils.showSnackBar(layout, context.getResources().getString(R.string.no_internet_connection));

    }


    @Override
    public void close() {
        profileModel.close();
    }

    @Override
    public void loggedInByAnotherError(String msg) {
        mView.hideLoadingView();
        mView.showLoggedInByAnother(msg);
    }

    private void validation(String fName, String lName, String vehicleType, boolean availableStatus,
                            String orderLimit, String phone, String email, String location, String latitude,
                            String longitude, String uri,String addressName,String licenseName) {
        String license = "";//PreferenceUtils.readString(context, DRIVING_LICENSE, "file");
        String address_proof = "";//PreferenceUtils.readString(context, ADDRESS_PROOF, "file");
        System.out.println("license:"+license);
        System.out.println("address_proof:"+address_proof);
        if (!fName.equals("")) {
            if (!lName.equals("")) {
                if (!phone.equals("")) {
                    if (!email.equals("")) {
                        if (!vehicleType.equals(CommonStrings.CHOOSE_VEHICLE_TYPE)) {
                            if (!orderLimit.equals("")) {
                                /**
                                 * proof check
                                 * */
                                    if(!mApprepository.getUploadDocumentStatus().equals("Updated")){
//                                        if(TextUtils.isEmpty(license) || license.equals("file")){
//                                            mView.showSnackBar(R.string.select_license);
//                                        }else if(TextUtils.isEmpty(address_proof) || address_proof.equals("file")){
//                                            mView.showSnackBar(R.string.select_address_proof);
//                                        }else{
//                                            mView.showLoadingView();
//                                            profileModel.requestProfileUpdate(apiInterface, lan, fName, lName,
//                                                    availableStatus, vehicleType, orderLimit, phone,
//                                                    email, location, latitude, longitude, uri,license,address_proof);
//                                        }
                                        mView.showLoadingView();
                                        profileModel.requestProfileUpdate(apiInterface, lan, fName, lName,
                                                availableStatus, vehicleType, orderLimit, phone,
                                                email, location, latitude, longitude, uri,license,address_proof);
                                    }else{
                                        mView.showLoadingView();
                                        profileModel.requestProfileUpdate(apiInterface, lan, fName, lName,
                                                availableStatus, vehicleType, orderLimit, phone,
                                                email, location, latitude, longitude, uri,license.equals(licenseName)?"":license,address_proof.equals(addressName)?"":address_proof);
                                    }

                            } else {
                                mView.showSnackBar(R.string.fill_order_limit);
                            }

                        } else {
                            mView.showSnackBar(R.string.select_vehicle_type);
                        }

                    } else {
                        mView.showSnackBar(R.string.fill_email_id);
                    }

                } else {
                    mView.showSnackBar(R.string.fill_phone_number);
                }

            } else {
                mView.showSnackBar(R.string.fill_last_name);
            }
        } else {
            mView.showSnackBar(R.string.fill_first_name);
        }
    }


}
