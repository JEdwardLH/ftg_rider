package com.foodtogo.rider.ui.neworderfragment.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.foodtogo.rider.R;
import com.foodtogo.rider.customview.MyAdapter;
import com.foodtogo.rider.customview.PicassoRoundedTransformation;
import com.foodtogo.rider.model.neworders.OrderNew;
import com.foodtogo.rider.ui.invoice.activity.InvoiceActivity;
import com.foodtogo.rider.ui.neworderfragment.mvp.NewOrderPresenter;
import com.foodtogo.rider.util.CommonStrings;
import com.foodtogo.rider.util.ConversionUtils;
import com.foodtogo.rider.util.ViewUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The type New order adapter.
 */
public class NewOrderAdapter extends MyAdapter<MyAdapter.ItemViewHolder> {
    private List<OrderNew> newOrderList;
    private Context context;
    private NewOrderPresenter presenter;

    /**
     * The interface Listener.
     */
    public interface Listener extends MyAdapter.Listener {
        void callToRestaurant(String phone);
    }

    /**
     * The Listener.
     */
    Listener listener;

    /**
     * Instantiates a new New order adapter.
     *
     * @param newOrderList the new order list
     * @param context      the context
     * @param presenter    the presenter
     * @param listener     the listener
     */
    public NewOrderAdapter(List<OrderNew> newOrderList, Context context, NewOrderPresenter presenter, Listener listener) {
        super(listener);
        this.listener = listener;
        this.newOrderList = newOrderList;
        this.context = context;
        this.presenter = presenter;
    }


    @Override
    public MyAdapter.ItemViewHolder onCreateHolder(int viewType) {
        return new ItemViewHolder(this);
    }

    @Override
    public int _getItemCount() {
        return newOrderList.size();
    }

    @Override
    public boolean isLoadMoreEnabled() {
        return true;
    }

    /**
     * The type Item view holder.
     */
    public class ItemViewHolder extends MyAdapter.ItemViewHolder {

        /**
         * The Store image.
         */
        @BindView(R.id.restaurant_image)
        ImageView storeImage;
        /**
         * The Info icon.
         */
        @BindView(R.id.info)
        ImageView info_icon;
        /**
         * The Store name.
         */
        @BindView(R.id.restaurant_name)
        TextView storeName;
        /**
         * The Store address.
         */
        @BindView(R.id.address)
        TextView storeAddress;
        /**
         * The Order id.
         */
        @BindView(R.id.order_id)
        TextView orderId;
        /**
         * The Order date.
         */
        @BindView(R.id.order_date)
        TextView orderDate;

        @BindView(R.id.order_payment_type)
        TextView orderPaymentType;
        /**
         * The Amount.
         */
        @BindView(R.id.amount)
        TextView amount;
        /**
         * The Btn select.
         */
        @BindView(R.id.btn_select)
        Button btnSelect;
        /**
         * The Btn cancel.
         */
        @BindView(R.id.btn_cancel)
        Button btnCancel;
        /**
         * The Invoice layout.
         */
        @BindView(R.id.invoice_layout)
        RelativeLayout invoiceLayout;

        @BindView(R.id.phone_number)
        TextView phoneNumber;

        @BindView(R.id.total_order_amount)
        TextView totalOrderAmount;

        /**
         * Instantiates a new Item view holder.
         *
         * @param adapter the adapter
         */
        public ItemViewHolder(MyAdapter adapter) {
            super(adapter, R.layout.new_order_item);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void update(int position) {
            super.update(position);
            final OrderNew mNewOrder = newOrderList.get(position);
            Picasso.get()
                    .load(mNewOrder.getStoreLogo())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .transform(new PicassoRoundedTransformation(10, 5))
                    .into(storeImage);
            storeName.setText(mNewOrder.getStoreName());
            storeAddress.setText(mNewOrder.getStoreAddress());
            orderId.setText(mNewOrder.getOrderId());
            orderDate.setText(ConversionUtils.getFormatDay(mNewOrder.getOrderDate()) + " " + ConversionUtils.getFormatTime(mNewOrder.getOrderTime()));
            orderPaymentType.setText(mNewOrder.getPayType());
            totalOrderAmount.setText(String.format(context.getString(R.string.bind_total_amount), mNewOrder.getOrderCurrency(), mNewOrder.getTotalOrderAmount()));
            if (mNewOrder.getPayType().equals("COD")) {
                amount.setText(String.format(context.getString(R.string.bind_total_amount), mNewOrder.getOrderCurrency(), mNewOrder.getTotalReceivableAmount()));
                amount.setTextColor(context.getResources().getColor(R.color.colorBlack));
            } else {
                amount.setText(context.getString(R.string.paid));
                amount.setTextColor(context.getResources().getColor(R.color.green));
            }
            LatLng storeLatLng = new LatLng(Double.valueOf(mNewOrder.getStoreLatitude()), Double.valueOf(mNewOrder.getStoreLongitude()));
            btnSelect.setOnClickListener(v -> presenter.acceptOrder(CommonStrings.ACCEPT_ORDER, mNewOrder.getOrderId(), String.valueOf(mNewOrder.getStoreId()), storeLatLng));
            btnCancel.setOnClickListener(v -> showDialog(mNewOrder.getOrderId(), String.valueOf(mNewOrder.getStoreId())));
            invoiceLayout.setOnClickListener(v -> {
                Intent i = InvoiceActivity.gotoInvoiceActivity(context, String.valueOf(mNewOrder.getStoreId()), mNewOrder.getOrderId());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            });
            if (mNewOrder.getStorePhone() != null && !mNewOrder.getStorePhone().equals("")) {
                phoneNumber.setVisibility(View.VISIBLE);
                phoneNumber.setText(mNewOrder.getStorePhone());
            } else {
                phoneNumber.setVisibility(View.GONE);
            }

            phoneNumber.setOnClickListener(view -> {
                if (!TextUtils.isEmpty(phoneNumber.getText().toString())) {
                    listener.callToRestaurant(phoneNumber.getText().toString());
                }
            });

            info_icon.setOnClickListener(v -> {
                String msg;
                if (mNewOrder.getPayType().equals("COD")) {
                    msg = "Need to collect:" + amount.getText().toString();
                    paidInfo(msg);
                } else {
                    msg = "Amount paid by " + mNewOrder.getPayType();
                    paidInfo(msg);
                }
            });
        }
    }


    /**
     * Show dialog.
     *
     * @param orderId the order id
     * @param storId  the stor id
     */
    void showDialog(String orderId, String storId) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_reject_order);

        EditText reason = dialog.findViewById(R.id.edt_reason);
        reason.setVisibility(View.VISIBLE);
        TextView title = dialog.findViewById(R.id.reason_text);
        title.setText(context.getResources().getString(R.string.reason));
        Button dialogCancel = dialog.findViewById(R.id.btn_cancel);
        Button dialogButton = dialog.findViewById(R.id.btn_go);
        dialogButton.setText(context.getResources().getString(R.string.send));

        dialogButton.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(reason.getText().toString())) {
                dialog.dismiss();
                presenter.rejectOrder(CommonStrings.REJECT_ORDER, orderId, storId, reason.getText().toString());
            } else {
                ViewUtils.showSnackBar(dialogButton, context.getString(R.string.enter_reason_reject));
            }
        });

        dialogCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    /**
     * Paid info.
     *
     * @param msg the msg
     */
    void paidInfo(String msg) {
        final Dialog mDismiss = new Dialog(context);
        mDismiss.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDismiss.setCancelable(true);
        mDismiss.setContentView(R.layout.dialog_reject_order);
        Button dialogCancel = mDismiss.findViewById(R.id.btn_cancel);
        dialogCancel.setVisibility(View.GONE);
        EditText reason = mDismiss.findViewById(R.id.edt_reason);
        reason.setVisibility(View.GONE);
        TextView title = mDismiss.findViewById(R.id.reason_text);
        title.setText(msg);
        Button dialogButton = mDismiss.findViewById(R.id.btn_go);
        dialogButton.setText(context.getResources().getString(R.string.ok));
        dialogButton.setOnClickListener(v -> mDismiss.dismiss());
        mDismiss.show();
    }

    public int getBgSingle() {
        return R.drawable.bg_card_single;
    }

    public int getBgTop() {
        return R.drawable.bg_card_top;
    }

    public int getBgCenter() {
        return R.drawable.bg_card_center;
    }

    public int getBgBottom() {
        return R.drawable.bg_card_bottom;
    }

    @Override
    public boolean isLoading() {
        return true;
    }
}
