package com.foodtogo.rider.customview;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * The type Abstract adapter.
 *
 * @param <VH> the type parameter
 */
public abstract class AbstractAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements View.OnClickListener {

    /**
     * On create holder vh.
     *
     * @param viewType the view type
     * @return the vh
     */
    public abstract VH onCreateHolder(int viewType);

    /**
     * Get item view type int.
     *
     * @param position the position
     * @return the int
     */
    public abstract int _getItemViewType(int position);

    /**
     * Is header boolean.
     *
     * @return the boolean
     */
    public abstract boolean isHeader();

    /**
     * Is footer boolean.
     *
     * @return the boolean
     */
    public abstract boolean isFooter();

    /**
     * Gets no item message.
     *
     * @return the no item message
     */
    public abstract String getNoItemMessage();

    /**
     * Gets no item resource.
     *
     * @return the no item resource
     */
    public abstract int getNoItemResource();

    /**
     * Is loading boolean.
     *
     * @return the boolean
     */
    public abstract boolean isLoading();

    /**
     * Gets header view holder.
     *
     * @return the header view holder
     */
    public abstract MyAdapter.ItemViewHolder getHeaderViewHolder();

    /**
     * Gets footer view holder.
     *
     * @return the footer view holder
     */
    public abstract MyAdapter.ItemViewHolder getFooterViewHolder();

    /**
     * Get item count int.
     *
     * @return the int
     */
    public abstract int _getItemCount();

    /**
     * Is load more enabled boolean.
     *
     * @return the boolean
     */
    public abstract boolean isLoadMoreEnabled();

    /**
     * Gets bg single.
     *
     * @return the bg single
     */
    public abstract int getBgSingle();

    /**
     * Gets bg top.
     *
     * @return the bg top
     */
    public abstract int getBgTop();

    /**
     * Gets bg center.
     *
     * @return the bg center
     */
    public abstract int getBgCenter();

    /**
     * Gets bg bottom.
     *
     * @return the bg bottom
     */
    public abstract int getBgBottom();

}
