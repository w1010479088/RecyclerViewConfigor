package com.bruceewu.configor.entity;

import android.text.TextUtils;

import com.bruceewu.configor.IConfigor;

public interface ISchemaor {
    String TAG_ROUTER = "schemaor";

    long getId();

    String getName();

    String getLmNumber();

    String getAdId();

    boolean isNative();

    boolean needLogin();

    boolean isCredit();

    String getTitle();

    String getLink();

    static boolean isOcrUpdate(boolean isNative, String link) {
        return isNative && (TextUtils.equals(link, IConfigor.configor().reformOCR()) || TextUtils.equals(link, IConfigor.configor().reformFace()) || TextUtils.equals(link, IConfigor.configor().reformAll()));
    }

    static void open(ISchemaor schemaor) {
        open(schemaor, "");
    }

    static void open(ISchemaor schemaor, String eventID) {
        IConfigor.configor().postJump(schemaor, eventID);
    }
}
