package com.foodtogo.rider.model.commission_transaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Transaction list.
 */
public class TransactionList {
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("pay_type")
    @Expose
    private String payType;
    @SerializedName("commission_received")
    @Expose
    private String commissionReceived;
    @SerializedName("paid_order_amount")
    @Expose
    private String paidOrderAmount;
    @SerializedName("paid_date")
    @Expose
    private String paidDate;

    @SerializedName("commission_currency")
    @Expose
    private String commissionCurrency;

    /**
     * Gets transaction id.
     *
     * @return the transaction id
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Sets transaction id.
     *
     * @param transactionId the transaction id
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * Gets pay type.
     *
     * @return the pay type
     */
    public String getPayType() {
        return payType;
    }

    /**
     * Sets pay type.
     *
     * @param payType the pay type
     */
    public void setPayType(String payType) {
        this.payType = payType;
    }

    /**
     * Gets commission received.
     *
     * @return the commission received
     */
    public String getCommissionReceived() {
        return commissionReceived;
    }

    /**
     * Sets commission received.
     *
     * @param commissionReceived the commission received
     */
    public void setCommissionReceived(String commissionReceived) {
        this.commissionReceived = commissionReceived;
    }

    /**
     * Gets paid order amount.
     *
     * @return the paid order amount
     */
    public String getPaidOrderAmount() {
        return paidOrderAmount;
    }

    /**
     * Sets paid order amount.
     *
     * @param paidOrderAmount the paid order amount
     */
    public void setPaidOrderAmount(String paidOrderAmount) {
        this.paidOrderAmount = paidOrderAmount;
    }

    /**
     * Gets paid date.
     *
     * @return the paid date
     */
    public String getPaidDate() {
        return paidDate;
    }

    /**
     * Sets paid date.
     *
     * @param paidDate the paid date
     */
    public void setPaidDate(String paidDate) {
        this.paidDate = paidDate;
    }

    public String getCommissionCurrency() {
        return commissionCurrency;
    }

    public void setCommissionCurrency(String commissionCurrency) {
        this.commissionCurrency = commissionCurrency;
    }



}
