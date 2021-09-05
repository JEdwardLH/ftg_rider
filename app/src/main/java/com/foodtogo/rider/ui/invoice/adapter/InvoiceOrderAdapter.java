package com.foodtogo.rider.ui.invoice.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodtogo.rider.R;
import com.foodtogo.rider.customview.PicassoRoundedTransformation;
import com.foodtogo.rider.model.invoice.OrderDetailArray;
import com.foodtogo.rider.ui.invoice.interfaces.ItemInfoListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.foodtogo.rider.base.AppConstants.SPACE;

/**
 * The type Invoice order adapter.
 */
public class InvoiceOrderAdapter extends RecyclerView.Adapter<InvoiceOrderAdapter.ViewHolder> {
    private List<OrderDetailArray> orderList;
    private Context context;
    private ItemInfoListener itemInfoListener;

    /**
     * Instantiates a new Invoice order adapter.
     *
     * @param orderList the order list
     * @param context   the context
     */
    public InvoiceOrderAdapter(List<OrderDetailArray> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    /**
     * Sets item info listener.
     *
     * @param itemInfoListener the item info listener
     */
    public void setItemInfoListener(ItemInfoListener itemInfoListener) {
        this.itemInfoListener = itemInfoListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.invoice_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        OrderDetailArray orderListArray = orderList.get(position);
        if (!orderListArray.getSpecialRequest().equals("")) {
            viewHolder.mChoiceDot.setVisibility(View.VISIBLE);
            viewHolder.mChoice.setText(String.format("%s%s", context.getString(R.string.include), orderListArray.getSpecialRequest()));
        } else {
            viewHolder.mChoiceDot.setVisibility(View.GONE);
        }
        Picasso.get()
                .load(orderListArray.getPdtImage())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .transform(new PicassoRoundedTransformation(10, 5))
                .into(viewHolder.mFoodImg);
        String quantityInfo = context.getResources().getString(R.string.qty) + SPACE + orderListArray.getOrdQuantity();
        viewHolder.tvQuantityItem.setText(quantityInfo);
        viewHolder.mFoodName.setText(orderListArray.getItemName());
        viewHolder.mFoodPrice.setText(String.format("%s %s", orderListArray.getOrdCurrency(), orderListArray.getOrdUnitPrice()));
        viewHolder.itemView.setOnClickListener(v -> itemInfoListener.onClickItem(orderListArray));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    /**
     * The type View holder.
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * The M food img.
         */
        @BindView(R.id.iv_food)
        ImageView mFoodImg;

        /**
         * The M choice dot.
         */
        @BindView(R.id.view_extras)
        View mChoiceDot;
        /**
         * The M food name.
         */
        @BindView(R.id.tv_item_name)
        TextView mFoodName;
        /**
         * The M food price.
         */
        @BindView(R.id.tv_food_price)
        TextView mFoodPrice;
        /**
         * The M choice.
         */
        @BindView(R.id.tv_include)
        TextView mChoice;

        /**
         * The Tv quantity item.
         */
        @BindView(R.id.iv_quantity_item)
        TextView tvQuantityItem;


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
