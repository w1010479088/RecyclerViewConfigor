package com.bruceewu.configor.holder;

import android.text.TextUtils;

import com.bruceewu.configor.entity.HolderEntity;
import com.bruceewu.configor.holder.base.CusBaseHolder;
import com.bruceewu.configor.holder.base.ErrorHolder;
import com.bruceewu.configor.holder.base.GalleryHolder;
import com.bruceewu.configor.holder.base.HorizontalHolder;
import com.bruceewu.configor.holder.base.ImageHolder;
import com.bruceewu.configor.holder.base.OccupyHolder;
import com.bruceewu.configor.holder.base.DividerHolder;
import com.bruceewu.configor.holder.base.EmptyHolder;
import com.bruceewu.configor.holder.base.FlowHolder;
import com.bruceewu.configor.holder.base.FooterHolder;
import com.bruceewu.configor.holder.base.R10BottomHolder;
import com.bruceewu.configor.holder.base.R10EndHolder;
import com.bruceewu.configor.holder.base.R10StartHolder;
import com.bruceewu.configor.holder.base.R10TopHolder;
import com.bruceewu.configor.holder.base.TagHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Holder需要在这里配置
 */
public enum DefaultHolders {

    //流式布局
    Flow("flow", FlowHolder.class),
    TAG("tag", TagHolder.class),

    R10Start("r10_start", R10StartHolder.class),
    R10End("r10_end", R10EndHolder.class),
    R10Top("r10_top", R10TopHolder.class),
    R10Bottom("r10_bottom", R10BottomHolder.class),
    Gallery("gallery", GalleryHolder.class),
    Image("image", ImageHolder.class),
    Footer("footer", FooterHolder.class),
    Horizontal("horizontal", HorizontalHolder.class),
    Occupy("occupy", OccupyHolder.class),
    Divider("divider", DividerHolder.class),
    Empty("empty", EmptyHolder.class),
    Error("error", ErrorHolder.class);

    private final String showType;
    private final Class<? extends CusBaseHolder> clazz;

    DefaultHolders(String showType, Class<? extends CusBaseHolder> clazz) {
        this.showType = showType;
        this.clazz = clazz;
    }

    public String showType() {
        return showType;
    }

    public static List<HolderEntity> getHolders() {
        List<HolderEntity> result = new ArrayList<>();
        DefaultHolders[] holders = DefaultHolders.values();
        for (DefaultHolders holder : holders) {
            result.add(new HolderEntity(holder.showType, holder.clazz));
        }
        return result;
    }

    public static DefaultHolders parse(String showType) {
        DefaultHolders[] holders = DefaultHolders.values();
        for (DefaultHolders holder : holders) {
            if (TextUtils.equals(holder.showType, showType)) {
                return holder;
            }
        }
        return DefaultHolders.Error;
    }
}
