package com.foodtogo.rider.model.commission_transaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The type Transaction response.
 */
public class TransactionResponse {
    @SerializedName("transaction_list")
    @Expose
    private List<TransactionList> transactionList = null;

    /**
     * Gets transaction list.
     *
     * @return the transaction list
     */
    public List<TransactionList> getTransactionList() {
        return transactionList;
    }

    /**
     * Sets transaction list.
     *
     * @param transactionList the transaction list
     */
    public void setTransactionList(List<TransactionList> transactionList) {
        this.transactionList = transactionList;
    }
}
