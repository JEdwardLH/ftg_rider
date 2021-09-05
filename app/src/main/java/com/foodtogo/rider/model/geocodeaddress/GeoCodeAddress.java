
package com.foodtogo.rider.model.geocodeaddress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The type Geo code address.
 */
public class GeoCodeAddress {

    @SerializedName("plus_code")
    @Expose
    private PlusCode plusCode;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;
    @SerializedName("status")
    @Expose
    private String status;

    /**
     * Gets plus code.
     *
     * @return the plus code
     */
    public PlusCode getPlusCode() {
        return plusCode;
    }

    /**
     * Sets plus code.
     *
     * @param plusCode the plus code
     */
    public void setPlusCode(PlusCode plusCode) {
        this.plusCode = plusCode;
    }

    /**
     * Gets results.
     *
     * @return the results
     */
    public List<Result> getResults() {
        return results;
    }

    /**
     * Sets results.
     *
     * @param results the results
     */
    public void setResults(List<Result> results) {
        this.results = results;
    }

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

}
