package com.bruceewu.configor.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.bruceewu.configor.R;
import com.bruceewu.configor.entity.IStatusLayout;

public class StatusLayout extends FrameLayout implements IStatusLayout {
    private View mContentView;
    private Runnable mRetryListener;

    public StatusLayout(@NonNull Context context) {
        this(context, null);
    }

    public StatusLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.widget_status_layout, this);
        findViewById(R.id.error_retry).setOnClickListener(v -> {
            if (mRetryListener != null) {
                mRetryListener.run();
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 2) {
            throw new RuntimeException("StatusFrameLayout 只能包含1个Child");
        }
        mContentView = getChildAt(1);
    }

    public void setEmptyBg(int color) {
        findViewById(R.id.empty).setBackgroundColor(getContext().getResources().getColor(color));
    }

    public void setRetryListener(Runnable listener) {
        this.mRetryListener = listener;
    }

    @Override
    public void switchLoading() {
        switchStatus(StatusType.Loading);
    }

    @Override
    public void switchNormal() {
        switchStatus(StatusType.Normal);
    }

    @Override
    public void switchEmpty() {
        switchStatus(StatusType.Empty);
    }

    @Override
    public void switchError() {
        switchStatus(StatusType.Error);
    }

    private void switchStatus(StatusType type) {
        mContentView.setVisibility(type.showNormal ? View.VISIBLE : View.INVISIBLE);
        setVisi(R.id.loading, type.showLoading);
        setVisi(R.id.empty, type.showEmpty);
        setVisi(R.id.error, type.showError);
    }

    private void setVisi(int viewID, boolean visi) {
        findViewById(viewID).setVisibility(visi ? View.VISIBLE : View.INVISIBLE);
    }

    private enum StatusType {
        Normal(true, false, false, false),
        Loading(false, true, false, false),
        Empty(false, false, true, false),
        Error(false, false, false, true);

        private final boolean showNormal;
        private final boolean showLoading;
        private final boolean showEmpty;
        private final boolean showError;

        StatusType(boolean showNormal, boolean showLoading, boolean showEmpty, boolean showError) {
            this.showNormal = showNormal;
            this.showLoading = showLoading;
            this.showEmpty = showEmpty;
            this.showError = showError;
        }
    }
}
