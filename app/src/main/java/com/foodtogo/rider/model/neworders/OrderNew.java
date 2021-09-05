package com.foodtogo.rider.model.neworders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Order new.
 */
public class OrderNew {
    @SerializedName("cusId")
    @Expose
    private Integer cusId;
    @SerializedName("cusName")
    @Expose
    private String cusName;
    @SerializedName("cusAddress")
    @Expose
    private String cusAddress;
    @SerializedName("cusMobile1")
    @Expose
    private String cusMobile1;
    @SerializedName("cusMobile2")
    @Expose
    private String cusMobile2;
    @SerializedName("cusEmail")
    @Expose
    private String cusEmail;
    @SerializedName("cusLatitude")
    @Expose
    private String cusLatitude;
    @SerializedName("cusLongitude")
    @Expose
    private String cusLongitude;
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
    @SerializedName("orderDate")
    @Expose
    private String orderDate;
    public String getOrderTime() {
        return orderTime;
    }
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    @SerializedName("orderTime")
    @Expose
    private String orderTime;
    @SerializedName("orderSchedule")
    @Expose
    private String orderSchedule;
    @SerializedName("orderStatus")
    @Expose
    private String orderStatus;
    @SerializedName("storeId")
    @Expose
    private Integer storeId;
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
    @SerializedName("storeLogo")
    @Expose
    private String storeLogo;

    public String getTotalOrderAmount() {
        return totalOrderAmount;
    }

    public void setTotalOrderAmount(String totalOrderAmount) {
        this.totalOrderAmount = totalOrderAmount;
    }

    @SerializedName("total_order_amount")
    @Expose
    private String totalOrderAmount;

    public String getStorePhone() {
        return storePhone;
    }

    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
    }

    @SerializedName("storePhone")
    @Expose
    private String storePhone;

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * The Type.
     */
    String type;


    /**
     * Instantiates a new Order new.
     */
    public OrderNew() {
    }

    /**
     * Instantiates a new Order new.
     *
     * @param type the type
     */
    public OrderNew(String type) {
        this.type = type;
    }


    /**
     * Gets cus id.
     *
     * @return the cus id
     */
    public Integer getCusId() {
        return cusId;
    }

    /**
     * Sets cus id.
     *
     * @param cusId the cus id
     */
    public void setCusId(Integer cusId) {
        this.cusId = cusId;
    }

    /**
     * Gets cus name.
     *
     * @return the cus name
     */
    public String getCusName() {
        return cusName;
    }

    /**
     * Sets cus name.
     *
     * @param cusName the cus name
     */
    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    /**
     * Gets cus address.
     *
     * @return the cus address
     */
    public String getCusAddress() {
        return cusAddress;
    }

    /**
     * Sets cus address.
     *
     * @param cusAddress the cus address
     */
    public void setCusAddress(String cusAddress) {
        this.cusAddress = cusAddress;
    }

    /**
     * Gets cus mobile 1.
     *
     * @return the cus mobile 1
     */
    public String getCusMobile1() {
        return cusMobile1;
    }

    /**
     * Sets cus mobile 1.
     *
     * @param cusMobile1 the cus mobile 1
     */
    public void setCusMobile1(String cusMobile1) {
        this.cusMobile1 = cusMobile1;
    }

    /**
     * Gets cus mobile 2.
     *
     * @return the cus mobile 2
     */
    public String getCusMobile2() {
        return cusMobile2;
    }

    /**
     * Sets cus mobile 2.
     *
     * @param cusMobile2 the cus mobile 2
     */
    public void setCusMobile2(String cusMobile2) {
        this.cusMobile2 = cusMobile2;
    }

    /**
     * Gets cus email.
     *
     * @return the cus email
     */
    public String getCusEmail() {
        return cusEmail;
    }

    /**
     * Sets cus email.
     *
     * @param cusEmail the cus email
     */
    public void setCusEmail(String cusEmail) {
        this.cusEmail = cusEmail;
    }

    /**
     * Gets cus latitude.
     *
     * @return the cus latitude
     */
    public String getCusLatitude() {
        return cusLatitude;
    }

    /**
     * Sets cus latitude.
     *
     * @param cusLatitude the cus latitude
     */
    public void setCusLatitude(String cusLatitude) {
        this.cusLatitude = cusLatitude;
    }

    /**
     * Gets cus longitude.
     *
     * @return the cus longitude
     */
    public String getCusLongitude() {
        return cusLongitude;
    }

    /**
     * Sets cus longitude.
     *
     * @param cusLongitude the cus longitude
     */
    public void setCusLongitude(String cusLongitude) {
        this.cusLongitude = cusLongitude;
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
}
