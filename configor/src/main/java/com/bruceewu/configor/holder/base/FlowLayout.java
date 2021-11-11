package com.bruceewu.configor.holder.base;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bruceewu.configor.IConfigor;
import com.bruceewu.configor.entity.CusOnClickListener;
import com.bruceewu.configor.entity.DisplayItem;
import com.bruceewu.configor.entity.HolderParser;

import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup {
    private int mSpaceHor, mSpaceVer;
    private CusOnClickListener mListener;
    private List<DisplayItem> mItems;
    private final List<CusBaseHolder> mUIs = new ArrayList<>();

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void set(int spaceDpHor, int spaceDpVer, List<DisplayItem> items, CusOnClickListener listener) {
        this.mSpaceHor = IConfigor.configor().dip2px(spaceDpHor);
        this.mSpaceVer = IConfigor.configor().dip2px(spaceDpVer);
        this.mItems = items;
        this.mListener = listener;
        refresh();
    }

    private void refresh() {
        removeAllViews();
        mUIs.clear();
        parseHolders();
        addViews();
        requestLayout();
    }

    private void parseHolders() {
        if (mItems != null) {
            for (DisplayItem item : mItems) {
                CusBaseHolder holder = HolderParser.newHolder(this, item.showType());
                holder.renderUI(item, mListener);
                mUIs.add(holder);
            }
        }
    }

    private void addViews() {
        for (CusBaseHolder holder : mUIs) {
            addView(holder.getRootView());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
        setMeasuredDimension(getMeasuredWidth(), getParentHeight());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        reLayout();
    }

    private void reLayout() {
        int paddingStart = getPaddingStart();
        int paddingEnd = getPaddingEnd();
        int paddingTop = getPaddingTop();

        int validWidth = getMeasuredWidth() - paddingStart - paddingEnd;
        int l = paddingStart;
        int t = paddingTop;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            if (l + width > validWidth) {
                l = paddingStart;
                t += height + mSpaceVer;
            }
            child.layout(l, t, l + width, t + height);
            l += width + mSpaceHor;
        }
    }

    private int getParentHeight() {
        int paddingStart = getPaddingStart();
        int paddingEnd = getPaddingEnd();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int validWidth = getMeasuredWidth() - paddingStart - paddingEnd;
        int l = paddingStart;
        int t = paddingTop;
        int itemHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            itemHeight = height;
            if (l + width > validWidth) {
                l = paddingStart;
                t += height + mSpaceVer;
            }
            l += width + mSpaceHor;
        }
        return t + itemHeight + paddingBottom;
    }

    private void log(String content) {
        Log.d("tag_log_xyf", content);
    }
}
