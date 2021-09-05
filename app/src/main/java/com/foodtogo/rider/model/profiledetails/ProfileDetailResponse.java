package com.foodtogo.rider.model.profiledetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Profile detail response.
 */
public class ProfileDetailResponse {
    @SerializedName("delivery_id")
    @Expose
    private Integer deliveryId;

    /**
     * Gets delivery licence.
     *
     * @return the delivery licence
     */
    public String getDelivery_licence() {
        return delivery_licence;
    }

    /**
     * Sets delivery licence.
     *
     * @param delivery_licence the delivery licence
     */
    public void setDelivery_licence(String delivery_licence) {
        this.delivery_licence = delivery_licence;
    }

    /**
     * Gets delivery address proof.
     *
     * @return the delivery address proof
     */
    public String getDelivery_address_proof() {
        return delivery_address_proof;
    }

    /**
     * Sets delivery address proof.
     *
     * @param delivery_address_proof the delivery address proof
     */
    public void setDelivery_address_proof(String delivery_address_proof) {
        this.delivery_address_proof = delivery_address_proof;
    }

    @SerializedName("delivery_licence")
    @Expose
    private String delivery_licence;
    @SerializedName("delivery_address_proof")
    @Expose
    private String delivery_address_proof;

    public String getAddressProofName() {
        return addressProofName;
    }

    public void setAddressProofName(String addressProofName) {
        this.addressProofName = addressProofName;
    }

    public String getLicenseName() {
        return licenseName;
    }

    public void setLicenseName(String licenseName) {
        this.licenseName = licenseName;
    }

    @SerializedName("delivery_address_proof_name")
    @Expose
    private String addressProofName;
    @SerializedName("delivery_licence_name")
    @Expose
    private String licenseName;
    @SerializedName("delivery_fname")
    @Expose
    private String deliveryFname;
    @SerializedName("delivery_lname")
    @Expose
    private String deliveryLname;
    @SerializedName("delivery_email")
    @Expose
    private String deliveryEmail;
    @SerializedName("delivery_phone")
    @Expose
    private String deliveryPhone;
    @SerializedName("delivery_profile")
    @Expose
    private String deliveryProfile;
    @SerializedName("delivery_response_time")
    @Expose
    private String deliveryResponseTime;
    @SerializedName("delivery_base_fare")
    @Expose
    private String deliveryBaseFare;
    @SerializedName("delivery_fare_type")
    @Expose
    private String deliveryFareType;
    @SerializedName("delivery_km_Ormin_charge")
    @Expose
    private String deliveryKmOrminCharge;
    @SerializedName("delivery_location")
    @Expose
    private String deliveryLocation;
    @SerializedName("delivery_latitude")
    @Expose
    private String deliveryLatitude;
    @SerializedName("delivery_longitude")
    @Expose
    private String deliveryLongitude;
    @SerializedName("delivery_status")
    @Expose
    private String deliveryStatus;
    @SerializedName("delivery_vehicle")
    @Expose
    private String deliveryVehicle;
    @SerializedName("order_limit")
    @Expose
    private Integer orderLimit;
    @SerializedName("wallet")
    @Expose
    private String wallet;

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
     * Gets delivery fname.
     *
     * @return the delivery fname
     */
    public String getDeliveryFname() {
        return deliveryFname;
    }

    /**
     * Sets delivery fname.
     *
     * @param deliveryFname the delivery fname
     */
    public void setDeliveryFname(String deliveryFname) {
        this.deliveryFname = deliveryFname;
    }

    /**
     * Gets delivery lname.
     *
     * @return the delivery lname
     */
    public String getDeliveryLname() {
        return deliveryLname;
    }

    /**
     * Sets delivery lname.
     *
     * @param deliveryLname the delivery lname
     */
    public void setDeliveryLname(String deliveryLname) {
        this.deliveryLname = deliveryLname;
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

    /**
     * Gets delivery profile.
     *
     * @return the delivery profile
     */
    public String getDeliveryProfile() {
        return deliveryProfile;
    }

    /**
     * Sets delivery profile.
     *
     * @param deliveryProfile the delivery profile
     */
    public void setDeliveryProfile(String deliveryProfile) {
        this.deliveryProfile = deliveryProfile;
    }

    /**
     * Gets delivery response time.
     *
     * @return the delivery response time
     */
    public String getDeliveryResponseTime() {
        return deliveryResponseTime;
    }

    /**
     * Sets delivery response time.
     *
     * @param deliveryResponseTime the delivery response time
     */
    public void setDeliveryResponseTime(String deliveryResponseTime) {
        this.deliveryResponseTime = deliveryResponseTime;
    }

    /**
     * Gets delivery base fare.
     *
     * @return the delivery base fare
     */
    public String getDeliveryBaseFare() {
        return deliveryBaseFare;
    }

    /**
     * Sets delivery base fare.
     *
     * @param deliveryBaseFare the delivery base fare
     */
    public void setDeliveryBaseFare(String deliveryBaseFare) {
        this.deliveryBaseFare = deliveryBaseFare;
    }

    /**
     * Gets delivery fare type.
     *
     * @return the delivery fare type
     */
    public String getDeliveryFareType() {
        return deliveryFareType;
    }

    /**
     * Sets delivery fare type.
     *
     * @param deliveryFareType the delivery fare type
     */
    public void setDeliveryFareType(String deliveryFareType) {
        this.deliveryFareType = deliveryFareType;
    }

    /**
     * Gets delivery km ormin charge.
     *
     * @return the delivery km ormin charge
     */
    public String getDeliveryKmOrminCharge() {
        return deliveryKmOrminCharge;
    }

    /**
     * Sets delivery km ormin charge.
     *
     * @param deliveryKmOrminCharge the delivery km ormin charge
     */
    public void setDeliveryKmOrminCharge(String deliveryKmOrminCharge) {
        this.deliveryKmOrminCharge = deliveryKmOrminCharge;
    }

    /**
     * Gets delivery location.
     *
     * @return the delivery location
     */
    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    /**
     * Sets delivery location.
     *
     * @param deliveryLocation the delivery location
     */
    public void setDeliveryLocation(String deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

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
     * Gets delivery status.
     *
     * @return the delivery status
     */
    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    /**
     * Sets delivery status.
     *
     * @param deliveryStatus the delivery status
     */
    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    /**
     * Gets delivery vehicle.
     *
     * @return the delivery vehicle
     */
    public String getDeliveryVehicle() {
        return deliveryVehicle;
    }

    /**
     * Sets delivery vehicle.
     *
     * @param deliveryVehicle the delivery vehicle
     */
    public void setDeliveryVehicle(String deliveryVehicle) {
        this.deliveryVehicle = deliveryVehicle;
    }

    /**
     * Gets order limit.
     *
     * @return the order limit
     */
    public Integer getOrderLimit() {
        return orderLimit;
    }

    /**
     * Sets order limit.
     *
     * @param orderLimit the order limit
     */
    public void setOrderLimit(Integer orderLimit) {
        this.orderLimit = orderLimit;
    }

    /**
     * Gets order limit.
     *
     * @return the order limit
     */
    public String getWallet() {
        return wallet;
    }

    /**
     * Sets order limit.
     *
     * @param wallet the order limit
     */
    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

}
