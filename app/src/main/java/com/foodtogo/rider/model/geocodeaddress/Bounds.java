
package com.foodtogo.rider.model.geocodeaddress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Bounds.
 */
public class Bounds {

    @SerializedName("northeast")
    @Expose
    private Northeast_ northeast;
    @SerializedName("southwest")
    @Expose
    private Southwest_ southwest;

    /**
     * Gets northeast.
     *
     * @return the northeast
     */
    public Northeast_ getNortheast() {
        return northeast;
    }

    /**
     * Sets northeast.
     *
     * @param northeast the northeast
     */
    public void setNortheast(Northeast_ northeast) {
        this.northeast = northeast;
    }

    /**
     * Gets southwest.
     *
     * @return the southwest
     */
    public Southwest_ getSouthwest() {
        return southwest;
    }

    /**
     * Sets southwest.
     *
     * @param southwest the southwest
     */
    public void setSouthwest(Southwest_ southwest) {
        this.southwest = southwest;
    }

}
