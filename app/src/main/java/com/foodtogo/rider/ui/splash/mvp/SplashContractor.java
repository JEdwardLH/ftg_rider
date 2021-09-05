package com.foodtogo.rider.ui.splash.mvp;

import android.content.Intent;

/**
 * The interface Splash contractor.
 */
public interface SplashContractor {

    /**
     * The interface View.
     */
    interface View {
        /**
         * Move to next page.
         *
         * @param intent the intent
         */
        void moveToNextPage(Intent intent);
    }

    /**
     * The interface Presenter.
     */
    interface Presenter {
        /**
         * Splash delay.
         */
        void splashDelay();

        /**
         * Close.
         */
        void close();

    }


}
