package com.foodtogo.rider.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.foodtogo.rider.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * The type Sign out dialog.
 */
public class SignOutDialog {

    /**
     * The interface Listener.
     */
    public interface Listener {
        /**
         * On click sign out.
         */
        void onClickSignOut();
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
     * The Listener.
     */
    SignOutDialog.Listener listener;

    /**
     * Instantiates a new Sign out dialog.
     *
     * @param activity the activity
     */
    public SignOutDialog(Activity activity) {
        dialog = new Dialog(activity, R.style.AppCompatAlertDialogStyle);
        view = LayoutInflater.from(activity).inflate(R.layout.dialog_signout, null);
        ButterKnife.bind(this, view);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(view);

    }

    /**
     * On click positive.
     */
    @OnClick(R.id.btnPositive)
    public void onClickPositive() {
        listener.onClickSignOut();
    }

    /**
     * On click from.
     */
    @OnClick(R.id.btnNegative)
    public void onClickFrom() {
        dismiss();
    }

    /**
     * Show.
     *
     * @param listener the listener
     */
    public void show(SignOutDialog.Listener listener) {
        this.listener = listener;
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
}