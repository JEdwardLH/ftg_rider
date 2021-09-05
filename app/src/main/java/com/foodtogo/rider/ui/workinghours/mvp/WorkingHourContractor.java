package com.foodtogo.rider.ui.workinghours.mvp;

import android.widget.Button;
import android.widget.CheckBox;

import com.foodtogo.rider.BaseView;
import com.foodtogo.rider.model.Request;
import com.foodtogo.rider.model.workinghours.WorkingHoursRequest;
import com.foodtogo.rider.model.workinghours.getworkinghour.GetWorkingHourResponse;

/**
 * The interface Working hour contractor.
 */
public interface WorkingHourContractor {
    /**
     * The interface View.
     */
    interface View extends BaseView {
        /**
         * Bind time value.
         *
         * @param btn  the btn
         * @param time the time
         */
        void bindTimeValue(Button btn, String time);

        /**
         * Show snack.
         *
         * @param msg the msg
         */
        void showSnack(String msg);

        /**
         * Gets wh data.
         *
         * @param response the response
         * @param msg      the msg
         */
        void getWhData(GetWorkingHourResponse response, String msg);

        /**
         * Check document status.
         */
        void checkDocumentStatus();

    }

    /**
     * The interface Presenter.
     */
    interface Presenter {
        /**
         * Show time picker.
         *
         * @param btn the btn
         */
        void showTimePicker(Button btn);

        /**
         * Sets visible.
         *
         * @param from      the from
         * @param to        the to
         * @param isChecked the is checked
         */
        void setVisible(Button from, Button to, boolean isChecked);

        /**
         * Update working hour.
         *
         * @param arr the arr
         */
        void updateWorkingHour(CheckBox[] arr);

        /**
         * Working hour error.
         *
         * @param error the error
         */
        void WorkingHourError(String error);

        /**
         * Working hour error.
         *
         * @param error the error
         */
        void WorkingHourError(int error);

        /**
         * Update success.
         *
         * @param msg the msg
         */
        void updateSuccess(String msg);

        /**
         * Close.
         */
        void close();

        /**
         * Gets working hour data.
         */
        void getWorkingHourData();

        /**
         * Gets data success.
         *
         * @param response the response
         * @param msg      the msg
         */
        void getDataSuccess(GetWorkingHourResponse response, String msg);
        void loggedInByOtherError(String msg);
    }

    /**
     * The interface Model.
     */
    interface Model {
        /**
         * Request to upload.
         *
         * @param request the request
         */
        void requestToUpload(WorkingHoursRequest request);

        /**
         * Request to get working hours.
         *
         * @param request the request
         */
        void requestToGetWorkingHours(Request request);

        /**
         * Close.
         */
        void close();
    }
}
