package com.foodtogo.rider.ui.workinghours.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.foodtogo.rider.R;
import com.foodtogo.rider.base.BaseActivity;
import com.foodtogo.rider.base.FragmentActivity;
import com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys;
import com.foodtogo.rider.model.workinghours.getworkinghour.Friday;
import com.foodtogo.rider.model.workinghours.getworkinghour.GetWorkingHourResponse;
import com.foodtogo.rider.model.workinghours.getworkinghour.Monday;
import com.foodtogo.rider.model.workinghours.getworkinghour.Saturday;
import com.foodtogo.rider.model.workinghours.getworkinghour.Sunday;
import com.foodtogo.rider.model.workinghours.getworkinghour.Thursday;
import com.foodtogo.rider.model.workinghours.getworkinghour.Tuesday;
import com.foodtogo.rider.model.workinghours.getworkinghour.Wednesday;
import com.foodtogo.rider.model.workinghours.getworkinghour.WorkingHour;
import com.foodtogo.rider.ui.dashboard.activity.DashboardActivity;
import com.foodtogo.rider.ui.login.activity.Login;
import com.foodtogo.rider.ui.workinghours.mvp.WorkingHourContractor;
import com.foodtogo.rider.ui.workinghours.mvp.WorkingHourPresenter;
import com.foodtogo.rider.util.ConversionUtils;
import com.foodtogo.rider.util.PreferenceUtils;
import com.foodtogo.rider.util.ViewUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.foodtogo.rider.data.rest.ApiKeys.AVAILABLE;
import static com.foodtogo.rider.util.CommonStrings.FROM_EDIT_PROFILE;

/**
 * The type Working hours.
 */
public class WorkingHours extends BaseActivity implements WorkingHourContractor.View, View.OnClickListener, PreferenceKeys, CompoundButton.OnCheckedChangeListener {
    /**
     * The Sun from btn.
     */
    @BindView(R.id.sun_from)
    Button sun_from_btn;
    /**
     * The Sun to btn.
     */
    @BindView(R.id.sun_to)
    Button sun_to_btn;
    /**
     * The M sun chk.
     */
    @BindView(R.id.sun_status_chk)
    CheckBox mSunChk;
    /**
     * The Mon from btn.
     */
    @BindView(R.id.mon_from)
    Button mon_from_btn;
    /**
     * The Mon to btn.
     */
    @BindView(R.id.mon_to)
    Button mon_to_btn;
    /**
     * The M mon chk.
     */
    @BindView(R.id.mon_status_chk)
    CheckBox mMonChk;
    /**
     * The Tue from btn.
     */
    @BindView(R.id.tue_from)
    Button tue_from_btn;
    /**
     * The Tue to btn.
     */
    @BindView(R.id.tue_to)
    Button tue_to_btn;
    /**
     * The M tue chk.
     */
    @BindView(R.id.tue_status_chk)
    CheckBox mTueChk;
    /**
     * The Wed from btn.
     */
    @BindView(R.id.wed_from)
    Button wed_from_btn;
    /**
     * The Wed to btn.
     */
    @BindView(R.id.wed_to)
    Button wed_to_btn;
    /**
     * The M wed chk.
     */
    @BindView(R.id.wed_status_chk)
    CheckBox mWedChk;
    /**
     * The Thu from btn.
     */
    @BindView(R.id.thu_from)
    Button thu_from_btn;
    /**
     * The Thu to btn.
     */
    @BindView(R.id.thu_to)
    Button thu_to_btn;
    /**
     * The M thu chk.
     */
    @BindView(R.id.thu_status_chk)
    CheckBox mThuChk;
    /**
     * The Fri from btn.
     */
    @BindView(R.id.fri_from)
    Button fri_from_btn;
    /**
     * The Fri to btn.
     */
    @BindView(R.id.fri_to)
    Button fri_to_btn;
    /**
     * The M fri chk.
     */
    @BindView(R.id.fri_status_chk)
    CheckBox mFriChk;
    /**
     * The Sat from btn.
     */
    @BindView(R.id.sat_from)
    Button sat_from_btn;
    /**
     * The Sat to btn.
     */
    @BindView(R.id.sat_to)
    Button sat_to_btn;
    /**
     * The M sat chk.
     */
    @BindView(R.id.sat_status_chk)
    CheckBox mSatChk;
    /**
     * The Layout.
     */
    @BindView(R.id.inner_container)
    ConstraintLayout layout;
    /**
     * The Update.
     */
    @BindView(R.id.update)
    Button update;

    /**
     * The Presenter.
     */
    WorkingHourPresenter presenter;
    /**
     * The .
     */
    int i = 0;
    /**
     * The Is show info to user.
     */
    boolean isShowInfoToUser = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupToolBar();
        setTitle(getResources().getString(R.string.working_hour));
        presenter = new WorkingHourPresenter(this, WorkingHours.this);
        bindClickListener();
        bindCheckChange();
        if (isNetworkConnected()) {
            presenter.getWorkingHourData();
        } else {
            ViewUtils.showSnackBar(layout, getString(R.string.no_internet_connection));
        }
        getIntentDetails();

        /**
         * working hour update status if delivery boy not updated working hour
         * */
        if (isShowInfoToUser) {
            String title = "Update Working Hour";
            String msg = "To get orders, working hours need to update";
            showPopup(title, msg);
        }
        toolbar.setTitleCentered(true);
    }

    private void showPopup(String title, String msg) {
        new AlertDialog.Builder(WorkingHours.this,R.style.AppCompatAlertDialogStyle)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("ok", (dialog, which) -> {
                    dialog.dismiss();
                    if (title.equals("Upload document")) {
                        changeActivity(FragmentActivity.createIntent(this, FragmentActivity.OPEN_PROFILE));
                    }
                }).show();
    }

    /**
     * Gets intent details.
     */
    void getIntentDetails() {
        if (getIntent() != null && getIntent().hasExtra("from")) {
            if (getIntent().getStringExtra("from").equals(FROM_EDIT_PROFILE)) {
                isShowInfoToUser = false;
            }
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_working_hours;
    }

    @Override
    public void showLoadingView() {
        showProgress();
    }

    @Override
    public void hideLoadingView() {
        hideProgress();
    }

    @Override
    public void showError(String message) {
        showSnack(message);
    }

    @Override
    public void showError(int message) {
        changeActivityClearBackStack(Login.class);
    }

    @Override
    public void showLoggedInByAnother(String message) {
        showLoggedInByAnotherInfo(message);
    }

    @Override
    public void bindTimeValue(Button btn, String time) {
        btn.setText(time);
    }

    @Override
    public void showSnack(String msg) {
        ViewUtils.showSnackBar(layout, msg);
    }

    @Override
    public void getWhData(GetWorkingHourResponse response, String msg) {
        List<WorkingHour> resultArr = response.getWorkingHours();

        if (resultArr.size() > 0) {
            for (int i = 0; i < resultArr.size(); i++) {
                WorkingHour wHour = resultArr.get(i);
                if (i == 0) {
                    Monday mon = wHour.getMonday();
                    PreferenceUtils.writeString(context, MON_FROM, mon.getFormTime());
                    PreferenceUtils.writeString(context, MON_TO, mon.getToTime());
                    checkAvailableOrNot(mon.getStatus(), mon.getFormTime(), mon.getToTime(), mon_from_btn, mon_to_btn, mMonChk);
                } else if (i == 1) {
                    Tuesday tue = wHour.getTuesday();
                    PreferenceUtils.writeString(context, TUE_FROM, tue.getFormTime());
                    PreferenceUtils.writeString(context, TUE_TO, tue.getToTime());
                    checkAvailableOrNot(tue.getStatus(), tue.getFormTime(), tue.getToTime(), tue_from_btn, tue_to_btn, mTueChk);
                } else if (i == 2) {
                    Wednesday wed = wHour.getWednesday();
                    PreferenceUtils.writeString(context, WED_FROM, wed.getFormTime());
                    PreferenceUtils.writeString(context, WED_TO, wed.getToTime());
                    checkAvailableOrNot(wed.getStatus(), wed.getFormTime(), wed.getToTime(), wed_from_btn, wed_to_btn, mWedChk);
                } else if (i == 3) {
                    Thursday thu = wHour.getThursday();
                    PreferenceUtils.writeString(context, THU_FROM, thu.getFormTime());
                    PreferenceUtils.writeString(context, THU_TO, thu.getToTime());
                    checkAvailableOrNot(thu.getStatus(), thu.getFormTime(), thu.getToTime(), thu_from_btn, thu_to_btn, mThuChk);
                } else if (i == 4) {
                    Friday fri = wHour.getFriday();
                    PreferenceUtils.writeString(context, FRI_FROM, fri.getFormTime());
                    PreferenceUtils.writeString(context, FRI_TO, fri.getToTime());
                    checkAvailableOrNot(fri.getStatus(), fri.getFormTime(), fri.getToTime(), fri_from_btn, fri_to_btn, mFriChk);
                } else if (i == 5) {
                    Saturday sat = wHour.getSaturday();
                    PreferenceUtils.writeString(context, SAT_FROM, sat.getFormTime());
                    PreferenceUtils.writeString(context, SAT_TO, sat.getToTime());
                    checkAvailableOrNot(sat.getStatus(), sat.getFormTime(), sat.getToTime(), sat_from_btn, sat_to_btn, mSatChk);
                } else if (i == 6) {
                    Sunday sun = wHour.getSunday();
                    PreferenceUtils.writeString(context, SUN_FROM, sun.getFormTime());
                    PreferenceUtils.writeString(context, SUN_TO, sun.getToTime());
                    checkAvailableOrNot(sun.getStatus(), sun.getFormTime(), sun.getToTime(), sun_from_btn, sun_to_btn, mSunChk);
                }

            }
        }
        ViewUtils.showSnackBar(layout, msg);
    }

    @Override
    public void checkDocumentStatus() {
        appRepository.setWorkingStatus("Updated");
        if (!appRepository.getUploadDocumentStatus().equals("Updated")) {
            String title = getResources().getString(R.string.upload_document);
            String msg = getResources().getString(R.string.proof_add);
            showPopup(title, msg);
        } else {
            changeActivity(DashboardActivity.class);
        }
    }


    /**
     * Check available or not.
     *
     * @param availableStatus the available status
     * @param fromTime        the from time
     * @param toTime          the to time
     * @param fromBtn         the from btn
     * @param toBtn           the to btn
     *
     * @param checkBox        the check box
     */
    void checkAvailableOrNot(String availableStatus, String fromTime, String toTime, Button fromBtn, Button toBtn, CheckBox checkBox) {
      System.out.println("availableStatus:"+availableStatus);
        if (availableStatus.equals(getString(R.string.available))) {
            setFromAndToTime(fromBtn, toBtn, fromTime, toTime);
        } else {
            checkBox.setChecked(true);
            fromBtn.setVisibility(View.INVISIBLE);
            toBtn.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Sets from and to time.
     *
     * @param fromBtn  the from btn
     * @param toBtn    the to btn
     * @param fromTime the from time
     * @param toTime   the to time
     */
    void setFromAndToTime(Button fromBtn, Button toBtn, String fromTime, String toTime) {
        String[] from = fromTime.split(":");
        String[] to = toTime.split(":");
        fromBtn.setText(ConversionUtils.formatTime(Integer.parseInt(from[0]), Integer.parseInt(from[1])));
        toBtn.setText(ConversionUtils.formatTime(Integer.parseInt(to[0]), Integer.parseInt(to[1])));
    }

    /**
     * Update click.
     */
    @OnClick(R.id.update)
    void updateClick() {
        if (isNetworkConnected()) {
            validation();
        } else {
            showSnack(getString(R.string.no_internet_connection));
        }

    }

    /**
     * Bind click listener.
     */
    void bindClickListener() {
        sun_from_btn.setOnClickListener(this);
        sun_to_btn.setOnClickListener(this);
        mon_from_btn.setOnClickListener(this);
        mon_to_btn.setOnClickListener(this);
        tue_from_btn.setOnClickListener(this);
        tue_to_btn.setOnClickListener(this);
        wed_from_btn.setOnClickListener(this);
        wed_to_btn.setOnClickListener(this);
        thu_from_btn.setOnClickListener(this);
        thu_to_btn.setOnClickListener(this);
        fri_from_btn.setOnClickListener(this);
        fri_to_btn.setOnClickListener(this);
        sat_from_btn.setOnClickListener(this);
        sat_to_btn.setOnClickListener(this);
    }

    /**
     * Bind check change.
     */
    void bindCheckChange() {
        mSunChk.setOnCheckedChangeListener(this);
        mMonChk.setOnCheckedChangeListener(this);
        mTueChk.setOnCheckedChangeListener(this);
        mWedChk.setOnCheckedChangeListener(this);
        mThuChk.setOnCheckedChangeListener(this);
        mFriChk.setOnCheckedChangeListener(this);
        mSatChk.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sun_from:
                sun_from_btn.setTag(SUN_FROM);
                presenter.showTimePicker(sun_from_btn);
                break;
            case R.id.sun_to:
                sun_to_btn.setTag(SUN_TO);
                presenter.showTimePicker(sun_to_btn);
                break;
            case R.id.mon_from:
                mon_from_btn.setTag(MON_FROM);
                presenter.showTimePicker(mon_from_btn);
                break;
            case R.id.mon_to:
                mon_to_btn.setTag(MON_TO);
                presenter.showTimePicker(mon_to_btn);
                break;
            case R.id.tue_from:
                tue_from_btn.setTag(TUE_FROM);
                presenter.showTimePicker(tue_from_btn);
                break;
            case R.id.tue_to:
                tue_to_btn.setTag(TUE_TO);
                presenter.showTimePicker(tue_to_btn);
                break;
            case R.id.wed_from:
                wed_from_btn.setTag(WED_FROM);
                presenter.showTimePicker(wed_from_btn);
                break;
            case R.id.wed_to:
                wed_to_btn.setTag(WED_TO);
                presenter.showTimePicker(wed_to_btn);
                break;
            case R.id.thu_from:
                thu_from_btn.setTag(THU_FROM);
                presenter.showTimePicker(thu_from_btn);
                break;
            case R.id.thu_to:
                thu_to_btn.setTag(THU_TO);
                presenter.showTimePicker(thu_to_btn);
                break;
            case R.id.fri_from:
                fri_from_btn.setTag(FRI_FROM);
                presenter.showTimePicker(fri_from_btn);
                break;
            case R.id.fri_to:
                fri_to_btn.setTag(FRI_TO);
                presenter.showTimePicker(fri_to_btn);
                break;
            case R.id.sat_from:
                sat_from_btn.setTag(SAT_FROM);
                presenter.showTimePicker(sat_from_btn);
                break;
            case R.id.sat_to:
                sat_to_btn.setTag(SAT_TO);
                presenter.showTimePicker(sat_to_btn);
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton chk, boolean isChecked) {
        switch (chk.getId()) {
            case R.id.sun_status_chk:
                presenter.setVisible(sun_from_btn, sun_to_btn, isChecked);
                break;
            case R.id.mon_status_chk:
                presenter.setVisible(mon_from_btn, mon_to_btn, isChecked);
                break;
            case R.id.tue_status_chk:
                presenter.setVisible(tue_from_btn, tue_to_btn, isChecked);
                break;
            case R.id.wed_status_chk:
                presenter.setVisible(wed_from_btn, wed_to_btn, isChecked);
                break;
            case R.id.thu_status_chk:
                presenter.setVisible(thu_from_btn, thu_to_btn, isChecked);
                break;
            case R.id.fri_status_chk:
                presenter.setVisible(fri_from_btn, fri_to_btn, isChecked);
                break;
            case R.id.sat_status_chk:
                presenter.setVisible(sat_from_btn, sat_to_btn, isChecked);
                break;
        }
    }

    /**
     * From text view match string boolean.
     *
     * @param btn the btn
     * @return the boolean
     */
    boolean fromTextViewMatchString(Button btn) {
        return btn.getText().toString().equals(context.getString(R.string.from));
    }

    /**
     * To text view match string boolean.
     *
     * @param btn the btn
     * @return the boolean
     */
    boolean toTextViewMatchString(Button btn) {
        return btn.getText().toString().equals(context.getString(R.string.to));
    }

    /**
     * Validation.
     */
    void validation() {
        CheckBox arr[] = {mSunChk, mMonChk, mTueChk, mWedChk, mThuChk, mFriChk, mSatChk};
        String days[] = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        Button from[] = {sun_from_btn, mon_from_btn, tue_from_btn, wed_from_btn, thu_from_btn, fri_from_btn, sat_from_btn};
        Button to[] = {sun_to_btn, mon_to_btn, tue_to_btn, wed_to_btn, thu_to_btn, fri_to_btn, sat_to_btn};
        if (!arr[i].isChecked()) {
            if (fromTextViewMatchString(from[i]) || toTextViewMatchString(to[i])) {
                showSnack(context.getString(R.string.fill_from_to_date) + " " + days[i]);
            } else {
                validationSubTask(arr, from, to, days);
            }
        } else {
            validationSubTask(arr, from, to, days);
        }

    }

    /**
     * Validation sub task.
     *
     * @param arr  the arr
     * @param from the from
     * @param to   the to
     * @param days the days
     */
    void validationSubTask(CheckBox arr[], Button from[], Button to[], String days[]) {
        if (i < 6) {
            i++;
            recursiveMethod(arr, from, to, days);
        } else {
            presenter.updateWorkingHour(arr);
        }
    }


    /**
     * Recursive method.
     *
     * @param arr  the arr
     * @param from the from
     * @param to   the to
     * @param days the days
     */
    void recursiveMethod(CheckBox arr[], Button from[], Button to[], String days[]) {
        if (!arr[i].isChecked()) {
            if (fromTextViewMatchString(from[i]) || toTextViewMatchString(to[i])) {
                showSnack(context.getString(R.string.fill_from_to_date) + " " + days[i]);
            } else {
                recursiveSubTask(arr);
            }
        } else {
            recursiveSubTask(arr);
        }
    }

    /**
     * Recursive sub task.
     *
     * @param arr the arr
     */
    void recursiveSubTask(CheckBox arr[]) {
        if (i < 6) {
            i++;
            validation();
        } else {
            presenter.updateWorkingHour(arr);
        }
    }


}
