package com.foodtogo.rider.model.trackOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Update location request.
 */
public class UpdateLocationRequest {
    @SerializedName("deliver_latitude")
    @Expose
    private String deliveryLatitude;
    @SerializedName("deliver_longitude")
    @Expose
    private String deliveryLongitude;

    /**
     * Gets delivery latitude.
     *
     * @return the delivery latitude
     */
    public String getDeliveryLatitude() {
        return deliveryLatitude;
    }

    /**
     * Sets delivery latitude.
     *
     * @param deliveryLatitude the delivery latitude
     */
    public void setDeliveryLatitude(String deliveryLatitude) {
        this.deliveryLatitude = deliveryLatitude;
    }

    /**
     * Gets delivery longitude.
     *
     * @return the delivery longitude
     */
    public String getDeliveryLongitude() {
        return deliveryLongitude;
    }

    /**
     * Sets delivery longitude.
     *
     * @param deliveryLongitude the delivery longitude
     */
    public void setDeliveryLongitude(String deliveryLongitude) {
        this.deliveryLongitude = deliveryLongitude;
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

    @SerializedName("lang")
    @Expose
    private String lang;
}
