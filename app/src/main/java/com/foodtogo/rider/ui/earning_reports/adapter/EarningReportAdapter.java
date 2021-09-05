package com.foodtogo.rider.ui.earning_reports.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foodtogo.rider.R;
import com.foodtogo.rider.model.earningreports.Report;
import com.foodtogo.rider.util.ConversionUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The type Earning report adapter.
 */
public class EarningReportAdapter extends RecyclerView.Adapter<EarningReportAdapter.ViewHolder> {
    private List<Report> earningList;
    private Context context;

    /**
     * Instantiates a new Earning report adapter.
     *
     * @param earningList the earning list
     * @param context     the context
     */
    public EarningReportAdapter(List<Report> earningList, Context context) {
        this.earningList = earningList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.earning_report_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Report reportList = earningList.get(position);
        viewHolder.orderIdTxt.setText(reportList.getOrderId());
        String orderDate[] = reportList.getOrderDeliveredDate().split(" ");
        viewHolder.dateTxt.setText(ConversionUtils.deliveredOrderShowDate(orderDate[0]));
        viewHolder.statusItem.setText(reportList.getOrderStatus());
        viewHolder.chargeItem.setText(String.format("%s%s", reportList.getOrderCurrency(), reportList.getOrderCommission()));
    }

    @Override
    public int getItemCount() {
        return earningList.size();
    }


    /**
     * The type View holder.
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * The Order id txt.
         */
        @BindView(R.id.order_id_value)
        TextView orderIdTxt;
        /**
         * The Date txt.
         */
        @BindView(R.id.date_item)
        TextView dateTxt;
        /**
         * The Status item.
         */
        @BindView(R.id.status_item)
        TextView statusItem;
        /**
         * The Charge item.
         */
        @BindView(R.id.charge_item)
        TextView chargeItem;

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         */
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
