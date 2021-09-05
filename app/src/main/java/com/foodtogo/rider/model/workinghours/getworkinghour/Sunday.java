package com.foodtogo.rider.model.workinghours.getworkinghour;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Sunday.
 */
public class Sunday {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("form_time")
    @Expose
    private String formTime;
    @SerializedName("to_time")
    @Expose
    private String toTime;

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
     * Gets form time.
     *
     * @return the form time
     */
    public String getFormTime() {
        return formTime;
    }

    /**
     * Sets form time.
     *
     * @param formTime the form time
     */
    public void setFormTime(String formTime) {
        this.formTime = formTime;
    }

    /**
     * Gets to time.
     *
     * @return the to time
     */
    public String getToTime() {
        return toTime;
    }

    /**
     * Sets to time.
     *
     * @param toTime the to time
     */
    public void setToTime(String toTime) {
        this.toTime = toTime;
    }
}
