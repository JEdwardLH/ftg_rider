package com.foodtogo.rider.model.earningreports;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Report.
 */
public class Report {
    @SerializedName("order_delivered_date")
    @Expose
    private String orderDeliveredDate;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("order_amount")
    @Expose
    private String orderAmount;
    @SerializedName("order_currency")
    @Expose
    private String orderCurrency;
    @SerializedName("order_commission")
    @Expose
    private String orderCommission;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;

    /**
     * Gets order delivered date.
     *
     * @return the order delivered date
     */
    public String getOrderDeliveredDate() {
        return orderDeliveredDate;
    }

    /**
     * Sets order delivered date.
     *
     * @param orderDeliveredDate the order delivered date
     */
    public void setOrderDeliveredDate(String orderDeliveredDate) {
        this.orderDeliveredDate = orderDeliveredDate;
    }

    /**
     * Gets order id.
     *
     * @return the order id
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * Sets order id.
     *
     * @param orderId the order id
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * Gets order amount.
     *
     * @return the order amount
     */
    public String getOrderAmount() {
        return orderAmount;
    }

    /**
     * Sets order amount.
     *
     * @param orderAmount the order amount
     */
    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    /**
     * Gets order currency.
     *
     * @return the order currency
     */
    public String getOrderCurrency() {
        return orderCurrency;
    }

    /**
     * Sets order currency.
     *
     * @param orderCurrency the order currency
     */
    public void setOrderCurrency(String orderCurrency) {
        this.orderCurrency = orderCurrency;
    }

    /**
     * Gets order commission.
     *
     * @return the order commission
     */
    public String getOrderCommission() {
        return orderCommission;
    }

    /**
     * Sets order commission.
     *
     * @param orderCommission the order commission
     */
    public void setOrderCommission(String orderCommission) {
        this.orderCommission = orderCommission;
    }

    /**
     * Gets order status.
     *
     * @return the order status
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     * Sets order status.
     *
     * @param orderStatus the order status
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

}
