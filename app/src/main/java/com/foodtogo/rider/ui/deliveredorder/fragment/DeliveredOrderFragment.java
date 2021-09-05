package com.foodtogo.rider.ui.deliveredorder.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foodtogo.rider.R;
import com.foodtogo.rider.base.BaseActivity;
import com.foodtogo.rider.base.BaseFragment;
import com.foodtogo.rider.model.deliveredorders.NewOrderList;
import com.foodtogo.rider.ui.deliveredorder.adapter.DeliveryOrderAdapter;
import com.foodtogo.rider.ui.deliveredorder.mvp.DeliveredOrderContractor;
import com.foodtogo.rider.ui.deliveredorder.mvp.DeliveredOrderPresenter;
import com.foodtogo.rider.ui.dialog.DateRangeDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * The type Delivered order fragment.
 */
public class DeliveredOrderFragment extends BaseFragment implements DeliveredOrderContractor.View {
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
    DeliveredOrderPresenter presenter;
    /**
     * The Adapter.
     */
    DeliveryOrderAdapter adapter;

    /**
     * The Date from.
     */
    String dateFrom = "";
    /**
     * The Date to.
     */
    String dateTo = "";
    /**
     * The Search query.
     */
    String searchQuery = "";

    /**
     * The Delivered list.
     */
    List<NewOrderList> deliveredList;
    /**
     * The Date range dialog.
     */
    DateRangeDialog dateRangeDialog;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dateRangeDialog = new DateRangeDialog((BaseActivity)getActivity(), (fromDate, toDate) -> {
            if (fromDate.isEmpty()) {
                showToast(R.string.date_empty_from);
                return false;
            }
            if (toDate.isEmpty()) {
                showToast(R.string.date_empty_to);
                return false;
            }

            this.searchQuery = "";
            this.dateFrom = fromDate;
            this.dateTo = toDate;

            deliveredList.clear();
            adapter.reLoad();

            return true;
        },getResources().getString(R.string.date_range_title));
        presenter = new DeliveredOrderPresenter(this, getContext());
        recyclerViewSetUp();
        srl.setColorSchemeResources(
                R.color.colorNew,
                R.color.colorProcessing,
                R.color.colorDelivered);

        srl.setOnRefreshListener(() -> {
            srl.setRefreshing(false);
            deliveredList.clear();
            adapter.reLoad();
        });

    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater,
                                        ViewGroup parent,
                                        Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orders, parent, false);
    }

    @Override
    public void showSearchLoading() {
        ((DeliveryOrderAdapter.HeaderViewHolder) adapter.getHeader()).showLoadingView();
    }

    @Override
    public void hideSearchLoading() {
        ((DeliveryOrderAdapter.HeaderViewHolder) adapter.getHeader()).hideLoadingView();
    }

    /**
     * Recycler view set up.
     */
    void recyclerViewSetUp() {
        deliveredList = new ArrayList<>();
        adapter = new DeliveryOrderAdapter(deliveredList,
                getContext(),
                new DeliveryOrderAdapter.Listener() {
                    @Override
                    public void onChangeConfigure(String searchQuery) {
                        DeliveredOrderFragment.this.searchQuery = searchQuery;
                        deliveredList.clear();
                        adapter.reLoad();
                    }

                    @Override
                    public void onClickDateRange() {
                        dateRangeDialog.show();
                    }

                    @Override
                    public void onClickLoad(int offset) {
                        presenter.getDeliveredOrders(dateFrom, dateTo, String.valueOf(adapter.getPageNumber() + 1), searchQuery);
                    }
                });

        orderRv.setAdapter(adapter);
    }

    @Override
    public void loadDeliveredOrders(List<NewOrderList> orderList) {
        if (orderList == null) {
            adapter.onLoadFinished(false, 0);
        } else {
            this.deliveredList.addAll(orderList);
            adapter.onLoadFinished(true, orderList.size());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.close();
    }

    @Override
    public void showLoggedInByAnother(String message) {
        showLoggedInByOtherError(message);
    }
}
