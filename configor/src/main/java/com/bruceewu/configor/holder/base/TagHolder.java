package com.bruceewu.configor.holder.base;

import android.view.View;

import com.bruceewu.configor.R;
import com.bruceewu.configor.entity.CusOnClickListener;
import com.bruceewu.configor.entity.DisplayItem;

public class TagHolder extends CusBaseHolder {
    public TagHolder(View rootView) {
        super(rootView);
    }

    @Override
    public int layoutID() {
        return R.layout.holder_tag;
    }

    @Override
    public void renderUI(DisplayItem item, CusOnClickListener listener) {
        mHelper.setText(R.id.title, item.showData());
        mHelper.setClick(R.id.title, () -> listener.onClick(item));
    }
}
