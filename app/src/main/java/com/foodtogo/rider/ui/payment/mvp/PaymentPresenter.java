package com.foodtogo.rider.ui.payment.mvp;

import android.content.Context;

import com.foodtogo.rider.model.payment.PaymentConversionRequest;
import com.foodtogo.rider.model.payment.PaymentRequest;
import com.foodtogo.rider.util.PreferenceUtils;

import static com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys.LANGUAGE;


/**
 * The type Payment presenter.
 */
public class PaymentPresenter implements PaymentContractor.Presenter {
    private PaymentContractor.View view;
    private PaymentModal modal;
    private Context context;

    /**
     * Instantiates a new Payment presenter.
     *
     * @param view    the view
     * @param context the context
     */
    public PaymentPresenter(PaymentContractor.View view, Context context) {
        this.view = view;
        this.modal = new PaymentModal(this, context);
        this.context = context;
    }

    @Override
    public void payAmount(String transactionId, String amount, String currency, String payType) {
        PaymentRequest request = new PaymentRequest();
        request.setCurrencySymbol(currency);
        request.setLang(PreferenceUtils.readString(context, LANGUAGE, "en"));
        request.setPaid(amount);
        request.setPayType(payType);
        request.setStatus("2");
        request.setTransactionId(transactionId);
        modal.requestToPay(request);
    }

    @Override
    public void paidAmountSuccess(String msg) {
        view.hideLoadingView();
        view.showPaySuccess(msg);
    }

    @Override
    public void payError(String msg) {
        view.hideLoadingView();
        view.showError(msg);
    }

    @Override
    public void payError(int msg) {
        view.hideLoadingView();
        view.showError(msg);
    }

    @Override
    public void amountConversion(String amount, String currency) {
        PaymentConversionRequest request = new PaymentConversionRequest();
        request.setFinalAmount(amount);
        request.setFromCurrency(currency);
        request.setToCurrency("USD");
        modal.requestToCurrencyConversion(request);
    }

    @Override
    public void amountConversionSuccess(String amount) {
        view.hideLoadingView();
        view.parsePayPalIntent(amount);

    }

    @Override
    public void amountConversionError(int msg) {
        view.hideLoadingView();
        view.showError(msg);
    }

    @Override
    public void loggedInByAnotherError(String msg) {
        view.hideLoadingView();
        view.showLoggedInByAnother(msg);
    }

    @Override
    public void close() {
        modal.close();
    }
}
