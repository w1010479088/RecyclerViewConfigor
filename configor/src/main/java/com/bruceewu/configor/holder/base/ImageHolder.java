package com.bruceewu.configor.holder.base;


import android.view.View;

import com.bruceewu.configor.R;
import com.bruceewu.configor.entity.CusOnClickListener;
import com.bruceewu.configor.entity.DisplayItem;

public class ImageHolder extends CusBaseHolder {

    public ImageHolder(View rootView) {
        super(rootView);
    }

    @Override
    public int layoutID() {
        return R.layout.holder_image;
    }

    @Override
    public void renderUI(DisplayItem item, CusOnClickListener listener) {
        reSize(item, mHelper.getView(R.id.image));
        Object radiusObj = item.getExtra("radius");
        int radius = 0;
        if (radiusObj != null) {
            radius = (int) radiusObj;
        }
        mHelper.setImage(R.id.image, item.showData(), radius);
        mHelper.setClick(R.id.image, () -> listener.onClick(item));
    }
}
