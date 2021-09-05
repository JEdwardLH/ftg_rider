
package com.foodtogo.rider.model.login;

/**
 * The type Login request.
 */
public class LoginRequest {

    private String lang;
    private String login_id;
    private String delivery_password;
    private String andr_fcm_id;
    private String latitude;
    private String longitude;
    private String type;
    private String andr_device_id;

    /**
     * Gets location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    private String location;

    /**
     * Gets latitude.
     *
     * @return the latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Sets latitude.
     *
     * @param latitude the latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * Gets longitude.
     *
     * @return the longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Sets longitude.
     *
     * @param longitude the longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


    /**
     * Gets andr fcm id.
     *
     * @return the andr fcm id
     */
    public String getAndr_fcm_id() {
        return andr_fcm_id;
    }

    /**
     * Sets andr fcm id.
     *
     * @param andr_fcm_id the andr fcm id
     */
    public void setAndr_fcm_id(String andr_fcm_id) {
        this.andr_fcm_id = andr_fcm_id;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }


    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return login_id;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.login_id = email;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return delivery_password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.delivery_password = password;
    }

    /**
     * Gets language.
     *
     * @return the language
     */
    public String getLanguage() {
        return lang;
    }

    /**
     * Sets language.
     *
     * @param language the language
     */
    public void setLanguage(String language) {
        this.lang = language;
    }

    /**
     * Gets andr device id.
     *
     * @return the andr device id
     */
    public String getAndr_device_id() {
        return andr_device_id;
    }

    /**
     * Sets andr device id.
     *
     * @param andr_device_id the andr device id
     */
    public void setAndr_device_id(String andr_device_id) {
        this.andr_device_id = andr_device_id;
    }




}
