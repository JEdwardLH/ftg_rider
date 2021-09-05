package com.foodtogo.rider.ui.trackorder.mvp;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.foodtogo.rider.R;
import com.foodtogo.rider.data.rest.ApiClient;
import com.foodtogo.rider.data.rest.ApiInterface;
import com.foodtogo.rider.model.BaseResponse;
import com.foodtogo.rider.model.CommonResponse;
import com.foodtogo.rider.model.directions.DirectionResponse;
import com.foodtogo.rider.model.sendOtp.SendOtpRequest;
import com.foodtogo.rider.model.trackOrder.TrackOrderRequest;
import com.foodtogo.rider.model.trackOrder.TrackOrderResponse;
import com.foodtogo.rider.model.trackOrder.UpdateStatusRequest;
import com.foodtogo.rider.util.CommonStrings;
import com.foodtogo.rider.util.NetworkUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * The type Track order modal.
 */
public class TrackOrderModal implements TrackOrderContractor.Modal {
    private TrackOrderPresenter presenter;
    private ApiInterface apiInterface;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Context mContext;
    private Disposable disposable;

    /**
     * Instantiates a new Track order modal.
     *
     * @param presenter the presenter
     * @param context   the context
     */
    public TrackOrderModal(TrackOrderPresenter presenter, Context context) {
        this.presenter = presenter;
        apiInterface = ApiClient.getApiInterface();
        this.mContext = context;
    }

    @Override
    public void requestTrackOrder(TrackOrderRequest request) {
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();
        compositeDisposable.add(disposable = apiInterface.getTrackOrderDetails(request).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<BaseResponse<TrackOrderResponse>>() {
            @Override
            public void onSuccess(BaseResponse<TrackOrderResponse> baseResponse) {
                try {
                    if (baseResponse.getCode() == 200) {
                        presenter.trackOrderSuccess(baseResponse.getData());
                    } else if (baseResponse.getCode() == 401) {
                        presenter.loggedInByAnotherError(baseResponse.getMessage());
                    } else {
                        presenter.trackOrderError(baseResponse.getMessage());
                        if (baseResponse.getCode() == 400 && baseResponse.getMessage().equals(CommonStrings.TOKEN_EXPIRED)) {
                            presenter.trackOrderError(baseResponse.getCode());
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    presenter.trackOrderError(NetworkUtils.getErrorMessage(responseBody));
                } else if (e instanceof SocketTimeoutException) {
                    presenter.trackOrderError(R.string.time_out_error);
                } else if (e instanceof IOException) {
                    presenter.trackOrderError(R.string.network_error);
                } else {
                    presenter.trackOrderError(e.getMessage());
                }

            }
        }));

    }

    @Override
    public void updateStatus(UpdateStatusRequest request) {
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();
        compositeDisposable.add(disposable = apiInterface.updateOrderStatus(request).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<BaseResponse<CommonResponse>>() {
            @Override
            public void onSuccess(BaseResponse<CommonResponse> baseResponse) {
                try {
                    if (baseResponse.getCode() == 200) {
                        presenter.updateMqtt(request.getStatus());
                        presenter.updateOrderSuccess(baseResponse.getMessage());
                    } else if (baseResponse.getCode() == 401) {
                        presenter.loggedInByAnotherError(baseResponse.getMessage());
                    } else {
                        presenter.trackOrderError(baseResponse.getMessage());
                        if (baseResponse.getCode() == 400 && baseResponse.getMessage().equals(CommonStrings.TOKEN_EXPIRED)) {
                            presenter.trackOrderError(baseResponse.getCode());
                        } else if (baseResponse.getCode() == 400 && baseResponse.getMessage().equals(CommonStrings.ORDER_FAILED)) {
                            presenter.orderFailed(baseResponse.getMessage());
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    presenter.trackOrderError(NetworkUtils.getErrorMessage(responseBody));
                } else if (e instanceof SocketTimeoutException) {
                    presenter.trackOrderError(R.string.time_out_error);
                } else if (e instanceof IOException) {
                    presenter.trackOrderError(R.string.network_error);
                } else {
                    presenter.trackOrderError(e.getMessage());
                }

            }
        }));

    }


    @Override
    public void requestToSendOtp(SendOtpRequest request) {
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();
        compositeDisposable.add(disposable = apiInterface.sendOtp(request).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse baseResponse) {
                try {
                    if (baseResponse.getCode() == 200) {
                        presenter.otpSuccess(baseResponse.getMessage());
                    } else if (baseResponse.getCode() == 401) {
                        presenter.loggedInByAnotherError(baseResponse.getMessage());
                    } else if (baseResponse.getCode() == 400 && baseResponse.getMessage().equals(CommonStrings.TOKEN_EXPIRED)) {
                        presenter.trackOrderError(baseResponse.getCode());
                    } else {
                        presenter.trackOrderError(baseResponse.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    presenter.trackOrderError(NetworkUtils.getErrorMessage(responseBody));
                } else if (e instanceof SocketTimeoutException) {
                    presenter.trackOrderError(R.string.time_out_error);
                } else if (e instanceof IOException) {
                    presenter.trackOrderError(R.string.network_error);
                } else {
                    presenter.trackOrderError(e.getMessage());
                }

            }
        }));
    }

    @Override
    public void close() {
        compositeDisposable.dispose();
    }

    @Override
    public void requestPolyLineData(LatLng originDriver, LatLng destCustomer, LatLng restaurant) {
        String origin = originDriver.latitude + "," + originDriver.longitude;
        String dest = destCustomer.latitude + "," + destCustomer.longitude;
        String wayPoints = "optimize:true|" + restaurant.latitude + "," + restaurant.longitude + "|";
        String sensor = "sensor=false";
        String mode = "mode=driving";

        compositeDisposable.add(disposable = apiInterface.getPolylineData(origin, dest, wayPoints, sensor, mode, mContext.getString(R.string.direction_api_key)).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<DirectionResponse>() {
            @Override
            public void onSuccess(DirectionResponse directionResponse) {
                try {
                    if (directionResponse.getStatus().equals("OK")) {
                        presenter.onDirectionResponse(directionResponse);
                    } else {
                        presenter.directionAPIError(directionResponse.getStatus());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    presenter.trackOrderError(NetworkUtils.getErrorMessage(responseBody));
                } else if (e instanceof SocketTimeoutException) {
                    presenter.trackOrderError(R.string.time_out_error);
                } else if (e instanceof IOException) {
                    presenter.trackOrderError(R.string.network_error);
                } else {
                    presenter.trackOrderError(e.getMessage());
                }
            }
        }));
    }

    @Override
    public void requestPolyLineWithoutWayPoints(LatLng originDriver, LatLng destCustomer) {
        String origin = originDriver.latitude + "," + originDriver.longitude;
        String dest = destCustomer.latitude + "," + destCustomer.longitude;
        String sensor = "sensor=false";
        String mode = "mode=driving";
        compositeDisposable.add(disposable = apiInterface.getPolylineDataWithoutWaypoints(origin, dest, sensor, mode, mContext.getString(R.string.direction_api_key)).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<DirectionResponse>() {
            @Override
            public void onSuccess(DirectionResponse directionResponse) {
                try {
                    if (directionResponse.getStatus().equals("OK")) {
                        presenter.onDirectionResponse(directionResponse);
                    } else {
                        presenter.directionAPIError(directionResponse.getStatus());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    presenter.trackOrderError(NetworkUtils.getErrorMessage(responseBody));
                } else if (e instanceof SocketTimeoutException) {
                    presenter.trackOrderError(R.string.time_out_error);
                } else if (e instanceof IOException) {
                    presenter.trackOrderError(R.string.network_error);
                } else {
                    presenter.trackOrderError(e.getMessage());
                }
            }
        }));

    }
}
