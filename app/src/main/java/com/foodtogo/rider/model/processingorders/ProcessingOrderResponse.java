package com.foodtogo.rider.model.processingorders;

import com.foodtogo.rider.model.neworders.OrderNew;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The type Processing order response.
 */
public class ProcessingOrderResponse {

    @SerializedName("newOrderList")
    @Expose
    private List<OrderNew> orderNew = null;
    @SerializedName("max_page_count")
    @Expose
    private Integer maxPageCount;

    /**
     * Gets order new.
     *
     * @return the order new
     */
    public List<OrderNew> getOrderNew() {
        return orderNew;
    }

    /**
     * Sets order new.
     *
     * @param orderNew the order new
     */
    public void setOrderNew(List<OrderNew> orderNew) {
        this.orderNew = orderNew;
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
