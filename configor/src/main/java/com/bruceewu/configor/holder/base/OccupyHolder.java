package com.bruceewu.configor.holder.base;

import android.view.View;

import com.bruceewu.configor.R;
import com.bruceewu.configor.entity.CusOnClickListener;
import com.bruceewu.configor.entity.DisplayItem;

//占位Holder
public class OccupyHolder extends CusBaseHolder {

    public OccupyHolder(View itemView) {
        super(itemView);
    }

    @Override
    public int layoutID() {
        return R.layout.holder_coupon_occupy;
    }

    @Override
    public void renderUI(DisplayItem item, CusOnClickListener listener) {

    }
}
