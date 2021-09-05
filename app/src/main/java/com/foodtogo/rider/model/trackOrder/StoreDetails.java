package com.foodtogo.rider.model.trackOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Store details.
 */
public class StoreDetails {
    @SerializedName("store_latitude")
    @Expose
    private String storeLatitude;
    @SerializedName("store_longitude")
    @Expose
    private String storeLongitude;
    @SerializedName("store_id")
    @Expose
    private String storeId;

    /**
     * Gets store latitude.
     *
     * @return the store latitude
     */
    public String getStoreLatitude() {
        return storeLatitude;
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
     * Gets store id.
     *
     * @return the store id
     */
    public String getStoreId() {
        return storeId;
    }


}
