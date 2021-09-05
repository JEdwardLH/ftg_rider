package com.foodtogo.rider.model.payment_settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Payment setting response.
 */
public class PaymentSettingResponse {
    @SerializedName("deliver_stripe_status")
    @Expose
    private String deliverStripeStatus;
    @SerializedName("deliver_stripe_clientid")
    @Expose
    private String deliverStripeClientid;
    @SerializedName("deliver_stripe_secretid")
    @Expose
    private String deliverStripeSecretid;
    @SerializedName("deliver_paypal_status")
    @Expose
    private String deliverPaypalStatus;
    @SerializedName("deliver_paypal_clientid")
    @Expose
    private String deliverPaypalClientid;
    @SerializedName("deliver_paypal_secretid")
    @Expose
    private String deliverPaypalSecretid;
    @SerializedName("deliver_netbank_status")
    @Expose
    private String deliverNetbankStatus;
    @SerializedName("deliver_bank_name")
    @Expose
    private String deliverBankName;
    @SerializedName("deliver_branch")
    @Expose
    private String deliverBranch;
    @SerializedName("deliver_bank_accno")
    @Expose
    private String deliverBankAccno;
    @SerializedName("deliver_ifsc")
    @Expose
    private String deliverIfsc;

    /**
     * Gets deliver stripe status.
     *
     * @return the deliver stripe status
     */
    public String getDeliverStripeStatus() {
        return deliverStripeStatus;
    }

    /**
     * Sets deliver stripe status.
     *
     * @param deliverStripeStatus the deliver stripe status
     */
    public void setDeliverStripeStatus(String deliverStripeStatus) {
        this.deliverStripeStatus = deliverStripeStatus;
    }

    /**
     * Gets deliver stripe clientid.
     *
     * @return the deliver stripe clientid
     */
    public String getDeliverStripeClientid() {
        return deliverStripeClientid;
    }

    /**
     * Sets deliver stripe clientid.
     *
     * @param deliverStripeClientid the deliver stripe clientid
     */
    public void setDeliverStripeClientid(String deliverStripeClientid) {
        this.deliverStripeClientid = deliverStripeClientid;
    }

    /**
     * Gets deliver stripe secretid.
     *
     * @return the deliver stripe secretid
     */
    public String getDeliverStripeSecretid() {
        return deliverStripeSecretid;
    }

    /**
     * Sets deliver stripe secretid.
     *
     * @param deliverStripeSecretid the deliver stripe secretid
     */
    public void setDeliverStripeSecretid(String deliverStripeSecretid) {
        this.deliverStripeSecretid = deliverStripeSecretid;
    }

    /**
     * Gets deliver paypal status.
     *
     * @return the deliver paypal status
     */
    public String getDeliverPaypalStatus() {
        return deliverPaypalStatus;
    }

    /**
     * Sets deliver paypal status.
     *
     * @param deliverPaypalStatus the deliver paypal status
     */
    public void setDeliverPaypalStatus(String deliverPaypalStatus) {
        this.deliverPaypalStatus = deliverPaypalStatus;
    }

    /**
     * Gets deliver paypal clientid.
     *
     * @return the deliver paypal clientid
     */
    public String getDeliverPaypalClientid() {
        return deliverPaypalClientid;
    }

    /**
     * Sets deliver paypal clientid.
     *
     * @param deliverPaypalClientid the deliver paypal clientid
     */
    public void setDeliverPaypalClientid(String deliverPaypalClientid) {
        this.deliverPaypalClientid = deliverPaypalClientid;
    }

    /**
     * Gets deliver paypal secretid.
     *
     * @return the deliver paypal secretid
     */
    public String getDeliverPaypalSecretid() {
        return deliverPaypalSecretid;
    }

    /**
     * Sets deliver paypal secretid.
     *
     * @param deliverPaypalSecretid the deliver paypal secretid
     */
    public void setDeliverPaypalSecretid(String deliverPaypalSecretid) {
        this.deliverPaypalSecretid = deliverPaypalSecretid;
    }

    /**
     * Gets deliver netbank status.
     *
     * @return the deliver netbank status
     */
    public String getDeliverNetbankStatus() {
        return deliverNetbankStatus;
    }

    /**
     * Sets deliver netbank status.
     *
     * @param deliverNetbankStatus the deliver netbank status
     */
    public void setDeliverNetbankStatus(String deliverNetbankStatus) {
        this.deliverNetbankStatus = deliverNetbankStatus;
    }

    /**
     * Gets deliver bank name.
     *
     * @return the deliver bank name
     */
    public String getDeliverBankName() {
        return deliverBankName;
    }

    /**
     * Sets deliver bank name.
     *
     * @param deliverBankName the deliver bank name
     */
    public void setDeliverBankName(String deliverBankName) {
        this.deliverBankName = deliverBankName;
    }

    /**
     * Gets deliver branch.
     *
     * @return the deliver branch
     */
    public String getDeliverBranch() {
        return deliverBranch;
    }

    /**
     * Sets deliver branch.
     *
     * @param deliverBranch the deliver branch
     */
    public void setDeliverBranch(String deliverBranch) {
        this.deliverBranch = deliverBranch;
    }

    /**
     * Gets deliver bank accno.
     *
     * @return the deliver bank accno
     */
    public String getDeliverBankAccno() {
        return deliverBankAccno;
    }

    /**
     * Sets deliver bank accno.
     *
     * @param deliverBankAccno the deliver bank accno
     */
    public void setDeliverBankAccno(String deliverBankAccno) {
        this.deliverBankAccno = deliverBankAccno;
    }

    /**
     * Gets deliver ifsc.
     *
     * @return the deliver ifsc
     */
    public String getDeliverIfsc() {
        return deliverIfsc;
    }

    /**
     * Sets deliver ifsc.
     *
     * @param deliverIfsc the deliver ifsc
     */
    public void setDeliverIfsc(String deliverIfsc) {
        this.deliverIfsc = deliverIfsc;
    }

}
