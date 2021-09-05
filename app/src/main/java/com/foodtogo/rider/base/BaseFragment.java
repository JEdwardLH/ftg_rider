package com.foodtogo.rider.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.foodtogo.rider.BaseView;
import com.foodtogo.rider.data.source.AppDataSource;
import com.foodtogo.rider.data.source.AppRepository;
import com.foodtogo.rider.data.source.sharedpreference.AppPreferenceDataSource;
import com.foodtogo.rider.util.AppUtils;
import com.foodtogo.rider.util.NetworkUtils;

import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * The type Base fragment.
 */
public abstract class BaseFragment extends AbstractFragment implements BaseView {

    /**
     * The Context.
     */
    protected Context context;
    /**
     * The App repository.
     */
    protected AppRepository appRepository;
    /**
     * The App data source.
     */
    protected AppDataSource appDataSource;

    /**
     * The Unbinder.
     */
    Unbinder unbinder;

    @Override
    public void onRefresh() {

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanseState) {
        View view = provideYourFragmentView(inflater, parent, savedInstanseState);
        context = this.getActivity();
        appDataSource = new AppPreferenceDataSource(getActivity());
        appRepository = new AppRepository(appDataSource);
        unbinder = ButterKnife.bind(this, view);
        updateLocale(appRepository.getLanguage());
        return view;
    }
    private void updateLocale(String defaultLang) {
        AppUtils.updateLocale(defaultLang, Objects.requireNonNull(getActivity()));
        appRepository.setLanguage(defaultLang);
    }


    /**
     * Provide your fragment view view.
     *
     * @param inflater           the inflater
     * @param parent             the parent
     * @param savedInstanceState the saved instance state
     * @return the view
     */
    public abstract View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState);


    /**
     * Change activity for result.
     *
     * @param i the
     */
    public void changeActivityForResult(Intent i) {
        BaseActivity baseActivity = ((BaseActivity) getActivity());
        if (baseActivity != null) baseActivity.startActivityForResult(i, 0);
    }

    /**
     * Change activity.
     *
     * @param clz the clz
     */
    public void changeActivity(Class clz) {
        BaseActivity baseActivity = ((BaseActivity) getActivity());
        if (baseActivity != null) baseActivity.changeActivity(clz);
    }

    /**
     * Change activity.
     *
     * @param i the
     */
    public void changeActivity(Intent i) {
        BaseActivity baseActivity = ((BaseActivity) getActivity());
        if (baseActivity != null) baseActivity.changeActivity(i);
    }

    /**
     * Change activity clear back stack.
     *
     * @param clz the clz
     */
    public void changeActivityClearBackStack(Class clz) {
        BaseActivity baseActivity = ((BaseActivity) getActivity());
        if (baseActivity != null) baseActivity.changeActivityClearBackStack(clz);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * Gets status bar height.
     *
     * @return the status bar height
     */
    public int getStatusBarHeight() {
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }


    /**
     * Is network connected boolean.
     *
     * @return the boolean
     */
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getContext());
    }

    /**
     * Change activity with string.
     *
     * @param clz  the clz
     * @param from the from
     */
    public void changeActivityWithString(Class clz, String from) {
        Intent i = new Intent(getActivity(), clz);
        i.putExtra("from", from);
        startActivity(i);
    }


    public void showLoadingView() {
        if (getActivity() != null)
            ((BaseActivity) getActivity()).showProgress();
    }

    public void hideLoadingView() {
        if (getActivity() != null)
            ((BaseActivity) getActivity()).hideProgress();
    }

    public void showWarning(int message) {
        showWarning(getResources().getString(message));
    }

    public void showWarning(String message) {
        BaseActivity baseActivity = ((BaseActivity) getActivity());
        if (baseActivity != null) baseActivity.showWarning(message);
    }


    public void showError(int message) {
        showError(getResources().getString(message));
    }

    public void showError(String message) {
        BaseActivity baseActivity = ((BaseActivity) getActivity());
        if (baseActivity != null) baseActivity.showError(message);
    }

    public void showConnectionError(int message) {
        showConnectionError(getResources().getString(message));
    }

    public void showConnectionErrorFinish(int message) {
        showConnectionErrorFinish(getResources().getString(message));
    }

    public void showConnectionErrorRetry(int message, BaseActivity.RetryListener retryListener) {
        showConnectionErrorRetry(getResources().getString(message), retryListener);
    }

    public void showConnectionError(String message) {
        BaseActivity baseActivity = ((BaseActivity) getActivity());
        if (baseActivity != null) baseActivity.showConnectionError(message);
    }

    public void showTokenExpired(String message) {
        BaseActivity baseActivity = ((BaseActivity) getActivity());
        if (baseActivity != null) baseActivity.showTokenExpired(message);
    }

    public void showTokenExpired(int message) {
        BaseActivity baseActivity = ((BaseActivity) getActivity());
        if (baseActivity != null) baseActivity.showTokenExpired(message);
    }

    public void showConnectionErrorFinish(String message) {

        BaseActivity baseActivity = ((BaseActivity) getActivity());
        if (baseActivity != null) baseActivity.showConnectionErrorFinish(message);
    }

    public void showConnectionErrorRetry(String message, BaseActivity.RetryListener retryListener) {

        BaseActivity baseActivity = ((BaseActivity) getActivity());
        if (baseActivity != null) baseActivity.showConnectionErrorRetry(message, retryListener);
    }

    public void showToast(int message) {
        showToast(getResources().getString(message));
    }

    public void showToast(String message) {
        BaseActivity baseActivity = ((BaseActivity) getActivity());
        if (baseActivity != null) baseActivity.showToast(message);
    }

    public void showLoggedInByOtherError(String msg){
        BaseActivity baseActivity = ((BaseActivity) getActivity());
        if (baseActivity != null) baseActivity.showLoggedInByAnotherInfo(msg);
    }

}