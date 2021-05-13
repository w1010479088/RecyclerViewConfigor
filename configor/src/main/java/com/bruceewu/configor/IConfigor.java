package com.bruceewu.configor;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bruceewu.configor.entity.HolderEntity;
import com.bruceewu.configor.entity.HolderParser;
import com.bruceewu.configor.entity.ISchemaor;
import com.bruceewu.configor.helper.ErrorLogger;
import com.bruceewu.configor.holder.DefaultHolders;

import java.util.List;

public interface IConfigor {
    IConfigor configor = null;

    static void configDefaultHolders() {
        config(DefaultHolders.getHolders());
    }

    static void config(List<HolderEntity> holders) {
        HolderParser.config(holders);
    }

    void showSingleImageText(Context context, String imgUrl, String text, TextView textView, int start, int end);

    void loadImage(Context context, String url, ImageView imageView);

    void loadRoundImageByDp(Context context, String url, ImageView imageView, int radius);

    void setImageRTop(ImageView view, String url, int radiusDP);

    void clearImage(ImageView view);

    String reformOCR();

    String reformFace();

    String reformAll();

    void postJump(ISchemaor schemaor, String eventID);

    ErrorLogger getLogger();

    int dip2px(int dp);

    int getScreenWidth();

    int defaultBgColor();

    int defaultEmptyIcon();

    int colorIndicator();

    int colorUnselTabText();
}
