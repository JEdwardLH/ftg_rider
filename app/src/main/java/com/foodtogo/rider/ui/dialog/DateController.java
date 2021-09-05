package com.foodtogo.rider.ui.dialog;

import androidx.fragment.app.FragmentManager;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * The type Date controller.
 */
public class DateController implements DatePickerDialog.OnDateSetListener {

    /**
     * The Dpd.
     */
    DatePickerDialog dpd;

    /**
     * The Fragment manager.
     */
    FragmentManager fragmentManager;

    /**
     * Instantiates a new Date controller.
     *
     * @param fragmentManager the fragment manager
     */
    public DateController(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        dpd = DatePickerDialog.newInstance(this);
    }


    /**
     * The interface Listener.
     */
    public interface Listener {
        /**
         * On select date.
         *
         * @param date the date
         */
        void onSelectDate(String date);
    }

    /**
     * The Listener.
     */
    Listener listener;

    /**
     * Show.
     *
     * @param mindate  the mindate
     * @param maxdate  the maxdate
     * @param listener the listener
     */
    void show(String mindate, String maxdate, Listener listener) {
        this.listener = listener;
        Calendar calMin = getCalendar(mindate);
        Calendar calMax = getCalendar(maxdate);

        Calendar now;

        if (calMin != null) {
            now = calMin;
        } else if (calMax != null) {
            now = calMax;
        } else {
            now = Calendar.getInstance();
        }

        dpd.initialize(this, now);

        if (calMin != null)
            dpd.setMinDate(calMin);
        else {
            Calendar c1 = Calendar.getInstance();
            c1.set(Calendar.YEAR, c1.get(Calendar.YEAR) - 10);
            dpd.setMinDate(c1);
        }
        if (calMax != null)
            dpd.setMaxDate(calMax);
        else {
            Calendar c1 = Calendar.getInstance();
            dpd.setMaxDate(c1);
        }

        dpd.show(fragmentManager, "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.YEAR, year);
        now.set(Calendar.MONTH, monthOfYear);
        now.set(Calendar.DATE, dayOfMonth);

        listener.onSelectDate(getServerDate(now));
    }

    /**
     * The Date format server.
     */
    final String DATE_FORMAT_SERVER = "yyyy-MM-dd";

    /**
     * Gets server date.
     *
     * @param calendar the calendar
     * @return the server date
     */
    String getServerDate(Calendar calendar) {
        return calendar == null ? null : new SimpleDateFormat(DATE_FORMAT_SERVER).format(calendar.getTime());
    }

    /**
     * Gets calendar.
     *
     * @param date the date
     * @return the calendar
     */
    Calendar getCalendar(String date) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_SERVER, Locale.ENGLISH);
        try {
            cal.setTime(sdf.parse(date));
            return cal;
        } catch (NullPointerException e) {
            return null;
        } catch (ParseException e) {
            return null;
        }
    }
}
