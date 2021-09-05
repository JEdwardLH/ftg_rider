package com.foodtogo.rider.ui.invoice.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.foodtogo.rider.R;
import com.foodtogo.rider.base.BaseActivity;
import com.foodtogo.rider.model.invoice.Choice;
import com.foodtogo.rider.model.invoice.CustomerDetailArray;
import com.foodtogo.rider.model.invoice.InvoiceResponse;
import com.foodtogo.rider.model.invoice.OrderDetailArray;
import com.foodtogo.rider.ui.invoice.adapter.InvoiceOrderAdapter;
import com.foodtogo.rider.ui.invoice.interfaces.ItemInfoListener;
import com.foodtogo.rider.ui.invoice.mvp.InvoiceContractor;
import com.foodtogo.rider.ui.invoice.mvp.InvoicePresenter;
import com.foodtogo.rider.ui.login.activity.Login;
import com.foodtogo.rider.util.ConversionUtils;
import com.foodtogo.rider.util.ViewUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.foodtogo.rider.base.AppConstants.SPACE;
import static com.foodtogo.rider.util.CommonStrings.ORDER_ID;
import static com.foodtogo.rider.util.CommonStrings.STORE_ID;
import static com.foodtogo.rider.util.ConversionUtils.removeComma;

/**
 * The type Invoice activity.
 */
public class InvoiceActivity extends BaseActivity implements InvoiceContractor.View, ItemInfoListener {
    /**
     * The Error text.
     */
    @BindView(R.id.error_text)
    TextView errorText;
    /**
     * The Retry image.
     */
    @BindView(R.id.retry_image)
    ImageView retryImage;
    /**
     * The Retry text.
     */
    @BindView(R.id.retry_text)
    TextView retryText;
    /**
     * The Error layout.
     */
    @BindView(R.id.error_layout)
    ConstraintLayout errorLayout;
    /**
     * The User name.
     */
    @BindView(R.id.user_name)
    TextView userName;
    /**
     * The M address.
     */
    @BindView(R.id.address)
    TextView mAddress;

    @BindView(R.id.landmark_txt)
    TextView landMarkTv;
    /**
     * The M phone.
     */
    @BindView(R.id.phone_number)
    TextView mPhone;
    /**
     * The M email.
     */
    @BindView(R.id.email_address)
    TextView mEmail;
    /**
     * The M food rv.
     */
    @BindView(R.id.food_rv)
    RecyclerView mFoodRv;
    /**
     * The M order amount.
     */
    @BindView(R.id.order_amount_txt)
    TextView mOrderAmount;
    /**
     * The M wallet.
     */
    @BindView(R.id.wallet_txt)
    TextView mWallet;

    @BindView(R.id.tax_txt)
    TextView taxText;

    @BindView(R.id.tax_label)
    TextView taxLabel;

    @BindView(R.id.wallet_label)
    TextView mWalletTv;

    @BindView(R.id.offer_txt)
    TextView mOffer;

    @BindView(R.id.offer_label)
    TextView mOfferTv;
    /**
     * The M delivery charge.
     */
    @BindView(R.id.delivery_charge_txt)
    TextView mDeliveryCharge;
    /**
     * The M total amount.
     */
    @BindView(R.id.grant_total_txt)
    TextView mTotalAmount;
    /**
     * The M store label.
     */
    @BindView(R.id.store_label)
    TextView mStoreLabel;
    /**
     * The M order id.
     */
    @BindView(R.id.order_id)
    TextView mOrderId;

    @BindView(R.id.cancel_item_label)
    TextView cancelItemLabel;

    @BindView(R.id.cancel_item_txt)
    TextView cancelItemText;
    /**
     * The M layout.
     */
    @BindView(R.id.container)
    ConstraintLayout mLayout;
    /**
     * The Profile layout.
     */
    @BindView(R.id.profile_container)
    ConstraintLayout profileLayout;

    /**
     * The Profile layout.
     */
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    /**
     * The M container.
     */
    @BindView(R.id.card_container)
    CardView mContainer;
    /**
     * The Order id.
     */
    String orderId = "";
    /**
     * The Store id.
     */
    String storeId = "";
    /**
     * The Presenter.
     */
    InvoicePresenter presenter;
    /**
     * The Adapter.
     */
    InvoiceOrderAdapter adapter;

    String ZERO_VALUE = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupToolBar();
        setTitle(getResources().getString(R.string.invoice_report));
        mLayout.setVisibility(View.GONE);
        presenter = new InvoicePresenter(this, getApplicationContext());
        if (getIntent() != null) {
            if (getIntent().getStringExtra(STORE_ID) != null && getIntent().getStringExtra(ORDER_ID) != null) {
                orderId = getIntent().getStringExtra(ORDER_ID);
                storeId = getIntent().getStringExtra(STORE_ID);
            }
        }

        if (isNetworkConnected()) {
            showProgress();
            presenter.getInvoiceDetail(storeId, orderId);
        } else {
            networkErrorLayout();
        }
        toolbar.setTitleCentered(true);
    }

    /**
     * Goto invoice activity intent.
     *
     * @param context the context
     * @param storeId the store id
     * @param orderId the order id
     * @return the intent
     */
    public static Intent gotoInvoiceActivity(Context context, String storeId, String orderId) {
        Intent i = new Intent(context, InvoiceActivity.class);
        i.putExtra(STORE_ID, storeId);
        i.putExtra(ORDER_ID, orderId);
        return i;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_in_voice;
    }


    @Override
    public void bindViewOnSuccess(InvoiceResponse response) {
        errorLayout.setVisibility(View.GONE);
        mLayout.setVisibility(View.VISIBLE);

        /*customer details*/
        CustomerDetailArray customerArray = response.getCustomerDetailArray();
        try {
            if (customerArray != null) {
                mOrderId.setText(String.format("%s: %s", getString(R.string.transaction_id), response.getOrderId()));
                userName.setText(customerArray.getCustomeName());
                mAddress.setText(customerArray.getCustomerAddress1().trim());
                landMarkTv.setText(customerArray.getCustomerAddress2().trim());
                mPhone.setText(customerArray.getCustomerMobile());
                mEmail.setText(customerArray.getCustomerEmail());
            } else {
                mContainer.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            mContainer.setVisibility(View.GONE);
        }

        OrderDetailArray toGetOrder = response.getOrderDetailArray().get(0);
        mOrderAmount.setText(String.format("%s %s", toGetOrder.getOrdCurrency(), String.valueOf(removeComma(response.getGrandSubTotal()))));

        /* offer and wallet views*/
        mWallet.setVisibility(response.getWalletUsed().equals(ZERO_VALUE) ? View.GONE : View.VISIBLE);
        mWalletTv.setVisibility(response.getWalletUsed().equals(ZERO_VALUE) ? View.GONE : View.VISIBLE);
        mWallet.setText(String.format("- %s %s", toGetOrder.getOrdCurrency(), String.valueOf(removeComma(response.getWalletUsed()))));

        /*Tax*/
        //taxLabel.setVisibility(response.getWalletUsed().equals(ZERO_VALUE) ? View.GONE : View.VISIBLE);
        //taxText.setText(response.getWalletUsed().equals(ZERO_VALUE) ? View.GONE : View.VISIBLE);
        //taxText.setText(String.format("%s %s", toGetOrder.getOrdCurrency(), String.valueOf(response.getGrandSubTotal())));


       cancelItemLabel.setVisibility(response.getCancelItemTotal().equals(ZERO_VALUE)?View.GONE:View.VISIBLE);
        cancelItemText.setVisibility(response.getCancelItemTotal().equals(ZERO_VALUE)?View.GONE:View.VISIBLE);
        cancelItemText.setText(String.format("- %s %s", toGetOrder.getOrdCurrency(), String.valueOf(removeComma(response.getCancelItemTotal()))));


        //mOfferTv.setVisibility(response.getOfferUsed().equals(ZERO_VALUE)? View.GONE:View.VISIBLE);
        // mOffer.setVisibility(response.getOfferUsed().equals(ZERO_VALUE)? View.GONE:View.VISIBLE);
        // mOffer.setText(String.format("- %s %s", toGetOrder.getOrdCurrency(), response.getOfferUsed()));


        mDeliveryCharge.setText(String.format("%s %s", toGetOrder.getOrdCurrency(), String.valueOf(removeComma(response.getDeliveryFee()))));
        mTotalAmount.setText(String.format("%s %s", toGetOrder.getOrdCurrency(), String.valueOf(removeComma(response.getTotalReceivableAmount()))));
        mStoreLabel.setText(toGetOrder.getStoreName());

        /* order detail setup*/
        List<OrderDetailArray> orderDetailArray = response.getOrderDetailArray();
        adapter = new InvoiceOrderAdapter(orderDetailArray, getApplicationContext());
        adapter.setItemInfoListener(this);
        mFoodRv.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mFoodRv.setLayoutManager(mLayoutManager);
        mFoodRv.setAdapter(adapter);

    }

    @Override
    public void showNetworkLayout() {
        networkErrorLayout();
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
        System.out.println("v" + message);
        ViewUtils.showSnackBar(errorLayout, message);
        noDataError(message);
    }

    @Override
    public void showError(int message) {
        changeActivityClearBackStack(Login.class);
    }

    /**
     * Network error layout.
     */
    void networkErrorLayout() {
        errorLayout.setVisibility(View.VISIBLE);
        errorText.setText(getString(R.string.no_internet_connection));
        retryText.setVisibility(View.GONE);
        retryImage.setVisibility(View.GONE);
    }

    /**
     * No data error.
     *
     * @param message the message
     */
    void noDataError(String message) {
        mLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
        errorText.setText(message);
        retryText.setVisibility(View.GONE);
        retryImage.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.close();
    }

    /**
     * The Arrow.
     */
    @BindView(R.id.arrow)
    ImageView arrow;


    /**
     * On click customer details.
     */
    @OnClick(R.id.grey_bg)
    public void onClickCustomerDetails() {
        arrow.setImageResource(ViewUtils.expand(profileLayout) ? R.drawable.ic_collapse : R.drawable.ic_expand);
    }

    @Override
    public void onClickItem(OrderDetailArray orderDetailArray) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_item_info);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final TextView tvTitle = dialog.findViewById(R.id.tv_title);
        final TextView tvPriceInfo = dialog.findViewById(R.id.tv_price_info);
        final TextView tvItemTax = dialog.findViewById(R.id.tv_item_tax);
        final TextView tvTaxInfo = dialog.findViewById(R.id.tv_tax_info);
        final TextView tvPreOrder = dialog.findViewById(R.id.tv_pre_order);
        final TextView tvPreorderInfo = dialog.findViewById(R.id.tv_preorder_info);
        final TextView tvQuantityInfo = dialog.findViewById(R.id.tv_quantity_info);
        final TextView tvChoice = dialog.findViewById(R.id.tv_choice);
        final TextView tvChoiceInfo = dialog.findViewById(R.id.tv_choice_info);
        final TextView tvSpecial = dialog.findViewById(R.id.tv_special);
        final TextView tvSpecialInfo = dialog.findViewById(R.id.tv_special_info);
        tvTitle.setText(orderDetailArray.getItemName());
        String priceInfo = orderDetailArray.getOrdCurrency() + orderDetailArray.getOrdUnitPrice();
        tvPriceInfo.setText(priceInfo);
        String quantityInfo = SPACE + orderDetailArray.getOrdQuantity();
        tvQuantityInfo.setText(quantityInfo);
        tvTaxInfo.setText(orderDetailArray.getOrdCurrency() + orderDetailArray.getOrdTaxAmt());
        tvPreorderInfo.setText(ConversionUtils.getFormatDateTime1(orderDetailArray.getPreOrderDate()).toUpperCase());
        if (orderDetailArray.getChoice() != null) {
            tvChoice.setVisibility(orderDetailArray.getChoice().size() == 0 ? View.GONE : View.VISIBLE);
            tvChoiceInfo.setVisibility(orderDetailArray.getChoice().size() == 0 ? View.GONE : View.VISIBLE);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < orderDetailArray.getChoice().size(); i++) {
                Choice choice = orderDetailArray.getChoice().get(i);
                stringBuilder.append(i == 0 ? "" : "\n");
                stringBuilder.append(choice.getChoicename() + " = " + orderDetailArray.getOrdCurrency() + choice.getChoiceAmount());
            }
            tvChoiceInfo.setText(stringBuilder.toString());
        } else {
            tvChoice.setVisibility(View.GONE);
            tvChoiceInfo.setVisibility(View.GONE);
        }


        String extraItems = getResources().getString(R.string.include) + SPACE + orderDetailArray.getSpecialRequest();
        tvSpecialInfo.setText(extraItems);
        tvSpecialInfo.setVisibility(orderDetailArray.getSpecialRequest().equals("") ? View.GONE : View.VISIBLE);
        tvSpecial.setVisibility(orderDetailArray.getSpecialRequest().equals("") ? View.GONE : View.VISIBLE);
        final ImageView ivClose = dialog.findViewById(R.id.iv_close);
        ivClose.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    @Override
    public void showLoggedInByAnother(String message) {
        showLoggedInByAnotherInfo(message);
    }
}
