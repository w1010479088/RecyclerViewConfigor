package com.bruceewu.configor.holder.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bruceewu.configor.IConfigor;
import com.bruceewu.configor.R;
import com.bruceewu.configor.adapter.CusBaseAdapter;
import com.bruceewu.configor.entity.CusOnClickListener;
import com.bruceewu.configor.entity.DisplayItem;
import com.bruceewu.configor.entity.NormalSchemaor;
import com.bruceewu.configor.helper.ThreadPool;
import com.bruceewu.configor.holder.DefaultHolders;

import java.util.ArrayList;
import java.util.List;

public class GalleryHolder extends HorizontalHolder implements ThreadPool.IUpdater {
    private boolean mInfinite;
    private boolean mNeedAutoScroll;
    private int mRealSize;
    private int mTimerUpdate;
    private PagerSnapHelper mSnapHelper;

    public static DisplayItem newInstance(float scale, List<NormalSchemaor> items) {
        DisplayItem gallery = DisplayItem.newItem(DefaultHolders.Gallery.showType());
        int width = IConfigor.configor.getScreenWidth() - IConfigor.configor.dip2px(15 * 2);
        int height = (int) (width * scale);
        gallery.setWidth(width);
        gallery.setHeight(height);

        List<DisplayItem> banners = new ArrayList<>();
        for (NormalSchemaor banner : items) {
            DisplayItem item = DisplayItem.newItem(DefaultHolders.Image.showType());
            item.setShowData(banner.img);
            item.setISchemaor(banner);
            item.setWidth(width);
            item.setHeight(height);
            item.putExtra("radius", 10);
            banners.add(item);
        }

        boolean needScroll = banners.size() > 1;
        gallery.putExtra("infinite", needScroll);
        gallery.putExtra("auto_scroll", needScroll);
        gallery.setChildren(banners);
        return gallery;
    }

    @Override
    public int layoutID() {
        return R.layout.holder_gallery;
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return getRootView().findViewById(R.id.list);
    }

    public GalleryHolder(View rootView) {
        super(rootView);
        try {
            mSnapHelper = new PagerSnapHelper();
            mSnapHelper.attachToRecyclerView((RecyclerView) getRecyclerView());
            getRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        View curView = mSnapHelper.findSnapView(recyclerView.getLayoutManager());
                        int pos = recyclerView.getLayoutManager().getPosition(curView);
                        setPos(pos);
                    }

                    if (mNeedAutoScroll) {
                        if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                            mTimerUpdate = 0;
                        }
                    }
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                }
            });
        } catch (Exception ex) {
            IConfigor.configor.getLogger().log(ex.getMessage());
        }
    }

    @Override
    public void renderUI(DisplayItem item, CusOnClickListener listener) {
        configInfinite(item);
        super.renderUI(item, listener);

        mRealSize = item.children().size();
        CusIndicatorView indicatorView = mHelper.getView(R.id.indicator);
        indicatorView.setSize(mRealSize);
        mHelper.setVisibility(R.id.indicator, mRealSize != 1);

        int realStart = 0;
        int resetPos = fixStart(realStart);
        setPos(realStart);
        getRecyclerView().scrollToPosition(resetPos);
    }

    private void setPos(int pos) {
        CusIndicatorView indicatorView = mHelper.getView(R.id.indicator);
        indicatorView.setCur(fixPos(pos));
    }

    private int fixPos(int pos) {
        return CusBaseAdapter.fixPos(mInfinite, pos, mRealSize);
    }

    private int fixStart(int realStart) {
        if (mInfinite) {
            return realStart + mRealSize * CusBaseAdapter.MULTIPLY_FACTOR / 2;
        } else {
            return 0;
        }
    }

    private void configInfinite(DisplayItem item) {
        Object infiniteObj = item.getExtra("infinite");
        Object autoScrollObj = item.getExtra("auto_scroll");
        if (infiniteObj != null) {
            mInfinite = (boolean) infiniteObj;
        }
        if (autoScrollObj != null) {
            mNeedAutoScroll = (boolean) autoScrollObj;
        }
        getConfigor().config(mInfinite);
    }

    @Override
    protected void onAttachToWindow() {
        if (mNeedAutoScroll) {
            ThreadPool.registerObserver(this);
        }
    }

    @Override
    protected void onDetachFromWindow() {
        if (mNeedAutoScroll) {
            ThreadPool.unRegisterObserver(this);
        }
    }

    @Override
    public void update() {
        if (mNeedAutoScroll) {
            mTimerUpdate++;
            if (mTimerUpdate % 3 == 0) {
                View curView = mSnapHelper.findSnapView(getRecyclerView().getLayoutManager());
                int pos = getRecyclerView().getLayoutManager().getPosition(curView);
                getRecyclerView().smoothScrollToPosition(++pos);
            }
        }
    }
}
