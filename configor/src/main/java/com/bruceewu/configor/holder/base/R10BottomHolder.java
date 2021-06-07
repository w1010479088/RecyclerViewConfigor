package com.bruceewu.configor.holder.base;

import android.view.View;

import com.bruceewu.configor.R;
import com.bruceewu.configor.entity.CusOnClickListener;
import com.bruceewu.configor.entity.DisplayItem;

public class R10BottomHolder extends CusBaseHolder {
    public R10BottomHolder(View rootView) {
        super(rootView);
    }

    @Override
    public int layoutID() {
        return R.layout.holder_r10_bottom;
    }

    @Override
    public void renderUI(DisplayItem item, CusOnClickListener listener) {
        R10TopHolder.setMargin(mHelper.getRootView(), item);
    }
}
