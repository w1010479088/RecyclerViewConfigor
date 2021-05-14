package com.bruceewu.configor.holder.base;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bruceewu.configor.IConfigor;
import com.bruceewu.configor.R;
import com.bruceewu.configor.entity.CusOnClickListener;
import com.bruceewu.configor.entity.DisplayItem;
import com.bruceewu.configor.entity.IDivider;

public class R10TopHolder extends CusBaseHolder implements IDivider {
    
    public R10TopHolder(View rootView) {
        super(rootView);
    }

    @Override
    public int layoutID() {
        return R.layout.holder_r10_top;
    }

    @Override
    public void renderUI(DisplayItem item, CusOnClickListener listener) {

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = IConfigor.configor().dip2px(10);
    }
}
