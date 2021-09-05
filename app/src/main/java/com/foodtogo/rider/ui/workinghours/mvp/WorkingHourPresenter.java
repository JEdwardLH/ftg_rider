package com.foodtogo.rider.ui.workinghours.mvp;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.foodtogo.rider.R;
import com.foodtogo.rider.data.rest.ApiClient;
import com.foodtogo.rider.data.rest.ApiInterface;
import com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys;
import com.foodtogo.rider.model.Request;
import com.foodtogo.rider.model.workinghours.WorkingHoursRequest;
import com.foodtogo.rider.model.workinghours.getworkinghour.GetWorkingHourResponse;
import com.foodtogo.rider.util.ConversionUtils;
import com.foodtogo.rider.util.PreferenceUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.foodtogo.rider.util.CommonStrings.HOUR;
import static com.foodtogo.rider.util.CommonStrings.MIN;

/**
 * The type Working hour presenter.
 */
public class WorkingHourPresenter implements WorkingHourContractor.Presenter, PreferenceKeys {
    private WorkingHourContractor.View mView;
    private WorkingHourModal model;
    private ApiInterface apiInterface;
    private Context context;
    private String initialTime = "00:00";
    private String one = "1";
    private String zero = "0";

    /**
     * Instantiates a new Working hour presenter.
     *
     * @param view     the view
     * @param mContext the m context
     */
    public WorkingHourPresenter(WorkingHourContractor.View view, Context mContext) {
        mView = view;
        model = new WorkingHourModal(this, mContext);
        this.context = mContext;
        apiInterface = ApiClient.getApiInterface();

    }

    @Override
    public void showTimePicker(Button btn) {
        int mHour = hourToShowTime(btn);
        int mMinute = minToShowTime(btn);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,R.style.AppCompatAlertDialogStyle,
                (view, hourOfDay, minute) -> {
                    saveAllDaysFromValue(String.valueOf(hourOfDay), String.valueOf(minute), btn);
                    if (ifItFromToPicker(btn.getTag())) {
                        checkTimeIsGreaterOrNot(btn, getToTimeByKey(btn, "from"), getToTimeByKey(btn, "to"), ConversionUtils.formatTime(hourOfDay, minute));
                    } else {
                        mView.bindTimeValue(btn, ConversionUtils.formatTime(hourOfDay, minute));
                    }


                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


    @Override
    public void setVisible(Button from, Button to, boolean isChecked) {
        if (isChecked) {
            from.setVisibility(View.INVISIBLE);
            to.setVisibility(View.INVISIBLE);
        } else {
            from.setVisibility(View.VISIBLE);
            to.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void updateWorkingHour(CheckBox[] arr) {
        mView.showLoadingView();
        WorkingHoursRequest workingRequest = new WorkingHoursRequest();
        workingRequest.setLang(PreferenceUtils.readString(context, LANGUAGE, "en"));
        //sun
        workingRequest.setSunStatus(!arr[0].isChecked() ? one : zero);
        workingRequest.setSunFrom(!arr[0].isChecked() ? getPreference(SUN_FROM) : initialTime);
        workingRequest.setSunTo(!arr[0].isChecked() ? getPreference(SUN_TO) : initialTime);
        //mon
        workingRequest.setMonStatus(!arr[1].isChecked() ? one : zero);
        workingRequest.setMonFrom(!arr[1].isChecked() ? getPreference(MON_FROM) : initialTime);
        workingRequest.setMonTo(!arr[1].isChecked() ? getPreference(MON_TO) : initialTime);
        //TUE
        workingRequest.setTueStatus(!arr[2].isChecked() ? one : zero);
        workingRequest.setTueFrom(!arr[2].isChecked() ? getPreference(TUE_FROM) : initialTime);
        workingRequest.setTueTo(!arr[2].isChecked() ? getPreference(TUE_TO) : initialTime);
        //wed
        workingRequest.setWedStatus(!arr[3].isChecked() ? one : zero);
        workingRequest.setWedFrom(!arr[3].isChecked() ? getPreference(WED_FROM) : initialTime);
        workingRequest.setWedTo(!arr[3].isChecked() ? getPreference(WED_TO) : initialTime);
        //thu
        workingRequest.setThuStatus(!arr[4].isChecked() ? one : zero);
        workingRequest.setThuFrom(!arr[4].isChecked() ? getPreference(THU_FROM) : initialTime);
        workingRequest.setThuTo(!arr[4].isChecked() ? getPreference(THU_TO) : initialTime);
        //fri
        workingRequest.setFriStatus(!arr[5].isChecked() ? one : zero);
        workingRequest.setFriFrom(!arr[5].isChecked() ? getPreference(FRI_FROM) : initialTime);
        workingRequest.setFriTo(!arr[5].isChecked() ? getPreference(FRI_TO) : initialTime);
        //sat
        workingRequest.setSatStatus(!arr[6].isChecked() ? one : zero);
        workingRequest.setSatFrom(!arr[6].isChecked() ? getPreference(SAT_FROM) : initialTime);
        workingRequest.setSatTo(!arr[6].isChecked() ? getPreference(SAT_TO) : initialTime);
        model.requestToUpload(workingRequest);


    }

    @Override
    public void WorkingHourError(String error) {
        mView.hideLoadingView();
        mView.showError(error);
    }

    @Override
    public void WorkingHourError(int error) {
        mView.hideLoadingView();
        mView.showError(error);
    }

    @Override
    public void updateSuccess(String msg) {
        mView.hideLoadingView();
        mView.showSnack(msg);
        mView.checkDocumentStatus();
    }

    @Override
    public void close() {
        model.close();
    }

    @Override
    public void getWorkingHourData() {
        mView.showLoadingView();
        Request request = new Request();
        request.setLang(PreferenceUtils.readString(context, LANGUAGE, "en"));
        model.requestToGetWorkingHours(request);
    }

    @Override
    public void getDataSuccess(GetWorkingHourResponse response, String msg) {
        mView.hideLoadingView();
        mView.getWhData(response, msg);
    }

    @Override
    public void loggedInByOtherError(String msg) {
        mView.hideLoadingView();
        mView.showLoggedInByAnother(msg);
    }


    private void checkTimeIsGreaterOrNot(Button btn, String fromTime, String toTime, String showingTime) {
        System.out.println("fromTime" + fromTime + ":" + toTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        try {
            if (dateFormat.parse(toTime).after(dateFormat.parse(fromTime))) {
                mView.bindTimeValue(btn, showingTime);
            } else {
                mView.showSnack(context.getString(R.string.to_time_msg));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets preference.
     *
     * @param key the key
     * @return the preference
     */
    String getPreference(String key) {
        return PreferenceUtils.readString(context, key, "00:00");
    }

    /**
     * Write preference.
     *
     * @param key   the key
     * @param value the value
     */
    void writePreference(String key, String value) {
        PreferenceUtils.writeString(context, key, value);
    }


    /**
     * Save all days from value.
     *
     * @param hourOfDay the hour of day
     * @param min       the min
     * @param btn       the btn
     */
    public void saveAllDaysFromValue(String hourOfDay, String min, Button btn) {
        hourOfDay = hourOfDay.length() == 1 ? "0" + hourOfDay : hourOfDay;
        min = min.length() == 1 ? "0" + min : min;
        String time = hourOfDay + ":" + min;
        if (btn.getTag().equals(SUN_FROM)) {
            writePreference(SUN_FROM, time);
        } else if (btn.getTag().equals(MON_FROM)) {
            writePreference(MON_FROM, time);
        } else if (btn.getTag().equals(TUE_FROM)) {
            writePreference(TUE_FROM, time);
        } else if (btn.getTag().equals(WED_FROM)) {
            writePreference(WED_FROM, time);
        } else if (btn.getTag().equals(THU_FROM)) {
            writePreference(THU_FROM, time);
        } else if (btn.getTag().equals(FRI_FROM)) {
            writePreference(FRI_FROM, time);
        } else if (btn.getTag().equals(SAT_FROM)) {
            writePreference(SAT_FROM, time);
        } else if (btn.getTag().equals(SUN_TO)) {
            writePreference(SUN_TO, time);
        } else if (btn.getTag().equals(MON_TO)) {
            writePreference(MON_TO, time);
        } else if (btn.getTag().equals(TUE_TO)) {
            writePreference(TUE_TO, time);
        } else if (btn.getTag().equals(WED_TO)) {
            writePreference(WED_TO, time);
        } else if (btn.getTag().equals(THU_TO)) {
            writePreference(THU_TO, time);
        } else if (btn.getTag().equals(FRI_TO)) {
            writePreference(FRI_TO, time);
        } else if (btn.getTag().equals(SAT_TO)) {
            writePreference(SAT_TO, time);
        }
    }


    private String getToTimeByKey(Button btn, String btnFrom) {
        String toTime = "00:00";
        String fromTime = "00:00";
        if (btn.getTag().equals(SUN_TO)) {
            toTime = getPreference(SUN_TO);
            fromTime = getPreference(SUN_FROM);
        } else if (btn.getTag().equals(MON_TO)) {
            toTime = getPreference(MON_TO);
            fromTime = getPreference(MON_FROM);
        } else if (btn.getTag().equals(TUE_TO)) {
            toTime = getPreference(TUE_TO);
            fromTime = getPreference(TUE_FROM);
        } else if (btn.getTag().equals(WED_TO)) {
            toTime = getPreference(WED_TO);
            fromTime = getPreference(WED_FROM);
        } else if (btn.getTag().equals(THU_TO)) {
            toTime = getPreference(THU_TO);
            fromTime = getPreference(THU_FROM);
        } else if (btn.getTag().equals(FRI_TO)) {
            toTime = getPreference(FRI_TO);
            fromTime = getPreference(FRI_FROM);
        } else if (btn.getTag().equals(SAT_TO)) {
            toTime = getPreference(SAT_TO);
            fromTime = getPreference(SAT_FROM);
        }
        if (btnFrom.equals("from")) {
            return fromTime;
        } else {
            return toTime;
        }

    }

    private boolean ifItFromToPicker(Object tag) {
        return tag.equals(SUN_TO) || tag.equals(MON_TO) || tag.equals(TUE_TO) || tag.equals(WED_TO) || tag.equals(THU_TO)
                || tag.equals(FRI_TO) || tag.equals(SAT_TO);

    }


    /**
     * Hour to show time int.
     *
     * @param btn the btn
     * @return the int
     */
    int hourToShowTime(Button btn) {
        int hour = 0;
        if (btn.getTag().equals(SUN_FROM)) {
            hour = getHour(SUN_FROM, HOUR);
        } else if (btn.getTag().equals(MON_FROM)) {
            hour = getHour(MON_FROM, HOUR);
        } else if (btn.getTag().equals(TUE_FROM)) {
            hour = getHour(TUE_FROM, HOUR);
        } else if (btn.getTag().equals(WED_FROM)) {
            hour = getHour(WED_FROM, HOUR);
        } else if (btn.getTag().equals(THU_FROM)) {
            hour = getHour(THU_FROM, HOUR);
        } else if (btn.getTag().equals(FRI_FROM)) {
            hour = getHour(FRI_FROM, HOUR);
        } else if (btn.getTag().equals(SAT_FROM)) {
            hour = getHour(SAT_FROM, HOUR);
        } else if (btn.getTag().equals(SUN_TO)) {
            hour = getHour(SUN_TO, HOUR);
        } else if (btn.getTag().equals(MON_TO)) {
            hour = getHour(MON_TO, HOUR);
        } else if (btn.getTag().equals(TUE_TO)) {
            hour = getHour(TUE_TO, HOUR);
        } else if (btn.getTag().equals(WED_TO)) {
            hour = getHour(WED_TO, HOUR);
        } else if (btn.getTag().equals(THU_TO)) {
            hour = getHour(THU_TO, HOUR);
        } else if (btn.getTag().equals(FRI_TO)) {
            hour = getHour(FRI_TO, HOUR);
        } else if (btn.getTag().equals(SAT_TO)) {
            hour = getHour(SAT_TO, HOUR);
        }
        return hour;
    }


    /**
     * Min to show time int.
     *
     * @param btn the btn
     * @return the int
     */
    int minToShowTime(Button btn) {
        int min = 0;
        if (btn.getTag().equals(SUN_FROM)) {
            min = getHour(SUN_FROM, MIN);
        } else if (btn.getTag().equals(MON_FROM)) {
            min = getHour(MON_FROM, MIN);
        } else if (btn.getTag().equals(TUE_FROM)) {
            min = getHour(TUE_FROM, MIN);
        } else if (btn.getTag().equals(WED_FROM)) {
            min = getHour(WED_FROM, MIN);
        } else if (btn.getTag().equals(THU_FROM)) {
            min = getHour(THU_FROM, MIN);
        } else if (btn.getTag().equals(FRI_FROM)) {
            min = getHour(FRI_FROM, MIN);
        } else if (btn.getTag().equals(SAT_FROM)) {
            min = getHour(SAT_FROM, MIN);
        } else if (btn.getTag().equals(SUN_TO)) {
            min = getHour(SUN_TO, MIN);
        } else if (btn.getTag().equals(MON_TO)) {
            min = getHour(MON_TO, MIN);
        } else if (btn.getTag().equals(TUE_TO)) {
            min = getHour(TUE_TO, MIN);
        } else if (btn.getTag().equals(WED_TO)) {
            min = getHour(WED_TO, MIN);
        } else if (btn.getTag().equals(THU_TO)) {
            min = getHour(THU_TO, MIN);
        } else if (btn.getTag().equals(FRI_TO)) {
            min = getHour(FRI_TO, MIN);
        } else if (btn.getTag().equals(SAT_TO)) {
            min = getHour(SAT_TO, MIN);
        }
        return min;
    }


    /**
     * Gets hour.
     *
     * @param key       the key
     * @param hourOrMin the hour or min
     * @return the hour
     */
    int getHour(String key, String hourOrMin) {
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        if (getPreference(key) != null && !getPreference(key).equals("00:00")) {
            String hourMin[] = getPreference(key).split(":");
            if (hourOrMin.equals(HOUR)) {
                mHour = Integer.parseInt(hourMin[0]);
            } else {
                mMinute = Integer.parseInt(hourMin[1]);
                mHour = mMinute;
            }
        } else {
            mHour = hourOrMin.equals(HOUR) ? mHour : mMinute;
        }
        return mHour;
    }


}
