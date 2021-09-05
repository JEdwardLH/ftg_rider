package com.foodtogo.rider.base.mvp;

public class BasePresenter implements BaseContractor.Presenter {
    private BaseModel model;
    private BaseContractor.View view;

    public BasePresenter(BaseContractor.View view) {
        this.model = new BaseModel(this);
        this.view=view;
    }
    @Override
    public void postLocation(String lat, String lng, String language) {
        model.requestToPostData(lat,lng,language);
    }

    @Override
    public void showError(String msg) {
        view.showError(msg);
    }

    @Override
    public void showError(int msg) {
      view.showError(msg);
    }

    @Override
    public void close() {
        model.close();
    }
}
