package com.foodtogo.rider.model.commissiontracking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The type Commission tracking response.
 */
public class CommissionTrackingResponse {
    @SerializedName("commission_list")
    @Expose
    private List<CommissionList> commissionList = null;
    @SerializedName("paypal_status")
    @Expose
    private String paypalStatus;
    @SerializedName("paypal_client_id")
    @Expose
    private String paypalClientId;
    @SerializedName("paypal_secret_id")
    @Expose
    private String paypalSecretId;
    @SerializedName("net_bank_status")
    @Expose
    private String netBankStatus;
    @SerializedName("net_acc_no")
    @Expose
    private String netAccNo;
    @SerializedName("net_bank_name")
    @Expose
    private String netBankName;
    @SerializedName("net_branch")
    @Expose
    private String netBranch;
    @SerializedName("net_ifsc")
    @Expose
    private String netIfsc;

    /**
     * Gets commission list.
     *
     * @return the commission list
     */
    public List<CommissionList> getCommissionList() {
        return commissionList;
    }

    /**
     * Sets commission list.
     *
     * @param commissionList the commission list
     */
    public void setCommissionList(List<CommissionList> commissionList) {
        this.commissionList = commissionList;
    }

    /**
     * Gets paypal status.
     *
     * @return the paypal status
     */
    public String getPaypalStatus() {
        return paypalStatus;
    }

    /**
     * Sets paypal status.
     *
     * @param paypalStatus the paypal status
     */
    public void setPaypalStatus(String paypalStatus) {
        this.paypalStatus = paypalStatus;
    }

    /**
     * Gets paypal client id.
     *
     * @return the paypal client id
     */
    public String getPaypalClientId() {
        return paypalClientId;
    }

    /**
     * Sets paypal client id.
     *
     * @param paypalClientId the paypal client id
     */
    public void setPaypalClientId(String paypalClientId) {
        this.paypalClientId = paypalClientId;
    }

    /**
     * Gets paypal secret id.
     *
     * @return the paypal secret id
     */
    public String getPaypalSecretId() {
        return paypalSecretId;
    }

    /**
     * Sets paypal secret id.
     *
     * @param paypalSecretId the paypal secret id
     */
    public void setPaypalSecretId(String paypalSecretId) {
        this.paypalSecretId = paypalSecretId;
    }

    /**
     * Gets net bank status.
     *
     * @return the net bank status
     */
    public String getNetBankStatus() {
        return netBankStatus;
    }

    /**
     * Sets net bank status.
     *
     * @param netBankStatus the net bank status
     */
    public void setNetBankStatus(String netBankStatus) {
        this.netBankStatus = netBankStatus;
    }

    /**
     * Gets net acc no.
     *
     * @return the net acc no
     */
    public String getNetAccNo() {
        return netAccNo;
    }

    /**
     * Sets net acc no.
     *
     * @param netAccNo the net acc no
     */
    public void setNetAccNo(String netAccNo) {
        this.netAccNo = netAccNo;
    }

    /**
     * Gets net bank name.
     *
     * @return the net bank name
     */
    public String getNetBankName() {
        return netBankName;
    }

    /**
     * Sets net bank name.
     *
     * @param netBankName the net bank name
     */
    public void setNetBankName(String netBankName) {
        this.netBankName = netBankName;
    }

    /**
     * Gets net branch.
     *
     * @return the net branch
     */
    public String getNetBranch() {
        return netBranch;
    }

    /**
     * Sets net branch.
     *
     * @param netBranch the net branch
     */
    public void setNetBranch(String netBranch) {
        this.netBranch = netBranch;
    }

    /**
     * Gets net ifsc.
     *
     * @return the net ifsc
     */
    public String getNetIfsc() {
        return netIfsc;
    }

    /**
     * Sets net ifsc.
     *
     * @param netIfsc the net ifsc
     */
    public void setNetIfsc(String netIfsc) {
        this.netIfsc = netIfsc;
    }

}
