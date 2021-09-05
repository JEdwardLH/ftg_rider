package com.foodtogo.rider.model.changepassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Change password request.
 */
public class ChangePasswordRequest {
    @SerializedName("old_password")
    @Expose
    private String oldPassword;
    @SerializedName("new_password")
    @Expose
    private String newPassword;
    @SerializedName("lang")
    @Expose
    private String lang;

    /**
     * Gets old password.
     *
     * @return the old password
     */
    public String getOldPassword() {
        return oldPassword;
    }

    /**
     * Sets old password.
     *
     * @param oldPassword the old password
     */
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    /**
     * Gets new password.
     *
     * @return the new password
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * Sets new password.
     *
     * @param newPassword the new password
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
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
}
