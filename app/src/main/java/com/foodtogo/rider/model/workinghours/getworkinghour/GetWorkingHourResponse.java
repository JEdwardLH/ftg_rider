package com.foodtogo.rider.model.workinghours.getworkinghour;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The type Get working hour response.
 */
public class GetWorkingHourResponse {
    @SerializedName("working_hours")
    @Expose
    private List<WorkingHour> workingHours = null;

    /**
     * Gets working hours.
     *
     * @return the working hours
     */
    public List<WorkingHour> getWorkingHours() {
        return workingHours;
    }

    /**
     * Sets working hours.
     *
     * @param workingHours the working hours
     */
    public void setWorkingHours(List<WorkingHour> workingHours) {
        this.workingHours = workingHours;
    }


}
