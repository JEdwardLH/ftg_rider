package com.foodtogo.rider.customview;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foodtogo.rider.R;


/**
 * The type My adapter.
 *
 * @param <VH> the type parameter
 */
public abstract class MyAdapter<VH extends MyAdapter.ItemViewHolder> extends AbstractAdapter<VH> implements View.OnClickListener {

    /**
     * The Is no item.
     */
    boolean isNoItem = false;
    /**
     * The Is loading.
     */
    boolean isLoading = false;
    /**
     * The Is loading error.
     */
    boolean isLoadingError = false;
    /**
     * The Is loading more.
     */
    boolean isLoadingMore = false;
    /**
     * The Is loading more error.
     */
    boolean isLoadingMoreError = false;

    /**
     * Is loading error boolean.
     *
     * @return the boolean
     */
    public boolean isLoadingError() {
        return isLoadingError;
    }

    /**
     * Instantiates a new My adapter.
     *
     * @param listener the listener
     */
    public MyAdapter(Listener listener) {
        this.listener = listener;
    }

    /**
     * Reset.
     */
    public void reset() {
        /* pageNumber = 0;*/
        isNoItem = false;
        isLoading = true;
        isLoadingError = false;
        isLoadingMore = false;
        isLoadingMoreError = false;
        notifyDataSetChanged();
        endlessRvScrollListener.reset();
    }

    /**
     * Re load.
     */
    public void reLoad() {
        reset();
        listener.onClickLoad(0);
    }

/*
    int pageNumber = 1;*/

    /**
     * Gets page number.
     *
     * @return the page number
     */
    public int getPageNumber() {
        return endlessRvScrollListener.getCurrentPage();
    }

    /**
     * On load finished.
     *
     * @param success the success
     * @param count   the count
     */
    public void onLoadFinished(boolean success, int count) {
        //    Log.d("LOAD_TRIGGERED : ","("+String.valueOf(success)+","+count+")");
        if (success) {
            /*  pageNumber++;*/

            if (isLoading) {
                isLoading = false;
                if (count == 0) {
                    isNoItem = true;
                    // notifyItemChanged(isHeader()?1:0);

                } else {
                    //  _notifyItemRemoved(isHeader()?1:0);
                }
                notifyDataSetChanged();
            } else if (isLoadingMore) {
                isLoadingMore = false;
                if (count == 0) $notifyItemRemoved(getItemCount());
            } else if (count == 0 && !isLoadMoreEnabled()) {
                isNoItem = true;
                notifyDataSetChanged();
            }

            if (count != 0) {
                int startPosition = getItemCount();
                int endPosition = startPosition + count;
                //           Log.d("NOTIFY INSERTED : ","("+startPosition+","+endPosition+")");
                notifyItemRangeInserted(startPosition, endPosition);
                if (endlessRvScrollListener != null)
                    endlessRvScrollListener.loadingFinished(getItemCount());
            }

        } else {
            if (isLoading) {
                isLoading = false;
                isLoadingError = true;
                notifyDataSetChanged();
            } else if (isLoadingMore) {
                isLoadingMore = false;
                isLoadingMoreError = true;
                notifyItemChanged(getItemCount());
            }
        }
    }

    /**
     * The interface Listener.
     */
    public interface Listener {
        /**
         * On click load.
         *
         * @param offset the offset
         */
// void onItemClick(int position);
        void onClickLoad(int offset);
    }

    /**
     * The Listener.
     */
    protected Listener listener;

    /**
     * On click load.
     */
    public void onClickLoad() {
        Log.d("ON_CLICK", "" + _getItemCount());
        if (_getItemCount() == 0) {
            if (isLoadingError) {
                isLoadingError = false;
                //_notifyItemRemoved(isHeader()?1:0);
            }
            isLoading = true;
            //notifyItemInserted(isHeader()?1:0);
            notifyDataSetChanged();
        } else {
            if (isLoadingMoreError) {
                isLoadingMoreError = false;
                $notifyItemRemoved(getItemCount());
            }

            if (isLoadMoreEnabled()) {
                isLoadingMore = true;
                $notifyItemInserted(getItemCount() - 1);
            }
        }
        if (listener != null)
            listener.onClickLoad(_getItemCount());
    }

    /**
     * Notify item removed.
     *
     * @param position the position
     */
    public void _notifyItemRemoved(int position) {
        $notifyItemRemoved((isHeader() ? position + 1 : position));
    }

    /**
     * Notify item removed.
     *
     * @param position the position
     */
    public void $notifyItemRemoved(int position) {
        if (position > 0) notifyItemChanged(position - 1);
        notifyItemRemoved(position);
    }

    /**
     * Notify item inserted.
     *
     * @param position the position
     */
    public void _notifyItemInserted(int position) {
        $notifyItemInserted(isHeader() ? position + 1 : position);
    }

    /**
     * Notify item inserted.
     *
     * @param position the position
     */
    public void $notifyItemInserted(int position) {
        if (position > 0) notifyItemChanged(position - 1);
        notifyItemInserted(position);
    }

    /**
     * The Endless rv scroll listener.
     */
    EndlessRecyclerViewScrollListener endlessRvScrollListener;
    /**
     * The Recycler view.
     */
    RecyclerView recyclerView;

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        isLoading = _getItemCount() == 0;
        this.recyclerView = recyclerView;
        //    Log.d("ON_CLICK","LOAD");
        if (isLoadMoreEnabled()) {
            endlessRvScrollListener = new EndlessRecyclerViewScrollListener(recyclerView.getLayoutManager()) {
                @Override
                public void onLoadMore(int page, int totalItemsCount) {
                    MyAdapter.this.recyclerView.post(new Runnable() {
                        public void run() {
                            MyAdapter.this.onClickLoad();
                        }
                    });
                }
            };
            recyclerView.addOnScrollListener(endlessRvScrollListener);
        }
        super.onAttachedToRecyclerView(recyclerView);
        onClickLoad();
    }

    /**
     * The constant VT_NO_ITEM.
     */
    public final static int VT_NO_ITEM = -1000;

    /**
     * The constant VT_HEADER.
     */
    public final static int VT_HEADER = -1001;
    /**
     * The constant VT_FOOTER.
     */
    public final static int VT_FOOTER = -1002;

    /**
     * The constant VT_LOADING.
     */
    public final static int VT_LOADING = -1003;
    /**
     * The constant VT_LOADING_ERROR.
     */
    public final static int VT_LOADING_ERROR = -1004;

    /**
     * The constant VT_LOADING_MORE.
     */
    public final static int VT_LOADING_MORE = -1005;
    /**
     * The constant VT_LOADING_MORE_ERROR.
     */
    public final static int VT_LOADING_MORE_ERROR = -1006;

    /**
     * The Parent.
     */
    public ViewGroup parent;

    private NoItemViewHolder noItemViewHolder;

    private ItemViewHolder header;
    private ItemViewHolder footer;

    /**
     * Gets header.
     *
     * @return the header
     */
    public ItemViewHolder getHeader() {
        return header;
    }

    /**
     * Gets footer.
     *
     * @return the footer
     */
    public ItemViewHolder getFooter() {
        return footer;
    }

    private LoadingViewHolder loadingViewHolder;
    private LoadingErrorViewHolder loadingErrorViewHolder;

    private LoadingMoreViewHolder loadingMoreViewHolder;
    private LoadingMoreErrorViewHolder loadingMoreErrorViewHolder;

    /**
     * Notify item range inserted.
     *
     * @param positionStart the position start
     * @param itemCount     the item count
     */
    public final void _notifyItemRangeInserted(int positionStart, int itemCount) {
        int pos = positionStart - 2;
        if (pos > -1)
            notifyItemChanged(pos);
        super.notifyItemRangeInserted(positionStart + (isHeader() ? 1 : 0), itemCount);
    }

    /**
     * Notify item range removed.
     *
     * @param positionStart the position start
     * @param itemCount     the item count
     */
    public final void _notifyItemRangeRemoved(int positionStart, int itemCount) {
        super.notifyItemRangeRemoved(positionStart + (isHeader() ? 1 : 0), itemCount);
    }

    /**
     * Notify item changed.
     *
     * @param position the position
     */
    public final void _notifyItemChanged(int position) {
        super.notifyItemChanged(position + (isHeader() ? 1 : 0));
    }

    /**
     * Notify item range changed.
     *
     * @param positionStart the position start
     * @param itemCount     the item count
     */
    public final void _notifyItemRangeChanged(int positionStart, int itemCount) {
        super.notifyItemRangeChanged(positionStart + (isHeader() ? 1 : 0), itemCount);
    }


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.parent = parent;
        //  Log.d("viewType", "" + viewType);
        ItemViewHolder itemViewHolder;

        switch (viewType) {
            case VT_LOADING_MORE_ERROR:
                itemViewHolder = loadingMoreErrorViewHolder = new LoadingMoreErrorViewHolder();
                break;
            case VT_LOADING_MORE:
                itemViewHolder = loadingMoreViewHolder = new LoadingMoreViewHolder();
                break;
            case VT_LOADING:
                itemViewHolder = loadingViewHolder = new LoadingViewHolder();
                break;
            case VT_NO_ITEM:
                itemViewHolder = noItemViewHolder = new NoItemViewHolder();
                break;
            case VT_LOADING_ERROR:
                itemViewHolder = loadingErrorViewHolder = new LoadingErrorViewHolder();
                break;
            case VT_HEADER:
                itemViewHolder = header = getHeaderViewHolder();
                if (headerCreatedListener != null) headerCreatedListener.onHeaderCreated(header);
                break;
            case VT_FOOTER:
                itemViewHolder = footer = getFooterViewHolder();
                break;
            default:
                itemViewHolder = onCreateHolder(viewType);
                break;
        }
        itemViewHolder.itemView.setTag(viewType);
        return (VH) itemViewHolder;
    }

    /**
     * The Header created listener.
     */
    HeaderCreatedListener headerCreatedListener;

    /**
     * Sets header created listener.
     *
     * @param headerCreatedListener the header created listener
     */
    public void setHeaderCreatedListener(HeaderCreatedListener headerCreatedListener) {
        this.headerCreatedListener = headerCreatedListener;
    }

    /**
     * The interface Header created listener.
     */
    public interface HeaderCreatedListener {
        /**
         * On header created.
         *
         * @param headerViewHolder the header view holder
         */
        void onHeaderCreated(ItemViewHolder headerViewHolder);
    }

    public abstract VH onCreateHolder(int viewType);


    /**
     * The Binding.
     */
    boolean binding = false;

    /**
     * Is binding boolean.
     *
     * @return the boolean
     */
    public boolean isBinding() {
        return binding;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        binding = true;
        holder.update(position - (isHeader() ? 1 : 0));
        binding = false;

    }

    @Override
    public int getItemViewType(int position) {
        if (isNoItem && position == (isHeader() ? 1 : 0) && !isLoading && !isLoadingError)
            return VT_NO_ITEM;
        if (isLoadingMore && position == getItemCount() - 1) return VT_LOADING_MORE;
        if (isLoadingMoreError && position == getItemCount() - 1) return VT_LOADING_MORE_ERROR;
        if (isLoading() && isLoading && position == (isHeader() ? 1 : 0)) return VT_LOADING;
        if (isFooter() && position == getItemCount() - 1) return VT_FOOTER;
        if (isHeader() && position == 0) return VT_HEADER;
        if (_getItemCount() == 0 && position == (isHeader() ? 1 : 0) && isLoadingError)
            return VT_LOADING_ERROR;
        else return _getItemViewType(position - (isHeader() ? 1 : 0));
    }

    @Override
    public int getItemCount() {

        int countList = _getItemCount();
        int countLoading = isLoading() && isLoading ? 1 : 0;
        int countLoadingError = isLoadingError ? 1 : 0;
        int countNoItem = isNoItem ? 1 : 0;
        int countHeader = isHeader() ? 1 : 0;
        int countFooter = isFooter() ? 1 : 0;
        int countLoadingMore = isLoadingMore ? 1 : 0;
        int countLoadingMoreError = isLoadingMoreError ? 1 : 0;

/*
        Log.d("countList : ",""+countList);
        Log.d("countLoading : ",""+countLoading);
        Log.d("countNoItem : ",""+countNoItem);
        Log.d("countHeader : ",""+countHeader);
        Log.d("countFooter : ",""+countFooter);
        Log.d("countLoadingMore : ",""+countLoadingMore);
        Log.d("countLoadingMoreError",""+countLoadingMoreError);
        Log.d("countLoadingError : ",""+countLoadingError);
*/

        int itemCount = countList + countLoading + countNoItem + countHeader + countFooter + countLoadingMore + countLoadingMoreError + countLoadingError;
        //      Log.d("ITEM_COUNT : ",""+itemCount);
        return itemCount;
    }

    public abstract int _getItemCount();


    /**
     * The type Abstract view holder.
     */
    public abstract static class AbstractViewHolder extends RecyclerView.ViewHolder {

        /**
         * Instantiates a new Abstract view holder.
         *
         * @param itemView the item view
         */
        public AbstractViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        /**
         * Update.
         *
         * @param position the position
         */
        public abstract void update(int position);
    }

    /**
     * The type Item view holder.
     */
    public abstract static class ItemViewHolder extends AbstractViewHolder {

        /**
         * The My adapter.
         */
        MyAdapter myAdapter;

        /**
         * Instantiates a new Item view holder.
         *
         * @param myAdapter        the my adapter
         * @param layoutResourceId the layout resource id
         */
        public ItemViewHolder(MyAdapter myAdapter, int layoutResourceId) {
            super(LayoutInflater.from(myAdapter.parent.getContext()).inflate(layoutResourceId, myAdapter.parent, false));
            this.myAdapter = myAdapter;
        }

        private int bg = 0;

        @Override
        public void update(int position) {
            if (getAdapterPosition() == 0) {
                if (myAdapter.getItemCount() == 1) {
                    if (myAdapter.getBgSingle() != 0)
                        setBb(myAdapter.getBgSingle());
                } else {
                    if (myAdapter.getBgTop() != 0)
                        setBb(myAdapter.getBgTop());
                }
            } else if (getAdapterPosition() == myAdapter.getItemCount() - 1) {
                if (myAdapter.getBgBottom() != 0)
                    setBb(myAdapter.getBgBottom());
            } else {
                if (myAdapter.getBgCenter() != 0)
                    setBb(myAdapter.getBgCenter());
            }
        }

        private void setBb(int bg) {
            if (this.bg != bg) {
                itemView.setBackgroundResource(bg);
                this.bg = bg;
            }
        }


        /**
         * Get adapter position int.
         *
         * @return the int
         */
        public int _getAdapterPosition() {
            //        Log.d("POS", getAdapterPosition() + "");
            //        Log.d("POS_IS_HEADER", myAdapter.isHeader() + "  " + (myAdapter.isHeader() ? 1 : 0));
            return getAdapterPosition() - (myAdapter.isHeader() ? 1 : 0);
        }
    }


    /**
     * The type No item view holder.
     */
    class NoItemViewHolder extends ItemViewHolder {
        /**
         * The Tv no item.
         */
        TextView tvNoItem;

        /**
         * Instantiates a new No item view holder.
         */
        public NoItemViewHolder() {
            super(MyAdapter.this, R.layout.li_no_item);
            tvNoItem = itemView.findViewById(R.id.tvNoItem);
            tvNoItem.setText(getNoItemMessage() != null ? getNoItemMessage() : itemView.getResources().getString(R.string.no_item));
            tvNoItem.setCompoundDrawablesWithIntrinsicBounds(0, getNoItemResource(), 0, 0);
        }

        @Override
        public void update(int position) {
            super.update(position);
        }

    }

    /**
     * The type Loading error view holder.
     */
    class LoadingErrorViewHolder extends ItemViewHolder {
        /**
         * The Tv loading error.
         */
        TextView tvLoadingError;

        /**
         * Instantiates a new Loading error view holder.
         */
        public LoadingErrorViewHolder() {
            super(MyAdapter.this, R.layout.li_loading_error);
            tvLoadingError = itemView.findViewById(R.id.tvLoadingError);
            // tvLoadingError.setText(getNoItemMessage() != null ? getNoItemMessage() : "Error Loading");
            tvLoadingError.setCompoundDrawablesWithIntrinsicBounds(0, getNoItemResource(), 0, 0);
            tvLoadingError.setOnClickListener(MyAdapter.this);
        }

        @Override
        public void update(int position) {
            super.update(position);
        }

    }

    /**
     * The type Loading view holder.
     */
    class LoadingViewHolder extends ItemViewHolder {

        /**
         * Instantiates a new Loading view holder.
         */
        public LoadingViewHolder() {
            super(MyAdapter.this, R.layout.li_loading);
        }

        @Override
        public void update(int position) {
            super.update(position);
        }
    }

    /**
     * The type Loading more view holder.
     */
    class LoadingMoreViewHolder extends ItemViewHolder {

        /**
         * Instantiates a new Loading more view holder.
         */
        public LoadingMoreViewHolder() {
            super(MyAdapter.this, R.layout.li_loading_more);
        }

        @Override
        public void update(int position) {
            super.update(position);
        }
    }

    /**
     * The type Loading more error view holder.
     */
    class LoadingMoreErrorViewHolder extends ItemViewHolder {

        /**
         * The Tv loading more error.
         */
        TextView tvLoadingMoreError;

        /**
         * Instantiates a new Loading more error view holder.
         */
        public LoadingMoreErrorViewHolder() {
            super(MyAdapter.this, R.layout.li_loading_more_error);
            tvLoadingMoreError = itemView.findViewById(R.id.tvLoadingMoreError);
            // tvLoadingMoreError.setText(getNoItemMessage() != null ? getNoItemMessage() : "Error Loading More");
            tvLoadingMoreError.setCompoundDrawablesWithIntrinsicBounds(0, getNoItemResource(), 0, 0);
            tvLoadingMoreError.setOnClickListener(MyAdapter.this);
        }

        @Override
        public void update(int position) {
            super.update(position);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvLoadingMoreError:
                onClickLoad();
                break;
            case R.id.tvLoadingError:
                onClickLoad();
                break;
        }
    }

    @Override
    public int _getItemViewType(int position) {
        return 0;
    }

    @Override
    public boolean isHeader() {
        return false;
    }

    @Override
    public boolean isFooter() {
        return false;
    }

    @Override
    public String getNoItemMessage() {
        return null;
    }

    @Override
    public int getNoItemResource() {
        return 0;
    }

    @Override
    public boolean isLoading() {
        return false;
    }

    @Override
    public ItemViewHolder getHeaderViewHolder() {
        return null;
    }

    @Override
    public ItemViewHolder getFooterViewHolder() {
        return null;
    }

    @Override
    public int getBgSingle() {
        return 0;
    }

    @Override
    public int getBgTop() {
        return 0;
    }

    @Override
    public int getBgCenter() {
        return 0;
    }

    @Override
    public int getBgBottom() {
        return 0;
    }
}
