package com.foodtogo.rider.util;

import android.annotation.SuppressLint;
import android.util.Log;

import com.foodtogo.rider.base.AppConstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * The type Conversion utils.
 */
public class ConversionUtils implements AppConstants {

    private static String FORMAT_CFM_DATA = "%.2f";

    /**
     * Md 5 format string.
     *
     * @param s the s
     * @return the string
     */
    public static String md5Format(String s) {
        StringBuffer sb = null;
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(s.getBytes());
            sb = new StringBuffer();
            for (byte anArray : array) {
                sb.append(Integer.toHexString((anArray & 0xFF) | 0x100).substring(1, 3));
            }
        } catch (java.security.NoSuchAlgorithmException e) {
            Log.e(EXCEPTION, e.toString());
        }
        assert sb != null;
        return sb.toString();
    }


    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    /**
     * Bytes to hex string.
     *
     * @param bytes the bytes
     * @return the string
     */
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String getCurrentDayOfWeek(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        return sdf.format(d);
    }


    /**
     * Hex string to byte array byte [ ].
     *
     * @param response the response
     * @return the byte [ ]
     */
    public static byte[] hexStringToByteArray(String response) {
        int len = response.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(response.charAt(i), 16) << 4)
                    + Character.digit(response.charAt(i + 1), 16));
        }
        return data;
    }

    /**
     * Gets format date time.
     *
     * @return the format date time
     */
    public static String getFormatDateTime() {
        DateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
        return spf.format(new Date());
    }

    /**
     * Parse date string.
     *
     * @param inputDateString  the input date string
     * @param inputDateFormat  the input date format
     * @param outputDateFormat the output date format
     * @return the string
     */
    public static String parseDate(String inputDateString, SimpleDateFormat inputDateFormat, SimpleDateFormat outputDateFormat) {
        Date date = null;
        String outputDateString = null;
        try {
            date = inputDateFormat.parse(inputDateString);
            outputDateString = outputDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDateString;
    }

    /**
     * Order date format string.
     *
     * @param date the date
     * @return the string
     */
    public static String orderDateFormat(String date) {
        String[] apiDate = date.split(" ");
        SimpleDateFormat ymdFormat = new SimpleDateFormat("mm/dd/yyyy");

        SimpleDateFormat ddMMMyyyy = new SimpleDateFormat("dd MMM yyyy");
        return ConversionUtils.parseDate(apiDate[0], ymdFormat, ddMMMyyyy);
    }

    /**
     * Transaction date string.
     *
     * @param date the date
     * @return the string
     */
    public static String transactionDate(String date) {
        String[] apiDate = date.split(" ");
        SimpleDateFormat ymdFormat = new SimpleDateFormat("mm/dd/yyyy");
        SimpleDateFormat ddMMMyyyy = new SimpleDateFormat("dd-mm-yyyy");
        return ConversionUtils.parseDate(apiDate[0], ymdFormat, ddMMMyyyy);
    }

    /**
     * Delivered order show date string.
     *
     * @param date the date
     * @return the string
     */
    public static String deliveredOrderShowDate(String date) {
        String[] apiDate = date.split(" ");
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-mm-dd");

        SimpleDateFormat ddMMMyyyy = new SimpleDateFormat("dd-mm-yyyy");
        return ConversionUtils.parseDate(apiDate[0], ymdFormat, ddMMMyyyy);
    }

    /**
     * Format time string.
     *
     * @param hourOfDay the hour of day
     * @param minute    the minute
     * @return the string
     */
    public static String formatTime(int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        return dateFormat.format(calendar.getTime());
    }

    /**
     * Gets format date time.
     *
     * @param dateTime the date time
     * @return the format date time
     */
    public static String getFormatDateTime(String dateTime) {
        if (dateTime != null) {
            DateFormat spf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date newDate = null;
            try {
                newDate = spf.parse(dateTime);
                spf = new SimpleDateFormat("hh:mm a");
                dateTime = spf.format(newDate);
                return dateTime;
            } catch (ParseException e) {
                return dateTime;
            }
        } else {
            return "";
        }
    }






    /**
     * Gets format date time.
     *
     * @param dateTime the date time
     * @return the format date time
     */
    public static String getFormatDate(String dateTime) {
        if (dateTime != null) {
            DateFormat spf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Date newDate = null;
            try {
                newDate = spf.parse(dateTime);
                spf = new SimpleDateFormat("dd MMM yyyy");
                dateTime = spf.format(newDate);
                return dateTime;
            } catch (ParseException e) {
                return dateTime;
            }
        } else {
            return "";
        }
    }


    public static String getFormatDay(String dateTime) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        if (dateTime != null) {
            DateFormat spf = new SimpleDateFormat("MM/dd/yyyy");
            Date newDate = null;
            try {
                newDate = spf.parse(dateTime);
                String yearStr = new SimpleDateFormat("yyyy").format(newDate);
                if(yearStr.equals(String.valueOf(year))){
                    spf = new SimpleDateFormat("dd MMM");
                }else{
                    spf = new SimpleDateFormat("dd MMM yyyy");
                }
                dateTime = spf.format(newDate);
                return dateTime;
            } catch (ParseException e) {
                return dateTime;
            }
        } else {
            return "";

        }
    }


    public static String getFormatDayTime(String dateTime) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        if (dateTime != null) {
            DateFormat spf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date newDate = null;
            try {
                newDate = spf.parse(dateTime);
                String yearStr = new SimpleDateFormat("yyyy").format(newDate);
                if(yearStr.equals(String.valueOf(year))){
                    spf = new SimpleDateFormat("dd MMM hh:mm:aa");
                }else{
                    spf = new SimpleDateFormat("dd MMM yyyy hh:mm:aa");
                }
                dateTime = spf.format(newDate);
                return dateTime;
            } catch (ParseException e) {
                return dateTime;
            }
        } else {
            return dateTime;

        }
    }


    public static String getFormatTime(String dateTime) {
        if (dateTime != null) {
            DateFormat spf = new SimpleDateFormat("HH:mm:ss");
            Date newDate = null;
            try {
                newDate = spf.parse(dateTime);
                spf = new SimpleDateFormat("hh:mm:aa");
                dateTime = spf.format(newDate);
                return dateTime;
            } catch (ParseException e) {
                return dateTime;
            }
        } else {
            return dateTime;

        }
    }

    /**
     * Gets format date time 1.
     *
     * @param dateTime the date time
     * @return the format date time 1
     */
    public static String getFormatDateTime1(String dateTime) {
        if (dateTime != null) {
            DateFormat spf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Date newDate = null;
            try {
                newDate = spf.parse(dateTime);
                spf = new SimpleDateFormat("dd MMM yy, hh:mm a");
                dateTime = spf.format(newDate);
                return dateTime;
            } catch (ParseException e) {
                return dateTime;
            }
        } else {
            return dateTime;
        }
    }

    public static Float removeComma(String price){
        String newValue=price;
        if(price.contains(",")){
            newValue=price.replaceAll(",","");
        }
        return Float.valueOf(newValue);
    }


}
