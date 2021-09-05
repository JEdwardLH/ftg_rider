package com.foodtogo.rider.ui.invoice.interfaces;

import com.foodtogo.rider.model.invoice.OrderDetailArray;

/**
 * The interface Item info listener.
 */
public interface ItemInfoListener {

    /**
     * On click item.
     *
     * @param orderDetailArray the order detail array
     */
    void onClickItem(OrderDetailArray orderDetailArray);
}
