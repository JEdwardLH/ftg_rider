package com.foodtogo.rider.model.neworders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Accept or reject order request.
 */
public class AcceptOrRejectOrderRequest {
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

    /**
     * Gets est arrival hour.
     *
     * @return the est arrival hour
     */
    public String getEstArrivalHour() {
        return estArrivalHour;
    }

    /**
     * Sets est arrival hour.
     *
     * @param estArrivalHour the est arrival hour
     */
    public void setEstArrivalHour(String estArrivalHour) {
        this.estArrivalHour = estArrivalHour;
    }

    /**
     * Gets est arrival min.
     *
     * @return the est arrival min
     */
    public String getEstArrivalMin() {
        return estArrivalMin;
    }

    /**
     * Sets est arrival min.
     *
     * @param estArrivalMin the est arrival min
     */
    public void setEstArrivalMin(String estArrivalMin) {
        this.estArrivalMin = estArrivalMin;
    }

    @SerializedName("estimated_arrival_inHours")
    @Expose
    private String estArrivalHour;
    @SerializedName("estimated_arrival_inMins")
    @Expose
    private String estArrivalMin;

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
}
