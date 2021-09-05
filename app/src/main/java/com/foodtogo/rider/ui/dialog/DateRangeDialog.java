package com.foodtogo.rider.ui.dialog;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.foodtogo.rider.R;
import com.foodtogo.rider.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * The type Date range dialog.
 */
public class DateRangeDialog {


    /**
     * The interface Listener.
     */
    public interface Listener {
        /**
         * On select date range boolean.
         *
         * @param fromDate the from date
         * @param toDate   the to date
         * @return the boolean
         */
        boolean onSelectDateRange(String fromDate, String toDate);
    }

    /**
     * The Dialog.
     */
    Dialog dialog;
    /**
     * The View.
     */
    View view;

    /**
     * The Et date from.
     */
    @BindView(R.id.etDateFrom)
    EditText etDateFrom;

    /**
     * The Et date to.
     */
    @BindView(R.id.etDateTo)
    EditText etDateTo;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    /**
     * The Listener.
     */
    Listener listener;

    String title;

    /**
     * Instantiates a new Date range dialog.
     *
     * @param activity the activity
     * @param listener the listener
     */
    public DateRangeDialog(AppCompatActivity activity, Listener listener,String mTitle) {
        this.listener = listener;
        this.title=mTitle;
        dialog = new Dialog(activity, R.style.AppCompatAlertDialogStyle);
        view = LayoutInflater.from(activity).inflate(R.layout.dialog_date_range, null);
        ButterKnife.bind(this, view);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(view);
        tvTitle.setText(title);

        dateController = new DateController(activity.getSupportFragmentManager());
    }

    /**
     * On click positive.
     */
    @OnClick(R.id.btnPositive)
    public void onClickPositive() {
        if (listener.onSelectDateRange(etDateFrom.getText().toString(), etDateTo.getText().toString())) {
            dismiss();
        }
    }

    /**
     * On click from.
     */
    @OnClick(R.id.etDateFrom)
    public void onClickFrom() {
        dateController.show("", etDateTo.getText().toString(), date -> {
            etDateFrom.setText(date);
        });
    }

    /**
     * On click to.
     */
    @OnClick(R.id.etDateTo)
    public void onClickTo() {
        dateController.show(etDateFrom.getText().toString(), "", date -> {
            etDateTo.setText(date);
        });
    }

    /**
     * Show.
     */
    public void show() {
        etDateFrom.setText("");
        etDateTo.setText("");
        dialog.show();
    }

    /**
     * Dismiss.
     */
    public void dismiss() {
        dialog.dismiss();
    }

    /**
     * Destroy.
     */
    public void destroy() {
        if (dialog.isShowing())
            dialog.dismiss();
        dialog = null;
    }

    /**
     * The Date controller.
     */
    DateController dateController;
}