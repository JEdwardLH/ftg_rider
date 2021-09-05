package com.foodtogo.rider.model.workinghours.getworkinghour;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Working hour.
 */
public class WorkingHour {
    @SerializedName("Monday")
    @Expose
    private Monday monday;
    @SerializedName("Tuesday")
    @Expose
    private Tuesday tuesday;
    @SerializedName("Wednesday")
    @Expose
    private Wednesday wednesday;
    @SerializedName("Thursday")
    @Expose
    private Thursday thursday;
    @SerializedName("Friday")
    @Expose
    private Friday friday;
    @SerializedName("Saturday")
    @Expose
    private Saturday saturday;
    @SerializedName("Sunday")
    @Expose
    private Sunday sunday;

    /**
     * Gets monday.
     *
     * @return the monday
     */
    public Monday getMonday() {
        return monday;
    }

    /**
     * Sets monday.
     *
     * @param monday the monday
     */
    public void setMonday(Monday monday) {
        this.monday = monday;
    }

    /**
     * Gets tuesday.
     *
     * @return the tuesday
     */
    public Tuesday getTuesday() {
        return tuesday;
    }

    /**
     * Sets tuesday.
     *
     * @param tuesday the tuesday
     */
    public void setTuesday(Tuesday tuesday) {
        this.tuesday = tuesday;
    }

    /**
     * Gets wednesday.
     *
     * @return the wednesday
     */
    public Wednesday getWednesday() {
        return wednesday;
    }

    /**
     * Sets wednesday.
     *
     * @param wednesday the wednesday
     */
    public void setWednesday(Wednesday wednesday) {
        this.wednesday = wednesday;
    }

    /**
     * Gets thursday.
     *
     * @return the thursday
     */
    public Thursday getThursday() {
        return thursday;
    }

    /**
     * Sets thursday.
     *
     * @param thursday the thursday
     */
    public void setThursday(Thursday thursday) {
        this.thursday = thursday;
    }

    /**
     * Gets friday.
     *
     * @return the friday
     */
    public Friday getFriday() {
        return friday;
    }

    /**
     * Sets friday.
     *
     * @param friday the friday
     */
    public void setFriday(Friday friday) {
        this.friday = friday;
    }

    /**
     * Gets saturday.
     *
     * @return the saturday
     */
    public Saturday getSaturday() {
        return saturday;
    }

    /**
     * Sets saturday.
     *
     * @param saturday the saturday
     */
    public void setSaturday(Saturday saturday) {
        this.saturday = saturday;
    }

    /**
     * Gets sunday.
     *
     * @return the sunday
     */
    public Sunday getSunday() {
        return sunday;
    }

    /**
     * Sets sunday.
     *
     * @param sunday the sunday
     */
    public void setSunday(Sunday sunday) {
        this.sunday = sunday;
    }
}
