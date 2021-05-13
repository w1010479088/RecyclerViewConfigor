package com.bruceewu.configor.holder.base;

import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;

import com.bruceewu.configor.entity.CusOnClickListener;
import com.bruceewu.configor.entity.DisplayItem;
import com.bruceewu.configor.helper.ViewHelper;

/**
 * Holder的基类
 */
public abstract class CusBaseHolder extends RecyclerView.ViewHolder {
    protected ViewHelper mHelper;

    public CusBaseHolder(View rootView) {
        super(rootView);
        mHelper = new ViewHelper(rootView);
        addWindowListener();
    }

    public View getRootView() {
        return itemView;
    }

    public abstract int layoutID();

    public abstract void renderUI(DisplayItem item, CusOnClickListener listener);

    protected void onAttachToWindow() {

    }

    protected void onDetachFromWindow() {

    }

    protected void reSize(DisplayItem item, View view) {
        int width = item.width();
        int height = item.height();
        if (width != 0 && height != 0) {
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.width = width;
            params.height = height;
            view.setLayoutParams(params);
        }
    }

    private void addWindowListener() {
        itemView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View view) {
                onAttachToWindow();
            }

            @Override
            public void onViewDetachedFromWindow(View view) {
                onDetachFromWindow();
            }
        });
    }
}
