package com.foodtogo.rider.ui.processingorder.fragment;

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
import com.foodtogo.rider.base.BaseFragment;
import com.foodtogo.rider.model.neworders.OrderNew;
import com.foodtogo.rider.ui.login.activity.Login;
import com.foodtogo.rider.ui.processingorder.adapter.ProcessingOrderAdapter;
import com.foodtogo.rider.ui.processingorder.mvp.ProcessingOrderContractor;
import com.foodtogo.rider.ui.processingorder.mvp.ProcessingOrderPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * The type Processing order fragment.
 */
public class ProcessingOrderFragment extends BaseFragment implements ProcessingOrderContractor.View,ProcessingOrderAdapter.Listener {
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
    ProcessingOrderPresenter presenter;
    /**
     * The Adapter.
     */
    ProcessingOrderAdapter adapter;
    /**
     * The Processing order list.
     */
    List<OrderNew> processingOrderList;
    private final static int MY_PERMISSIONS_REQUEST_CALL_PHONE = 102;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new ProcessingOrderPresenter(this, getContext());
        srl.setColorSchemeResources(
                R.color.colorNew,
                R.color.colorProcessing,
                R.color.colorDelivered);

        srl.setOnRefreshListener(() -> {
            srl.setRefreshing(false);
            recyclerViewSetUp();
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
        processingOrderList = new ArrayList<>();
        adapter = new ProcessingOrderAdapter(processingOrderList, getContext(),this );
        orderRv.setAdapter(adapter);
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
    public void loadOrders(List<OrderNew> orderList) {
        if (orderList == null) {
            adapter.onLoadFinished(false, 0);
        } else {
            this.processingOrderList.addAll(orderList);
            adapter.onLoadFinished(true, orderList.size());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerViewSetUp();
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
        presenter.getProcessingOrders(String.valueOf(adapter.getPageNumber() + 1));
    }
}
