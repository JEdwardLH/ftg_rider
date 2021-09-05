package com.foodtogo.rider.ui.deliveredorder.adapter;


import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.foodtogo.rider.R;
import com.foodtogo.rider.customview.MyAdapter;
import com.foodtogo.rider.customview.PicassoRoundedTransformation;
import com.foodtogo.rider.model.deliveredorders.NewOrderList;
import com.foodtogo.rider.ui.invoice.activity.InvoiceActivity;
import com.foodtogo.rider.util.ConversionUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * The type Delivery order adapter.
 */
public class DeliveryOrderAdapter extends MyAdapter<MyAdapter.ItemViewHolder> /*implements Filterable*/ {
    private List<NewOrderList> deliveredList;
    private Context context;
    /**
     * The Listener.
     */
    Listener listener;


    /**
     * The interface Listener.
     */
    public interface Listener extends MyAdapter.Listener {
        /**
         * On change configure.
         *
         * @param searchQuery the search query
         */
        void onChangeConfigure(String searchQuery);

        /**
         * On click date range.
         */
        void onClickDateRange();
    }


    /**
     * Instantiates a new Delivery order adapter.
     *
     * @param deliveredList the delivered list
     * @param context       the context
     * @param listener      the listener
     */
    public DeliveryOrderAdapter(List<NewOrderList> deliveredList, Context context, Listener listener) {
        super(listener);
        this.listener = listener;
        this.deliveredList = deliveredList;
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateHolder(int viewType) {
        return new OrderItemHolder(this);
    }


    @Override
    public int _getItemCount() {
        return deliveredList.size();
    }

    @Override
    public boolean isLoadMoreEnabled() {
        return true;
    }

    /**
     * The type Order item holder.
     */
    class OrderItemHolder extends ItemViewHolder {

        /**
         * The Store image.
         */
        @BindView(R.id.restaurant_image)
        ImageView storeImage;
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
        /**
         * The Amount.
         */
        @BindView(R.id.amount)
        TextView amount;

        @BindView(R.id.received_amount)
        TextView receivedAmount;
        /**
         * The Payment type.
         */
        @BindView(R.id.payment_type_text)
        TextView paymentType;
        /**
         * The Payment time.
         */
        @BindView(R.id.delivered_time_txt)
        TextView paymentTime;
        /**
         * The Invoice layout.
         */
        @BindView(R.id.invoice_layout)
        RelativeLayout invoiceLayout;

        /**
         * Instantiates a new Order item holder.
         *
         * @param myAdapter the my adapter
         */
        public OrderItemHolder(MyAdapter myAdapter) {
            super(myAdapter, R.layout.delivered_order_item);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void update(int position) {
            super.update(position);

            final NewOrderList deliveredOrder = deliveredList.get(position);
            Picasso.get()
                    .load(deliveredOrder.getStoreLogo())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .transform(new PicassoRoundedTransformation(10, 5))
                    .into(storeImage);
            storeName.setText(deliveredOrder.getStoreName());
            storeAddress.setText(deliveredOrder.getStoreAddress());
            orderId.setText(deliveredOrder.getOrderId());
            orderDate.setText(ConversionUtils.getFormatDay(deliveredOrder.getOrderDate()) + " " + ConversionUtils.getFormatTime(deliveredOrder.getOrderTime()));
            paymentType.setText(deliveredOrder.getPayType());
            String dateAndTime = deliveredOrder.getDeliveredOn();
            paymentTime.setText(ConversionUtils.getFormatDayTime(dateAndTime));
            amount.setText(String.format(context.getString(R.string.bind_delivered_total_amount), deliveredOrder.getOrderCurrency(), deliveredOrder.getTotalOrderAmount()));
            receivedAmount.setText(String.format(context.getString(R.string.bind_delivered_total_amount), deliveredOrder.getOrderCurrency(), deliveredOrder.getTotalReceivableAmount()));
            invoiceLayout.setOnClickListener(v -> {
                Intent i = InvoiceActivity.gotoInvoiceActivity(context, String.valueOf(deliveredOrder.getStoreId()), deliveredOrder.getOrderId());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            });
        }
    }

/*
    class OrderItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.calendar_icon)
        ImageView calendarIcon;
        @BindView(R.id.)
        EditText searchEdit;

        public OrderItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


            searchEdit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s.length()!=0){
                        adapter.getFilter().filter(s);
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }


        @OnClick(R.id.calendar_icon)
        void showDialog() {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.check_delivered_order);

            fromDate = dialog.findViewById(R.id.edt_from_date);
            toDate = dialog.findViewById(R.id.edt_to_date);
            Button dialogButton = dialog.findViewById(R.id.btn_go);
            dialogButton.setOnClickListener(v -> {
                if (!TextUtils.isEmpty(fromDate.getText().toString())) {
                    if (!TextUtils.isEmpty(fromDate.getText().toString())) {
                        dialog.dismiss();
                        if(isNetworkConnected()) {
                            showProgress();
                            presenter.getDeliveredOrders(apiFromDate, apiToDate, "0", "");
                        }else{
                            ViewUtils.showSnackBar(srl,getString(R.string.no_internet_connection));
                        }
                    } else {
                        ViewUtils.showSnackBar(dialogButton, context.getString(R.string.fill_to_date));
                    }

                } else {
                    ViewUtils.showSnackBar(dialogButton, context.getString(R.string.fill_from_date));
                }
            });

            fromDate.setOnClickListener(v -> showDateDialog(CommonStrings.FROM_DATE));
            toDate.setOnClickListener(v -> showDateDialog(CommonStrings.TO_DATE));

            dialog.show();
        }

        public void showDateDialog(String fromOrToDate) {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                    (view, year, monthOfYear, dayOfMonth) ->
                            bindDateValues(dayOfMonth,monthOfYear+1,year,fromOrToDate), mYear, mMonth, mDay);

            datePickerDialog.show();
        }

    }
*/

    @Override
    public boolean isLoading() {
        return false;
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
    public boolean isHeader() {
        return true;
    }

    @Override
    public ItemViewHolder getHeaderViewHolder() {
        return new HeaderViewHolder(this);
    }

    /**
     * The type Header view holder.
     */
    public class HeaderViewHolder extends MyAdapter.ItemViewHolder {

/*
        @BindView(R.id.search_id)
        EditText etQuery;

        @BindView(R.id.llDummy)
        LinearLayout llDummy;*/


        /**
         * The Pb processing.
         */
        @BindView(R.id.pbProcessing)
        ProgressBar pbProcessing;

        /**
         * Show loading view.
         */
        public void showLoadingView() {
            pbProcessing.setVisibility(View.VISIBLE);
        }

        /**
         * Hide loading view.
         */
        public void hideLoadingView() {
            pbProcessing.setVisibility(View.GONE);
        }

        /**
         * Instantiates a new Header view holder.
         *
         * @param myAdapter the my adapter
         */
        public HeaderViewHolder(MyAdapter myAdapter) {
            super(myAdapter, R.layout.head_delivered_orders);
            ButterKnife.bind(this, itemView);
            pbProcessing.setVisibility(View.GONE);
        }

        @Override
        public void update(int position) {
            super.update(position);
        }

        /**
         * The Search query.
         */
        String searchQuery = "";

        /**
         * On click date range.
         */
        @OnClick(R.id.calendar_icon)
        public void onClickDateRange() {
            if (getAdapterPosition() >= 0)
                listener.onClickDateRange();
        }

        /**
         * On click search.
         *
         * @param editable the editable
         */
        @OnTextChanged(R.id.search_id)
        public void onClickSearch(Editable editable) {
            if (getAdapterPosition() >= 0) {
                //   llDummy.requestFocus();
                searchQuery = editable.toString().trim();
                listener.onChangeConfigure(searchQuery);
            }
        }

/*        @OnEditorAction(R.id.search_id)
        public boolean onClickKeyboardSearch() {
            if(getAdapterPosition()>=0){
                onClickSearch();
            }
            return true;
        }*/

    }

}

