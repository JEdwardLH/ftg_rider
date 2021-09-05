package com.foodtogo.rider.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Request.
 */
public class Request {

    @SerializedName("lang")
    @Expose
    private String lang;

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
    public String getPage_no() {
        return page_no;
    }

    /**
     * Sets page no.
     *
     * @param page_no the page no
     */
    public void setPage_no(String page_no) {
        this.page_no = page_no;
    }

    private String page_no;


}
