package com.foodtogo.rider.model.commissiontracking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Commission list.
 */
public class CommissionList {
    @SerializedName("delboyId")
    @Expose
    private Integer delboyId;
    @SerializedName("totalOrders")
    @Expose
    private Integer totalOrders;
    @SerializedName("totalOrderAmt")
    @Expose
    private String totalOrderAmt;
    @SerializedName("totalRcvdAmt")
    @Expose
    private String totalRcvdAmt;
    @SerializedName("totComisonAmt")
    @Expose
    private String totComisonAmt;
    @SerializedName("paidAmt")
    @Expose
    private String paidAmt;
    @SerializedName("receivedAmt")
    @Expose
    private String receivedAmt;
    @SerializedName("balAmtToReceive")
    @Expose
    private String balAmtToReceive;
    @SerializedName("balAmtToPay")
    @Expose
    private String balAmtToPay;
    @SerializedName("viewTransaxn")
    @Expose
    private String viewTransaxn;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("currency_code")
    @Expose
    private String currencyCode;

    /**
     * Gets currency code.
     *
     * @return the currency code
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * Sets currency code.
     *
     * @param currencyCode the currency code
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }


    /**
     * Gets currency.
     *
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets currency.
     *
     * @param currency the currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Gets delboy id.
     *
     * @return the delboy id
     */
    public Integer getDelboyId() {
        return delboyId;
    }

    /**
     * Sets delboy id.
     *
     * @param delboyId the delboy id
     */
    public void setDelboyId(Integer delboyId) {
        this.delboyId = delboyId;
    }

    /**
     * Gets total orders.
     *
     * @return the total orders
     */
    public Integer getTotalOrders() {
        return totalOrders;
    }

    /**
     * Sets total orders.
     *
     * @param totalOrders the total orders
     */
    public void setTotalOrders(Integer totalOrders) {
        this.totalOrders = totalOrders;
    }

    /**
     * Gets total order amt.
     *
     * @return the total order amt
     */
    public String getTotalOrderAmt() {
        return totalOrderAmt;
    }

    /**
     * Sets total order amt.
     *
     * @param totalOrderAmt the total order amt
     */
    public void setTotalOrderAmt(String totalOrderAmt) {
        this.totalOrderAmt = totalOrderAmt;
    }

    /**
     * Gets total rcvd amt.
     *
     * @return the total rcvd amt
     */
    public String getTotalRcvdAmt() {
        return totalRcvdAmt;
    }

    /**
     * Sets total rcvd amt.
     *
     * @param totalRcvdAmt the total rcvd amt
     */
    public void setTotalRcvdAmt(String totalRcvdAmt) {
        this.totalRcvdAmt = totalRcvdAmt;
    }

    /**
     * Gets tot comison amt.
     *
     * @return the tot comison amt
     */
    public String getTotComisonAmt() {
        return totComisonAmt;
    }

    /**
     * Sets tot comison amt.
     *
     * @param totComisonAmt the tot comison amt
     */
    public void setTotComisonAmt(String totComisonAmt) {
        this.totComisonAmt = totComisonAmt;
    }

    /**
     * Gets paid amt.
     *
     * @return the paid amt
     */
    public String getPaidAmt() {
        return paidAmt;
    }

    /**
     * Sets paid amt.
     *
     * @param paidAmt the paid amt
     */
    public void setPaidAmt(String paidAmt) {
        this.paidAmt = paidAmt;
    }

    /**
     * Gets received amt.
     *
     * @return the received amt
     */
    public String getReceivedAmt() {
        return receivedAmt;
    }

    /**
     * Sets received amt.
     *
     * @param receivedAmt the received amt
     */
    public void setReceivedAmt(String receivedAmt) {
        this.receivedAmt = receivedAmt;
    }

    /**
     * Gets bal amt to receive.
     *
     * @return the bal amt to receive
     */
    public String getBalAmtToReceive() {
        return balAmtToReceive;
    }

    /**
     * Sets bal amt to receive.
     *
     * @param balAmtToReceive the bal amt to receive
     */
    public void setBalAmtToReceive(String balAmtToReceive) {
        this.balAmtToReceive = balAmtToReceive;
    }

    /**
     * Gets bal amt to pay.
     *
     * @return the bal amt to pay
     */
    public String getBalAmtToPay() {
        return balAmtToPay;
    }

    /**
     * Sets bal amt to pay.
     *
     * @param balAmtToPay the bal amt to pay
     */
    public void setBalAmtToPay(String balAmtToPay) {
        this.balAmtToPay = balAmtToPay;
    }

    /**
     * Gets view transaxn.
     *
     * @return the view transaxn
     */
    public String getViewTransaxn() {
        return viewTransaxn;
    }

    /**
     * Sets view transaxn.
     *
     * @param viewTransaxn the view transaxn
     */
    public void setViewTransaxn(String viewTransaxn) {
        this.viewTransaxn = viewTransaxn;
    }
}
