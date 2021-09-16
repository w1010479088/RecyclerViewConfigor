package com.bruceewu.configor;

import android.widget.ImageView;
import android.widget.TextView;

import com.bruceewu.configor.entity.DisplayItem;
import com.bruceewu.configor.entity.HolderEntity;
import com.bruceewu.configor.entity.HolderParser;
import com.bruceewu.configor.helper.ErrorLogger;
import com.bruceewu.configor.holder.DefaultHolders;

import java.util.List;

public abstract class IConfigor {
    private static boolean mInit;
    private static IConfigor configor = null;

    public static void init(IConfigor configor) {
        if (!mInit) {
            mInit = true;
            IConfigor.configor = configor;
            config(DefaultHolders.getHolders());
        }
    }

    public static IConfigor configor() {
        return IConfigor.configor;
    }

    public static void config(List<HolderEntity> holders) {
        HolderParser.config(holders);
    }

    public abstract void showSingleImageText(TextView view, String imgUrl, String text, int start, int end);

    public abstract void loadImage(ImageView view, String url);

    public abstract void loadRoundImageByDp(ImageView view, String url, int radius);

    public abstract void setImageRTop(ImageView view, String url, int radiusDP);

    public abstract void clearImage(ImageView view);

    public abstract ErrorLogger getLogger();

    public abstract int loadingSize();

    public abstract int dip2px(int dp);

    public abstract int getScreenWidth();

    public abstract int defaultBgColor();

    public abstract int defaultEmptyIcon();

    public abstract int colorIndicator();

    public abstract int colorUnselTabText();

    public abstract int colorSelTabText();

    public abstract void setSchemaor(DisplayItem item, Object schemaor);
}