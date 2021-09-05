package com.foodtogo.rider.ui.processingorder.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.foodtogo.rider.R;
import com.foodtogo.rider.customview.MyAdapter;
import com.foodtogo.rider.customview.PicassoRoundedTransformation;
import com.foodtogo.rider.model.neworders.OrderNew;
import com.foodtogo.rider.ui.invoice.activity.InvoiceActivity;
import com.foodtogo.rider.ui.trackorder.activity.TrackOrderMapsActivity;
import com.foodtogo.rider.util.ConversionUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The type Processing order adapter.
 */
public class ProcessingOrderAdapter extends MyAdapter<MyAdapter.ItemViewHolder> {
    private List<OrderNew> processingOrderList;
    private Context context;
    private Listener listener;

    /**
     * Instantiates a new Processing order adapter.
     *
     * @param processingOrderList the processing order list
     * @param context             the context
     * @param mListener           the listener
     */
    public ProcessingOrderAdapter(List<OrderNew> processingOrderList, Context context, Listener mListener) {
        super(mListener);
        this.listener = mListener;
        this.processingOrderList = processingOrderList;
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateHolder(int viewType) {
        return new OrderItemHolder(this);
    }

    @Override
    public int _getItemCount() {
        return processingOrderList.size();
    }

    @Override
    public boolean isLoadMoreEnabled() {
        return true;
    }

    /**
     * The type Order item holder.
     */
    class OrderItemHolder extends MyAdapter.ItemViewHolder {
        /**
         * The Store image.
         */
        @BindView(R.id.restaurant_image)
        public ImageView storeImage;
        /**
         * The Store name.
         */
        @BindView(R.id.restaurant_name)
        public TextView storeName;
        /**
         * The Store address.
         */
        @BindView(R.id.address)
        public TextView storeAddress;
        /**
         * The Order id.
         */
        @BindView(R.id.order_id)
        public TextView orderId;
        /**
         * The Order date.
         */
        @BindView(R.id.order_date)
        public TextView orderDate;

        @BindView(R.id.order_payment_type)
        public TextView orderPaymentype;
        /**
         * The Amount.
         */
        @BindView(R.id.amount)
        public TextView amount;

        @BindView(R.id.total_order_amount)
        public TextView totalAmount;
        /**
         * The Invoice layout.
         */
        @BindView(R.id.invoice_layout)
        public RelativeLayout invoiceLayout;
        /**
         * The Track layout.
         */
        @BindView(R.id.track_layout)
        public RelativeLayout trackLayout;
        /**
         * The Info icon.
         */
        @BindView(R.id.info)
        ImageView info_icon;

        @BindView(R.id.phone_number)
        TextView phoneNumber;

        /**
         * Instantiates a new Order item holder.
         *
         * @param myAdapter the my adapter
         */
        public OrderItemHolder(@NonNull MyAdapter myAdapter) {
            super(myAdapter, R.layout.processing_order_item);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void update(int position) {
            super.update(position);

            final OrderNew mNewOrder = processingOrderList.get(position);
            Picasso.get()
                    .load(mNewOrder.getStoreLogo())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .transform(new PicassoRoundedTransformation(10, 5))
                    .into(storeImage);
            storeName.setText(mNewOrder.getStoreName());
            storeAddress.setText(mNewOrder.getStoreAddress());
            orderId.setText(mNewOrder.getOrderId());
            orderDate.setText(ConversionUtils.getFormatDay(mNewOrder.getOrderDate()) + " "+ConversionUtils.getFormatTime(mNewOrder.getOrderTime()));
            orderPaymentype.setText(mNewOrder.getPayType());

            totalAmount.setText(String.format("%s%s", mNewOrder.getOrderCurrency(), mNewOrder.getTotalOrderAmount()));
            if (mNewOrder.getPayType().equals("COD")) {
                amount.setText(String.format(context.getString(R.string.bind_total_amount), mNewOrder.getOrderCurrency(), mNewOrder.getTotalReceivableAmount()));
            } else {
                amount.setText(context.getString(R.string.paid));
            }

            invoiceLayout.setOnClickListener(v -> {
                Intent i = InvoiceActivity.gotoInvoiceActivity(context, String.valueOf(mNewOrder.getStoreId()), mNewOrder.getOrderId());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            });
            trackLayout.setOnClickListener(v -> {
                String amount = mNewOrder.getOrderCurrency() + mNewOrder.getTotalReceivableAmount();
                Intent i = TrackOrderMapsActivity.gotoTrackOrderActivity(context, String.valueOf(mNewOrder.getStoreId()), mNewOrder.getOrderId(), amount);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
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
        }
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

        EditText reason = mDismiss.findViewById(R.id.edt_reason);
        reason.setVisibility(View.GONE);
        Button dialogCancel = mDismiss.findViewById(R.id.btn_cancel);
        dialogCancel.setVisibility(View.GONE);
        TextView title = mDismiss.findViewById(R.id.reason_text);
        title.setText(msg);
        Button dialogButton = mDismiss.findViewById(R.id.btn_go);
        dialogButton.setText(context.getResources().getString(R.string.ok));
        dialogButton.setOnClickListener(v -> mDismiss.dismiss());
        mDismiss.show();
    }

    @Override
    public boolean isLoading() {
        return true;
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

    /**
     * The interface Listener.
     */
    public interface Listener extends MyAdapter.Listener {
        void callToRestaurant(String phone);
    }
}
