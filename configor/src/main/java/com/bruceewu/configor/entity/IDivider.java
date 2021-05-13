package com.bruceewu.configor.entity;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 配置分割线的参数，写Holder的时候去实现它即可配置
 */
public interface IDivider {
    void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state);
}
