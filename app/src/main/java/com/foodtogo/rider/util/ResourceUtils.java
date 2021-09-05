package com.foodtogo.rider.util;

import com.foodtogo.rider.base.BaseApplication;

/**
 * The type Resource utils.
 */
public class ResourceUtils {

    /**
     * Gets color.
     *
     * @param resource the resource
     * @return the color
     */
    public static int getColor(int resource) {
        return BaseApplication.getContext().getResources().getColor(resource);
    }

    /**
     * Gets string.
     *
     * @param resource the resource
     * @return the string
     */
    public static String getString(int resource) {
        return BaseApplication.getContext().getResources().getString(resource);
    }

    /**
     * Gets boolean.
     *
     * @param resource the resource
     * @return the boolean
     */
    public static boolean getBoolean(int resource) {
        return BaseApplication.getContext().getResources().getBoolean(resource);
    }

    /**
     * Gets string.
     *
     * @param resource   the resource
     * @param formatArgs the format args
     * @return the string
     */
    public static String getString(int resource, Object... formatArgs) {
        return BaseApplication.getContext().getResources().getString(resource, formatArgs);
    }

    /**
     * Gets int.
     *
     * @param resource the resource
     * @return the int
     */
    public static int getInt(int resource) {
        return BaseApplication.getContext().getResources().getInteger(resource);
    }

}
