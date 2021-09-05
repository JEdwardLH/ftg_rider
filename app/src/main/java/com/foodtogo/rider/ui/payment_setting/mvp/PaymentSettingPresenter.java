package com.foodtogo.rider.ui.payment_setting.mvp;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import com.foodtogo.rider.model.Request;
import com.foodtogo.rider.model.payment_settings.PaymentSettingResponse;
import com.foodtogo.rider.model.payment_settings.UpdatePaymentRequest;
import com.foodtogo.rider.util.PreferenceUtils;

import static com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys.LANGUAGE;

/**
 * The type Payment setting presenter.
 */
public class PaymentSettingPresenter implements PaymentSettingContractor.Presenter {
    private PaymentSettingContractor.View mView;
    private Context context;
    private PaymentSettingModal modal;

    /**
     * Instantiates a new Payment setting presenter.
     *
     * @param view    the view
     * @param context the context
     */
    public PaymentSettingPresenter(PaymentSettingContractor.View view, Context context) {
        this.mView = view;
        this.context = context;
        this.modal = new PaymentSettingModal(this, context);
    }

    @Override
    public void getPaymentSettingsDetails() {
        Request request = new Request();
        request.setLang(PreferenceUtils.readString(context, LANGUAGE, "en"));
        modal.requestPaymentSettings(request);
    }

    @Override
    public void paymentSettingSuccess(PaymentSettingResponse response) {
        mView.hideLoadingView();
        mView.bindSettingResponse(response);
    }

    @Override
    public void paymentSettingError(String msg) {
        mView.hideLoadingView();
        mView.showError(msg);
    }

    @Override
    public void paymentSettingError(int msg) {
        mView.hideLoadingView();
        mView.showError(msg);
    }

    @Override
    public void updatePaymentSetting(UpdatePaymentRequest request, String pId, String pKey, String accNo, String bank, String branch, String ifsc) {
        request.setDeliverBankAccno(accNo);
        request.setDeliverBankName(bank);
        request.setDeliverBranch(branch);
        request.setDeliverIfsc(ifsc);
        request.setDeliverPaypalClientid(pId);
        request.setDeliverPaypalSecretid(pKey);
        request.setLang(PreferenceUtils.readString(context, LANGUAGE, "en"));
        modal.requestToUpdateSettings(request);
    }

    @Override
    public boolean isPayPal(EditText clientId, EditText secretKey) {
        boolean isAllFilled = false;
        if (!TextUtils.isEmpty(clientId.getText().toString()) && !TextUtils.isEmpty(secretKey.getText().toString())) {
            isAllFilled = true;
        }
        return isAllFilled;
    }

    @Override
    public boolean isNetBank(EditText card, EditText bank, EditText branch) {
        boolean isAll = false;
        if (!TextUtils.isEmpty(card.getText().toString()) && !TextUtils.isEmpty(bank.getText().toString())
                && !TextUtils.isEmpty(branch.getText().toString())) {
            isAll = true;
        }
        return isAll;
    }

    @Override
    public void updateSettingSuccess(String msg) {
        mView.hideLoadingView();
        mView.showError(msg);
    }

    @Override
    public void setCursorAtLast(EditText edt) {
        edt.setSelection(edt.getText().length());
    }

    @Override
    public void loggedInByAnother(String msg) {
        mView.hideLoadingView();
        mView.showLoggedInByAnother(msg);
    }

    @Override
    public void close() {
        modal.close();
    }


}
