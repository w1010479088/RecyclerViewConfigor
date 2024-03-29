package com.bruceewu.configor.holder.base;

import android.support.v7.widget.RecyclerView;
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
        if (width != 0) {
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.width = width;
            view.setLayoutParams(params);
        }
        if (height != 0) {
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = height;
            view.setLayoutParams(params);
        }
        setMargin(view, item);
        setBg(view, item);
    }

    protected void setBg(View view, DisplayItem item) {
        Object colorObj = item.getExtra("bg_color");
        if (colorObj != null) {
            int bgColor = (int) colorObj;
            view.setBackgroundColor(bgColor);
        }

        Object drawableObj = item.getExtra("bg_drawable");
        if (drawableObj != null) {
            int bgDrawable = (int) drawableObj;
            view.setBackgroundResource(bgDrawable);
        }
    }

    protected void setMargin(View view, DisplayItem item) {
        Object marginObj = item.getExtra("margin_hor");
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if (marginObj == null) {
            params.setMarginStart(0);
            params.setMarginEnd(0);
        } else {
            int margin = (int) marginObj;
            params.setMarginStart(margin);
            params.setMarginEnd(margin);
        }
        view.setLayoutParams(params);
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
