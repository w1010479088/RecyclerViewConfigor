package com.bruceewu.recyclerviewconfigor;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bruceewu.configor.IConfigor;
import com.bruceewu.configor.RecyclerViewConfigor;
import com.bruceewu.configor.entity.DisplayItem;
import com.bruceewu.configor.helper.ErrorLogger;
import com.bruceewu.configor.helper.ThreadPool;
import com.bruceewu.configor.holder.DefaultHolders;
import com.bruceewu.configor.holder.base.GalleryHolder;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_main);
        initTimer();
        initRecyclerView();
        testRecyclerView();
    }

    private int count;

    private final ThreadPool.IUpdater updater = new ThreadPool.TimeRepeator(4, () -> {
        LogUtils.log("time tag......");
        count++;
        if (count == 10) {
            unRegister();
        }
    });

    private void unRegister() {
        ThreadPool.unRegisterObserver(updater);
        LogUtils.log("time stop");
    }

    private void initTimer() {
        ThreadPool.registerObserver(updater);
    }

    private void testRecyclerView() {
        RecyclerViewConfigor builder = new RecyclerViewConfigor
                .Builder()
                .buildRecyclerView(findViewById(R.id.list))
                .buildScrollType(RecyclerViewConfigor.ScrollType.Vertical)
                .buildRefresh(false)
                .buildLoadMore(false)
                .buildInfinite(false)
                .buildClickListener(item -> {
                })
                .buildBgColor(getResources().getColor(R.color.black))
                .build();

        List<DisplayItem> items = new ArrayList<>();
        List<DisplayItem> children = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            children.add(DisplayItem.newItem(DefaultHolders.Error.showType()));
        }

        DisplayItem gallery = new GalleryHolder
                .Builder()
                .setInfinite(true)
                .setAutoScroll(true)
                .setHideIndicator(false)
                .setIndicatorType(GalleryHolder.INDICATOR_TYPE_NUM)
                .setTime(2)
                .setChildren(children)
                .build();

        items.add(gallery);

        builder.set(items);
    }

    private void initRecyclerView() {
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
                return new ErrorLogger() {
                    @Override
                    public void log(Exception ex) {
                        LogUtils.log(ex.getMessage());
                    }

                    @Override
                    public void log(String content) {
                        LogUtils.log(content);
                    }
                };
            }

            @Override
            public int loadingSize() {
                return 0;
            }

            @Override
            public int dip2px(int dp) {
                return 0;
            }

            @Override
            public int getScreenWidth() {
                return 0;
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
                return 0;
            }

            @Override
            public int colorUnselTabText() {
                return 0;
            }

            @Override
            public int colorSelTabText() {
                return 0;
            }
        });
    }
}