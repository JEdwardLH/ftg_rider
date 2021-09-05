package com.foodtogo.rider.model.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Order request.
 */
public class OrderRequest {
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("page_no")
    @Expose
    private String pageNo;

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

}
