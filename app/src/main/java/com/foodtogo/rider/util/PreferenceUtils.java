package com.foodtogo.rider.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


/**
 * The type Preference utils.
 */
public class PreferenceUtils {

    private static final String PREF_NAME = "PEOPLE_PREFERENCES";
    private static final int MODE = Context.MODE_PRIVATE;


    /**
     * The constant USER_ID.
     */
    public static final String USER_ID = "userId";


    /**
     * Write boolean.
     *
     * @param context the context
     * @param key     the key
     * @param value   the value
     */
    public static void writeBoolean(Context context, String key, boolean value) {
        getEditor(context).putBoolean(key, value).commit();
    }

    /**
     * Read boolean boolean.
     *
     * @param context  the context
     * @param key      the key
     * @param defValue the def value
     * @return the boolean
     */
    public static boolean readBoolean(Context context, String key, boolean defValue) {
        return getPreferences(context).getBoolean(key, defValue);

    }

    /**
     * Write integer.
     *
     * @param context the context
     * @param key     the key
     * @param value   the value
     */
    public static void writeInteger(Context context, String key, int value) {
        getEditor(context).putInt(key, value).commit();

    }

    /**
     * Read integer int.
     *
     * @param context  the context
     * @param key      the key
     * @param defValue the def value
     * @return the int
     */
    public static int readInteger(Context context, String key, int defValue) {
        return getPreferences(context).getInt(key, defValue);
    }

    /**
     * Write string.
     *
     * @param context the context
     * @param key     the key
     * @param value   the value
     */
    public static void writeString(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();

    }

    /**
     * Read string string.
     *
     * @param context  the context
     * @param key      the key
     * @param defValue the def value
     * @return the string
     */
    public static String readString(Context context, String key, String defValue) {
        return getPreferences(context).getString(key, defValue);
    }

    /**
     * Write float.
     *
     * @param context the context
     * @param key     the key
     * @param value   the value
     */
    public static void writeFloat(Context context, String key, float value) {
        getEditor(context).putFloat(key, value).commit();
    }

    /**
     * Read float float.
     *
     * @param context  the context
     * @param key      the key
     * @param defValue the def value
     * @return the float
     */
    public static float readFloat(Context context, String key, float defValue) {
        return getPreferences(context).getFloat(key, defValue);
    }

    /**
     * Write long.
     *
     * @param context the context
     * @param key     the key
     * @param value   the value
     */
    public static void writeLong(Context context, String key, long value) {
        getEditor(context).putLong(key, value).commit();
    }

    /**
     * Read long long.
     *
     * @param context  the context
     * @param key      the key
     * @param defValue the def value
     * @return the long
     */
    public static long readLong(Context context, String key, long defValue) {
        return getPreferences(context).getLong(key, defValue);
    }

    /**
     * Gets preferences.
     *
     * @param context the context
     * @return the preferences
     */
    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, MODE);
    }

    /**
     * Gets editor.
     *
     * @param context the context
     * @return the editor
     */
    public static Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

}