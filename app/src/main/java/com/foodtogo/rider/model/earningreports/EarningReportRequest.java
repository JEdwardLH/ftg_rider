package com.foodtogo.rider.model.earningreports;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Earning report request.
 */
public class EarningReportRequest {
    @SerializedName("lang")
    @Expose
    private String lang;
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
