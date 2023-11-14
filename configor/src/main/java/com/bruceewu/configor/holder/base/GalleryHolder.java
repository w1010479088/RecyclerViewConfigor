package com.bruceewu.configor.holder.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.webkit.ValueCallback;

import com.bruceewu.configor.IConfigor;
import com.bruceewu.configor.R;
import com.bruceewu.configor.adapter.CusBaseAdapter;
import com.bruceewu.configor.entity.CusOnClickListener;
import com.bruceewu.configor.entity.DisplayItem;
import com.bruceewu.configor.helper.ThreadPool;
import com.bruceewu.configor.holder.DefaultHolders;

import java.util.ArrayList;
import java.util.List;

public class GalleryHolder extends HorizontalHolder implements ThreadPool.IUpdater {
    public static final String INDICATOR_TYPE_DOT = "dot";
    public static final String INDICATOR_TYPE_NUM = "num";

    private static final int TIME_DEFAULT = 3;
    private boolean mInfinite;
    private boolean mNeedAutoScroll;
    private boolean mHideIndicator;
    private String mIndicatorType;
    private int mRealSize;
    private int mTimerUpdate;
    private int mTimer = TIME_DEFAULT;
    private PagerSnapHelper mSnapHelper;
    private ValueCallback<Object> mLoopCallBack;

    public static DisplayItem newInstance(float scale, List<Pair<String, Object>> items) {
        int width = IConfigor.configor().getScreenWidth() - IConfigor.configor().dip2px(15 * 2);
        int height = (int) (width * scale);

        List<DisplayItem> banners = new ArrayList<>();
        for (Pair<String, Object> banner : items) {
            DisplayItem item = DisplayItem.newItem(DefaultHolders.Image.showType());
            item.setShowData(banner.first);
            item.putExtra("obj", banner.second);
            item.putExtra("radius", 10);
            item.setWidth(width);
            item.setHeight(height);
            banners.add(item);
        }
        boolean needScroll = banners.size() > 1;

        return new GalleryHolder
                .Builder()
                .setWidth(width)
                .setHeight(height)
                .setInfinite(needScroll)
                .setAutoScroll(needScroll)
                .setTime(TIME_DEFAULT)
                .setHideIndicator(false)
                .setIndicatorType(INDICATOR_TYPE_DOT)
                .setChildren(banners)
                .build();
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
            IConfigor.configor().getLogger().log(ex.getMessage());
        }
    }

    @Override
    public void renderUI(DisplayItem item, CusOnClickListener listener) {
        configInfinite(item);
        super.renderUI(item, listener);

        mRealSize = item.children().size();
        boolean showDotIndicator = showDotIndicator();
        if (showDotIndicator) {
            CusIndicatorView indicatorView = mHelper.getView(R.id.indicator_dot);
            indicatorView.setSize(mRealSize);
        }
        mHelper.setVisibility(R.id.indicator_dot, showDotIndicator);
        mHelper.setVisibility(R.id.indicator_num, showNumIndicator());

        int realStart = 0;
        int resetPos = fixStart(realStart);
        setPos(realStart);
        getRecyclerView().scrollToPosition(resetPos);
    }

    private boolean showDotIndicator() {
        return !mHideIndicator && mRealSize != 1 && TextUtils.equals(mIndicatorType, INDICATOR_TYPE_DOT);
    }

    private boolean showNumIndicator() {
        return !mHideIndicator && mRealSize != 1 && TextUtils.equals(mIndicatorType, INDICATOR_TYPE_NUM);
    }

    private void setPos(int pos) {
        int realPos = fixPos(pos);
        if (showDotIndicator()) {
            CusIndicatorView indicatorView = mHelper.getView(R.id.indicator_dot);
            indicatorView.setCur(realPos);
        } else if (showNumIndicator()) {
            mHelper.setText(R.id.indicator_num, String.format("%d/%d", (realPos + 1), mRealSize));
        }
        if (mInfinite && mLoopCallBack != null) {
            mLoopCallBack.onReceiveValue(realPos);
        }
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
        Object timeObj = item.getExtra("time");
        Object hideIndicatorObj = item.getExtra("hide_indicator");
        Object indicatorTypeObj = item.getExtra("indicator_type");
        Object loopCallBack = item.getExtra("loop_callback");
        if (infiniteObj != null) {
            mInfinite = (boolean) infiniteObj;
        }
        if (autoScrollObj != null) {
            mNeedAutoScroll = (boolean) autoScrollObj;
        }
        if (timeObj != null) {
            mTimer = (int) timeObj;
            if (mTimer == 0) {
                mTimer = TIME_DEFAULT;
            }
        }
        if (hideIndicatorObj != null) {
            mHideIndicator = (boolean) hideIndicatorObj;
        }
        if (indicatorTypeObj != null) {
            mIndicatorType = (String) indicatorTypeObj;
        } else {
            mIndicatorType = INDICATOR_TYPE_DOT;
        }
        if (loopCallBack instanceof ValueCallback) {
            mLoopCallBack = (ValueCallback<Object>) loopCallBack;
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
            if (mTimerUpdate % mTimer == 0) {
                mTimerUpdate = 0;
                View curView = mSnapHelper.findSnapView(getRecyclerView().getLayoutManager());
                int pos = getRecyclerView().getLayoutManager().getPosition(curView);
                getRecyclerView().smoothScrollToPosition(++pos);
            }
        }
    }

    public static class Builder {
        private final DisplayItem gallery = DisplayItem.newItem(DefaultHolders.Gallery.showType());

        public Builder setWidth(int width) {
            gallery.setWidth(width);
            return this;
        }

        public Builder setHeight(int height) {
            gallery.setHeight(height);
            return this;
        }

        public Builder setInfinite(boolean infinite) {
            gallery.putExtra("infinite", infinite);
            return this;
        }

        public Builder setAutoScroll(boolean autoScroll) {
            gallery.putExtra("auto_scroll", autoScroll);
            return this;
        }

        public Builder setHideIndicator(boolean hideIndicator) {
            gallery.putExtra("hide_indicator", hideIndicator);
            return this;
        }

        //目前有两种,中部的小点,和右下角的数字3/10模式,后续可以添加
        //type = INDICATOR_TYPE_DOT,INDICATOR_TYPE_NUM
        public Builder setIndicatorType(String type) {
            gallery.putExtra("indicator_type", type);
            return this;
        }

        public Builder setTime(int time) {
            gallery.putExtra("time", time);
            return this;
        }

        public Builder setChildren(List<DisplayItem> children) {
            gallery.setChildren(children);
            return this;
        }

        public Builder setLoopCallBack(ValueCallback<Object> callBack) {
            gallery.putExtra("loop_callback", callBack);
            return this;
        }

        public DisplayItem build() {
            return gallery;
        }
    }
}
