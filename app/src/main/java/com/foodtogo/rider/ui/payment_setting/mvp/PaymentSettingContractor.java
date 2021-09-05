package com.foodtogo.rider.ui.payment_setting.mvp;

import android.widget.EditText;

import com.foodtogo.rider.BaseView;
import com.foodtogo.rider.model.Request;
import com.foodtogo.rider.model.payment_settings.PaymentSettingResponse;
import com.foodtogo.rider.model.payment_settings.UpdatePaymentRequest;

/**
 * The interface Payment setting contractor.
 */
public interface PaymentSettingContractor {
    /**
     * The interface View.
     */
    interface View extends BaseView {
        /**
         * Bind setting response.
         *
         * @param response the response
         */
        void bindSettingResponse(PaymentSettingResponse response);
    }

    /**
     * The interface Presenter.
     */
    interface Presenter {
        /**
         * Close.
         */
        void close();

        /**
         * Gets payment settings details.
         */
        void getPaymentSettingsDetails();

        /**
         * Payment setting success.
         *
         * @param response the response
         */
        void paymentSettingSuccess(PaymentSettingResponse response);

        /**
         * Payment setting error.
         *
         * @param msg the msg
         */
        void paymentSettingError(String msg);

        /**
         * Payment setting error.
         *
         * @param msg the msg
         */
        void paymentSettingError(int msg);

        /**
         * Update payment setting.
         *
         * @param request the request
         * @param pId     the p id
         * @param pKey    the p key
         * @param card    the card
         * @param bank    the bank
         * @param branch  the branch
         * @param ifsc    the ifsc
         */
        void updatePaymentSetting(UpdatePaymentRequest request, String pId, String pKey, String card, String bank, String branch, String ifsc);

        /**
         * Is pay pal boolean.
         *
         * @param clientId  the client id
         * @param secretKey the secret key
         * @return the boolean
         */
        boolean isPayPal(EditText clientId, EditText secretKey);

        /**
         * Is net bank boolean.
         *
         * @param card   the card
         * @param bank   the bank
         * @param branch the branch

         * @return the boolean
         */
        boolean isNetBank(EditText card, EditText bank, EditText branch);

        /**
         * Update setting success.
         *
         * @param msg the msg
         */
        void updateSettingSuccess(String msg);

        /**
         * Sets cursor at last.
         *
         * @param edt the edt
         */
        void setCursorAtLast(EditText edt);

        void loggedInByAnother(String msg);
    }

    /**
     * The interface Modal.
     */
    interface Modal {
        /**
         * Close.
         */
        void close();

        /**
         * Request payment settings.
         *
         * @param request the request
         */
        void requestPaymentSettings(Request request);

        /**
         * Request to update settings.
         *
         * @param request the request
         */
        void requestToUpdateSettings(UpdatePaymentRequest request);

    }
}
