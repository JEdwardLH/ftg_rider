package com.foodtogo.rider.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.foodtogo.rider.R;
import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys.CURRENT_CITY;
import static com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys.LATITUDE;
import static com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys.LONGITUDE;

/**
 * The type Location finder.
 */
public class LocationFinder {
    /**
     * The constant MIN_DISTANCE_CHANGE_FOR_UPDATES.
     */
    public static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    /**
     * The constant MIN_TIME_BW_UPDATES.
     */
    public static final long MIN_TIME_BW_UPDATES = 1000 * 60;
    /**
     * The constant MIN_TIME_BW_LAYOUT_UPDATE.
     */
    public static final long MIN_TIME_BW_LAYOUT_UPDATE = 1000 * 5;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;


    /**
     * Find un asked permissions array list.
     *
     * @param wanted  the wanted
     * @param context the context
     * @return the array list
     */
    public static ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted, Context context) {
        ArrayList result = new ArrayList();

        for (String perm : wanted) {
            if (!hasPermission(perm, context)) {
                result.add(perm);
            }
        }

        return result;
    }


    /**
     * Has permission boolean.
     *
     * @param permission the permission
     * @param context    the context
     * @return the boolean
     */
    public static boolean hasPermission(String permission, Context context) {
        if (canAskPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return checkPermission(permission, context);
            }
        }
        return true;
    }

    private static boolean canAskPermission() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    private static boolean checkPermission(String permission, Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            int result = ContextCompat.checkSelfPermission(context, permission);
            if (result == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * Update ui.
     *
     * @param latitude  the latitude
     * @param longitude the longitude
     * @param context   the context
     */
    public static void updateUI(String latitude, String longitude, Context context) {
        try {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(context, Locale.getDefault());
            PreferenceUtils.writeString(context, LATITUDE, latitude);
            PreferenceUtils.writeString(context, LONGITUDE, longitude);
            try {
                addresses = geocoder.getFromLocation(Double.valueOf(latitude), Double.valueOf(longitude), 1);
                String city = addresses.get(0).getLocality();
                PreferenceUtils.writeString(context, CURRENT_CITY, city);
                System.out.println("city" + city);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * tracking location permissions
     *
     * @param context the context
     * @return the boolean
     */
    public static boolean checkLocationPermissions(Context context) {
        int permissionState = ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Request location permissions.
     *
     * @param context the context
     * @param view    the view
     */
    public static void requestLocationPermissions(Activity context, View view) {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(context,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i("LocationTracking", "Displaying permission rationale to provide additional context.");
            Snackbar.make(view,
                    R.string.permission_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, view1 -> {
                        // Request permission
                        ActivityCompat.requestPermissions(context,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                REQUEST_PERMISSIONS_REQUEST_CODE);
                    })
                    .show();
        } else {
            Log.i("LocationTracking", "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(context,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    /**
     * Gets location result text.
     *
     * @param locations the locations
     * @return the location result text
     */
    public static LatLng getLocationResultText(List<Location> locations) {
        LatLng driverLatLng = null;
        if (!locations.isEmpty()) {
            for (Location location : locations) {
                driverLatLng = new LatLng(location.getLatitude(), location.getLongitude());
            }
        }
        return driverLatLng;
    }


    /**
     * Download url string.
     *
     * @param strUrl the str url
     * @return the string
     * @throws IOException the io exception
     */
    public static String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuilder sb = new StringBuilder();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    /**
     * Gets distance url.
     *
     * @param origin  the origin
     * @param dest    the dest
     * @param context the context
     * @return the distance url
     */
    public static String getDistanceUrl(LatLng origin, LatLng dest, Context context) {
        //https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=11.01292094,76.96531442&destinations=11.0168445,76.95583209999995&key=AIzaSyBg5e4lx9fS1voiwnPjJ8YkjISFt7-sbfU
        // Origin of route
        String str_origin = "origins=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String unit = "units=metric";
        String str_dest = "destinations=" + dest.latitude + "," + dest.longitude;
        //String mode = "mode=driving";
        String key = "key=";
        String language = "language=en";

        // Building the parameters to the web service
        String parameters = unit + "&" + str_origin + "&" + str_dest + "&" + key + context.getString(R.string.google_maps_key);

        // Output format
        String output = "json";

        // Building the url to the web service

        String url = "https://maps.googleapis.com/maps/api/distancematrix/" + output + "?" + parameters;

        System.out.println("url:" + url);
        return url;
    }


    public static int[] getFormattedDuration(String timeItems) {
        int[] total = {0, 0, 0}; // days, hours, minutes
        if (timeItems.contains("day ")) {
            total[0]++;
        } else if (timeItems.contains("days")) {
            total[0] += Integer.valueOf(timeItems.substring(0, timeItems.indexOf(" days")));
        }
        if (timeItems.contains("hour ")) {
            total[1]++;
        } else if (timeItems.contains("hours")) {
            if (timeItems.indexOf(" hours") <= 3) {
                total[1] += Integer.valueOf(timeItems.substring(0, timeItems.indexOf(" hours")));
            } else {
                try {
                    if (timeItems.contains("days")) {
                        total[1] += Integer.valueOf(timeItems.substring(timeItems.lastIndexOf("days ")) + 5, timeItems.indexOf(" hours"));
                    } else {
                        total[1] += Integer.valueOf(timeItems.substring(timeItems.lastIndexOf("day ")) + 4, timeItems.indexOf(" hours"));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        if (timeItems.contains("min ")) {
            total[2]++;
        } else if (timeItems.contains("mins")) {
            if (timeItems.indexOf(" mins") <= 3) {
                total[2] += Integer.valueOf(timeItems.substring(0, timeItems.indexOf(" mins")));
            } else {
                if (timeItems.contains("hours")) {
                    total[2] += Integer.valueOf(timeItems.substring(timeItems.indexOf("hours ") + 6, timeItems.indexOf(" mins")));
                } else {
                    total[2] += Integer.valueOf(timeItems.substring(timeItems.indexOf("hour ") + 5, timeItems.indexOf(" mins")));
                }
            }
        }
        return total;
    }

    public static long convertSecondsToHour(long seconds) {
        try {
            int day = (int) TimeUnit.SECONDS.toDays(seconds);
            return (TimeUnit.SECONDS.toHours(seconds) - (day * 24)) + (day * 24);
        } catch (Exception e) {
            return 0;
        }
    }
    public static long convertSecondsToMin(long seconds) {
        try {
            int day = (int) TimeUnit.SECONDS.toDays(seconds);
            return TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
        } catch (Exception e) {
            return 0;
        }
    }
    public static long convertSecondsTotalMin(long seconds) {
        try {
            int day = (int) TimeUnit.SECONDS.toDays(seconds);
            long totalHours = (TimeUnit.SECONDS.toHours(seconds) - (day * 24)) + (day * 24);
            long minutes = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
            return (totalHours * 60) + minutes;
        } catch (Exception e) {
            return 0;
        }
    }






}
