package com.foodtogo.rider.model.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Payment conversion request.
 */
public class PaymentConversionRequest {
    @SerializedName("from")
    @Expose
    private String fromCurrency;
    @SerializedName("to")
    @Expose
    private String toCurrency;

    /**
     * Gets from currency.
     *
     * @return the from currency
     */
    public String getFromCurrency() {
        return fromCurrency;
    }

    /**
     * Sets from currency.
     *
     * @param fromCurrency the from currency
     */
    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    /**
     * Gets to currency.
     *
     * @return the to currency
     */
    public String getToCurrency() {
        return toCurrency;
    }

    /**
     * Sets to currency.
     *
     * @param toCurrency the to currency
     */
    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    /**
     * Gets final amount.
     *
     * @return the final amount
     */
    public String getFinalAmount() {
        return finalAmount;
    }

    /**
     * Sets final amount.
     *
     * @param finalAmount the final amount
     */
    public void setFinalAmount(String finalAmount) {
        this.finalAmount = finalAmount;
    }

    @SerializedName("amount")
    @Expose
    private String finalAmount;
}
