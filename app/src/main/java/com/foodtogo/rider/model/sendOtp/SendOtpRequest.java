package com.foodtogo.rider.model.sendOtp;

/**
 * The type Send otp request.
 */
public class SendOtpRequest {
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
     * Gets customer id.
     *
     * @return the customer id
     */
    public String getCustomer_id() {
        return customer_id;
    }

    /**
     * Sets customer id.
     *
     * @param customer_id the customer id
     */
    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    /**
     * Gets customer phone.
     *
     * @return the customer phone
     */
    public String getCustomer_phone() {
        return customer_phone;
    }

    /**
     * Sets customer phone.
     *
     * @param customer_phone the customer phone
     */
    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
    }

    /**
     * Gets order id.
     *
     * @return the order id
     */
    public String getOrder_id() {
        return order_id;
    }

    /**
     * Sets order id.
     *
     * @param order_id the order id
     */
    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    private String customer_id;
    private String customer_phone;
    private String order_id;
    private String customer_email;
    private String store_id;

}
