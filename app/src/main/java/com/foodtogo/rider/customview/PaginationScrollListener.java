package com.foodtogo.rider.customview;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * The type Pagination scroll listener.
 */
public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

    /**
     * The Layout manager.
     */
    LinearLayoutManager layoutManager;

    /**
     * Instantiates a new Pagination scroll listener.
     *
     * @param layoutManager the layout manager
     */
    public PaginationScrollListener(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0) {
                loadMoreItems();
            }
        }

    }


    /**
     * Load more items.
     */
    protected abstract void loadMoreItems();

    /**
     * Is last page boolean.
     *
     * @return the boolean
     */
    public abstract boolean isLastPage();

    /**
     * Is loading boolean.
     *
     * @return the boolean
     */
    public abstract boolean isLoading();

}
