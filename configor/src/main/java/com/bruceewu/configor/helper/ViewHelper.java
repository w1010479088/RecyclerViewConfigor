package com.bruceewu.configor.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.TextUtils;
import android.util.Pair;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bruceewu.configor.IConfigor;

import java.util.ArrayList;
import java.util.List;

public class ViewHelper {
    private final View mRootView;
    private final SparseArray<View> mViews = new SparseArray<>();

    public ViewHelper(View rootView) {
        this.mRootView = rootView;
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mRootView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getRootView() {
        return mRootView;
    }

    private Context getContext() {
        return mRootView.getContext();
    }

    public void setText(int viewID, String content) {
        ((TextView) getView(viewID)).setText(content);
    }

    public void setTextWithGone(int viewID, String content) {
        setText(viewID, content);
        setVisibility(viewID, !TextUtils.isEmpty(content));
    }

    public void setText(int viewID, String content, String iconUrl, int start, int end) {
        if (TextUtils.isEmpty(iconUrl)) {
            setText(viewID, content);
        } else {
            TextView view = getView(viewID);
            IConfigor.configor().showSingleImageText(view, iconUrl, content, start, end);
        }
    }

    public String getText(int viewID) {
        TextView view = getView(viewID);
        return view.getText().toString();
    }

    public void setTextHtml(int viewID, String content) {
        if (TextUtils.isEmpty(content)) {
            setText(viewID, "");
        } else {
            ((TextView) getView(viewID)).setText(Html.fromHtml(fixContent(content)));
        }
    }

    public void setTextHtmlWithGone(int viewID, String content) {
        setVisibility(viewID, !TextUtils.isEmpty(content));
        if (!TextUtils.isEmpty(content)) {
            ((TextView) getView(viewID)).setText(Html.fromHtml(fixContent(content)));
        }
    }

    public void setTextColor(int viewID, int color) {
        ((TextView) getView(viewID)).setTextColor(color);
    }

    public void setWidth(int viewID, int width) {
        View view = getView(viewID);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = width;
        view.setLayoutParams(params);
    }

    public void setHeight(int viewID, int height) {
        View view = getView(viewID);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
        view.setLayoutParams(params);
    }

    public void setMarginStart(int viewID, int margin) {
        View view = getView(viewID);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        params.setMarginStart(margin);
        view.setLayoutParams(params);
    }

    public void setMarginEnd(int viewID, int margin) {
        View view = getView(viewID);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        params.setMarginEnd(margin);
        view.setLayoutParams(params);
    }

    public void setMarginTop(int viewID, int margin) {
        View view = getView(viewID);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        params.topMargin = margin;
        view.setLayoutParams(params);
    }

    public void setMarginBottom(int viewID, int margin) {
        View view = getView(viewID);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        params.bottomMargin = margin;
        view.setLayoutParams(params);
    }

    public void setImage(int viewID, int res) {
        ImageView imageView = getView(viewID);
        imageView.setImageResource(res);
    }

    public void setImage(int viewID, String url) {
        ImageView imageView = getView(viewID);
        IConfigor.configor().loadImage(imageView, url);
    }

    public void setImage(int viewID, String url, int radiusDP) {
        if (radiusDP <= 0) {
            setImage(viewID, url);
        } else {
            ImageView imageView = getView(viewID);
            IConfigor.configor().loadRoundImageByDp(imageView, url, radiusDP);
        }
    }

    public void setImageRTop(int viewID, String url, int radiusDP) {
        if (radiusDP <= 0) {
            setImage(viewID, url);
        } else {
            ImageView imageView = getView(viewID);
            IConfigor.configor().setImageRTop(imageView, url, radiusDP);
        }
    }

    public void clearImage(int viewID) {
        ImageView view = getView(viewID);
        IConfigor.configor().clearImage(view);
    }

    public void setVisibility(int viewID, boolean show) {
        getView(viewID).setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void setInVisibility(int viewID, boolean show) {
        getView(viewID).setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    public void setClick(int viewID, Runnable listener) {
        click(getView(viewID), listener);
    }

    public void setClick(Runnable listener) {
        click(mRootView, listener);
    }

    public void setAlpha(int viewID, float alpha) {
        getView(viewID).setAlpha(alpha);
    }

    public void setSel(int viewID, boolean sel) {
        getView(viewID).setSelected(sel);
    }

    public void setEnable(int viewID, boolean able) {
        getView(viewID).setEnabled(able);
    }

    public void setBGColor(int viewID, int color) {
        getView(viewID).setBackgroundColor(color);
    }

    @SuppressLint("NewApi")
    public void setBgDrawable(int viewID, int drawable) {
        getView(viewID).setBackground(getContext().getDrawable(drawable));
    }

    public void setBgDrawable(int viewID, Drawable drawable) {
        getView(viewID).setBackground(drawable);
    }

    public void setDrawableBottom(int viewID, int drawable, int width, int height) {
        TextView textView = getView(viewID);
        if (drawable == 0) {
            textView.setCompoundDrawables(null, null, null, null);
        } else {
            Drawable icon = getContext().getResources().getDrawable(drawable);
            icon.setBounds(0, 0, width, height);
            textView.setCompoundDrawables(null, null, null, icon);
        }
    }

    private String fixContent(String content) {
        if (!TextUtils.isEmpty(content)) {
            String fixedContent = content;
            List<Pair<String, String>> tags = new ArrayList<>();
            tags.add(new Pair<>("\n", "<br>"));//此处可能有很多Tag
            for (Pair<String, String> tag : tags) {
                fixedContent = fixedContent.replace(tag.first, tag.second);
            }
            return fixedContent;
        }
        return content;
    }

    private long mPreClickTime;

    private void click(View view, Runnable listener) {
        view.setOnClickListener(v -> {
            if (curTime() - mPreClickTime > TIME_PERIOD) {
                listener.run();
                mPreClickTime = curTime();
            }
        });
    }

    private static final long TIME_PERIOD = 100;

    private long curTime() {
        return System.currentTimeMillis();
    }
}
