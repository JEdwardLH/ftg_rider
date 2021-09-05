
package com.foodtogo.rider.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.foodtogo.rider.R;
import com.foodtogo.rider.base.BaseApplication;

import java.util.Arrays;
import java.util.List;

/**
 * The type Base response.
 *
 * @param <T> the type parameter
 */
public class BaseResponse<T> {

    /**
     * The constant SUCCESS.
     */
    public static final int SUCCESS = 200;

    /**
     * The Token expired.
     */
    List<String> TOKEN_EXPIRED = Arrays.asList("Token is Invalid", "Token is Expired");
    /**
     * The No item.
     */
    List<String> NO_ITEM = Arrays.asList("No records found!", "No records Found");

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    private T data;

    /**
     * Gets data.
     *
     * @return the data
     */
    public T getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * Sets code.
     *
     * @param code the code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }


    /**
     * Is success boolean.
     *
     * @return the boolean
     */
    public boolean isSuccess() {
        return code == SUCCESS || isNoItem()/* || isTokenExpired()*/;
    }

    /**
     * Is token expired boolean.
     *
     * @return the boolean
     */
    public boolean isTokenExpired() {
        return TOKEN_EXPIRED.contains(message);
    }

    /**
     * Is no item boolean.
     *
     * @return the boolean
     */
    public boolean isNoItem() {
        System.out.println(message +":" +BaseApplication.getContext().getString(R.string.no_record_found));
        return message.equals(BaseApplication.getContext().getString(R.string.no_record_found));
    }

}
