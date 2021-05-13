package com.bruceewu.configor.entity;

public class H5Schemaor implements ISchemaor {
    private String url;

    public H5Schemaor(String url) {
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
        return false;
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
