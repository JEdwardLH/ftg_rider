package com.foodtogo.rider.ui.neworderfragment.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foodtogo.rider.R;
import com.foodtogo.rider.base.BaseApplication;
import com.foodtogo.rider.base.BaseFragment;
import com.foodtogo.rider.base.FragmentActivity;
import com.foodtogo.rider.model.neworders.OrderNew;
import com.foodtogo.rider.ui.login.activity.Login;
import com.foodtogo.rider.ui.neworderfragment.adapter.NewOrderAdapter;
import com.foodtogo.rider.ui.neworderfragment.mvp.NewOrderContractor;
import com.foodtogo.rider.ui.neworderfragment.mvp.NewOrderPresenter;
import com.foodtogo.rider.util.LocationFinder;
import com.foodtogo.rider.util.ViewUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.foodtogo.rider.util.CommonStrings.isNeedToRefresh;

/**
 * The type New order fragment.
 */
public class NewOrderFragment extends BaseFragment implements NewOrderContractor.View,NewOrderAdapter.Listener {

    /**
     * The Srl.
     */
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    /**
     * The Order rv.
     */
    @BindView(R.id.order_rv)
    RecyclerView orderRv;
    /**
     * The Presenter.
     */
    NewOrderPresenter presenter;
    /**
     * The New order adapter.
     */
    NewOrderAdapter newOrderAdapter;
    /**
     * The New order list.
     */
    public static List<OrderNew> newOrderList;
    private final static int MY_PERMISSIONS_REQUEST_CALL_PHONE = 102;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new NewOrderPresenter(this,
                getContext(), appRepository);
        recyclerViewSetUp();
        srl.setColorSchemeResources(
                R.color.colorNew,
                R.color.colorProcessing,
                R.color.colorDelivered);

        srl.setOnRefreshListener(() -> {
            srl.setRefreshing(false);
            newOrderList.clear();
            newOrderAdapter.reLoad();
        });

    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater,
                                        ViewGroup parent,
                                        Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orders, parent, false);
    }


    /**
     * Recycler view set up.
     */
    void recyclerViewSetUp() {
        newOrderList = new ArrayList<>();
        newOrderAdapter = new NewOrderAdapter(newOrderList,getActivity(),presenter,this);
        orderRv.setAdapter(newOrderAdapter);
    }

    @Override
    public void showError(int message) {
        changeActivityClearBackStack(Login.class);
    }

    @Override
    public void showLoggedInByAnother(String message) {
        showLoggedInByOtherError(message);
    }

    @Override
    public void reLoad() {
        newOrderList.clear();
        newOrderAdapter.reLoad();
    }

    @Override
    public void loadOrders(List<OrderNew> orderList) {
        if (orderList == null) {
            newOrderAdapter.onLoadFinished(false, 0);
        } else {
            this.newOrderList.addAll(orderList);
            newOrderAdapter.onLoadFinished(true, orderList.size());
        }
    }

    @Override
    public void acceptRejectSuccess(String msg) {
        isNeedToRefresh = true;
        ViewUtils.showSnackBar(srl, msg);
        newOrderList.clear();
        newOrderAdapter.reLoad();
        changeActivity(FragmentActivity.createIntent(getContext(),
                FragmentActivity.ORDERS_PROCESSING));

    }


    @Override
    public void updateTime(String result) {
        String duration = "30 mins"; //default estimated time set as 30 min, if don't get estimated result from google direction api
        try {
            JSONObject obj = new JSONObject(result);
            if (obj.has("rows")) {
                JSONArray rowArr = obj.getJSONArray("rows");
                if (rowArr.length() > 0) {
                    JSONArray elementArr = ((JSONObject) rowArr.get(0)).getJSONArray("elements");
                    if (elementArr.length() > 0) {
                        if (((JSONObject) elementArr.get(0)).has("duration")) {
                            duration = ((JSONObject) elementArr.get(0)).getJSONObject("duration").getString("text");
                        }
                    }
                }
            }
            System.out.println("duration" + duration);
            if (duration != null && !duration.equals("")) {
                int[] time = LocationFinder.getFormattedDuration(duration);
                presenter.acceptWithEstimatedTime(time);
            } else {
                System.out.print("cant get duration");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendOrderAcceptDetails(String acceptJson, String orderId, String storeId) {
        System.out.println("acceptJson" + acceptJson);
        // Toast.makeText(context, TYPE, Toast.LENGTH_SHORT).show();
        try {
            if (BaseApplication.getMQTTClient() != null) {
                String publishTopic = "order/" + orderId + "/restaurant/" + storeId;
                System.out.println("publishTopic" + publishTopic);
                BaseApplication.getMQTTServerClient().publish(BaseApplication.getMQTTClient(), publishTopic, acceptJson);
            }
        } catch (Exception e) {
            System.out.println("Mqtt Publish error" + e);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.close();
    }


    @Override
    public void callToRestaurant(String phone) {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
            presenter.callPhone(phone);
        }
    }

    @Override
    public void onClickLoad(int offset) {
        presenter.getNewOrderData(String.valueOf(newOrderAdapter.getPageNumber()));
    }
}
