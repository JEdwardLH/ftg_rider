package com.foodtogo.rider.ui.commissiontransaction.adapter;

import android.widget.TextView;

import com.foodtogo.rider.R;
import com.foodtogo.rider.customview.MyAdapter;
import com.foodtogo.rider.model.commission_transaction.TransactionList;
import com.foodtogo.rider.util.ConversionUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * The type Transaction adapter.
 */
public class TransactionAdapter extends MyAdapter<MyAdapter.ItemViewHolder> {
    private List<TransactionList> transactionList;
    /**
     * The Listener.
     */
    Listener listener;

    /**
     * The interface Listener.
     */
    public interface Listener extends MyAdapter.Listener {
        /**
         * On click date range.
         */
        void onClickDateRange();
    }

    /**
     * Instantiates a new Transaction adapter.
     *
     * @param transactionList the transaction list
     * @param listener        the listener
     */
    public TransactionAdapter(List<TransactionList> transactionList, Listener listener) {
        super(listener);
        this.listener = listener;
        this.transactionList = transactionList;
    }

    @Override
    public ItemViewHolder onCreateHolder(int viewType) {
        return new ViewHolder(this);
    }

    @Override
    public int _getItemCount() {
        return transactionList.size();
    }

    @Override
    public boolean isLoadMoreEnabled() {
        return true;
    }

    @Override
    public boolean isLoading() {
        return true;
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

        /**
         * Instantiates a new Header view holder.
         *
         * @param myAdapter the my adapter
         */
        public HeaderViewHolder(MyAdapter myAdapter) {
            super(myAdapter, R.layout.head_commission_transaction);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void update(int position) {
            super.update(position);
        }

        /**
         * On click date range.
         */
        @OnClick(R.id.calendar_icon)
        public void onClickDateRange() {
            if (getAdapterPosition() >= 0)
                listener.onClickDateRange();
        }
    }

    /**
     * The type View holder.
     */
    class ViewHolder extends ItemViewHolder {
        /**
         * The Transaction id.
         */
        @BindView(R.id.tran_id)
        TextView transactionId;
        /**
         * The Date.
         */
        @BindView(R.id.date_item)
        TextView date;
        /**
         * The Payment type.
         */
        @BindView(R.id.payment_type)
        TextView paymentType;
        /**
         * The Paid amount.
         */
        @BindView(R.id.paid_amount)
        TextView paidAmount;
        /**
         * The Received amount.
         */
        @BindView(R.id.received_amount)
        TextView receivedAmount;

        /**
         * Instantiates a new View holder.
         *
         * @param adapter the adapter
         */
        public ViewHolder(MyAdapter adapter) {
            super(adapter, R.layout.transaction_item);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void update(int position) {
            super.update(position);

            TransactionList list = transactionList.get(position);
            transactionId.setText(String.format(":", list.getTransactionId()));
            date.setText(String.format(": %s", ConversionUtils.transactionDate(list.getPaidDate())));
            paymentType.setText(String.format(": %s", list.getPayType()));
            paidAmount.setText(String.format(": %s%s", list.getCommissionCurrency(), list.getPaidOrderAmount()));
            receivedAmount.setText(String.format(": %s%s", list.getCommissionCurrency(), list.getCommissionReceived()));
        }
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

}
