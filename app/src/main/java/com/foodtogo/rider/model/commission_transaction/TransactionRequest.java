package com.foodtogo.rider.model.commission_transaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Transaction request.
 */
public class TransactionRequest {
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("page_no")
    @Expose
    private String pageNo;
    @SerializedName("from_date")
    @Expose
    private String fromDate;
    @SerializedName("to_date")
    @Expose
    private String toDate;

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
     * Gets page no.
     *
     * @return the page no
     */
    public String getPageNo() {
        return pageNo;
    }

    /**
     * Sets page no.
     *
     * @param pageNo the page no
     */
    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * Gets from date.
     *
     * @return the from date
     */
    public String getFromDate() {
        return fromDate;
    }

    /**
     * Sets from date.
     *
     * @param fromDate the from date
     */
    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * Gets to date.
     *
     * @return the to date
     */
    public String getToDate() {
        return toDate;
    }

    /**
     * Sets to date.
     *
     * @param toDate the to date
     */
    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

}
