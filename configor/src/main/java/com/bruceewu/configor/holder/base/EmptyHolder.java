package com.bruceewu.configor.holder.base;

import android.view.View;

import com.bruceewu.configor.IConfigor;
import com.bruceewu.configor.R;
import com.bruceewu.configor.entity.CusOnClickListener;
import com.bruceewu.configor.entity.DisplayItem;

public class EmptyHolder extends CusBaseHolder {

    public EmptyHolder(View rootView) {
        super(rootView);
    }

    @Override
    public int layoutID() {
        return R.layout.holder_empty;
    }

    @Override
    public void renderUI(DisplayItem item, CusOnClickListener listener) {
        int height = item.getExtra("height") == null ? 400 : (int) item.getExtra("height");
        int icon = item.getExtra("icon") == null ? 0 : (int) item.getExtra("icon");
        String tip = item.getExtra("tip") == null ? "暂无数据" : (String) item.getExtra("tip");

        mHelper.setImage(R.id.img, IConfigor.configor.defaultEmptyIcon());
        mHelper.setHeight(R.id.root, height);
        mHelper.setText(R.id.tip, tip);
        if (icon == 0) {
            mHelper.setVisibility(R.id.img, false);
        } else {
            mHelper.setVisibility(R.id.img, true);
            mHelper.setImage(R.id.img, icon);
        }
    }
}
