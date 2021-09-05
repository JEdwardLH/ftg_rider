package com.foodtogo.rider.model.dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The type Dashboard response.
 */
public class DashboardResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("total_orders")
    @Expose
    private Integer totalOrdersCount;
    @SerializedName("new_orders")
    @Expose
    private Integer newOrdersCount;
    @SerializedName("processing_orders")
    @Expose
    private Integer processingOrdersCount;
    @SerializedName("delivered_orders")
    @Expose
    private Integer deliveredOrdersCount;

    public Integer getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Integer activeStatus) {
        this.activeStatus = activeStatus;
    }

    @SerializedName("active_status")
    @Expose
    private Integer activeStatus;

    @SerializedName("wk_unavil_days")
    @Expose
    private List<String> workUnAvailDaysList;

    /**
     * Gets status.
     *
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets total order count.
     *
     * @return the total order count
     */
    public Integer getTotalOrderCount() {
        return totalOrdersCount;
    }

    /**
     * Gets new order count.
     *
     * @return the new order count
     */
    public Integer getNewOrderCount() {
        return newOrdersCount;
    }

    /**
     * Gets processing order count.
     *
     * @return the processing order count
     */
    public Integer getProcessingOrderCount() {
        return processingOrdersCount;
    }

    /**
     * Gets delivered order count.
     *
     * @return the delivered order count
     */
    public Integer getDeliveredOrderCount() {
        return deliveredOrdersCount;
    }
    public List<String> getWorkUnAvailDaysList() {
        return workUnAvailDaysList;
    }

    public void setWorkUnAvailDaysList(List<String> workUnAvailDaysList) {
        this.workUnAvailDaysList = workUnAvailDaysList;
    }

}
