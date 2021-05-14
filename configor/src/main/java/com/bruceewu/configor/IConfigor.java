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

public abstract class IConfigor {
    private static IConfigor configor = null;

    public static void init(IConfigor configor) {
        IConfigor.configor = configor;
        config(DefaultHolders.getHolders());
    }

    public static IConfigor configor() {
        return IConfigor.configor;
    }

    public static void config(List<HolderEntity> holders) {
        HolderParser.config(holders);
    }

    public abstract void showSingleImageText(Context context, String imgUrl, String text, TextView textView, int start, int end);

    public abstract void loadImage(Context context, String url, ImageView imageView);

    public abstract void loadRoundImageByDp(Context context, String url, ImageView imageView, int radius);

    public abstract void setImageRTop(ImageView view, String url, int radiusDP);

    public abstract void clearImage(ImageView view);

    public abstract String reformOCR();

    public abstract String reformFace();

    public abstract String reformAll();

    public abstract void postJump(ISchemaor schemaor, String eventID);

    public abstract ErrorLogger getLogger();

    public abstract int dip2px(int dp);

    public abstract int getScreenWidth();

    public abstract int defaultBgColor();

    public abstract int defaultEmptyIcon();

    public abstract int colorIndicator();

    public abstract int colorUnselTabText();
}