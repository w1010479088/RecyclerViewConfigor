package com.bruceewu.configor.entity;

import android.text.TextUtils;

public class NotificationSchemaor implements ISchemaor {
    private String isH5;
    private String url;
    private String needLogin;

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getLmNumber() {
        return null;
    }

    @Override
    public String getAdId() {
        return null;
    }

    @Override
    public boolean isNative() {
        return TextUtils.equals(isH5, "0");
    }

    @Override
    public boolean needLogin() {
        return TextUtils.equals(needLogin, "1");
    }

    @Override
    public boolean isCredit() {
        return false;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getLink() {
        return url;
    }
}
