
package com.foodtogo.rider.model.geocodeaddress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The type Result.
 */
public class Result {

    @SerializedName("address_components")
    @Expose
    private List<AddressComponent> addressComponents = null;
    @SerializedName("formatted_address")
    @Expose
    private String formattedAddress;
    @SerializedName("geometry")
    @Expose
    private Geometry geometry;
    @SerializedName("place_id")
    @Expose
    private String placeId;
    @SerializedName("plus_code")
    @Expose
    private PlusCode_ plusCode;
    @SerializedName("types")
    @Expose
    private List<String> types = null;

    /**
     * Gets address components.
     *
     * @return the address components
     */
    public List<AddressComponent> getAddressComponents() {
        return addressComponents;
    }

    /**
     * Sets address components.
     *
     * @param addressComponents the address components
     */
    public void setAddressComponents(List<AddressComponent> addressComponents) {
        this.addressComponents = addressComponents;
    }

    /**
     * Gets formatted address.
     *
     * @return the formatted address
     */
    public String getFormattedAddress() {
        return formattedAddress;
    }

    /**
     * Sets formatted address.
     *
     * @param formattedAddress the formatted address
     */
    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    /**
     * Gets geometry.
     *
     * @return the geometry
     */
    public Geometry getGeometry() {
        return geometry;
    }

    /**
     * Sets geometry.
     *
     * @param geometry the geometry
     */
    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    /**
     * Gets place id.
     *
     * @return the place id
     */
    public String getPlaceId() {
        return placeId;
    }

    /**
     * Sets place id.
     *
     * @param placeId the place id
     */
    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    /**
     * Gets plus code.
     *
     * @return the plus code
     */
    public PlusCode_ getPlusCode() {
        return plusCode;
    }

    /**
     * Sets plus code.
     *
     * @param plusCode the plus code
     */
    public void setPlusCode(PlusCode_ plusCode) {
        this.plusCode = plusCode;
    }

    /**
     * Gets types.
     *
     * @return the types
     */
    public List<String> getTypes() {
        return types;
    }

    /**
     * Sets types.
     *
     * @param types the types
     */
    public void setTypes(List<String> types) {
        this.types = types;
    }

}
