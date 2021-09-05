package com.foodtogo.rider.ui.dashboard.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.foodtogo.rider.R;
import com.foodtogo.rider.base.AppConstants;
import com.foodtogo.rider.base.BaseActivity;
import com.foodtogo.rider.customview.PicassoCircleTransformation;
import com.foodtogo.rider.model.dashboard.DashboardResponse;
import com.foodtogo.rider.ui.commissiontracking.fragment.CommissionTracking;
import com.foodtogo.rider.ui.commissiontransaction.fragment.CommissionTransaction;
import com.foodtogo.rider.ui.dashboard.fragment.DashboardFragment;
import com.foodtogo.rider.ui.dashboard.mvp.DashboardContractor;
import com.foodtogo.rider.ui.dashboard.mvp.DashboardPresenter;
import com.foodtogo.rider.ui.deliveredorder.fragment.DeliveredOrderFragment;
import com.foodtogo.rider.ui.dialog.ExitDialog;
import com.foodtogo.rider.ui.dialog.SignOutDialog;
import com.foodtogo.rider.ui.earning_reports.fragment.EarningReportsFragment;
import com.foodtogo.rider.ui.login.activity.Login;
import com.foodtogo.rider.ui.neworderfragment.fragment.NewOrderFragment;
import com.foodtogo.rider.ui.payment_setting.fragment.PaymentSetting;
import com.foodtogo.rider.ui.processingorder.fragment.ProcessingOrderFragment;
import com.foodtogo.rider.ui.profile.fragment.EditProfileFragment;
import com.foodtogo.rider.ui.workinghours.activity.WorkingHours;
import com.foodtogo.rider.util.AppUtils;
import com.foodtogo.rider.util.LocationFinder;
import com.foodtogo.rider.util.NetworkUtils;
import com.foodtogo.rider.util.PreferenceUtils;
import com.foodtogo.rider.util.ResourceUtils;
import com.foodtogo.rider.util.ViewUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys.ADDRESS_PROOF;
import static com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys.DRIVING_LICENSE;
import static com.foodtogo.rider.data.source.sharedpreference.PreferenceKeys.USER_IMAGE;
import static com.foodtogo.rider.util.CommonStrings.FROM_EDIT_PROFILE;
import static com.foodtogo.rider.util.CommonStrings.LOCAL_USER_NAME;

/**
 * The type Dashboard activity.
 */
public class DashboardActivity extends BaseActivity implements DashboardContractor.View, ActivityCompat.OnRequestPermissionsResultCallback {

    /**
     * The Drawer layout.
     */
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    /**
     * The Sv navigation.
     */
    @BindView(R.id.svNavigation)
    ScrollView svNavigation;

    /**
     * The Rg navigation.
     */
    @BindView(R.id.rgNavigation)
    RadioGroup rgNavigation;

    /**
     * The Iv avatar.
     */
    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;

    /**
     * The Exit dialog.
     */
    ExitDialog exitDialog;
    /**
     * The Sign out dialog.
     */
    SignOutDialog signOutDialog;
    /**
     * The Action refresh.
     */
    MenuItem actionRefresh;
    MenuItem actionWorkingHour;
    Fragment currentFragment=null;

    /**
     * The Presenter.
     */
    public DashboardPresenter presenter;
    private static String ARG_OPEN = "arg_open";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupToolBar();

        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) toolbar.getLayoutParams();
        layoutParams.setMargins(0, getStatusBarHeight(), 0, 0);
        toolbar.setLayoutParams(layoutParams);

        setTitle(getResources().getString(R.string.dashboard));
        appRepository.setLanguage(getString(R.string.language));
        presenter = new DashboardPresenter(this, appRepository, getApplicationContext());
        drawerLayout = findViewById(R.id.drawerLayout);

        ViewGroup.MarginLayoutParams layoutParams1 = (ViewGroup.MarginLayoutParams) toolbar.getLayoutParams();
        layoutParams1.setMargins(0, getStatusBarHeight(), 0, 0);
        toolbar.setLayoutParams(layoutParams1);
        if (drawerLayout != null) {
            initDrawer();
        }

        // navigationDrawerSetup();
        rbDashboard.performClick();
        refreshLayoutForEveryTwentySecond();
        signOutDialog = new SignOutDialog(this);
        exitDialog = new ExitDialog(this);
        locationBroadCastReceiver();


        if (getIntent() != null && getIntent().getStringExtra(ARG_OPEN) != null) {
            System.out.println("ARG_OPEN123");
            replaceFragment(R.id.flContainer, new NewOrderFragment(), ResourceUtils.getString(R.string.new_order));
        }
    }

    private void locationBroadCastReceiver() {
        LocalBroadcastManager.getInstance(this).registerReceiver(
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        String latitude = intent.getStringExtra(BaseActivity.EXTRA_LATITUDE);
                        String longitude = intent.getStringExtra(BaseActivity.EXTRA_LONGITUDE);
                        if(latitude!=null && longitude!=null)
                        LocationFinder.updateUI(latitude, longitude, getApplicationContext());
                    }
                }, new IntentFilter(BaseActivity.ACTION_LOCATION_BROADCAST)
        );
    }

    private void refreshLayoutForEveryTwentySecond() {
        presenter.startTimer(this);
    }

    /**
     * The Dashboard fragment.
     */
    DashboardFragment dashboardFragment;

    /**
     * Init drawer.
     */
    void initDrawer() {
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDrawerToggle.setToolbarNavigationClickListener(view -> drawerLayout.openDrawer(
                GravityCompat.END));
        drawerLayout.addDrawerListener(mDrawerToggle);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {
                svNavigation.scrollTo(0, 0);
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                hideKeyboard();
                super.onDrawerOpened(drawerView);
            }
        });
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        //mDrawerToggle.syncState();
        initNavigation();
    }


    @Override
    protected void onPause() {
        super.onPause();
        presenter.stopTimer();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(listener);
    }

    /**
     * The Tv name.
     */
    @BindView(R.id.tvName)
    TextView tvName;
    /**
     * The Tv email.
     */
    @BindView(R.id.tvEmail)
    TextView tvEmail;

    /**
     * The Rb dashboard.
     */
    @BindView(R.id.rbDashboard)
    RadioButton rbDashboard;
    /**
     * The Rb new orders.
     */
    @BindView(R.id.rbNewOrders)
    RadioButton rbNewOrders;
    /**
     * The Rb precessing orders.
     */
    @BindView(R.id.rbPrecessingOrders)
    RadioButton rbPrecessingOrders;
    /**
     * The Rb delivered orders.
     */
    @BindView(R.id.rbDeliveredOrders)
    RadioButton rbDeliveredOrders;
    /**
     * The Rb earning reports.
     */
    @BindView(R.id.rbEarningReports)
    RadioButton rbEarningReports;
    /**
     * The Rb commission transaction.
     */
    @BindView(R.id.rbCommissionTransaction)
    RadioButton rbCommissionTransaction;
    /**
     * The Rb commission tracking.
     */
    @BindView(R.id.rbCommissionTracking)
    RadioButton rbCommissionTracking;
    /**
     * The Rb payment settings.
     */
    @BindView(R.id.rbPaymentSettings)
    RadioButton rbPaymentSettings;

    @BindView(R.id.rbLanguageSettings)
    RadioButton rbLanguageSettings;
    /**
     * The Tv sign out.
     */
    @BindView(R.id.tvSignOut)
    TextView tvSignOut;
    private final String ENGLISH = "en";
    private final String THAI = "th";



    /**
     * Init navigation.
     */
    public void initNavigation() {
        svNavigation.setPadding(0, getStatusBarHeight(), 0, 0);

        rbDashboard.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dashboard, 0, 0, 0);
        rbLanguageSettings.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_worlwide, 0, 0, 0);
        rbNewOrders.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_new_orders, 0, 0, 0);
        rbPrecessingOrders.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_processing_orders, 0, 0, 0);
        rbDeliveredOrders.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_delivered_orders, 0, 0, 0);
        rbEarningReports.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_earning_reports, 0, 0, 0);
        rbCommissionTransaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_commission_transaction, 0, 0, 0);
        rbCommissionTracking.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_commission_tracking, 0, 0, 0);
        rbPaymentSettings.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_payment_settings, 0, 0, 0);
        tvSignOut.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_signout, 0, 0, 0);


        String url = PreferenceUtils.readString(DashboardActivity.this, USER_IMAGE, "image");
        System.out.println("urlImage" + url);
        Picasso.get()
                .load(url)
                .resize(600, 200)
                .centerInside()
                .placeholder(R.drawable.ic_user_default)
                .error(R.drawable.ic_user_default)
                .transform(new PicassoCircleTransformation())
                .into(ivAvatar);

        tvName.setText(PreferenceUtils.readString(DashboardActivity.this, LOCAL_USER_NAME, "name"));

        setNavigationPanelText();
    }
    void setNavigationPanelText(){
        rbDashboard.setText(getResources().getString(R.string.dashboard));
        rbNewOrders.setText(getString(R.string.new_order));
        rbPrecessingOrders.setText(getString(R.string.processing_order));
        rbDeliveredOrders.setText(getString(R.string.delivered_order));
        rbEarningReports.setText(getString(R.string.earning_report));
        rbCommissionTransaction.setText(getString(R.string.commission_transaction));
        rbCommissionTracking.setText(getString(R.string.commission_tracking));
        rbPaymentSettings.setText(getString(R.string.payment_settings));
        rbLanguageSettings.setText(getString(R.string.language_settings));
        tvSignOut.setText(getString(R.string.sign_out));
    }

    @Override
    public int getLayout() {
        return R.layout.activity_dashboard;
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
        showToast(message);
    }

    @Override
    public void showError(int message) {
        changeActivityClearBackStack(Login.class);
    }

    @Override
    public void showLoggedInByAnother(String message) {
        //not used here
    }

    @Override
    public void onBackPressed() {
        if (currentFragment!=null && currentFragment instanceof DashboardFragment) {
            exitDialog.show(this::finish);
        } else {
            rbDashboard.performClick();
        }
    }

    @Override
    protected void onDestroy() {
        if (signOutDialog != null) signOutDialog.destroy();
        if (exitDialog != null) exitDialog.destroy();
        super.onDestroy();
    }

    @Override
    public void showRefreshView() {
        if (dashboardFragment != null) dashboardFragment.showRefreshView();
    }

    @Override
    public void hideRefreshView() {
        if (dashboardFragment != null) dashboardFragment.hideRefreshView();
    }

    @Override
    public void loadData(DashboardResponse dashboardResponse) {
        hideProgress();
        bindViews(dashboardResponse);

    }

    @Override
    public void exitAppOnSignOut() {
        appRepository.saveIsLoggedIn(false);
        appRepository.setOAuthKey("");
        changeActivityClearBackStack(Login.class);
    }

    @Override
    public void showSnackBar(String msg) {
        ViewUtils.showSnackBar(drawerLayout, msg);
    }

    /**
     * Bind views.
     *
     * @param dashboardResponse the dashboard response
     */
    void bindViews(DashboardResponse dashboardResponse) {

        if (dashboardFragment != null) dashboardFragment.loadData(dashboardResponse);
        tvName.setText(appRepository.getUserName());
        tvEmail.setText(appRepository.getUserEmail());
    }

    /**
     * On click profile.
     */
    @OnClick({R.id.llProfile, R.id.ibEdit})
    public void onClickProfile() {
        rgNavigation.clearCheck();
        replaceFragment(R.id.flContainer, new EditProfileFragment(), ResourceUtils.getString(R.string.profile_edit));
        closeDrawer();
    }

    /**
     * On click dashboard.
     */
    @OnClick(R.id.rbDashboard)
    public void onClickDashboard() {
        dashboardFragment = new DashboardFragment();
        replaceFragment(R.id.flContainer, dashboardFragment, ResourceUtils.getString(R.string.dashboard));
        closeDrawer();
    }

    /**
     * On click new orders.
     */
    @OnClick(R.id.rbNewOrders)
    public void onClickNewOrders() {
        replaceFragment(R.id.flContainer, new NewOrderFragment(), ResourceUtils.getString(R.string.new_order));
        closeDrawer();
    }

    /**
     * On click precessing orders.
     */
    @OnClick(R.id.rbPrecessingOrders)
    public void onClickPrecessingOrders() {
        replaceFragment(R.id.flContainer, new ProcessingOrderFragment(), ResourceUtils.getString(R.string.processing_order));
        closeDrawer();
    }

    /**
     * On click delivered orders.
     */
    @OnClick(R.id.rbDeliveredOrders)
    public void onClickDeliveredOrders() {
        replaceFragment(R.id.flContainer, new DeliveredOrderFragment(), ResourceUtils.getString(R.string.delivered_order));
        closeDrawer();
    }

    /**
     * On click earning reports.
     */
    @OnClick(R.id.rbEarningReports)
    public void onClickEarningReports() {
        replaceFragment(R.id.flContainer, new EarningReportsFragment(), ResourceUtils.getString(R.string.earning_report));
        closeDrawer();
    }

    /**
     * On click commission transaction.
     */
    @OnClick(R.id.rbCommissionTransaction)
    public void onClickCommissionTransaction() {
        replaceFragment(R.id.flContainer, new CommissionTransaction(), ResourceUtils.getString(R.string.commission_transaction));
        closeDrawer();
    }

    /**
     * On click commission tracking.
     */
    @OnClick(R.id.rbCommissionTracking)
    public void onClickCommissionTracking() {
        replaceFragment(R.id.flContainer, new CommissionTracking(), ResourceUtils.getString(R.string.commission_tracking));
        closeDrawer();
    }

    /**
     * On click payment settings.
     */
    @OnClick(R.id.rbPaymentSettings)
    public void onClickPaymentSettings() {
        replaceFragment(R.id.flContainer, new PaymentSetting(), ResourceUtils.getString(R.string.payment_settings));
        closeDrawer();
    }

    @Override
    public void replaceFragment(int container, Fragment fragment, String tag) {
        currentFragment=fragment;
        updateRefreshVisibility();
        updateWorkingHourVisibility();
        super.replaceFragment(container, fragment, tag);
    }
    @OnClick(R.id.rbLanguageSettings)
    public void onClickLanguageSettings() {
      //  changeLanguage();
        closeDrawer();
    }

    private BroadcastReceiver listener = new BroadcastReceiver() {
        @Override
        public void onReceive( Context context, Intent intent ) {
            showLoadingView();
            presenter.loadDashboard(true);

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
        lbm.registerReceiver(listener, new IntentFilter("new_order"));
    }



    /**
     * Close drawer.
     */
    void closeDrawer() {
        drawerLayout.closeDrawers();
    }


    private void changeLanguage() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_change_language);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final RadioButton rbEnglish = dialog.findViewById(R.id.rb_english);
        final RadioButton rbIndonesia = dialog.findViewById(R.id.rb_thai);
        final RadioGroup radioGroup = dialog.findViewById(R.id.rg_language_option);
        if (getString(R.string.language).equals(ENGLISH)) {
            rbEnglish.setChecked(true);
        } else if (getString(R.string.language).equals(THAI)) {
            rbIndonesia.setChecked(true);
        }


        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (rbEnglish.isChecked()) {
                AppUtils.updateLocale(ENGLISH, DashboardActivity.this);
                appRepository.setLanguage(ENGLISH);
            } else if (rbIndonesia.isChecked()) {
                AppUtils.updateLocale(THAI, DashboardActivity.this);
                appRepository.setLanguage(THAI);
            }

            dialog.dismiss();
            changeActivityClearBackStack(DashboardActivity.class);
        });
        dialog.show();
    }


    /**
     * On click sign out.
     */
    @OnClick(R.id.tvSignOut)
    public void onClickSignOut() {
        PreferenceUtils.writeString(context, ADDRESS_PROOF, "");
        PreferenceUtils.writeString(context, DRIVING_LICENSE, "");
        signOutDialog.show(() -> {
            drawerLayout.closeDrawers();
            if (isNetworkConnected()) {
                signOutDialog.dismiss();
                presenter.signOutClicked();
            } else {
                showToast(R.string.no_internet_connection);
            }
        });
    }


    /**
     * Update refresh visibility.
     */
    void updateRefreshVisibility() {
        int checkedId = rgNavigation.getCheckedRadioButtonId();
        if (actionRefresh != null) actionRefresh.setVisible(checkedId == R.id.rbDashboard);
    }

    void updateWorkingHourVisibility() {
        int checkId = rgNavigation.getCheckedRadioButtonId();
        System.out.println("checkId:" + checkId);
        if (actionWorkingHour != null) actionWorkingHour.setVisible(checkId == -1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_main, menu);
        actionRefresh = menu.findItem(R.id.actionRefresh);
        actionWorkingHour = menu.findItem(R.id.actionWorkHour);
        updateRefreshVisibility();
        updateWorkingHourVisibility();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionRefresh:
                if (NetworkUtils.isNetworkConnected(context)) {
                    presenter.loadDashboard(true);
                } else
                    showSnackBar(context.getString(R.string.no_internet_connection));
                break;
            case R.id.actionWorkHour:
                changeActivityWithString(WorkingHours.class, FROM_EDIT_PROFILE);
                break;
            case R.id.actionMenu:
                if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                    drawerLayout.closeDrawer(GravityCompat.END);
                } else {
                    drawerLayout.openDrawer(GravityCompat.END);
                }
                break;


        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (Fragment fragment : fragments) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }


}
