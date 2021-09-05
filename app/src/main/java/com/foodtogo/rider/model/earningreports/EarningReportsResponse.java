package com.foodtogo.rider.model.earningreports;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The type Earning reports response.
 */
public class EarningReportsResponse {
    @SerializedName("currency_code")
    @Expose
    private String currencyCode;
    @SerializedName("grant_total_commission")
    @Expose
    private String grantTotalCommission;
    @SerializedName("grant_total_order_amount")
    @Expose
    private String grantTotalOrderAmount;
    @SerializedName("reports")
    @Expose
    private List<Report> reports = null;
    @SerializedName("page_commission")
    @Expose
    private String pageCommission;

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
     * Gets grant total commission.
     *
     * @return the grant total commission
     */
    public String getGrantTotalCommission() {
        return grantTotalCommission;
    }

    /**
     * Sets grant total commission.
     *
     * @param grantTotalCommission the grant total commission
     */
    public void setGrantTotalCommission(String grantTotalCommission) {
        this.grantTotalCommission = grantTotalCommission;
    }

    /**
     * Gets grant total order amount.
     *
     * @return the grant total order amount
     */
    public String getGrantTotalOrderAmount() {
        return grantTotalOrderAmount;
    }

    /**
     * Sets grant total order amount.
     *
     * @param grantTotalOrderAmount the grant total order amount
     */
    public void setGrantTotalOrderAmount(String grantTotalOrderAmount) {
        this.grantTotalOrderAmount = grantTotalOrderAmount;
    }

    /**
     * Gets reports.
     *
     * @return the reports
     */
    public List<Report> getReports() {
        return reports;
    }

    /**
     * Sets reports.
     *
     * @param reports the reports
     */
    public void setReports(List<Report> reports) {
        this.reports = reports;
    }

    /**
     * Gets page commission.
     *
     * @return the page commission
     */
    public String getPageCommission() {
        return pageCommission;
    }

    /**
     * Sets page commission.
     *
     * @param pageCommission the page commission
     */
    public void setPageCommission(String pageCommission) {
        this.pageCommission = pageCommission;
    }
}
