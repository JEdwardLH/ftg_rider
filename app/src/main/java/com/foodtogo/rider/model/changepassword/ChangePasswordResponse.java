package com.foodtogo.rider.model.changepassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Change password response.
 */
public class ChangePasswordResponse {
    @SerializedName("delivery_id")
    @Expose
    private Integer deliveryId;
    @SerializedName("delivery_name")
    @Expose
    private String deliveryName;
    @SerializedName("delivery_email")
    @Expose
    private String deliveryEmail;
    @SerializedName("delivery_phone")
    @Expose
    private String deliveryPhone;

    /**
     * Gets delivery id.
     *
     * @return the delivery id
     */
    public Integer getDeliveryId() {
        return deliveryId;
    }

    /**
     * Sets delivery id.
     *
     * @param deliveryId the delivery id
     */
    public void setDeliveryId(Integer deliveryId) {
        this.deliveryId = deliveryId;
    }

    /**
     * Gets delivery name.
     *
     * @return the delivery name
     */
    public String getDeliveryName() {
        return deliveryName;
    }

    /**
     * Sets delivery name.
     *
     * @param deliveryName the delivery name
     */
    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    /**
     * Gets delivery email.
     *
     * @return the delivery email
     */
    public String getDeliveryEmail() {
        return deliveryEmail;
    }

    /**
     * Sets delivery email.
     *
     * @param deliveryEmail the delivery email
     */
    public void setDeliveryEmail(String deliveryEmail) {
        this.deliveryEmail = deliveryEmail;
    }

    /**
     * Gets delivery phone.
     *
     * @return the delivery phone
     */
    public String getDeliveryPhone() {
        return deliveryPhone;
    }

    /**
     * Sets delivery phone.
     *
     * @param deliveryPhone the delivery phone
     */
    public void setDeliveryPhone(String deliveryPhone) {
        this.deliveryPhone = deliveryPhone;
    }
}
