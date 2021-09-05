package com.foodtogo.rider.ui.dashboard.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foodtogo.rider.R;
import com.foodtogo.rider.base.BaseFragment;
import com.foodtogo.rider.base.FragmentActivity;
import com.foodtogo.rider.model.dashboard.DashboardResponse;
import com.foodtogo.rider.ui.dashboard.activity.DashboardActivity;
import com.foodtogo.rider.ui.dashboard.mvp.DashboardContractor;
import com.foodtogo.rider.ui.dashboard.mvp.DashboardPresenter;
import com.foodtogo.rider.ui.dashboard.view.PieChart;
import com.foodtogo.rider.ui.login.activity.Login;
import com.foodtogo.rider.ui.workinghours.activity.WorkingHours;
import com.foodtogo.rider.util.ConversionUtils;
import com.foodtogo.rider.util.ViewUtils;

import butterknife.BindView;
import butterknife.OnClick;
import lecho.lib.hellocharts.view.PieChartView;

import static android.app.Activity.RESULT_OK;

/**
 * The type Dashboard fragment.
 */
public class DashboardFragment extends BaseFragment implements DashboardContractor.View {

    /**
     * New instance dashboard fragment.
     *
     * @return the dashboard fragment
     */
    public static DashboardFragment newInstance(/*int resId*/) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        /*args.putSerializable(ARG_RES_ID, resId);*/
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater,
                                        ViewGroup parent,
                                        Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_dashboard, parent, false);

    }

    /**
     * The Dashboard activity.
     */
    DashboardActivity dashboardActivity;
    /**
     * The Presenter.
     */
    DashboardPresenter presenter;
    /**
     * The Pie chart.
     */
    PieChart pieChart;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.dashboardActivity = (DashboardActivity) getActivity();
        this.presenter = dashboardActivity.presenter;

        srl.setColorSchemeResources(
                R.color.colorNew,
                R.color.colorProcessing,
                R.color.colorDelivered);

        srl.setOnRefreshListener(() -> dashboardActivity.presenter.loadDashboard(true));

        pieChart = new PieChart(pieChartView);

        dashboardActivity.showLoadingView();
        presenter.loadDashboard(false);

        srl.post(() -> {
            if (srl != null) srl.requestLayout();
        });
    }

    /**
     * The Srl.
     */
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    /**
     * The Tv total order count.
     */
    @BindView(R.id.tvTotalOrderCount)
    TextView tvTotalOrderCount;

    @BindView(R.id.active_status_txt)
    TextView activeStatusText;

    /**
     * The Tv new order count.
     */
    @BindView(R.id.tvNewOrderCount)
    TextView tvNewOrderCount;

    /**
     * The Tv processing order count.
     */
    @BindView(R.id.tvProcessingOrderCount)
    TextView tvProcessingOrderCount;

    /**
     * The Tv delivered order count.
     */
    @BindView(R.id.tvDeliveredOrderCount)
    TextView tvDeliveredOrderCount;

    /**
     * On click new orders.
     */
    @OnClick(R.id.llNewOrders)
    public void onClickNewOrders() {
        changeActivity(FragmentActivity.createIntent(getContext(), FragmentActivity.ORDERS_NEW));
    }

    /**
     * On click processing orders.
     */
    @OnClick(R.id.llProcessingOrders)
    public void onClickProcessingOrders() {
        changeActivity(FragmentActivity.createIntent(getContext(),
                FragmentActivity.ORDERS_PROCESSING));
    }

    @OnClick(R.id.active_status_txt)
    void workingHourStatusClick(){
        changeActivity(WorkingHours.class);
    }

    /**
     * On click delivered orders.
     */
    @OnClick(R.id.llDeliveredOrders)
    public void onClickDeliveredOrders() {
        changeActivity(FragmentActivity.createIntent(getContext(),
                FragmentActivity.ORDERS_DELIVERED));
    }

    /**
     * The Dashboard data.
     */
    DashboardResponse dashboardData;


    /**
     * The Pie chart view.
     */
    @BindView(R.id.pieChartView)
    PieChartView pieChartView;

    @Override
    public void showError(int message) {
        changeActivityClearBackStack(Login.class);

        //showToast(message);
    }

    @Override
    public void showLoggedInByAnother(String message) {
        //no use here
    }


    @Override
    public void showError(String message) {
        showToast(message);
    }

    @Override
    public void exitAppOnSignOut() {
        appRepository.saveIsLoggedIn(false);
        appRepository.setOAuthKey("");
        changeActivityClearBackStack(Login.class);
    }

    @Override
    public void showSnackBar(String msg) {
        ViewUtils.showSnackBar(getView(), msg);
    }

    @Override
    public void loadData(DashboardResponse dashboardData) {
        this.dashboardData = dashboardData;

        if (tvTotalOrderCount != null) {
            //activeStatusText.setVisibility(dashboardData.getActiveStatus() == 1 ? View.GONE : View.VISIBLE);
            tvTotalOrderCount.setText(String.valueOf(dashboardData.getTotalOrderCount()));
            tvNewOrderCount.setText(String.valueOf(dashboardData.getNewOrderCount()));
            tvProcessingOrderCount.setText(String.valueOf(dashboardData.getProcessingOrderCount()));
            tvDeliveredOrderCount.setText(String.valueOf(dashboardData.getDeliveredOrderCount()));

            ViewUtils.forceRippleAnimation(
                    tvTotalOrderCount,
                    tvNewOrderCount,
                    tvProcessingOrderCount,
                    tvDeliveredOrderCount);

            pieChart.loadData(dashboardData.getNewOrderCount(),
                    dashboardData.getProcessingOrderCount(),
                    dashboardData.getDeliveredOrderCount());
           checkInActiveDayInfoShowOrNot();
        }
    }

    void checkInActiveDayInfoShowOrNot(){
         if(appRepository.getIsInActiveDayInfoShown()){
             if(!appRepository.getIsShownInfoDate().equals("") && !appRepository.getIsShownInfoDate().equals(ConversionUtils.getFormatDateTime())){
                 appRepository.setIsInActiveDayInfoShown(false);
             }
         }

        /* if(!appRepository.getIsInActiveDayInfoShown()){
             if(dashboardData.getWorkUnAvailDaysList().contains(ConversionUtils.getCurrentDayOfWeek())){
                 appRepository.setIsShownInfoDate(ConversionUtils.getFormatDateTime());
                 appRepository.setIsInActiveDayInfoShown(true);
                 showPopup(getString(R.string.dialog_title_info),getString(R.string.inactive_info,ConversionUtils.getCurrentDayOfWeek()));
             }
         }*/
    }

    @Override
    public void showRefreshView() {
        if (srl != null) srl.setRefreshing(true);
    }

    @Override
    public void hideRefreshView() {
        if (srl != null) srl.setRefreshing(false);
    }

    /**
     * Refresh.
     */
    public void refresh() {
        showRefreshView();
        presenter.loadDashboard(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            refresh();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showPopup(String title, String msg) {
        new AlertDialog.Builder(getActivity(),R.style.AppCompatAlertDialogStyle)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false)
                .setNegativeButton("No", (dialog, i) -> {
                    dialog.dismiss();

                })
                .setPositiveButton("Yes", (dialog, which) -> {
                    dialog.dismiss();
                       startActivity(new Intent(context, WorkingHours.class));
                }).show();
    }
}
