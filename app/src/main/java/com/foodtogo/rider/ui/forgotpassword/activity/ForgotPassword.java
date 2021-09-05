package com.foodtogo.rider.ui.forgotpassword.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.widget.EditText;

import com.foodtogo.rider.R;
import com.foodtogo.rider.base.BaseActivity;
import com.foodtogo.rider.model.forgotpassword.ForgotPasswordResponse;
import com.foodtogo.rider.ui.forgotpassword.mvp.ForgotPasswordContractor;
import com.foodtogo.rider.ui.forgotpassword.mvp.ForgotPasswordPresenter;
import com.foodtogo.rider.ui.login.activity.Login;
import com.foodtogo.rider.util.PreferenceUtils;

import butterknife.BindView;
import butterknife.OnClick;

import static com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys.LANGUAGE;

/**
 * The type Forgot password.
 */
public class ForgotPassword extends BaseActivity implements ForgotPasswordContractor.View {
    /**
     * The Et email address.
     */
    @BindView(R.id.edt_name)
    EditText etEmailAddress;
    /**
     * The Presenter.
     */
    ForgotPasswordPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ForgotPasswordPresenter(this, getApplicationContext());
    }

    /**
     * Forgot go button.
     */
    @OnClick(R.id.btn_go)
    public void forgotGoButton() {
        if (isNetworkConnected())
            presenter.goButtonClicked(etEmailAddress.getText().toString().trim(), PreferenceUtils.readString(getApplicationContext(), LANGUAGE, "en"));
        else
            showToast(R.string.no_internet_connection);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_forgot_password;
    }

    @Override
    public void showEmailEmptyError() {
        showToast(R.string.warning_empty_email);
    }

    @Override
    public void showNotValidEmailError() {
        showToast(R.string.warning_invalid_email);
    }

    @Override
    public void showForgotPasswordResponse(ForgotPasswordResponse forgotResponse) {
        hideKeyboard();
        showToast(context.getString(R.string.please_refer_mail));
        changeActivityClearBackStack(Login.class);
    }

    @Override
    public void showLoadingView() {
        showProgress();
    }

    @Override
    public void hideLoadingView() {
        hideProgress();
    }

    @Override
    public void showError(String message) {
        showToast(message);
    }

    @Override
    public void showError(int message) {
        showToast(message);
    }

    @Override
    public void showLoggedInByAnother(String message) {
        //no use here
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.close();
    }
}
