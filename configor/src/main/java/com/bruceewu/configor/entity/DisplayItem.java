package com.bruceewu.configor.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用类型的Model，主要针对列表类型
 */
public class DisplayItem {
    private String showType;
    private String showData;
    private String actionType;
    private String actionData;
    private int width;
    private int height;
    private Object reserved;    //保留字段
    private int spanSize;   //Grid占位
    private final Map<String, Object> extras = new HashMap<>();
    private List<DisplayItem> children; //有子Item的情况

    public static DisplayItem newItem(String showType) {
        DisplayItem item = new DisplayItem();
        item.setShowType(showType);
        return item;
    }

    public String showType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public String showData() {
        return showData;
    }

    public void setShowData(String showData) {
        this.showData = showData;
    }

    public String actionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String actionData() {
        return actionData;
    }

    public void setActionData(String actionData) {
        this.actionData = actionData;
    }

    public Object reserved() {
        return reserved;
    }

    public void setReserved(Object reserved) {
        this.reserved = reserved;
    }

    public void setISchemaor(ISchemaor schemaor) {
        setActionType(ISchemaor.TAG_ROUTER);
        putExtra(ISchemaor.TAG_ROUTER, schemaor);
    }

    public int spanSize() {
        return spanSize;
    }

    public void setSpanSize(int size) {
        this.spanSize = size;
    }

    public int width() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int height() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<DisplayItem> children() {
        return children;
    }

    public void setChildren(List<DisplayItem> children) {
        this.children = children;
    }

    public void putExtra(String key, Object value) {
        extras.put(key, value);
    }

    public Object getExtra(String key) {
        return extras.get(key);
    }
}
