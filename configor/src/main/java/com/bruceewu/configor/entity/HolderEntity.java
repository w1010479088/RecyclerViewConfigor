package com.bruceewu.configor.entity;

import com.bruceewu.configor.holder.base.CusBaseHolder;

public class HolderEntity {
    private final String showType;
    private final Class<? extends CusBaseHolder> clazz;

    public HolderEntity(String showType, Class<? extends CusBaseHolder> clazz) {
        this.showType = showType;
        this.clazz = clazz;
    }

    public String showType() {
        return showType;
    }

    public Class<? extends CusBaseHolder> clazz() {
        return clazz;
    }
}
