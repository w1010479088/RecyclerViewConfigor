package com.bruceewu.configor;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import android.support.v7.widget.RecyclerView;

import com.bruceewu.configor.adapter.CusBaseAdapter;
import com.bruceewu.configor.entity.CusOnClickListener;
import com.bruceewu.configor.entity.DisplayItem;
import com.bruceewu.configor.entity.HasMoreWrapperEntity;
import com.bruceewu.configor.entity.IDivider;
import com.bruceewu.configor.entity.IRefresher;
import com.bruceewu.configor.holder.DefaultHolders;

import java.util.ArrayList;
import java.util.List;

/**
 * List类型View的配置类，方便实现
 */
public class RecyclerViewConfigor {
    private final RecyclerView mRecyclerView;
    private final IRefresher mRefreshLayout;
    private final OnRefreshLoadMoreListener mRefreshLoadMoreListener;
    private final boolean mRefresh;
    private final boolean mLoadMore;
    private final int horizontalPadding;
    private final ScrollType mScrollType;
    private final List<DisplayItem> mUIs = new ArrayList<>();
    private final HasMoreWrapperEntity mHasMore = new HasMoreWrapperEntity();
    private final DisplayItem mFooter = DisplayItem.newItem(DefaultHolders.Footer.showType());

    private int bgColor;
    private int mCurPage = 1;
    private int mSpanSize = 2;
    private boolean mRequesting = false;
    private CusBaseAdapter mAdapter;

    private RecyclerViewConfigor(RecyclerView recyclerView, @Nullable IRefresher refreshLayout, ScrollType type, int spanSize, int horizontalPadding, int bgColor, boolean refresh, boolean loadMore, @Nullable OnRefreshLoadMoreListener refreshLoadMoreListener) {
        this.mRecyclerView = recyclerView;
        this.bgColor = bgColor;
        this.mRefreshLayout = refreshLayout;
        this.mScrollType = type;
        if (spanSize != 0) {
            mSpanSize = spanSize;
        }
        this.horizontalPadding = horizontalPadding;
        this.mRefresh = refresh;
        this.mLoadMore = loadMore;
        this.mRefreshLoadMoreListener = refreshLoadMoreListener;
        this.mFooter.setReserved(mHasMore);
        config();
    }

    public void config(CusOnClickListener listener) {
        if (mLoadMore && mRefreshLoadMoreListener != null) {
            mAdapter.set(item -> {
                if (DefaultHolders.parse(item.showType()) == DefaultHolders.Footer) {
                    footerShow();
                } else {
                    listener.onClick(item);
                }
            });
        } else {
            mAdapter.set(listener);
        }
    }

    public void config(boolean infinite) {
        mAdapter.set(infinite);
    }

    public void config(int bgColor) {
        this.bgColor = bgColor;
        if (this.bgColor != 0) {
            mRecyclerView.setBackgroundColor(this.bgColor);
        }
    }

    public void set(List<DisplayItem> items) {
        set(items, true);
    }

    public void set(List<DisplayItem> items, boolean hasMore) {
        clear();
        add(items, hasMore);
        refreshFinish();
    }

    public void add(List<DisplayItem> items, boolean hasMore) {
        this.mHasMore.setHasMore(hasMore);
        if (mLoadMore && !mUIs.isEmpty()) {//移除Footer
            DisplayItem item = mUIs.get(mUIs.size() - 1);
            if (DefaultHolders.parse(item.showType()) == DefaultHolders.Footer) {
                mUIs.remove(mFooter);
            }
        }
        if (items != null && !items.isEmpty()) {//添加真正的数据
            mUIs.addAll(items);
        }
        if (mLoadMore) {//添加Footer
            mUIs.add(mFooter);
        }
        refresh();
        loadMoreFinish();
    }

    public void refresh() {
        mAdapter.notifyDataSetChanged();
    }

    public void refresh(int pos) {
        mAdapter.notifyItemChanged(pos);
    }

    public List<DisplayItem> uis() {
        return mUIs;
    }

    private void config() {
        mAdapter = new CusBaseAdapter();
        mAdapter.set(mUIs);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                RecyclerView.ViewHolder holder = parent.getChildViewHolder(view);
                if (holder instanceof IDivider) {
                    ((IDivider) holder).getItemOffsets(outRect, view, parent, state);
                }
            }
        });
        config(bgColor);
        if (horizontalPadding != 0) {
            mRecyclerView.setPadding(horizontalPadding, 0, horizontalPadding, 0);
        }
        //刷新加载更多的配置
        if (mRefresh && mRefreshLayout != null && mRefreshLoadMoreListener != null) {
            mRefreshLayout.setOnRefreshListener(() -> {// 延迟刷新，控制恶意刷新
                mRecyclerView.postDelayed(() -> {
                    mCurPage = 1;
                    mHasMore.setHasMore(true);
                    mRequesting = true;
                    mRefreshLoadMoreListener.onRequest(mCurPage);
                }, 1000);
            });
        }
    }

    public void footerShow() {
        if (mRequesting) return;
        if (!mHasMore.hasMore()) return;

        mCurPage++;
        mRefreshLoadMoreListener.onRequest(mCurPage);
        mRequesting = true;
    }

    public void setCurPage(int page) {
        this.mCurPage = page;
    }

    public void setHasMore(boolean hasMore) {
        this.mHasMore.setHasMore(hasMore);
    }

    public boolean isRequesting() {
        return mRequesting;
    }

    private void clear() {
        mCurPage = 1;
        mUIs.clear();
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        switch (mScrollType) {
            case Horizontal:
                return getHorizontalManager();
            case Vertical:
            default:
                return getGridLayoutManager();
        }
    }

    private RecyclerView.LayoutManager getGridLayoutManager() {
        GridLayoutManager layoutManager = new GridLayoutManager(mRecyclerView.getContext(), mSpanSize);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                DisplayItem item = mUIs.get(position);
                if (item == null) return mSpanSize;

                int spanSize = item.spanSize();
                if (spanSize <= 0 || spanSize >= mSpanSize) {
                    return mSpanSize;
                } else {
                    return spanSize;
                }
            }
        });
        return layoutManager;
    }

    private RecyclerView.LayoutManager getHorizontalManager() {
        return new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
    }

    private void refreshFinish() {
        if (mRefreshLayout != null) {
            mRefreshLayout.setRefreshing(false);
        }
    }

    private void loadMoreFinish() {
        this.mRequesting = false;
    }

    private Context getContext() {
        return mRecyclerView.getContext();
    }

    public static class Builder {
        private RecyclerView recyclerView;
        private ScrollType scrollType;
        private CusOnClickListener listener;
        private IRefresher refreshLayout;
        private OnRefreshLoadMoreListener refreshLoadMoreListener;
        private int spanSize;
        private int horizontalPadding;
        private int bgColor;
        private boolean infinite;
        private boolean refresh;
        private boolean loadMore;

        public Builder buildRecyclerView(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
            return this;
        }

        public Builder buildBgColor(int bgColor) {
            this.bgColor = bgColor;
            return this;
        }

        public Builder buildSwipeRefresh(IRefresher refreshLayout) {
            this.refreshLayout = refreshLayout;
            return this;
        }

        public Builder buildScrollType(ScrollType scrollType) {
            this.scrollType = scrollType;
            return this;
        }

        public Builder buildSpanSize(int spanSize) {
            this.spanSize = spanSize;
            return this;
        }

        public Builder buildHorizontalPadding(int horizontalPadding) {
            this.horizontalPadding = horizontalPadding;
            return this;
        }

        public Builder buildClickListener(CusOnClickListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder buildInfinite(boolean infinite) {
            this.infinite = infinite;
            return this;
        }

        public Builder buildRefresh(boolean refresh) {
            this.refresh = refresh;
            return this;
        }

        public Builder buildLoadMore(boolean loadMore) {
            this.loadMore = loadMore;
            return this;
        }

        public Builder builderRefreshLoadMoreListener(OnRefreshLoadMoreListener listener) {
            this.refreshLoadMoreListener = listener;
            return this;
        }

        public RecyclerViewConfigor build() {
            RecyclerViewConfigor configor = new RecyclerViewConfigor(recyclerView, refreshLayout, scrollType, spanSize, horizontalPadding, bgColor, refresh, loadMore, refreshLoadMoreListener);
            configor.config(listener);
            configor.config(infinite);
            return configor;
        }
    }

    public interface OnRefreshLoadMoreListener {
        void onRequest(int curPage);
    }

    public enum ScrollType {
        Horizontal,
        Vertical
    }
}
