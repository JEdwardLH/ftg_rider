package com.foodtogo.rider.model.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Payment request.
 */
public class PaymentRequest {
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("paid")
    @Expose
    private String paid;
    @SerializedName("currency_symbol")
    @Expose
    private String currencySymbol;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("pay_type")
    @Expose
    private String payType;

    /**
     * Gets lang.
     *
     * @return the lang
     */
    public String getLang() {
        return lang;
    }

    /**
     * Sets lang.
     *
     * @param lang the lang
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     * Gets paid.
     *
     * @return the paid
     */
    public String getPaid() {
        return paid;
    }

    /**
     * Sets paid.
     *
     * @param paid the paid
     */
    public void setPaid(String paid) {
        this.paid = paid;
    }

    /**
     * Gets currency symbol.
     *
     * @return the currency symbol
     */
    public String getCurrencySymbol() {
        return currencySymbol;
    }

    /**
     * Sets currency symbol.
     *
     * @param currencySymbol the currency symbol
     */
    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.status = status;
    }

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
}
