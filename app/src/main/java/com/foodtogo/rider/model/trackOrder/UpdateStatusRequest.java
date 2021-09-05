package com.foodtogo.rider.model.trackOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Update status request.
 */
public class UpdateStatusRequest {
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("store_id")
    @Expose
    private String storeId;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("pay_type")
    @Expose
    private String payType;

    @SerializedName("travel_duration")
    @Expose
    private String travelDuration;
    @SerializedName("travel_distance")
    @Expose
    private String travelDistance;

    /**
     * Gets travel duration.
     *
     * @return the travel duration
     */
    public String getTravelDuration() {
        return travelDuration;
    }

    /**
     * Sets travel duration.
     *
     * @param travelDuration the travel duration
     */
    public void setTravelDuration(String travelDuration) {
        this.travelDuration = travelDuration;
    }

    /**
     * Gets travel distance.
     *
     * @return the travel distance
     */
    public String getTravelDistance() {
        return travelDistance;
    }

    /**
     * Sets travel distance.
     *
     * @param travelDistance the travel distance
     */
    public void setTravelDistance(String travelDistance) {
        this.travelDistance = travelDistance;
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

    /**
     * Gets store id.
     *
     * @return the store id
     */
    public String getStoreId() {
        return storeId;
    }

    /**
     * Sets store id.
     *
     * @param storeId the store id
     */
    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    /**
     * Gets order id.
     *
     * @return the order id
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * Sets order id.
     *
     * @param orderId the order id
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets reason.
     *
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets reason.
     *
     * @param reason the reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * Gets latitude.
     *
     * @return the latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Sets latitude.
     *
     * @param latitude the latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * Gets longitude.
     *
     * @return the longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Sets longitude.
     *
     * @param longitude the longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * Gets pay type.
     *
     * @return the pay type
     */
    public String getPayType() {
        return payType;
    }

    /**
     * Sets pay type.
     *
     * @param payType the pay type
     */
    public void setPayType(String payType) {
        this.payType = payType;
    }



}
