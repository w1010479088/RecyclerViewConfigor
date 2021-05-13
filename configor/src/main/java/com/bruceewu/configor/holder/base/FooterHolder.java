package com.bruceewu.configor.holder.base;

import android.view.View;

import com.bruceewu.configor.R;
import com.bruceewu.configor.entity.CusOnClickListener;
import com.bruceewu.configor.entity.DisplayItem;
import com.bruceewu.configor.entity.HasMoreWrapperEntity;

public class FooterHolder extends CusBaseHolder {

    public FooterHolder(View rootView) {
        super(rootView);
    }

    @Override
    public int layoutID() {
        return R.layout.holder_footer;
    }

    @Override
    public void renderUI(DisplayItem item, CusOnClickListener listener) {
        Object reserved = item.reserved();
        if (reserved instanceof HasMoreWrapperEntity) {
            boolean hasMore = ((HasMoreWrapperEntity) reserved).hasMore();
            mHelper.setInVisibility(R.id.loading, hasMore);
            mHelper.setInVisibility(R.id.tip, hasMore);
        }
        listener.onClick(item);
    }
}
