package com.bruceewu.configor.entity;

public class NoneSchemaor implements ISchemaor {
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
        return "";
    }
}
