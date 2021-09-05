package com.foodtogo.rider.model.signout;

/**
 * The type Signout request.
 */
public class SignoutRequest {
    /**
     * The Lang.
     */
    public String lang;
    /**
     * The Token.
     */
    public String token;
    private String andr_fcm_id;
    private String andr_device_id;
    private String type;

    public String getAndr_fcm_id() {
        return andr_fcm_id;
    }

    public void setAndr_fcm_id(String andr_fcm_id) {
        this.andr_fcm_id = andr_fcm_id;
    }

    public String getAndr_device_id() {
        return andr_device_id;
    }

    public void setAndr_device_id(String andr_device_id) {
        this.andr_device_id = andr_device_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    /**
     * Gets lang.
     *
     * @return the lang
     */
    public String getLang() {
        return lang;
    }

    /**
     * Sets lang.
     *
     * @param lang the lang
     */
    public void setLang(String lang) {
        this.lang = lang;
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


}
