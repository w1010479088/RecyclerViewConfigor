package com.bruceewu.configor.holder.base;

import android.view.View;

import android.support.v7.widget.RecyclerView;

import com.bruceewu.configor.IConfigor;
import com.bruceewu.configor.R;
import com.bruceewu.configor.RecyclerViewConfigor;
import com.bruceewu.configor.entity.CusOnClickListener;
import com.bruceewu.configor.entity.DisplayItem;

public class HorizontalHolder extends CusBaseHolder {
    private RecyclerViewConfigor mConfigor;

    public HorizontalHolder(View rootView) {
        super(rootView);
        try {
            mConfigor = new RecyclerViewConfigor
                    .Builder()
                    .buildRecyclerView(getRecyclerView())
                    .buildScrollType(RecyclerViewConfigor.ScrollType.Horizontal)
                    .build();
        } catch (Exception ex) {
            IConfigor.configor.getLogger().log("HorizontalHolder Exception -> " + ex.getMessage());
        }
    }

    protected RecyclerView getRecyclerView() {
        return (RecyclerView) getRootView();
    }

    protected RecyclerViewConfigor getConfigor() {
        return mConfigor;
    }

    @Override
    public int layoutID() {
        return R.layout.holder_horizontal;
    }

    @Override
    public void renderUI(DisplayItem item, CusOnClickListener listener) {
        reSize(item, getRootView());
        Object nestScrollObj = item.getExtra("nest_scroll");
        if (nestScrollObj != null) {
            boolean nestScroll = (boolean) nestScrollObj;
            getRecyclerView().setNestedScrollingEnabled(nestScroll); // 防止协同布局出现滑动冲突
        }
        Object colorObj = item.getExtra("bg_color");
        if (colorObj != null) {
            int bgColor = (int) colorObj;
            mConfigor.config(bgColor);
        }
        mConfigor.config(listener);
        mConfigor.set(item.children());
    }
}
