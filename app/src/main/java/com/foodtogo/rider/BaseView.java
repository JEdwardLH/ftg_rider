package com.foodtogo.rider;

import android.content.Context;

import com.foodtogo.rider.base.BaseActivity;

/**
 * The interface Base view.
 */
public interface BaseView {

    /**
     * Show loading view.
     */
    void showLoadingView();

    /**
     * Hide loading view.
     */
    void hideLoadingView();

    /**
     * Show warning.
     *
     * @param message the message
     */
    void showWarning(String message);

    /**
     * Show warning.
     *
     * @param message the message
     */
    void showWarning(int message);

    /**
     * Show error.
     *
     * @param message the message
     */
    void showError(String message);

    /**
     * Show error.
     *
     * @param message the message
     */
    void showError(int message);

    /**
     * Show toast.
     *
     * @param message the message
     */
    void showToast(String message);

    /**
     * Show toast.
     *
     * @param message the message
     */
    void showToast(int message);

    /**
     * Show connection error.
     *
     * @param string the string
     */
    void showConnectionError(String string);

    /**
     * Show connection error.
     *
     * @param string the string
     */
    void showConnectionError(int string);

    /**
     * Show connection error finish.
     *
     * @param string the string
     */
    void showConnectionErrorFinish(String string);

    /**
     * Show connection error finish.
     *
     * @param string the string
     */
    void showConnectionErrorFinish(int string);

    /**
     * Show connection error retry.
     *
     * @param string        the string
     * @param retryListener the retry listener
     */
    void showConnectionErrorRetry(String string, BaseActivity.RetryListener retryListener);

    /**
     * Show connection error retry.
     *
     * @param string        the string
     * @param retryListener the retry listener
     */
    void showConnectionErrorRetry(int string, BaseActivity.RetryListener retryListener);

    /**
     * Show token expired.
     *
     * @param string the string
     */
    void showTokenExpired(String string);

    /**
     * Show token expired.
     *
     * @param string the string
     */
    void showTokenExpired(int string);

    void showLoggedInByAnother(String message);

    /**
     * Gets context.
     *
     * @return the context
     */
    Context getContext();


}