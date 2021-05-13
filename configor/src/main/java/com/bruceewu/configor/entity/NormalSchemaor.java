package com.bruceewu.configor.entity;

import android.text.TextUtils;

public class NormalSchemaor implements ISchemaor {
    private String title;
    public String subTitle;
    public String img;
    public String action;
    public String url;
    public int login;

    public static NormalSchemaor newInstance(String title, String subTitle, String img, String action, String url, int login) {
        NormalSchemaor schemaor = new NormalSchemaor();
        schemaor.title = title;
        schemaor.subTitle = subTitle;
        schemaor.img = img;
        schemaor.action = action;
        schemaor.url = url;
        schemaor.login = login;
        return schemaor;
    }

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
        return TextUtils.equals(action, "native");
    }

    @Override
    public boolean needLogin() {
        return login == 1;
    }

    @Override
    public boolean isCredit() {
        return false;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getLink() {
        return url;
    }
}
