package com.foodtogo.rider.ui.payment_setting.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.foodtogo.rider.R;
import com.foodtogo.rider.base.BaseFragment;
import com.foodtogo.rider.model.payment_settings.PaymentSettingResponse;
import com.foodtogo.rider.model.payment_settings.UpdatePaymentRequest;
import com.foodtogo.rider.ui.login.activity.Login;
import com.foodtogo.rider.ui.payment_setting.mvp.PaymentSettingContractor;
import com.foodtogo.rider.ui.payment_setting.mvp.PaymentSettingPresenter;
import com.foodtogo.rider.util.CommonStrings;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The type Payment setting.
 */
public class PaymentSetting extends BaseFragment implements PaymentSettingContractor.View {

    /**
     * The Presenter.
     */
    PaymentSettingPresenter presenter;

    /**
     * The Acc edt.
     */
    @BindView(R.id.acc_edt)
    EditText accEdt;
    /**
     * The Branch edt.
     */
    @BindView(R.id.branch_name_edt)
    EditText branchEdt;
    /**
     * The Bank name.
     */
    @BindView(R.id.bank_name_edt)
    EditText bankName;

    /**
     * The Update btn.
     */
    @BindView(R.id.update_btn)
    Button updateBtn;


    /**
     * The Net bank cv.
     */
    @BindView(R.id.net_bank_card)
    LinearLayout netBankCv;
    /**
     * The Pay pal id.
     */
    //api data
    String payPalId = "";
    /**
     * The Pay pal key.
     */
    String payPalKey = "";
    /**
     * The Net card.
     */
    String netCard = "";
    /**
     * The Net bank.
     */
    String netBank = "";
    /**
     * The Net branch.
     */
    String netBranch = "";
    /**
     * The Payment request.
     */
    UpdatePaymentRequest paymentRequest;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new PaymentSettingPresenter(this, getContext());
        if (isNetworkConnected()) {
            showLoadingView();
            presenter.getPaymentSettingsDetails();
        } else {
            showConnectionError(R.string.no_internet_connection);
        }
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater,
                                        ViewGroup parent,
                                        Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_payment_setting, parent, false);
    }

    @Override
    public void bindSettingResponse(PaymentSettingResponse response) {
        //bind views
        accEdt.setText(response.getDeliverBankAccno());
        bankName.setText(response.getDeliverBankName());
        branchEdt.setText(response.getDeliverBranch());
        presenter.setCursorAtLast(accEdt);
        presenter.setCursorAtLast(bankName);
        presenter.setCursorAtLast(branchEdt);


    }

    @Override
    public void showError(int message) {
        changeActivityClearBackStack(Login.class);
    }

    @Override
    public void showLoggedInByAnother(String message) {
        showLoggedInByOtherError(message);
    }

    /**
     * Update clicked.
     */
    @OnClick(R.id.update_btn)
    void updateClicked() {
        paymentRequest = new UpdatePaymentRequest();
        paymentRequest.setDeliverPaypalStatus(CommonStrings.UN_PUBLISH);

        netCard = accEdt.getText().toString().trim();
        netBank = bankName.getText().toString().trim();
        netBranch = branchEdt.getText().toString().trim();
        if (netBank.isEmpty()) {
            showToast(getString(R.string.enter_bank_name));
        } else if (netCard.isEmpty()) {
            showToast(getString(R.string.enter_account_number));
        } else if (netBranch.isEmpty()) {
            showToast(getString(R.string.enter_account_name));
        } else {
            paymentRequest.setDeliverNetbankStatus(CommonStrings.PUBLISH);
            callApi();
        }


    }

    /**
     * Call api.
     */
    void callApi() {
        if (isNetworkConnected()) {
            showLoadingView();
            presenter.updatePaymentSetting(paymentRequest, payPalId, payPalKey, netCard, netBank, netBranch, "");
        } else {
            showToast(R.string.no_internet_connection);
        }

    }


}
