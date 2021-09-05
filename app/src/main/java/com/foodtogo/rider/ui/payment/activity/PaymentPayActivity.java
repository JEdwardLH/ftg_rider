package com.foodtogo.rider.ui.payment.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.foodtogo.rider.R;
import com.foodtogo.rider.base.BaseActivity;
import com.foodtogo.rider.ui.login.activity.Login;
import com.foodtogo.rider.ui.payment.mvp.PaymentContractor;
import com.foodtogo.rider.ui.payment.mvp.PaymentPresenter;
import com.foodtogo.rider.util.ViewUtils;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.OnClick;

import static com.foodtogo.rider.util.CommonStrings.AMOUNT_TO_PAY;
import static com.foodtogo.rider.util.CommonStrings.CURRENCY;
import static com.foodtogo.rider.util.CommonStrings.NET_BANK_ACC;
import static com.foodtogo.rider.util.CommonStrings.NET_BANK_BRANCH;
import static com.foodtogo.rider.util.CommonStrings.NET_BANK_IFSC;
import static com.foodtogo.rider.util.CommonStrings.NET_BANK_NAME;
import static com.foodtogo.rider.util.CommonStrings.NET_BANK_STATUS;
import static com.foodtogo.rider.util.CommonStrings.PAY_PAL_ID;
import static com.foodtogo.rider.util.CommonStrings.PAY_PAL_SECERET;
import static com.foodtogo.rider.util.CommonStrings.PAY_PAL_STATUS;

/**
 * The type Payment pay activity.
 */
public class PaymentPayActivity extends BaseActivity implements  PaymentContractor.View {


    /**
     * The Payment layout.
     */
    @BindView(R.id.payment_layout)
    ConstraintLayout paymentLayout;

    /**
     * The Net banking rb.
     */
//radio
    @BindView(R.id.radio_net_banking)
    RadioButton netBankingRb;

    /**
     * The Net bank card edt.
     */
//net banking
    @BindView(R.id.card_edt)
    EditText netBankCardEdt;
    /**
     * The Net bank edt.
     */
    @BindView(R.id.bank_edt)
    EditText netBankEdt;
    /**
     * The Net branch edt.
     */
    @BindView(R.id.branch_edt)
    EditText netBranchEdt;


    /**
     * The Pay button.
     */
    @BindView(R.id.pay_btn)
    Button payButton;

    /**
     * The Net bank lay.
     */
//layout
    @BindView(R.id.net_banking_data_layout)
    LinearLayout netBankLay;
    /**
     * The Pay pal status.
     */
    String payPalStatus = "";
    /**
     * The Pay pal id.
     */
    String payPalId = "";
    /**
     * The Pay pal key.
     */
    String payPalKey = "";
    /**
     * The Net bank status.
     */
    String netBankStatus = "";
    /**
     * The Acc no.
     */
    String accNo = "";
    /**
     * The Bank name.
     */
    String bankName = "";
    /**
     * The Branch.
     */
    String branch = "";
    /**
     * The Ifsc.
     */
    String ifsc = "";
    /**
     * The Amount.
     */
    String amount = "";
    /**
     * The Currency.
     */
    String currency = "";
    /**
     * The Presenter.
     */
    PaymentPresenter presenter;

    /**
     * Goto payment activity intent.
     *
     * @param context  the context
     * @param pStatus  the p status
     * @param pId      the p id
     * @param pKey     the p key
     * @param nStatus  the n status
     * @param accNo    the acc no
     * @param name     the name
     * @param branch   the branch
     * @param ifsc     the ifsc
     * @param amount   the amount
     * @param currency the currency
     * @return the intent
     */
    public static Intent gotoPaymentActivity(Context context, String pStatus, String pId, String pKey, String nStatus, String accNo,
                                             String name, String branch, String ifsc, String amount, String currency) {
        Intent i = new Intent(context, PaymentPayActivity.class);
        i.putExtra(PAY_PAL_STATUS, pStatus);
        i.putExtra(PAY_PAL_ID, pId);
        i.putExtra(PAY_PAL_SECERET, pKey);
        i.putExtra(NET_BANK_STATUS, nStatus);
        i.putExtra(NET_BANK_ACC, accNo);
        i.putExtra(NET_BANK_NAME, name);
        i.putExtra(NET_BANK_BRANCH, branch);
        i.putExtra(NET_BANK_IFSC, ifsc);
        i.putExtra(AMOUNT_TO_PAY, amount);
        i.putExtra(CURRENCY, currency);
        return i;
    }

    private static final String TAG = "PayPalPayment";
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_NO_NETWORK;

    // note that these credentials will differ between live & sandbox environments
    private static final int REQUEST_CODE_PAYMENT = 1;
    /**
     * The Config.
     */
    PayPalConfiguration config;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupToolBar();
        setTitle(getResources().getString(R.string.payment));
        presenter = new PaymentPresenter(this, getApplicationContext());
        paymentLayout.setVisibility(View.VISIBLE);

        //Intent parsing
        if (getIntent() != null) {
            payPalStatus = getStringFromIntent(PAY_PAL_STATUS);
            payPalId = getStringFromIntent(PAY_PAL_ID);
            payPalKey = getStringFromIntent(PAY_PAL_SECERET);
            netBankStatus = getStringFromIntent(NET_BANK_STATUS);
            accNo = getStringFromIntent(NET_BANK_ACC);
            bankName = getStringFromIntent(NET_BANK_NAME);
            branch = getStringFromIntent(NET_BANK_BRANCH);
            ifsc = getStringFromIntent(NET_BANK_IFSC);
            amount = getStringFromIntent(AMOUNT_TO_PAY);
            currency = getStringFromIntent(CURRENCY);
            System.out.println("payPalId" + payPalId);
        }
        netBankCardEdt.setText(accNo);
        netBankEdt.setText(bankName);
        netBranchEdt.setText(branch);
        payButton.setText(String.format("Pay %s%s", currency, amount));
        String CONFIG_CLIENT_ID = payPalId;

        config = new PayPalConfiguration()
                .environment(CONFIG_ENVIRONMENT)
                .clientId(CONFIG_CLIENT_ID);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);
        toolbar.setTitleCentered(true);
    }

    @Override
    public int getLayout() {
        return R.layout.container_black_theme;
    }

    /**
     * Pay button click.
     */
    @OnClick(R.id.pay_btn)
    void payButtonClick() {
        showPopup();
    }

    private void showPopup() {
        final Dialog dialog = new Dialog(PaymentPayActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_payment);

        EditText transactionId = dialog.findViewById(R.id.transaction_id);
        ImageView closeImg = dialog.findViewById(R.id.close);
        Button dialogButton = dialog.findViewById(R.id.pay_btn);
        dialogButton.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(transactionId.getText().toString())) {
                dialog.dismiss();
                if (isNetworkConnected()) {
                    showProgress();
                    presenter.payAmount(transactionId.getText().toString().trim(), amount, currency, "0");
                } else
                    ViewUtils.showSnackBar(paymentLayout, getString(R.string.no_internet_connection));
            } else {
                dialog.dismiss();
                ViewUtils.showSnackBar(paymentLayout, context.getString(R.string.enter_transaction_id));
            }
        });
        closeImg.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    /**
     * Gets string from intent.
     *
     * @param key the key
     * @return the string from intent
     */
    String getStringFromIntent(String key) {
        String value = "";
        if (getIntent().getStringExtra(key) != null) {
            value = getIntent().getStringExtra(key);
        }
        return value;
    }



    private PayPalPayment getThingToBuy(String paymentIntent, String mAmount) {
        return new PayPalPayment(new BigDecimal(mAmount), "USD", "Amount to pay", paymentIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm =
                        data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        JSONObject payment = confirm.toJSONObject();
                        JSONObject response = payment.getJSONObject("response");
                        String transactionId = response.getString("id");
                        if (isNetworkConnected()) {
                            showProgress();
                            presenter.payAmount(transactionId, amount, currency, "1");
                        } else
                            ViewUtils.showSnackBar(paymentLayout, getString(R.string.no_internet_connection));


                        if (response.getString("state").equals("approved")) {
                            finish();
                        } else {
                            ViewUtils.showSnackBar(paymentLayout, response.getString("state"));
                        }
                    } catch (JSONException e) {
                        Log.d(TAG, e.getMessage());
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i(TAG, "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                ViewUtils.showSnackBar(paymentLayout, "An invalid Payment");
                Log.i(TAG, "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop service when done
        stopService(new Intent(this, PayPalService.class));
    }

    @Override
    public void showPaySuccess(String msg) {
        ViewUtils.showSnackBar(paymentLayout, msg);
        finish();
    }

    @Override
    public void parsePayPalIntent(String amount) {
        /*
         * PAYMENT_INTENT_SALE will cause the payment to complete immediately.
         * Change PAYMENT_INTENT_SALE to
         *   - PAYMENT_INTENT_AUTHORIZE to only authorize payment and capture funds later.
         *   - PAYMENT_INTENT_ORDER to create a payment for authorization and capture
         *     later via calls from your server.
         *
         * Also, to include additional payment details and an item list, see getStuffToBuy() below.
         */
        PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE, amount);

        /*
         * See getStuffToBuy(..) for examples of some available payment options.
         */

        Intent intent = new Intent(PaymentPayActivity.this, PaymentActivity.class);

        // send the same configuration for restart resiliency
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
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
        ViewUtils.showSnackBar(paymentLayout, message);
    }

    @Override
    public void showError(int message) {
        changeActivityClearBackStack(Login.class);
    }

    @Override
    public void showLoggedInByAnother(String message) {
        showLoggedInByAnotherInfo(message);
    }
}
