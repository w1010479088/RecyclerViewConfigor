package com.bruceewu.configor.holder.base;

import android.view.View;

import com.bruceewu.configor.R;
import com.bruceewu.configor.entity.CusOnClickListener;
import com.bruceewu.configor.entity.DisplayItem;

public class R10StartHolder extends CusBaseHolder {
    public R10StartHolder(View rootView) {
        super(rootView);
    }

    @Override
    public int layoutID() {
        return R.layout.holder_r10_start;
    }

    @Override
    public void renderUI(DisplayItem item, CusOnClickListener listener) {
        mHelper.setWidth(R.id.root, item.width());
        mHelper.setHeight(R.id.root, item.height());
    }
}
