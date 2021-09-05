package com.foodtogo.rider.ui.trackorder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.foodtogo.rider.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * The type Driver info window adapter.
 */
public class DriverInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;

    /**
     * Instantiates a new Driver info window adapter.
     *
     * @param mContext the m context
     */
    public DriverInfoWindowAdapter(Context mContext) {
        mWindow = LayoutInflater.from(mContext).inflate(R.layout.marker_info_window, null);
    }


    private void rendowWindowText(Marker marker, View view) {
        String title = marker.getTitle();
        TextView textView = view.findViewById(R.id.title);
        if (!title.equals("")) {
            textView.setText(title);
        }

    }

    @Override
    public View getInfoWindow(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }
}
