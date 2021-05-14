package com.bruceewu.configor.holder.base;

import android.view.View;

import com.bruceewu.configor.IConfigor;
import com.bruceewu.configor.R;
import com.bruceewu.configor.entity.CusOnClickListener;
import com.bruceewu.configor.entity.DisplayItem;

public class DividerHolder extends CusBaseHolder {
    private final int defaultfColor = IConfigor.configor().defaultBgColor();
    private final int defaultHeight = IConfigor.configor().dip2px(10);

    public DividerHolder(View rootView) {
        super(rootView);
    }

    @Override
    public int layoutID() {
        return R.layout.holder_divider;
    }

    @Override
    public void renderUI(DisplayItem item, CusOnClickListener listener) {
        int height = item.height();
        int width = item.width();
        Object colorObj = item.getExtra("bg_color");
        Object marginStartObj = item.getExtra("margin_start");
        Object marginEndObj = item.getExtra("margin_end");

        if (colorObj != null) {
            int color = (int) colorObj;
            mHelper.setBGColor(R.id.root, color);
        } else {
            mHelper.setBGColor(R.id.root, defaultfColor);
        }

        if (width != 0) {
            mHelper.setWidth(R.id.root, width);
        }

        if (height != 0) {
            mHelper.setHeight(R.id.root, height);
        } else {
            mHelper.setHeight(R.id.root, defaultHeight);
        }

        if (marginStartObj != null) {
            int marginStart = (int) marginStartObj;
            mHelper.setMarginStart(R.id.root, marginStart);
        } else {
            mHelper.setMarginStart(R.id.root, 0);
        }
        if (marginEndObj != null) {
            int marginEnd = (int) marginEndObj;
            mHelper.setMarginEnd(R.id.root, marginEnd);
        } else {
            mHelper.setMarginEnd(R.id.root, 0);
        }
    }
}
