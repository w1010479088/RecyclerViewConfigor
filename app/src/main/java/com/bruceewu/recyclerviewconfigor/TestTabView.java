package com.bruceewu.recyclerviewconfigor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TestTabView extends LinearLayout {
    public TestTabView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.widget_tab, this);
    }

    public void set(String title) {
        ((TextView) findViewById(R.id.title)).setText(title);
    }

    public String get() {
        return ((TextView) findViewById(R.id.title)).getText().toString();
    }

    public void set(boolean sel) {
        findViewById(R.id.indicator).setVisibility(sel ? View.VISIBLE : View.INVISIBLE);
    }
}
