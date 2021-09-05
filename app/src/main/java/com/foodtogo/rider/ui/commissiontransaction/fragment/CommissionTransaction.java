package com.foodtogo.rider.ui.commissiontransaction.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foodtogo.rider.R;
import com.foodtogo.rider.base.BaseActivity;
import com.foodtogo.rider.base.BaseFragment;
import com.foodtogo.rider.model.commission_transaction.TransactionList;
import com.foodtogo.rider.ui.commissiontransaction.adapter.TransactionAdapter;
import com.foodtogo.rider.ui.commissiontransaction.mvp.CmTransactionContractor;
import com.foodtogo.rider.ui.commissiontransaction.mvp.CmTransactionPresenter;
import com.foodtogo.rider.ui.dialog.DateRangeDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * The type Commission transaction.
 */
public class CommissionTransaction extends BaseFragment implements CmTransactionContractor.View {
    /**
     * The Srl.
     */
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    /**
     * The Transaction rv.
     */
    @BindView(R.id.commission_transactionRv)
    RecyclerView transactionRv;

    /**
     * The Presenter.
     */
    CmTransactionPresenter presenter;
    /**
     * The Adapter.
     */
    TransactionAdapter adapter;
    /**
     * The Date range dialog.
     */
    DateRangeDialog dateRangeDialog;

    /**
     * The Date from.
     */
    String dateFrom = "";
    /**
     * The Date to.
     */
    String dateTo = "";

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

            this.dateFrom = fromDate;
            this.dateTo = toDate;

            transactionList.clear();
            adapter.reLoad();

            return true;
        },getResources().getString(R.string.date_range_commission));

        presenter = new CmTransactionPresenter(this, getContext());

        transactionRv.setAdapter(adapter = new TransactionAdapter(transactionList = new ArrayList<>(),
                new TransactionAdapter.Listener() {
                    @Override
                    public void onClickDateRange() {
                        dateRangeDialog.show();
                    }

                    @Override
                    public void onClickLoad(int offset) {
                        presenter.getTransactionDetails(String.valueOf(adapter.getPageNumber() + 1),
                                dateFrom, dateTo);
                    }
                }));

        srl.setColorSchemeResources(
                R.color.colorNew,
                R.color.colorProcessing,
                R.color.colorDelivered);

        srl.setOnRefreshListener(() -> {
            srl.setRefreshing(false);
            transactionList.clear();
            adapter.reLoad();
        });

    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater,
                                        ViewGroup parent,
                                        Bundle savedInstanceState) {
        return inflater.inflate(R.layout.commission_transaction, parent, false);
    }

    /**
     * The Transaction list.
     */
    List<TransactionList> transactionList;

    @Override
    public void loadTransactionList(List<TransactionList> transactionList) {
        if (transactionList == null) {
            adapter.onLoadFinished(false, 0);
        } else {
            this.transactionList.addAll(transactionList);
            adapter.onLoadFinished(true, transactionList.size());
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
