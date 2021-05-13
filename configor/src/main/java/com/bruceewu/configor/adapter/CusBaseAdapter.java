package com.bruceewu.configor.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.bruceewu.configor.entity.CusOnClickListener;
import com.bruceewu.configor.entity.DisplayItem;
import com.bruceewu.configor.helper.ErrorLogger;
import com.bruceewu.configor.holder.base.CusBaseHolder;
import com.bruceewu.configor.entity.HolderParser;

import java.util.List;

public class CusBaseAdapter extends RecyclerView.Adapter<CusBaseHolder> {
    public static final int MULTIPLY_FACTOR = 100000;
    private boolean mInfinite;
    private List<DisplayItem> mItems;
    private CusOnClickListener mListener;

    public void set(boolean infinite) {
        this.mInfinite = infinite;
    }

    public void set(List<DisplayItem> items) {
        this.mItems = items;
    }

    public void set(CusOnClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public CusBaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return HolderParser.newHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull CusBaseHolder holder, int position) {
        try {
            holder.renderUI(mItems.get(fixPos(mInfinite, position, realSize())), mListener);
        } catch (Exception ex) {
            ErrorLogger.logError(ex);
        }
    }

    @Override
    public int getItemCount() {
        return infinite() ? realSize() * MULTIPLY_FACTOR : realSize();
    }

    @Override
    public int getItemViewType(int position) {
        try {
            String showType = mItems.get(fixPos(mInfinite, position, realSize())).showType();
            return HolderParser.showType2ViewType(showType);
        } catch (Exception ex) {
            ErrorLogger.logError(ex);
            return 0;
        }
    }

    public static int fixPos(boolean infinite, int pos, int realSize) {
        if (infinite) {
            return pos % realSize;
        } else {
            return pos;
        }
    }

    private int realSize() {
        return mItems.size();
    }

    private boolean infinite() {
        return mInfinite;
    }
}
