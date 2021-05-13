package com.bruceewu.configor.holder.base;

import android.view.View;

import com.bruceewu.configor.R;
import com.bruceewu.configor.entity.CusOnClickListener;
import com.bruceewu.configor.entity.DisplayItem;

public class R10EndHolder extends CusBaseHolder {
    public R10EndHolder(View rootView) {
        super(rootView);
    }

    @Override
    public int layoutID() {
        return R.layout.holder_r10_end;
    }

    @Override
    public void renderUI(DisplayItem item, CusOnClickListener listener) {
        mHelper.setWidth(R.id.root, item.width());
        mHelper.setHeight(R.id.root, item.height());
    }
}
