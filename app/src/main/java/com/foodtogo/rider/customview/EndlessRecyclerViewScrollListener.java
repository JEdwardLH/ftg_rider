package com.foodtogo.rider.customview;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.foodtogo.rider.base.AppConstants;


/**
 * The type Endless recycler view scroll listener.
 */
public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 5;
    // The current offset index of data you have loaded
    private int currentPage = 0;
    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;
    // True if we are still waiting for the last set of data to load.
    private boolean loading = true;
    // Sets the starting page index
    private int startingPageIndex = 0;

    /**
     * Gets current page.
     *
     * @return the current page
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * Reset.
     */
    public void reset() {
        visibleThreshold = 5;
        currentPage = 0;
        previousTotalItemCount = 0;
        loading = true;
        startingPageIndex = 0;
    }

    /**
     * Gets previous total item count.
     *
     * @return the previous total item count
     */
    public int getPreviousTotalItemCount() {
        return previousTotalItemCount;
    }

    /**
     * The M layout manager.
     */
    RecyclerView.LayoutManager mLayoutManager;

    /**
     * Instantiates a new Endless recycler view scroll listener.
     *
     * @param layoutManager the layout manager
     */
    public EndlessRecyclerViewScrollListener(RecyclerView.LayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    /**
     * Instantiates a new Endless recycler view scroll listener.
     *
     * @param layoutManager the layout manager
     */
    public EndlessRecyclerViewScrollListener(LinearLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    /**
     * Instantiates a new Endless recycler view scroll listener.
     *
     * @param layoutManager the layout manager
     */
    public EndlessRecyclerViewScrollListener(GridLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
    }

    /**
     * Instantiates a new Endless recycler view scroll listener.
     *
     * @param layoutManager the layout manager
     */
    public EndlessRecyclerViewScrollListener(StaggeredGridLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
    }

    /**
     * Gets last visible item.
     *
     * @param lastVisibleItemPositions the last visible item positions
     * @return the last visible item
     */
    public int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

    // This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to load some more data,
    // but first we check if we are waiting for the previous load to finish.


    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        check();
    }

    /**
     * Check.
     */
    void check() {
        int lastVisibleItemPosition = 0;
        int totalItemCount = mLayoutManager.getItemCount();

        if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) mLayoutManager).findLastVisibleItemPositions(null);
            // get maximum element within the list
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
        } else if (mLayoutManager instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        } else if (mLayoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        }

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }
        }
        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        //  if (loading && (totalItemCount > previousTotalItemCount)) {
        //      loading = false;
        //      previousTotalItemCount = totalItemCount;
        //   }

        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        // threshold should reflect how many total columns there are too
        if (!loading && (lastVisibleItemPosition + visibleThreshold) > totalItemCount) {
            currentPage++;
            if (totalItemCount != 0) onLoadMore(currentPage, totalItemCount);
            loading = true;
        }
    }

    /**
     * On load more.
     *
     * @param page            the page
     * @param totalItemsCount the total items count
     */
// Defines the process for actually loading more data based on page
    public abstract void onLoadMore(int page, int totalItemsCount);

    /**
     * Loading finished.
     *
     * @param totalItemCount the total item count
     */
    public void loadingFinished(int totalItemCount) {
        if (getPreviousTotalItemCount() < totalItemCount && totalItemCount >= AppConstants.DEFAULT_LIMIT) {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }
        check();
    }
}