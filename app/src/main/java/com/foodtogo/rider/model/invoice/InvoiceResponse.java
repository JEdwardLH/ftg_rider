package com.foodtogo.rider.model.invoice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The type Invoice response.
 */
public class InvoiceResponse {
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("customerDetailArray")
    @Expose
    private CustomerDetailArray customerDetailArray;
    @SerializedName("order_detailArray")
    @Expose
    private List<OrderDetailArray> orderDetailArray = null;
    @SerializedName("grand_sub_total")
    @Expose
    private String grandSubTotal;
    @SerializedName("delivery_fee")
    @Expose
    private String deliveryFee;
    @SerializedName("wallet_used")
    @Expose
    private String walletUsed;
    @SerializedName("cancel_item_total")
    @Expose
    private String cancelItemTotal;

    public String getCancelItemTotal() {
        return cancelItemTotal;
    }

    public void setCancelItemTotal(String cancelItemTotal) {
        this.cancelItemTotal = cancelItemTotal;
    }


    public String getOfferUsed() {
        return offerUsed;
    }

    public void setOfferUsed(String offerUsed) {
        this.offerUsed = offerUsed;
    }

    @SerializedName("offer_used")
    @Expose
    private String offerUsed;
    @SerializedName("totalReceivableAmount")
    @Expose
    private String totalReceivableAmount;

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
     * Gets order date.
     *
     * @return the order date
     */
    public String getOrderDate() {
        return orderDate;
    }

    /**
     * Sets order date.
     *
     * @param orderDate the order date
     */
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * Gets customer detail array.
     *
     * @return the customer detail array
     */
    public CustomerDetailArray getCustomerDetailArray() {
        return customerDetailArray;
    }

    /**
     * Sets customer detail array.
     *
     * @param customerDetailArray the customer detail array
     */
    public void setCustomerDetailArray(CustomerDetailArray customerDetailArray) {
        this.customerDetailArray = customerDetailArray;
    }

    /**
     * Gets order detail array.
     *
     * @return the order detail array
     */
    public List<OrderDetailArray> getOrderDetailArray() {
        return orderDetailArray;
    }

    /**
     * Sets order detail array.
     *
     * @param orderDetailArray the order detail array
     */
    public void setOrderDetailArray(List<OrderDetailArray> orderDetailArray) {
        this.orderDetailArray = orderDetailArray;
    }

    /**
     * Gets grand sub total.
     *
     * @return the grand sub total
     */
    public String getGrandSubTotal() {
        return grandSubTotal;
    }

    /**
     * Sets grand sub total.
     *
     * @param grandSubTotal the grand sub total
     */
    public void setGrandSubTotal(String grandSubTotal) {
        this.grandSubTotal = grandSubTotal;
    }

    /**
     * Gets delivery fee.
     *
     * @return the delivery fee
     */
    public String getDeliveryFee() {
        return deliveryFee;
    }

    /**
     * Sets delivery fee.
     *
     * @param deliveryFee the delivery fee
     */
    public void setDeliveryFee(String deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    /**
     * Gets wallet used.
     *
     * @return the wallet used
     */
    public String getWalletUsed() {
        return walletUsed;
    }

    /**
     * Sets wallet used.
     *
     * @param walletUsed the wallet used
     */
    public void setWalletUsed(String walletUsed) {
        this.walletUsed = walletUsed;
    }

    /**
     * Gets total receivable amount.
     *
     * @return the total receivable amount
     */
    public String getTotalReceivableAmount() {
        return totalReceivableAmount;
    }

    /**
     * Sets total receivable amount.
     *
     * @param totalReceivableAmount the total receivable amount
     */
    public void setTotalReceivableAmount(String totalReceivableAmount) {
        this.totalReceivableAmount = totalReceivableAmount;
    }
}
