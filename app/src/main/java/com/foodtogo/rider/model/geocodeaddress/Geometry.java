
package com.foodtogo.rider.model.geocodeaddress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Geometry.
 */
public class Geometry {

    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("location_type")
    @Expose
    private String locationType;
    @SerializedName("viewport")
    @Expose
    private Viewport viewport;
    @SerializedName("bounds")
    @Expose
    private Bounds bounds;

    /**
     * Gets location.
     *
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Gets location type.
     *
     * @return the location type
     */
    public String getLocationType() {
        return locationType;
    }

    /**
     * Sets location type.
     *
     * @param locationType the location type
     */
    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    /**
     * Gets viewport.
     *
     * @return the viewport
     */
    public Viewport getViewport() {
        return viewport;
    }

    /**
     * Sets viewport.
     *
     * @param viewport the viewport
     */
    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    /**
     * Gets bounds.
     *
     * @return the bounds
     */
    public Bounds getBounds() {
        return bounds;
    }

    /**
     * Sets bounds.
     *
     * @param bounds the bounds
     */
    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

}
