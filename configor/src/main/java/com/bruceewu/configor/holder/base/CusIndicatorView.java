package com.bruceewu.configor.holder.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bruceewu.configor.IConfigor;
import com.bruceewu.configor.R;

public class CusIndicatorView extends RelativeLayout {
    private final int[] IDS = {R.id.dot_1, R.id.dot_2, R.id.dot_3, R.id.dot_4, R.id.dot_5, R.id.dot_6, R.id.dot_7, R.id.dot_8, R.id.dot_9};

    public CusIndicatorView(Context context) {
        this(context, null);
    }

    public CusIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CusIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.widget_cus_indicator, this);
    }

    public void setSize(int size) {
        for (int i = 0; i < IDS.length; i++) {
            findViewById(IDS[i]).setVisibility(i < size ? View.VISIBLE : View.GONE);
        }
    }

    public void setCur(int pos) {
        for (int i = 0; i < IDS.length; i++) {
            findViewById(IDS[i]).setSelected(i == pos);
            View view = findViewById(IDS[i]);
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.width = (i == pos) ? IConfigor.configor().dip2px(12) : IConfigor.configor().dip2px(4);
            view.setLayoutParams(params);
        }
    }
}
