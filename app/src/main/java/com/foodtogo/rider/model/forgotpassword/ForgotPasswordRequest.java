package com.foodtogo.rider.model.forgotpassword;

/**
 * The type Forgot password request.
 */
public class ForgotPasswordRequest {
    private String lang;
    private String delivery_email;

    /**
     * Gets delivery email.
     *
     * @return the delivery email
     */
    public String getDelivery_email() {
        return delivery_email;
    }

    /**
     * Sets delivery email.
     *
     * @param delivery_email the delivery email
     */
    public void setDelivery_email(String delivery_email) {
        this.delivery_email = delivery_email;
    }


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


}
