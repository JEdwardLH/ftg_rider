package com.foodtogo.rider.model.deliveredorders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type New order list.
 */
public class NewOrderList {
    @SerializedName("delivered_on")
    @Expose
    private String deliveredOn;
    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("payType")
    @Expose
    private String payType;
    @SerializedName("orderCurrency")
    @Expose
    private String orderCurrency;
    @SerializedName("totalReceivableAmount")
    @Expose
    private String totalReceivableAmount;
    @SerializedName("total_order_amount")
    @Expose
    private String totalOrderAmount;
    @SerializedName("orderDate")
    @Expose
    private String orderDate;
    @SerializedName("orderSchedule")
    @Expose
    private String orderSchedule;
    @SerializedName("orderStatus")
    @Expose
    private String orderStatus;
    @SerializedName("storeId")
    @Expose
    private Integer storeId;
    @SerializedName("storeLogo")
    @Expose
    private String storeLogo;
    @SerializedName("storeName")
    @Expose
    private String storeName;
    @SerializedName("storeAddress")
    @Expose
    private String storeAddress;
    @SerializedName("storeLatitude")
    @Expose
    private String storeLatitude;
    @SerializedName("storeLongitude")
    @Expose
    private String storeLongitude;
    @SerializedName("storeDelivery_time")
    @Expose
    private String storeDeliveryTime;
    @SerializedName("storeDelivery_duration")
    @Expose
    private String storeDeliveryDuration;
    @SerializedName("preOrderDate")
    @Expose
    private String preOrderDate;
    @SerializedName("merchantName")
    @Expose
    private String merchantName;

    public String getTotalOrderAmount() {
        return totalOrderAmount;
    }

    public void setTotalOrderAmount(String totalOrderAmount) {
        this.totalOrderAmount = totalOrderAmount;
    }

    /**
     * Gets delivered on.
     *
     * @return the delivered on
     */
    public String getDeliveredOn() {
        return deliveredOn;
    }

    /**
     * Sets delivered on.
     *
     * @param deliveredOn the delivered on
     */
    public void setDeliveredOn(String deliveredOn) {
        this.deliveredOn = deliveredOn;
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
     * Gets order currency.
     *
     * @return the order currency
     */
    public String getOrderCurrency() {
        return orderCurrency;
    }

    /**
     * Sets order currency.
     *
     * @param orderCurrency the order currency
     */
    public void setOrderCurrency(String orderCurrency) {
        this.orderCurrency = orderCurrency;
    }

    /**
     * Gets total receivable amount.
     *
     * @return the total receivable amount
     */
    public String getTotalReceivableAmount() {
        return totalReceivableAmount;
    }

    /**
     * Sets total receivable amount.
     *
     * @param totalReceivableAmount the total receivable amount
     */
    public void setTotalReceivableAmount(String totalReceivableAmount) {
        this.totalReceivableAmount = totalReceivableAmount;
    }

    /**
     * Gets order date.
     *
     * @return the order date
     */
    public String getOrderDate() {
        return orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    @SerializedName("orderTime")
    @Expose
    private String orderTime;

    /**
     * Sets order date.
     *
     * @param orderDate the order date
     */
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * Gets order schedule.
     *
     * @return the order schedule
     */
    public String getOrderSchedule() {
        return orderSchedule;
    }

    /**
     * Sets order schedule.
     *
     * @param orderSchedule the order schedule
     */
    public void setOrderSchedule(String orderSchedule) {
        this.orderSchedule = orderSchedule;
    }

    /**
     * Gets order status.
     *
     * @return the order status
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     * Sets order status.
     *
     * @param orderStatus the order status
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * Gets store id.
     *
     * @return the store id
     */
    public Integer getStoreId() {
        return storeId;
    }

    /**
     * Sets store id.
     *
     * @param storeId the store id
     */
    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    /**
     * Gets store logo.
     *
     * @return the store logo
     */
    public String getStoreLogo() {
        return storeLogo;
    }

    /**
     * Sets store logo.
     *
     * @param storeLogo the store logo
     */
    public void setStoreLogo(String storeLogo) {
        this.storeLogo = storeLogo;
    }

    /**
     * Gets store name.
     *
     * @return the store name
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     * Sets store name.
     *
     * @param storeName the store name
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    /**
     * Gets store address.
     *
     * @return the store address
     */
    public String getStoreAddress() {
        return storeAddress;
    }

    /**
     * Sets store address.
     *
     * @param storeAddress the store address
     */
    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    /**
     * Gets store latitude.
     *
     * @return the store latitude
     */
    public String getStoreLatitude() {
        return storeLatitude;
    }

    /**
     * Sets store latitude.
     *
     * @param storeLatitude the store latitude
     */
    public void setStoreLatitude(String storeLatitude) {
        this.storeLatitude = storeLatitude;
    }

    /**
     * Gets store longitude.
     *
     * @return the store longitude
     */
    public String getStoreLongitude() {
        return storeLongitude;
    }

    /**
     * Sets store longitude.
     *
     * @param storeLongitude the store longitude
     */
    public void setStoreLongitude(String storeLongitude) {
        this.storeLongitude = storeLongitude;
    }

    /**
     * Gets store delivery time.
     *
     * @return the store delivery time
     */
    public String getStoreDeliveryTime() {
        return storeDeliveryTime;
    }

    /**
     * Sets store delivery time.
     *
     * @param storeDeliveryTime the store delivery time
     */
    public void setStoreDeliveryTime(String storeDeliveryTime) {
        this.storeDeliveryTime = storeDeliveryTime;
    }

    /**
     * Gets store delivery duration.
     *
     * @return the store delivery duration
     */
    public String getStoreDeliveryDuration() {
        return storeDeliveryDuration;
    }

    /**
     * Sets store delivery duration.
     *
     * @param storeDeliveryDuration the store delivery duration
     */
    public void setStoreDeliveryDuration(String storeDeliveryDuration) {
        this.storeDeliveryDuration = storeDeliveryDuration;
    }

    /**
     * Gets pre order date.
     *
     * @return the pre order date
     */
    public String getPreOrderDate() {
        return preOrderDate;
    }

    /**
     * Sets pre order date.
     *
     * @param preOrderDate the pre order date
     */
    public void setPreOrderDate(String preOrderDate) {
        this.preOrderDate = preOrderDate;
    }

    /**
     * Gets merchant name.
     *
     * @return the merchant name
     */
    public String getMerchantName() {
        return merchantName;
    }

    /**
     * Sets merchant name.
     *
     * @param merchantName the merchant name
     */
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
}
