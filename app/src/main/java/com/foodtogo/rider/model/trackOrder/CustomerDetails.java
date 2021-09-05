package com.foodtogo.rider.model.trackOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Customer details.
 */
public class CustomerDetails {
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
}
