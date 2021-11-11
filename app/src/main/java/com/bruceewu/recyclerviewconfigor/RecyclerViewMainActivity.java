package com.bruceewu.recyclerviewconfigor;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bruceewu.configor.IConfigor;
import com.bruceewu.configor.TabManager;
import com.bruceewu.configor.entity.DisplayItem;
import com.bruceewu.configor.helper.ErrorLogger;
import com.bruceewu.configor.holder.DefaultHolders;
import com.bruceewu.configor.holder.base.FlowLayout;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_main);

        testTab();
        testFlow();
    }

    private void testTab() {
        IConfigor.init(new IConfigor() {
            @Override
            public void showSingleImageText(TextView view, String imgUrl, String text, int start, int end) {

            }

            @Override
            public void loadImage(ImageView view, String url) {

            }

            @Override
            public void loadRoundImageByDp(ImageView view, String url, int radius) {

            }

            @Override
            public void setImageRTop(ImageView view, String url, int radiusDP) {

            }

            @Override
            public void clearImage(ImageView view) {

            }

            @Override
            public ErrorLogger getLogger() {
                return null;
            }

            @Override
            public int loadingSize() {
                return 0;
            }

            @Override
            public int dip2px(int dp) {
                return dp * 2;
            }

            @Override
            public int getScreenWidth() {
                return 1080;
            }

            @Override
            public int defaultBgColor() {
                return 0;
            }

            @Override
            public int defaultEmptyIcon() {
                return 0;
            }

            @Override
            public int colorIndicator() {
                return R.color.color_indicator;
            }

            @Override
            public int colorUnselTabText() {
                return R.color.teal_700;
            }

            @Override
            public int colorSelTabText() {
                return R.color.color_indicator;
            }

            @Override
            public void setSchemaor(DisplayItem item, Object schemaor) {

            }
        });
        TabManager manager = new TabManager(findViewById(R.id.tab_layout), findViewById(R.id.view_pager), true, getSupportFragmentManager());
        manager.setCusView(new TabManager.ICusView() {
            @Override
            public View createCusView(Context context) {
                return new TestTabView(context);
            }

            @Override
            public void setTitle(View cusView, String title) {
                ((TestTabView) cusView).set(title);
            }

            @Override
            public void tabSel(View cusView, boolean sel) {
                ((TestTabView) cusView).set(sel);
            }

            @Override
            public String getTabTitle(View cusView) {
                return ((TestTabView) cusView).get();
            }

        });
        List<Pair<String, Fragment>> pages = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            pages.add(new Pair<>("大俊子" + i, new TestFragment()));
        }
        manager.config(pages, pos -> {
            LogUtils.log(String.valueOf(pos));
        });
    }

    private void testFlow() {
        FlowLayout layout = findViewById(R.id.flow);
        List<DisplayItem> items = new ArrayList<>();
        items.add(newTag("大俊子"));
        items.add(newTag("大俊子1"));
        items.add(newTag("大俊子12"));
        items.add(newTag("大俊子123"));
        items.add(newTag("大俊子1234"));
        items.add(newTag("大俊子12345"));
        items.add(newTag("大俊子123456"));
        items.add(newTag("大俊子1234567"));
        items.add(newTag("大俊子12345678"));
        items.add(newTag("大俊子123456789"));
        items.add(newTag("大俊子12345678910"));
        items.add(newTag("大俊子1234567891011"));
        items.add(newTag("大俊子123456789101112"));
        items.add(newTag("大俊子12345678910111213"));
        items.add(newTag("大俊子1234567891011121314"));
        layout.set(10, 10, items, item -> LogUtils.log("点击有效!" + item.showData()));
    }

    private DisplayItem newTag(String content) {
        DisplayItem item = DisplayItem.newItem(DefaultHolders.TAG.showType());
        item.setShowData(content);
        return item;
    }
}