package com.foodtogo.rider.model.deliveredorders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The type Delivered response.
 */
public class DeliveredResponse {
    @SerializedName("newOrderList")
    @Expose
    private List<NewOrderList> newOrderList = null;
    @SerializedName("max_page_count")
    @Expose
    private Integer maxPageCount;

    /**
     * Gets new order list.
     *
     * @return the new order list
     */
    public List<NewOrderList> getNewOrderList() {
        return newOrderList;
    }

    /**
     * Sets new order list.
     *
     * @param newOrderList the new order list
     */
    public void setNewOrderList(List<NewOrderList> newOrderList) {
        this.newOrderList = newOrderList;
    }

    /**
     * Gets max page count.
     *
     * @return the max page count
     */
    public Integer getMaxPageCount() {
        return maxPageCount;
    }

    /**
     * Sets max page count.
     *
     * @param maxPageCount the max page count
     */
    public void setMaxPageCount(Integer maxPageCount) {
        this.maxPageCount = maxPageCount;
    }
}
