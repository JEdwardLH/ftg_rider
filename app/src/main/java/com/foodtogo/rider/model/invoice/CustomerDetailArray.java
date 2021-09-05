package com.foodtogo.rider.model.invoice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Customer detail array.
 */
public class CustomerDetailArray {

    @SerializedName("customeName")
    @Expose
    private String customeName;
    @SerializedName("customerAddress1")
    @Expose
    private String customerAddress1;
    @SerializedName("customerAddress2")
    @Expose
    private String customerAddress2;
    @SerializedName("customerMobile")
    @Expose
    private String customerMobile;
    @SerializedName("customerEmail")
    @Expose
    private String customerEmail;

    /**
     * Gets custome name.
     *
     * @return the custome name
     */
    public String getCustomeName() {
        return customeName;
    }

    /**
     * Sets custome name.
     *
     * @param customeName the custome name
     */
    public void setCustomeName(String customeName) {
        this.customeName = customeName;
    }

    /**
     * Gets customer address 1.
     *
     * @return the customer address 1
     */
    public String getCustomerAddress1() {
        return customerAddress1;
    }

    /**
     * Sets customer address 1.
     *
     * @param customerAddress1 the customer address 1
     */
    public void setCustomerAddress1(String customerAddress1) {
        this.customerAddress1 = customerAddress1;
    }

    /**
     * Gets customer address 2.
     *
     * @return the customer address 2
     */
    public String getCustomerAddress2() {
        return customerAddress2;
    }

    /**
     * Sets customer address 2.
     *
     * @param customerAddress2 the customer address 2
     */
    public void setCustomerAddress2(String customerAddress2) {
        this.customerAddress2 = customerAddress2;
    }

    /**
     * Gets customer mobile.
     *
     * @return the customer mobile
     */
    public String getCustomerMobile() {
        return customerMobile;
    }

    /**
     * Sets customer mobile.
     *
     * @param customerMobile the customer mobile
     */
    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    /**
     * Gets customer email.
     *
     * @return the customer email
     */
    public String getCustomerEmail() {
        return customerEmail;
    }

    /**
     * Sets customer email.
     *
     * @param customerEmail the customer email
     */
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

}
