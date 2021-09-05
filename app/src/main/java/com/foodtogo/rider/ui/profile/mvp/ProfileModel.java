package com.foodtogo.rider.ui.profile.mvp;

import android.net.Uri;

import com.foodtogo.rider.R;
import com.foodtogo.rider.data.rest.ApiInterface;
import com.foodtogo.rider.model.BaseResponse;
import com.foodtogo.rider.model.CommonResponse;
import com.foodtogo.rider.model.changepassword.ChangePasswordRequest;
import com.foodtogo.rider.model.changepassword.ChangePasswordResponse;
import com.foodtogo.rider.model.Request;
import com.foodtogo.rider.model.profile.ProfileRequest;
import com.foodtogo.rider.model.profile.ProfileResponse;
import com.foodtogo.rider.model.profiledetails.ProfileDetailResponse;
import com.foodtogo.rider.util.CommonStrings;
import com.foodtogo.rider.util.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.HttpException;


/**
 * The type Profile model.
 */
public class ProfileModel implements ProfileContractor.Model {
    private ProfilePresenter mPresenter;
    private CompositeDisposable disposable = new CompositeDisposable();


    /**
     * Instantiates a new Profile model.
     *
     * @param presenter the presenter
     */
    ProfileModel(ProfilePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void requestProfileUpdate(ApiInterface apiInterface, String lan, String fName, String lName,
                                     boolean availableStatus, String vehicleType, String orderLimit,
                                     String phone, String email, String location, String latitude,
                                     String longitude, String imageUri, String license, String addressProof) {
        ProfileRequest profileRequest = new ProfileRequest();
        profileRequest.setDelivery_img(imageUri);
        profileRequest.setLang(lan);
        profileRequest.setDelivery_fname(fName);
        profileRequest.setDelivery_lname(lName);
        profileRequest.setDelivery_status(availableStatus ? "1" : "0");
        profileRequest.setDelivery_vehicle(vehicleType);
        profileRequest.setOrder_limit(orderLimit);
        profileRequest.setDelivery_phone(phone);
        profileRequest.setDelivery_email(email);
        profileRequest.setDelivery_location(location);
        profileRequest.setDelivery_latitude(latitude);
        profileRequest.setDelivery_longitude(longitude);
        System.out.println("model_imageUri" + imageUri);
        disposable.add(apiInterface.profileUpdate(createPartFromString(profileRequest.getLang()), createPartFromString(profileRequest.getDelivery_fname()), createPartFromString(profileRequest.getDelivery_lname()),
                createPartFromString(profileRequest.getDelivery_location()), createPartFromString(profileRequest.getDelivery_latitude()), createPartFromString(profileRequest.getDelivery_longitude()),
                createPartFromString(profileRequest.getDelivery_status()), createPartFromString(profileRequest.getDelivery_vehicle()), createPartFromString(profileRequest.getOrder_limit()),
                createPartFromString(profileRequest.getDelivery_email()), createPartFromString(profileRequest.getDelivery_phone()),
                prepareMultipartFile("delivery_img", imageUri), prepareMultiPartDocument("licence", license),
                prepareMultiPartDocument("address_proof", addressProof))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<ProfileResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<ProfileResponse> baseResponse) {
                        try {
                            if (baseResponse.getCode() == 200 || baseResponse.getCode() == 201) {
                                mPresenter.profileUpdateSuccess(baseResponse.getData(), baseResponse.getMessage());
                            }else if(baseResponse.getCode()==401){
                              mPresenter.loggedInByAnotherError(baseResponse.getMessage());
                            } else {
                                mPresenter.profileError(baseResponse.getMessage());
                                if (baseResponse.getCode() == 400 && baseResponse.getMessage().equals(CommonStrings.TOKEN_EXPIRED)) {
                                    mPresenter.profileError(baseResponse.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("error1" + e);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("error" + e);
                        if (e instanceof HttpException) {
                            ResponseBody responseBody = ((HttpException) e).response().errorBody();
                            mPresenter.profileError(NetworkUtils.getErrorMessage(responseBody));
                        } else if (e instanceof SocketTimeoutException) {
                            mPresenter.profileError(R.string.time_out_error);
                        } else if (e instanceof IOException) {
                            mPresenter.profileError(R.string.network_error);
                        } else {
                            mPresenter.profileError(e.getMessage());
                        }

                    }
                }));

    }


    @Override
    public void close() {
        disposable.dispose();
    }


    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(MediaType.parse("text/plain"), descriptionString);
    }

    private MultipartBody.Part prepareMultipartFile(String partName, String fileUri) {
        File profileImageFile = new File(Uri.fromFile(new File(fileUri)).getPath());
        RequestBody requestFile;
        if (!fileUri.equals("")) {
            requestFile = RequestBody.create(MediaType.parse("image/*"), profileImageFile);
        } else {
            requestFile = RequestBody.create(MediaType.parse("image/*"), "");
        }

        return MultipartBody.Part.createFormData(partName, profileImageFile.getName(), requestFile);
    }

    private MultipartBody.Part prepareMultiPartDocument(String fileName, String filePath) {
        File documentFile = new File(Uri.fromFile(new File(filePath)).getPath());
        RequestBody requestFile;
        if (!filePath.equals("")) {
            if (getFileExtension(documentFile).equals("pdf")) {
                requestFile = RequestBody.create(MediaType.parse("application/pdf"), documentFile);
            } else {
                requestFile = RequestBody.create(MediaType.parse("*/*"), documentFile);
            }

        } else {
            requestFile = RequestBody.create(MediaType.parse("*/*"), "");
        }
        System.out.println("requestFile" + requestFile);
        return MultipartBody.Part.createFormData(fileName, documentFile.getName(), requestFile);
    }


    @Override
    public void requestChangePassword(ApiInterface apiInterface, String lan, String oldPassword, String newPassword) {
        ChangePasswordRequest passwordRequest = new ChangePasswordRequest();
        passwordRequest.setLang(lan);
        passwordRequest.setOldPassword(oldPassword);
        passwordRequest.setNewPassword(newPassword);
        disposable.add(apiInterface.changePassword(passwordRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<ChangePasswordResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<ChangePasswordResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.changePasswordSuccess(baseResponse.getData(), baseResponse.getMessage());
                        } else {
                            mPresenter.profileError(baseResponse.getMessage());
                            if (baseResponse.getCode() == 400 && baseResponse.getMessage().equals(CommonStrings.TOKEN_EXPIRED)) {
                                mPresenter.profileError(baseResponse.getCode());
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody responseBody = ((HttpException) e).response().errorBody();
                            mPresenter.profileError(NetworkUtils.getErrorMessage(responseBody));
                        } else if (e instanceof SocketTimeoutException) {
                            mPresenter.profileError(R.string.time_out_error);
                        } else if (e instanceof IOException) {
                            mPresenter.profileError(R.string.network_error);
                        } else {
                            mPresenter.profileError(e.getMessage());
                        }

                    }
                }));
    }

    @Override
    public void requestProfileData(ApiInterface apiInterface, String lan) {
        Request commonRequest = new Request();
        commonRequest.setLang(lan);
        disposable.add(apiInterface.getProfileData(commonRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<ProfileDetailResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<ProfileDetailResponse> baseResponse) {
                        if (baseResponse.getCode() == 201 || baseResponse.getCode() == 200) {
                            mPresenter.profileDetailSuccess(baseResponse.getData(), baseResponse.getMessage());
                        } else if(baseResponse.getCode()==401){
                            mPresenter.loggedInByAnotherError(baseResponse.getMessage());
                        }else{
                            mPresenter.profileError(baseResponse.getMessage());
                            if (baseResponse.getCode() == 400 && baseResponse.getMessage().equals(CommonStrings.TOKEN_EXPIRED)) {
                                mPresenter.profileError(baseResponse.getCode());
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody responseBody = ((HttpException) e).response().errorBody();
                            mPresenter.profileError(NetworkUtils.getErrorMessage(responseBody));
                        } else if (e instanceof SocketTimeoutException) {
                            mPresenter.profileError(R.string.time_out_error);
                        } else if (e instanceof IOException) {
                            mPresenter.profileError(R.string.network_error);
                        } else {
                            mPresenter.profileError(e.getMessage());
                        }

                    }
                }));
    }

    @Override
    public void requestProfileUpdateWithOtp(ApiInterface apiInterface, String lan, String fName, String lName, boolean availableStatus, String vehicleType, String orderLimit, String phone, String email, String location, String latitude, String longitude, String imageUri, String otp) {
        disposable.add(apiInterface.profileUpdateWithOtp(createPartFromString(lan), createPartFromString(otp), createPartFromString(otp), createPartFromString(fName), createPartFromString(lName),
                createPartFromString(location), createPartFromString(latitude), createPartFromString(longitude),
                createPartFromString(availableStatus ? "1" : "0"), createPartFromString(vehicleType), createPartFromString(orderLimit),
                createPartFromString(email), createPartFromString(phone),
                prepareMultipartFile("delivery_img", imageUri))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<CommonResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<CommonResponse> baseResponse) {
                        if (baseResponse.getCode() == 201) {
                            mPresenter.profileUpdateWithOtpSuccess(baseResponse.getMessage());
                        }else if(baseResponse.getCode()==401){
                           mPresenter.loggedInByAnotherError(baseResponse.getMessage());
                        } else {
                            mPresenter.profileError(baseResponse.getMessage());
                            if (baseResponse.getCode() == 400 && baseResponse.getMessage().equals(CommonStrings.TOKEN_EXPIRED)) {
                                mPresenter.profileError(baseResponse.getCode());
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody responseBody = ((HttpException) e).response().errorBody();
                            mPresenter.profileError(NetworkUtils.getErrorMessage(responseBody));
                        } else if (e instanceof SocketTimeoutException) {
                            mPresenter.profileError(R.string.time_out_error);
                        } else if (e instanceof IOException) {
                            mPresenter.profileError(R.string.network_error);
                        } else {
                            mPresenter.profileError(e.getMessage());
                        }

                    }
                }));

    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return "";
    }
}
