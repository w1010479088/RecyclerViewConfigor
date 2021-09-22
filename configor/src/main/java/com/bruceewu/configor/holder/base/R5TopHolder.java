package com.bruceewu.configor.holder.base;

import android.view.View;

import com.bruceewu.configor.R;
import com.bruceewu.configor.entity.CusOnClickListener;
import com.bruceewu.configor.entity.DisplayItem;

public class R5TopHolder extends CusBaseHolder {

    public R5TopHolder(View rootView) {
        super(rootView);
    }

    @Override
    public int layoutID() {
        return R.layout.holder_r5_top;
    }

    @Override
    public void renderUI(DisplayItem item, CusOnClickListener listener) {
        setMargin(mHelper.getRootView(), item);
    }
}
