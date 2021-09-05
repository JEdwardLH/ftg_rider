
package com.foodtogo.rider.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * The type Login response.
 */
public class LoginResponse implements Serializable {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("delivery_person_id")
    @Expose
    private Integer deliveryPersonId;
    @SerializedName("delivery_person_name")
    @Expose
    private String deliveryPersonName;
    @SerializedName("delivery_person_email")
    @Expose
    private String deliveryPersonEmail;
    @SerializedName("delivery_person_phone")
    @Expose
    private String deliveryPersonPhone;
    @SerializedName("delivery_person_status")
    @Expose
    private String deliveryPersonStatus;
    @SerializedName("wk_hr_status")
    @Expose
    private String workingStatus;
    @SerializedName("uploaded_document_status")
    @Expose
    private String uploadDocumentStatus;

    /**
     * Gets upload document status.
     *
     * @return the upload document status
     */
    public String getUploadDocumentStatus() {
        return uploadDocumentStatus;
    }

    /**
     * Sets upload document status.
     *
     * @param uploadDocumentStatus the upload document status
     */
    public void setUploadDocumentStatus(String uploadDocumentStatus) {
        this.uploadDocumentStatus = uploadDocumentStatus;
    }

    /**
     * Gets working status.
     *
     * @return the working status
     */
    public String getWorkingStatus() {
        return workingStatus;
    }

    /**
     * Sets working status.
     *
     * @param workingStatus the working status
     */
    public void setWorkingStatus(String workingStatus) {
        this.workingStatus = workingStatus;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets token.
     *
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets token.
     *
     * @param token the token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Gets delivery person id.
     *
     * @return the delivery person id
     */
    public Integer getDeliveryPersonId() {
        return deliveryPersonId;
    }

    /**
     * Sets delivery person id.
     *
     * @param deliveryPersonId the delivery person id
     */
    public void setDeliveryPersonId(Integer deliveryPersonId) {
        this.deliveryPersonId = deliveryPersonId;
    }

    /**
     * Gets delivery person name.
     *
     * @return the delivery person name
     */
    public String getDeliveryPersonName() {
        return deliveryPersonName;
    }

    /**
     * Sets delivery person name.
     *
     * @param deliveryPersonName the delivery person name
     */
    public void setDeliveryPersonName(String deliveryPersonName) {
        this.deliveryPersonName = deliveryPersonName;
    }

    /**
     * Gets delivery person email.
     *
     * @return the delivery person email
     */
    public String getDeliveryPersonEmail() {
        return deliveryPersonEmail;
    }

    /**
     * Sets delivery person email.
     *
     * @param deliveryPersonEmail the delivery person email
     */
    public void setDeliveryPersonEmail(String deliveryPersonEmail) {
        this.deliveryPersonEmail = deliveryPersonEmail;
    }

    /**
     * Gets delivery person phone.
     *
     * @return the delivery person phone
     */
    public String getDeliveryPersonPhone() {
        return deliveryPersonPhone;
    }

    /**
     * Sets delivery person phone.
     *
     * @param deliveryPersonPhone the delivery person phone
     */
    public void setDeliveryPersonPhone(String deliveryPersonPhone) {
        this.deliveryPersonPhone = deliveryPersonPhone;
    }

    /**
     * Gets delivery person status.
     *
     * @return the delivery person status
     */
    public String getDeliveryPersonStatus() {
        return deliveryPersonStatus;
    }

    /**
     * Sets delivery person status.
     *
     * @param deliveryPersonStatus the delivery person status
     */
    public void setDeliveryPersonStatus(String deliveryPersonStatus) {
        this.deliveryPersonStatus = deliveryPersonStatus;
    }
}
