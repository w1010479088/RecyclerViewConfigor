package com.bruceewu.configor.holder.base;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bruceewu.configor.IConfigor;
import com.bruceewu.configor.R;
import com.bruceewu.configor.entity.CusOnClickListener;
import com.bruceewu.configor.entity.DisplayItem;
import com.bruceewu.configor.entity.IDivider;

public class FlowHolder extends CusBaseHolder implements IDivider {

    public FlowHolder(View rootView) {
        super(rootView);
    }

    @Override
    public int layoutID() {
        return R.layout.holder_flow;
    }

    @Override
    public void renderUI(DisplayItem item, CusOnClickListener listener) {
        ((FlowLayout) mHelper.getView(R.id.flow_layout)).set(15, 10, item.children(), listener);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = IConfigor.configor().dip2px(15);
    }
}
