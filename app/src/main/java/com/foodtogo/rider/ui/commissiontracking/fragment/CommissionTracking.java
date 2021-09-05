package com.foodtogo.rider.ui.commissiontracking.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foodtogo.rider.R;
import com.foodtogo.rider.base.BaseFragment;
import com.foodtogo.rider.model.commissiontracking.CommissionList;
import com.foodtogo.rider.model.commissiontracking.CommissionTrackingResponse;
import com.foodtogo.rider.ui.commissiontracking.mvp.CmTrackingContractor;
import com.foodtogo.rider.ui.commissiontracking.mvp.CmTrackingPresenter;
import com.foodtogo.rider.ui.login.activity.Login;
import com.foodtogo.rider.ui.payment.activity.PaymentPayActivity;
import com.foodtogo.rider.util.CurrencyUtils;
import com.foodtogo.rider.util.ResourceUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * The type Commission tracking.
 */
public class CommissionTracking extends BaseFragment implements CmTrackingContractor.View {

    //commission amount

    /**
     * The Ll container.
     */
    @BindView(R.id.llContainer)
    LinearLayout llContainer;
    /**
     * The Ll main view.
     */
    @BindView(R.id.ll_mainView)
    LinearLayout llMainView;

    /**
     * The Pay button.
     */
    @BindView(R.id.pay_button)
    Button payButton;
    /**
     * The Presenter.
     */
    CmTrackingPresenter presenter;
    /**
     * The Tracking response.
     */
    CommissionTrackingResponse trackingResponse;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        payButton.setVisibility(View.GONE);
        presenter = new CmTrackingPresenter(this, getActivity());

    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_commission_tracking, parent, false);
    }

    /**
     * Pay button clicked.
     */
    @OnClick(R.id.pay_button)
    void payButtonClicked() {
        if (payButton.getText().toString().equals(getString(R.string.pay_request))) {
            if (isNetworkConnected()) {
                showLoadingView();
                presenter.requestToReceivePay(trackingResponse.getCommissionList().get(0).getBalAmtToReceive());
            } else {
                showConnectionError(R.string.no_internet_connection);
            }

        } else {
            Intent i = PaymentPayActivity.gotoPaymentActivity(getActivity(), trackingResponse.getPaypalStatus(),
                    trackingResponse.getPaypalClientId(), trackingResponse.getPaypalSecretId(), trackingResponse.getNetBankStatus(),
                    trackingResponse.getNetAccNo(), trackingResponse.getNetBankName(),
                    trackingResponse.getNetBranch(), trackingResponse.getNetIfsc(), trackingResponse.getCommissionList().get(0).getBalAmtToPay(),
                    trackingResponse.getCommissionList().get(0).getCurrencyCode());
            startActivity(i);
        }
    }

    @Override
    public void showCmTrackingData(CommissionTrackingResponse response) {
        trackingResponse = response;
        //bind views
        List<CommissionList> commissionList = response.getCommissionList();
        CommissionList commission = commissionList.get(0);
        llMainView.setVisibility(View.VISIBLE);
        try {
            llContainer.removeAllViews();
        } catch (Exception e) {

        }
        llContainer.addView(new HeadViewHolder(String.format("%s%s", commission.getCurrency(), commission.getBalAmtToReceive())).view);
        llContainer.addView(new ItemHolder(ResourceUtils.getString(R.string.total_orders), String.valueOf(commission.getTotalOrders())).view);
        llContainer.addView(new ItemHolder(ResourceUtils.getString(R.string.total_order_amount), CurrencyUtils.getDisplayCurrency(commission.getCurrency(), commission.getTotalOrderAmt())).view);
        llContainer.addView(new ItemHolder(ResourceUtils.getString(R.string.total_received_amount), CurrencyUtils.getDisplayCurrency(commission.getCurrency(), commission.getTotalRcvdAmt())).view);
        llContainer.addView(new ItemHolder(ResourceUtils.getString(R.string.paid_amount), CurrencyUtils.getDisplayCurrency(commission.getCurrency(), commission.getPaidAmt())).view);
        llContainer.addView(new ItemHolder(ResourceUtils.getString(R.string.balance_to_pay), CurrencyUtils.getDisplayCurrency(commission.getCurrency(), commission.getBalAmtToPay())).view);

        double amountToReceive = Double.valueOf(commission.getBalAmtToReceive().replace(",", ""));
        double balAmountToPay = Double.valueOf(commission.getBalAmtToPay().replace(",", ""));
        Log.i("amountToReceive", "" + amountToReceive);
        Log.i("balAmountToPay", "" + balAmountToPay);
        if (balAmountToPay > 0) {
            payButton.setVisibility(View.VISIBLE);
            payButton.setText(String.format(getString(R.string.pay), commission.getCurrency() + commission.getBalAmtToPay()));
        } else if (amountToReceive > 0) {
            payButton.setVisibility(View.VISIBLE);
            payButton.setText(getString(R.string.pay_request));
        } else {
            payButton.setVisibility(View.GONE);
        }
    }


    /**
     * The type Item holder.
     */
    class ItemHolder {
        /**
         * The Tv name.
         */
        @BindView(R.id.tvName)
        TextView tvName;
        /**
         * The Tv price.
         */
        @BindView(R.id.tvPrice)
        TextView tvPrice;
        /**
         * The View.
         */
        View view;

        /**
         * Instantiates a new Item holder.
         *
         * @param name  the name
         * @param price the price
         */
        public ItemHolder(String name, String price) {
            view = getLayoutInflater().inflate(R.layout.li_commission_tracking, llContainer, false);
            ButterKnife.bind(this, view);
            tvName.setText(name);
            tvPrice.setText(": " + price);
        }
    }


    /**
     * The type Head view holder.
     */
    class HeadViewHolder {

        /**
         * The Total commission amnt.
         */
        @BindView(R.id.tvTotalAmountReceive)
        TextView total_commission_amnt_receive;

        /**
         * The View.
         */
        View view;

        /**
         * Instantiates a new Head view holder.
         *
         * @param price the price
         */
        public HeadViewHolder(String price) {
            view = getLayoutInflater().inflate(R.layout.li__head_commission_tracking, llContainer, false);
            ButterKnife.bind(this, view);
            total_commission_amnt_receive.setText(price);
        }
    }


    @Override
    public void showNetworkLayout() {
        networkLayout();
    }


    @Override
    public void showError(int message) {
        changeActivityClearBackStack(Login.class);
    }

    @Override
    public void showLoggedInByAnother(String message) {
       showLoggedInByOtherError(message);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.close();
    }


    /**
     * Network layout.
     */
    void networkLayout() {
        showConnectionError(getString(R.string.no_internet_connection));
    }


    @Override
    public void onResume() {
        super.onResume();
        if (isNetworkConnected()) {
            showLoadingView();
            presenter.getCmTrackingDetails();
        } else {
            networkLayout();
        }
    }
}
