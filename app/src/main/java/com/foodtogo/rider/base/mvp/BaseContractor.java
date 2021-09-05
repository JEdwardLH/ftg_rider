package com.foodtogo.rider.base.mvp;


public interface BaseContractor {
    interface View {

        void showError(String msg);

        void showError(int msg);
    }
    interface Presenter{
        void postLocation(String lat, String lng, String language);
        void showError(String msg);
        void showError(int msg);
        void close();
    }
    interface Model{
        void requestToPostData(String lat, String lng, String language);
        void close();
    }
}
