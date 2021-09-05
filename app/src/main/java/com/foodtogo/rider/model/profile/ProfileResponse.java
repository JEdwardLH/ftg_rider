package com.foodtogo.rider.model.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Profile response.
 */
public class ProfileResponse {
    @SerializedName("deliver_fname")
    @Expose
    private String deliverFname;
    @SerializedName("deliver_lname")
    @Expose
    private String deliverLname;
    @SerializedName("deliver_email")
    @Expose
    private String deliverEmail;
    @SerializedName("deliver_phone1")
    @Expose
    private String deliverPhone1;
    @SerializedName("deliver_location")
    @Expose
    private String deliverLocation;
    @SerializedName("deliver_latitude")
    @Expose
    private String deliverLatitude;
    @SerializedName("deliver_longitude")
    @Expose
    private String deliverLongitude;
    @SerializedName("deliver_avail_status")
    @Expose
    private String deliverAvailStatus;
    @SerializedName("deliver_vehicle_details")
    @Expose
    private String deliverVehicleDetails;
    @SerializedName("deliver_order_limit")
    @Expose
    private String deliverOrderLimit;

    @SerializedName("otp")
    @Expose
    private Integer otp;

    /**
     * Gets otp.
     *
     * @return the otp
     */
    public Integer getOtp() {
        return otp;
    }

    /**
     * Sets otp.
     *
     * @param otp the otp
     */
    public void setOtp(Integer otp) {
        this.otp = otp;
    }


    /**
     * Gets deliver fname.
     *
     * @return the deliver fname
     */
    public String getDeliverFname() {
        return deliverFname;
    }

    /**
     * Sets deliver fname.
     *
     * @param deliverFname the deliver fname
     */
    public void setDeliverFname(String deliverFname) {
        this.deliverFname = deliverFname;
    }

    /**
     * Gets deliver lname.
     *
     * @return the deliver lname
     */
    public String getDeliverLname() {
        return deliverLname;
    }

    /**
     * Sets deliver lname.
     *
     * @param deliverLname the deliver lname
     */
    public void setDeliverLname(String deliverLname) {
        this.deliverLname = deliverLname;
    }

    /**
     * Gets deliver email.
     *
     * @return the deliver email
     */
    public String getDeliverEmail() {
        return deliverEmail;
    }

    /**
     * Sets deliver email.
     *
     * @param deliverEmail the deliver email
     */
    public void setDeliverEmail(String deliverEmail) {
        this.deliverEmail = deliverEmail;
    }

    /**
     * Gets deliver phone 1.
     *
     * @return the deliver phone 1
     */
    public String getDeliverPhone1() {
        return deliverPhone1;
    }

    /**
     * Sets deliver phone 1.
     *
     * @param deliverPhone1 the deliver phone 1
     */
    public void setDeliverPhone1(String deliverPhone1) {
        this.deliverPhone1 = deliverPhone1;
    }

    /**
     * Gets deliver location.
     *
     * @return the deliver location
     */
    public String getDeliverLocation() {
        return deliverLocation;
    }

    /**
     * Sets deliver location.
     *
     * @param deliverLocation the deliver location
     */
    public void setDeliverLocation(String deliverLocation) {
        this.deliverLocation = deliverLocation;
    }

    /**
     * Gets deliver latitude.
     *
     * @return the deliver latitude
     */
    public String getDeliverLatitude() {
        return deliverLatitude;
    }

    /**
     * Sets deliver latitude.
     *
     * @param deliverLatitude the deliver latitude
     */
    public void setDeliverLatitude(String deliverLatitude) {
        this.deliverLatitude = deliverLatitude;
    }

    /**
     * Gets deliver longitude.
     *
     * @return the deliver longitude
     */
    public String getDeliverLongitude() {
        return deliverLongitude;
    }

    /**
     * Sets deliver longitude.
     *
     * @param deliverLongitude the deliver longitude
     */
    public void setDeliverLongitude(String deliverLongitude) {
        this.deliverLongitude = deliverLongitude;
    }

    /**
     * Gets deliver avail status.
     *
     * @return the deliver avail status
     */
    public String getDeliverAvailStatus() {
        return deliverAvailStatus;
    }

    /**
     * Sets deliver avail status.
     *
     * @param deliverAvailStatus the deliver avail status
     */
    public void setDeliverAvailStatus(String deliverAvailStatus) {
        this.deliverAvailStatus = deliverAvailStatus;
    }

    /**
     * Gets deliver vehicle details.
     *
     * @return the deliver vehicle details
     */
    public String getDeliverVehicleDetails() {
        return deliverVehicleDetails;
    }

    /**
     * Sets deliver vehicle details.
     *
     * @param deliverVehicleDetails the deliver vehicle details
     */
    public void setDeliverVehicleDetails(String deliverVehicleDetails) {
        this.deliverVehicleDetails = deliverVehicleDetails;
    }

    /**
     * Gets deliver order limit.
     *
     * @return the deliver order limit
     */
    public String getDeliverOrderLimit() {
        return deliverOrderLimit;
    }

    /**
     * Sets deliver order limit.
     *
     * @param deliverOrderLimit the deliver order limit
     */
    public void setDeliverOrderLimit(String deliverOrderLimit) {
        this.deliverOrderLimit = deliverOrderLimit;
    }


}
