package com.foodtogo.rider.ui.earning_reports.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodtogo.rider.R;
import com.foodtogo.rider.base.BaseActivity;
import com.foodtogo.rider.base.BaseFragment;
import com.foodtogo.rider.model.earningreports.EarningReportsResponse;
import com.foodtogo.rider.model.earningreports.Report;
import com.foodtogo.rider.ui.dialog.DateRangeDialog;
import com.foodtogo.rider.ui.earning_reports.adapter.EarningReportAdapter;
import com.foodtogo.rider.ui.earning_reports.mvp.EarningReportsContractor;
import com.foodtogo.rider.ui.earning_reports.mvp.EarningReportsPresenter;
import com.foodtogo.rider.ui.login.activity.Login;
import com.foodtogo.rider.util.CommonStrings;
import com.foodtogo.rider.util.ViewUtils;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The type Earning reports fragment.
 */
public class EarningReportsFragment extends BaseFragment implements EarningReportsContractor.View {
    /**
     * The Layout.
     */
    @BindView(R.id.container_layout)
    CoordinatorLayout layout;
    /**
     * The Order rv.
     */
    @BindView(R.id.order_rv)
    RecyclerView orderRv;
    /**
     * The Error text.
     */
    @BindView(R.id.error_text)
    TextView errorText;
    /**
     * The Retry image.
     */
    @BindView(R.id.retry_image)
    ImageView retryImage;
    /**
     * The Retry text.
     */
    @BindView(R.id.retry_text)
    TextView retryText;
    /**
     * The Earning amount.
     */
    @BindView(R.id.amount)
    TextView earningAmount;
    /**
     * The Total earning amount.
     */
    @BindView(R.id.earning_amount)
    TextView totalEarningAmount;
    /**
     * The Error layout.
     */
    @BindView(R.id.error_layout)
    ConstraintLayout errorLayout;
    /**
     * The Order layout.
     */
    @BindView(R.id.order_container)
    ConstraintLayout orderLayout;
    /**
     * The Header layout.
     */
    @BindView(R.id.header_layout)
    ConstraintLayout headerLayout;
    /**
     * The Earning layout.
     */
    @BindView(R.id.earning_layout)
    CardView earningLayout;
    /**
     * The Presenter.
     */
    EarningReportsPresenter presenter;
    /**
     * The Api from date.
     */
    String apiFromDate = "";
    /**
     * The Api to date.
     */
    String apiToDate = "";
    /**
     * The Adapter.
     */
    EarningReportAdapter adapter;
    /**
     * The From date edt.
     */
    EditText fromDateEdt;
    /**
     * The To date edt.
     */
    EditText toDateEdt;
    /**
     * The Date range dialog.
     */
    DateRangeDialog dateRangeDialog;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new EarningReportsPresenter(this, getContext());

        if (isNetworkConnected()) {
            showLoadingView();
            presenter.getEarningReport(apiFromDate, apiToDate);
        } else {
            networkErrorLayout();
        }
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater,
                                        ViewGroup parent,
                                        Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_earning_reports, parent, false);
    }

    @Override
    public void bindReportResponse(EarningReportsResponse response) {
        errorLayout.setVisibility(View.GONE);
        orderLayout.setVisibility(View.VISIBLE);
        headerLayout.setVisibility(View.VISIBLE);
        earningLayout.setVisibility(View.VISIBLE);
        List<Report> processingOrderList = response.getReports();
        if (processingOrderList.size() > 0) {
            adapter = new EarningReportAdapter(processingOrderList, getContext());
            orderRv.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            orderRv.setLayoutManager(mLayoutManager);
            orderRv.setAdapter(adapter);
            earningAmount.setText(String.format("%s%s", response.getCurrencyCode(), response.getPageCommission()));
            totalEarningAmount.setText(String.format("%s%s", response.getCurrencyCode(), response.getGrantTotalCommission()));
        } else {
            errorLayout.setVisibility(View.VISIBLE);
            hideLoadingView();
            noDataError(getString(R.string.no_order_found));
        }
    }

    @Override
    public void showNetworkLayout() {
        networkErrorLayout();
    }

    @Override
    public void showSnack(String msg) {
        ViewUtils.showSnackBar(layout, msg);
    }

    @Override
    public void showError(String message) {
        showSnack(message);
        noDataError(message);
    }

    @Override
    public void showError(int message) {
        changeActivityClearBackStack(Login.class);
    }

    @Override
    public void showLoggedInByAnother(String message) {
        showLoggedInByOtherError(message);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.close();

    }

    /**
     * Retry image click.
     */
    @OnClick(R.id.retry_image)
    void retryImageClick() {
        presenter.retryClicked();
    }

    /**
     * Retry text click.
     */
    @OnClick(R.id.retry_text)
    void retryTextClick() {
        presenter.retryClicked();
    }

    /**
     * Network error layout.
     */
    void networkErrorLayout() {
        errorLayout.setVisibility(View.VISIBLE);
        errorText.setText(getString(R.string.no_internet_connection));
        retryText.setVisibility(View.VISIBLE);
        retryImage.setVisibility(View.VISIBLE);
    }

    /**
     * No data error.
     *
     * @param message the message
     */
    void noDataError(String message) {
        headerLayout.setVisibility(View.GONE);
        orderLayout.setVisibility(View.GONE);
        earningLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
        errorText.setText(message);
        retryText.setVisibility(View.VISIBLE);
        retryImage.setVisibility(View.VISIBLE);
    }

    /**
     * Show dialog.
     */
    @OnClick(R.id.ic_calendar)
    void showDialog() {
        dateRangeDialog = new DateRangeDialog((BaseActivity)getActivity(), (fromDate, toDate) -> {
            if (fromDate.isEmpty()) {
                showToast(R.string.date_empty_from);
                return false;
            }
            if (toDate.isEmpty()) {
                showToast(R.string.date_empty_to);
                return false;
            }

            this.apiFromDate = fromDate;
            this.apiToDate = toDate;

            dateRangeDialog.dismiss();
            if (isNetworkConnected()) {
                showLoadingView();
                presenter.getEarningReport(apiFromDate, apiToDate);
            } else {
                ViewUtils.showSnackBar(layout, getString(R.string.no_internet_connection));
            }

            return true;
        },getResources().getString(R.string.date_range_earning_reports));
        dateRangeDialog.show();

//
//        final Dialog dialog = new Dialog(getContext());
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(true);
//        dialog.setContentView(R.layout.check_delivered_order);
//
//        fromDateEdt = dialog.findViewById(R.id.edt_from_date);
//        toDateEdt = dialog.findViewById(R.id.edt_to_date);
//
//
//        Button dialogButton = dialog.findViewById(R.id.btn_go);
//        dialogButton.setOnClickListener(v -> {
//            if (!TextUtils.isEmpty(fromDateEdt.getText().toString())) {
//                if (!TextUtils.isEmpty(toDateEdt.getText().toString())) {
//                    dialog.dismiss();
//                    if (isNetworkConnected()) {
//                        showLoadingView();
//                        presenter.getEarningReport(apiFromDate, apiToDate);
//                    } else {
//                        ViewUtils.showSnackBar(layout, getString(R.string.no_internet_connection));
//                    }
//                } else {
//                    ViewUtils.showSnackBar(dialogButton, context.getString(R.string.fill_from_date));
//                }
//
//            } else {
//                ViewUtils.showSnackBar(dialogButton, context.getString(R.string.fill_to_date));
//            }
//        });
//
//        fromDateEdt.setOnClickListener(v -> showDateDialog(CommonStrings.FROM_DATE));
//        toDateEdt.setOnClickListener(v -> showDateDialog(CommonStrings.TO_DATE));
//
//        dialog.show();
    }

    /**
     * Show date dialog.
     *
     * @param fromOrToDate the from or to date
     */
    public void showDateDialog(String fromOrToDate) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                (view, year, monthOfYear, dayOfMonth) ->
                        bindDateValues(dayOfMonth, monthOfYear + 1, year, fromOrToDate), mYear, mMonth, mDay);

        datePickerDialog.show();
    }

    /**
     * Bind date values.
     *
     * @param day   the day
     * @param month the month
     * @param year  the year
     * @param from  the from
     */
    public void bindDateValues(int day, int month, int year, String from) {
        String mMonth = String.valueOf(month).length() == 1 ? "0" + String.valueOf(month) : String.valueOf(month);
        String mDay = String.valueOf(day).length() == 1 ? "0" + String.valueOf(day) : String.valueOf(day);
        if (from.equals(CommonStrings.FROM_DATE)) {
            apiFromDate = String.valueOf(year) + "-" + mMonth + "-" + mDay;
            fromDateEdt.setText(String.format("%s-%s-%s", mDay, mMonth, String.valueOf(year)));
        } else {
            apiToDate = String.valueOf(year) + "-" + mMonth + "-" + mDay;
            toDateEdt.setText(String.format("%s-%s-%s", mDay, mMonth, String.valueOf(year)));
        }

    }

}
