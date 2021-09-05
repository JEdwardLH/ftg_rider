package com.foodtogo.rider.model.deliveredorders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Delivered order request.
 */
public class DeliveredOrderRequest {
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("from")
    @Expose
    private String fromDate;
    @SerializedName("to")
    @Expose
    private String toDate;
    @SerializedName("page_no")
    @Expose
    private String pageNo;
    @SerializedName("search_text")
    @Expose
    private String searchText;

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
     * Gets search text.
     *
     * @return the search text
     */
    public String getSearchText() {
        return searchText;
    }

    /**
     * Sets search text.
     *
     * @param searchText the search text
     */
    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}
