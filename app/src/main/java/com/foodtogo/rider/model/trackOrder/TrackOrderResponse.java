package com.foodtogo.rider.model.trackOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The type Track order response.
 */
public class TrackOrderResponse {
    @SerializedName("customer_details")
    @Expose
    private CustomerDetails customerDetails;
    @SerializedName("store_details")
    @Expose
    private StoreDetails storeDetails;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("order_status_details")
    @Expose
    private List<OrderStatusDetail> orderStatusDetails = null;
    @SerializedName("pay_type")
    @Expose
    private String payType;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;

    /**
     * Gets customer details.
     *
     * @return the customer details
     */
    public CustomerDetails getCustomerDetails() {
        return customerDetails;
    }

    /**
     * Sets customer details.
     *
     * @param customerDetails the customer details
     */
    public void setCustomerDetails(CustomerDetails customerDetails) {
        this.customerDetails = customerDetails;
    }

    /**
     * Gets store details.
     *
     * @return the store details
     */
    public StoreDetails getStoreDetails() {
        return storeDetails;
    }

    /**
     * Sets store details.
     *
     * @param storeDetails the store details
     */
    public void setStoreDetails(StoreDetails storeDetails) {
        this.storeDetails = storeDetails;
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
     * Gets order status details.
     *
     * @return the order status details
     */
    public List<OrderStatusDetail> getOrderStatusDetails() {
        return orderStatusDetails;
    }

    /**
     * Sets order status details.
     *
     * @param orderStatusDetails the order status details
     */
    public void setOrderStatusDetails(List<OrderStatusDetail> orderStatusDetails) {
        this.orderStatusDetails = orderStatusDetails;
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
}
