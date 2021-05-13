package com.bruceewu.configor.entity;

public class NativeSchemaor implements ISchemaor {
    private String url;

    public NativeSchemaor(String url) {
        this.url = url;
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
        return true;
    }

    @Override
    public boolean needLogin() {
        return true;
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
