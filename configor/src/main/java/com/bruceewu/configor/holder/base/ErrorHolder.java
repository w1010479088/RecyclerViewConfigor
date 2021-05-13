package com.bruceewu.configor.holder.base;

import android.view.View;

import com.bruceewu.configor.R;
import com.bruceewu.configor.entity.CusOnClickListener;
import com.bruceewu.configor.entity.DisplayItem;

public class ErrorHolder extends CusBaseHolder {

    public ErrorHolder(View rootView) {
        super(rootView);
    }

    @Override
    public int layoutID() {
        return R.layout.holder_error;
    }

    @Override
    public void renderUI(DisplayItem item, CusOnClickListener listener) {

    }
}
