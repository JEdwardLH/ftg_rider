package com.foodtogo.rider.ui.profile.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.imagepicker.FilePickUtils;
import com.imagepicker.LifeCycleCallBackManager;
import com.foodtogo.rider.R;
import com.foodtogo.rider.base.BaseActivity;
import com.foodtogo.rider.base.BaseFragment;
import com.foodtogo.rider.customview.BottomDialog;
import com.foodtogo.rider.customview.PicassoCircleTransformation;
import com.foodtogo.rider.filepath.FilePath;
import com.foodtogo.rider.model.changepassword.ChangePasswordResponse;
import com.foodtogo.rider.model.profile.ProfileResponse;
import com.foodtogo.rider.model.profiledetails.ProfileDetailResponse;
import com.foodtogo.rider.ui.dashboard.activity.DashboardActivity;
import com.foodtogo.rider.ui.profile.mvp.ProfileContractor;
import com.foodtogo.rider.ui.profile.mvp.ProfilePresenter;
import com.foodtogo.rider.util.CommonStrings;
import com.foodtogo.rider.util.LocationFinder;
import com.foodtogo.rider.util.PreferenceUtils;
import com.foodtogo.rider.util.ViewUtils;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.imagepicker.FilePickUtils.CAMERA_PERMISSION;
import static com.imagepicker.FilePickUtils.STORAGE_PERMISSION_IMAGE;
import static com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys.ADDRESS_PROOF;
import static com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys.CURRENT_CITY;
import static com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys.DRIVING_LICENSE;
import static com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys.USER_IMAGE;
import static com.foodtogo.rider.util.CommonStrings.CHOOSE_VEHICLE_TYPE;

/**
 * The type Edit profile fragment.
 */
public class EditProfileFragment extends BaseFragment implements ProfileContractor.View {

    private BottomDialog bottomDialog;
    /**
     * The Vehicle array adapter.
     */
    ArrayAdapter<String> vehicleArrayAdapter;
    private FilePickUtils filePickUtils;
    private LifeCycleCallBackManager lifeCycleCallBackManager;
    /**
     * The Vehicle type array.
     */
    private String[] vehicleTypeArray =null;
    /**
     * The Selected vehicle type.
     */
    String selectedVehicleType = CommonStrings.CHOOSE_VEHICLE_TYPE;
    /**
     * The User image.
     */
    @BindView(R.id.user_image)
    ImageView userImage;
    /**
     * The Vehicle type spinner.
     */
    @BindView(R.id.spinner_vehicle_type)
    Spinner vehicleTypeSpinner;
    /**
     * The Status switch.
     */
    @BindView(R.id.switch_status)
    SwitchCompat statusSwitch;
    /**
     * The Update profile btn.
     */
    @BindView(R.id.update)
    Button updateProfileBtn;
    /**
     * The F name.
     */
    @BindView(R.id.edt_first_name)
    EditText fName;
    /**
     * The L name.
     */
    @BindView(R.id.edt_last_name)
    EditText lName;
    /**
     * The Email.
     */
    @BindView(R.id.edt_email)
    EditText email;
    /**
     * The Mobile.
     */
    @BindView(R.id.edt_mobile)
    EditText mobile;
    /**
     * The Order limit.
     */
    @BindView(R.id.edt_order_limit)
    EditText orderLimit;
    /**
     * The wallet limit.
     */
    @BindView(R.id.wallet)
    EditText wallet;
    /**
     * The Password.
     */
    @BindView(R.id.edt_password)
    EditText password;
    /**
     * The Response time.
     */
    @BindView(R.id.edt_response_time)
    EditText responseTime;
    /**
     * The Base fare.
     */
    @BindView(R.id.edt_base_fare)
    EditText baseFare;
    /**
     * The Km charge.
     */
    @BindView(R.id.edt_charge)
    EditText kmCharge;
    /**
     * The Change password.
     */
    @BindView(R.id.label_change_password)
    TextView changePassword;
    /**
     * The Edt address proof.
     */
    @BindView(R.id.edt_address_proof)
    EditText edtAddressProof;
    /**
     * The Edt driving license.
     */
    @BindView(R.id.edt_driving_license)
    EditText edtDrivingLicense;
    /**
     * The Constraint layout.
     */
    @BindView(R.id.inner_container)
    ConstraintLayout constraintLayout;

    @BindView(R.id.km_hr_charge_label)
    TextView fareText;
    /**
     * The Nsv.
     */
    @BindView(R.id.nsv)
    NestedScrollView nsv;
    /**
     * The Presenter.
     */
    ProfilePresenter presenter;
    /**
     * The Pick file request.
     */
    int PICK_FILE_REQUEST = 1;
    /**
     * The Proof.
     */
    int PROOF = 0;
    private String selectedFilePath="";
    private String drivingLicenseName="";
    private String addressProofName="";


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) nsv.getLayoutParams();
        params.setMargins(0, 0, 0, params.bottomMargin + getStatusBarHeight());
        nsv.setLayoutParams(params);

        password.setText(getResources().getString(R.string.dashboard));
        presenter = new ProfilePresenter(this, getContext(),appRepository);
        setVehicleTypeSpinner();
        if (isNetworkConnected())
            presenter.getProfileData();
        else
            showSnackBar(R.string.no_internet_connection);

        checkLocation();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup parent,
                             Bundle savedInstanseState) {

        return super.onCreateView(inflater, parent, savedInstanseState);
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater,
                                        ViewGroup parent,
                                        Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_profile, parent, false);
    }

    private void checkLocation() {
        if (PreferenceUtils.readString(context, CURRENT_CITY, "location").equals("location")) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationBroadCastReceiver();
        }
    }

    private void locationBroadCastReceiver() {
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        String latitude = intent.getStringExtra(BaseActivity.EXTRA_LATITUDE);
                        String longitude = intent.getStringExtra(BaseActivity.EXTRA_LONGITUDE);
                        if(latitude!=null && longitude!=null)
                        LocationFinder.updateUI(latitude, longitude, getActivity());
                    }
                }, new IntentFilter(BaseActivity.ACTION_LOCATION_BROADCAST)
        );
    }

    private void setVehicleTypeSpinner() {
        vehicleTypeArray=getResources().getStringArray(R.array.vehicle_type_array);
        vehicleArrayAdapter = new ArrayAdapter<>(getContext(),
                R.layout.simple_spinner_custom_text_view, vehicleTypeArray);
        vehicleTypeSpinner.setAdapter(vehicleArrayAdapter);

    }

    /**
     * Click password change.
     */
    @OnClick(R.id.edt_password)
    void clickPasswordChange() {
        changePassword.performClick();
    }

    /**
     * User image selection.
     */
    @OnClick(R.id.user_image)
    void userImageSelection() {
        filePickUtils = new FilePickUtils(EditProfileFragment.this, onFileChoose);
        lifeCycleCallBackManager = filePickUtils.getCallBackManager();
        bindBottomSheet();
    }

    /**
     * Go to work hour page.
     */
  /*  @OnClick(R.id.working_hour)
    void goToWorkHourPage() {
        changeActivityWithString(WorkingHours.class, FROM_EDIT_PROFILE);
    }*/

    /**
     * Update profile.
     */
    @OnClick(R.id.update)
    void updateProfile() {
            if (isNetworkConnected()) {
                selectedVehicleType = vehicleTypeArray[vehicleTypeSpinner.getSelectedItemPosition()];
                presenter.updateProfile(ViewUtils.getEditTextValue(fName), ViewUtils.getEditTextValue(lName), statusSwitch.isChecked(),
                        selectedVehicleType, ViewUtils.getEditTextValue(orderLimit), ViewUtils.getEditTextValue(mobile)
                        , ViewUtils.getEditTextValue(email).trim(),addressProofName,drivingLicenseName);
            } else {
                ViewUtils.showSnackBar(constraintLayout, getResources().getString(R.string.no_internet_connection));
            }

    }

    /**
     * Change password clicked.
     */
    @OnClick(R.id.label_change_password)
    void changePasswordClicked() {
        showChangePasswordDialog();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (lifeCycleCallBackManager != null) {
                lifeCycleCallBackManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        } else
            System.out.println("request permission");


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_FILE_REQUEST) {
                if (data == null) {
                    //no data present
                    return;
                }
                // selected file saved in preferences
                Uri selectedFileUri = data.getData();
                selectedFilePath = FilePath.getPath(getActivity(), selectedFileUri);
                // Log.i("Selected File Path:" ,selectedFilePath);

                if (selectedFilePath != null && !selectedFilePath.equals("")) {
                    if (PROOF == 100) {
                        PreferenceUtils.writeString(context, DRIVING_LICENSE, selectedFilePath);
                        edtDrivingLicense.setText(returnFileName(selectedFilePath));
                    } else if (PROOF == 200) {
                        PreferenceUtils.writeString(context, ADDRESS_PROOF, selectedFilePath);
                        edtAddressProof.setText(returnFileName(selectedFilePath));
                    }
                } else {
                    Toast.makeText(getActivity(), "Cannot upload file to server", Toast.LENGTH_SHORT).show();
                }
            }
        }

        if (lifeCycleCallBackManager != null) {
            lifeCycleCallBackManager.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void showSnackBar(int msg) {
        ViewUtils.showSnackBar(constraintLayout, context.getResources().getString(msg));
    }

    @Override
    public void profileResponse(ProfileResponse profileResponse, String msg) {
        appRepository.setUploadDocumentStatus("Updated");
        changeActivityClearBackStack(DashboardActivity.class);
        //showToast(msg);
    }

    @Override
    public void changePasswordResponse(ChangePasswordResponse response, String msg) {
        showToast(msg);
    }

    @Override
    public void profileDetails(ProfileDetailResponse response, String msg) {
        hideLoadingView();
        /**
         * bind profile details
         * */
        fareText.setText(response.getDeliveryFareType().equals("comm_fee")?getString(R.string.common_fare):getString(R.string.km_hr_charge));
        String fareKm=response.getDeliveryFareType().equals("per_km")?String.format(getResources().getString(R.string.bind_charge_fare_type), response.getDeliveryKmOrminCharge(), ""):response.getDeliveryKmOrminCharge();
        kmCharge.setText(fareKm);

        PreferenceUtils.writeString(context, USER_IMAGE, response.getDeliveryProfile());
        Picasso.get()
                .load(response.getDeliveryProfile())
                .resize(600, 200)
                .centerInside()
                .placeholder(R.drawable.ic_user_default)
                .error(R.drawable.ic_user_default)
                .transform(new PicassoCircleTransformation())
                .into(userImage);
        fName.setText(response.getDeliveryFname());
        fName.setSelection(fName.getText().length());
        lName.setText(response.getDeliveryLname());
        email.setText(response.getDeliveryEmail());
        mobile.setText(response.getDeliveryPhone());
        vehicleTypeSpinner.setSelection(response.getDeliveryVehicle().equals("Truck") ? 2 : 1);
        orderLimit.setText(String.valueOf(response.getOrderLimit()));
        wallet.setText(String.valueOf(response.getWallet()));
        responseTime.setText(presenter.responseTime(response.getDeliveryResponseTime()));
        baseFare.setText(response.getDeliveryBaseFare());
        if (response.getDeliveryStatus().equals("Available")) {
            statusSwitch.setChecked(true);
        } else {
            statusSwitch.setChecked(false);
        }
        edtDrivingLicense.setText(returnFileName(response.getDelivery_licence()));
        edtAddressProof.setText(returnFileName(response.getDelivery_address_proof()));
        if (!appRepository.getUploadDocumentStatus().equals("Updated")) {
            String title = getResources().getString(R.string.upload_document);
            String message = getResources().getString(R.string.proof_add);
            showPopup(title, message);

        }
        drivingLicenseName=response.getLicenseName();
        addressProofName=response.getAddressProofName();
    }

    @Override
    public void showOtpPopup() {
        otpDialog();
    }

    @Override
    public void profileWithOtpSuccess() {
        appRepository.setUploadDocumentStatus("Updated");
        changeActivityClearBackStack(DashboardActivity.class);
    }


    /**
     * Bind bottom sheet.
     */
    void bindBottomSheet() {
        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
        bottomDialog = new BottomDialog(context);
        bottomDialog.setContentView(bottomSheetView);
        final TextView tvCamera = bottomSheetView.findViewById(R.id.tvCamera);
        final TextView tvGallery = bottomSheetView.findViewById(R.id.tvGallery);
        tvCamera.setOnClickListener(onCameraListener);
        tvGallery.setOnClickListener(onGalleryListener);
        bottomDialog.show();
    }

    @Override
    public void showError(String message) {
        ViewUtils.showSnackBar(constraintLayout, message);
    }

    @Override
    public void showError(int message) {
        ViewUtils.showSnackBar(constraintLayout, getString(message));
    }

    @Override
    public void showLoggedInByAnother(String message) {
       showLoggedInByOtherError(message);
    }

    private View.OnClickListener onCameraListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            bottomDialog.dismiss();
            filePickUtils.requestImageCamera(CAMERA_PERMISSION, false, true);
        }
    };

    private View.OnClickListener onGalleryListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            bottomDialog.dismiss();
            filePickUtils.requestImageGallery(STORAGE_PERMISSION_IMAGE, false, true);
        }
    };
    private FilePickUtils.OnFileChoose onFileChoose = new FilePickUtils.OnFileChoose() {
        @Override
        public void onFileChoose(String fileUri, int requestCode, int size) {
            System.out.println("fileUri" + fileUri);
            PreferenceUtils.writeString(context, USER_IMAGE, fileUri);
            Picasso.get()
                    .load(Uri.fromFile(new File(fileUri)))
                    .placeholder(R.drawable.ic_user_default)
                    .error(R.drawable.ic_user_default)
                    .transform(new PicassoCircleTransformation())
                    .into(userImage);
        }
    };

    /**
     * Show change password dialog.
     */
    public void showChangePasswordDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_change_password);

        EditText oldPassword = dialog.findViewById(R.id.et_old_password);
        EditText newPassword = dialog.findViewById(R.id.et_new_password);
        EditText newConfirmPassword = dialog.findViewById(R.id.et_new_confirm_password);

        Button dialogButton = dialog.findViewById(R.id.btn_go);
        dialogButton.setOnClickListener(v -> {

            if (oldPassword.getText().toString().trim().equals("")) {
                ViewUtils.showSnackBar(constraintLayout, context.getString(R.string.warning_empty_old_password));
            } else if (newPassword.getText().toString().trim().equals("")) {
                ViewUtils.showSnackBar(constraintLayout, context.getString(R.string.warning_empty_new_password));
            } else if (newConfirmPassword.getText().toString().trim().equals("")) {
                ViewUtils.showSnackBar(constraintLayout, context.getString(R.string.warning_empty_confirm_new_password));
            } else if (!newPassword.getText().toString().trim().equals(newConfirmPassword.getText().toString().trim())) {
                ViewUtils.showSnackBar(constraintLayout, context.getString(R.string.password_confirm_does_not_match));
            } else {
                dialog.dismiss();
                showLoadingView();
                presenter.changePasswordValidation(constraintLayout, ViewUtils.getEditTextValue(oldPassword), ViewUtils.getEditTextValue(newPassword));
            }
        });

        dialog.show();
    }

    /**
     * Otp dialog.
     */
    public void otpDialog() {
        final Dialog otpDialog = new Dialog(getActivity());
        otpDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        otpDialog.setCancelable(false);
        otpDialog.setContentView(R.layout.otp_dialog);
        EditText edtOtp = otpDialog.findViewById(R.id.edt_otp);
        Button dialogButton = otpDialog.findViewById(R.id.btn_go);
        dialogButton.setOnClickListener(v -> {
            if (edtOtp.getText().length() == 0) {
                ViewUtils.showSnackBar(constraintLayout, context.getString(R.string.enter_otp));
            } else {
                otpDialog.dismiss();
                showLoadingView();
                presenter.checkAndUpdateOtp(constraintLayout, ViewUtils.getEditTextValue(edtOtp), ViewUtils.getEditTextValue(fName), ViewUtils.getEditTextValue(lName), statusSwitch.isChecked(),
                        selectedVehicleType, ViewUtils.getEditTextValue(orderLimit), "+917373857689"
                        , ViewUtils.getEditTextValue(email).trim());
            }
        });

        otpDialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.close();
    }

    /**
     * Upload licence.
     *
     * @param view the view
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.edt_driving_license)
    public void uploadLicence(View view) {
        PROOF = 100;
        showFileChooser();
    }

    /**
     * Upload address proof.
     *
     * @param view the view
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.edt_address_proof)
    public void uploadAddressProof(View view) {
        PROOF = 200;
        showFileChooser();
    }


    /**
     * Request permission for select file.
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    void requestPermissionForSelectFile() {
        if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to read the contacts
            }

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PICK_FILE_REQUEST);

            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
            // app-defined int constant that should be quite unique

            return;
        }
    }

    private void showFileChooser() {
        requestPermissionForSelectFile();
        Intent intent = new Intent();
        //sets the select file to all types of files
        intent.setType("*/*");
        //allows to select data and return it
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //starts new activity to select file and return data
        startActivityForResult(Intent.createChooser(intent, "Choose File to Upload.."), PICK_FILE_REQUEST);
    }

    /**
     * Return file name string.
     *
     * @param filePath the file path
     * @return the string
     */
    String returnFileName(String filePath) {
        String fileArr[] = filePath.split("/");
        return fileArr[fileArr.length - 1];
    }

    private void showPopup(String title, String msg) {
        new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("ok", (dialog, which) -> {
                    dialog.dismiss();
                }).show();
    }

}