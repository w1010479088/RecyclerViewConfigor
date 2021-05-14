package com.bruceewu.configor.entity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bruceewu.configor.IConfigor;
import com.bruceewu.configor.helper.ErrorLogger;
import com.bruceewu.configor.holder.DefaultHolders;
import com.bruceewu.configor.holder.base.CusBaseHolder;
import com.bruceewu.configor.holder.base.ErrorHolder;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义配置ViewType
 */
public class HolderParser {
    private int mCurViewType = 1;   //当前已经累计到的ViewType;
    private final Map<String, Integer> mShowType2ViewType = new HashMap<>();
    private final Map<Integer, Integer> mViewType2LayoutID = new HashMap<>();
    private final Map<Integer, Class<? extends CusBaseHolder>> mViewType2Clazz = new HashMap<>();

    private HolderParser() {
    }

    private static class HOLDER {
        private static final HolderParser INSTANCE = new HolderParser();
    }

    public static void config(List<HolderEntity> holders) {
        for (HolderEntity item : holders) {
            HOLDER.INSTANCE.add(item.showType(), item.clazz());
        }
    }

    public static int showType2ViewType(String showType) {
        Integer viewType = HOLDER.INSTANCE.mShowType2ViewType.get(showType);
        if (viewType == null) {
            return 0;
        } else {
            return viewType;
        }
    }

    public static CusBaseHolder newHolder(ViewGroup parent, String showType) {
        int viewType = showType2ViewType(showType);
        return newHolder(parent, viewType);
    }

    public static CusBaseHolder newHolder(ViewGroup parent, int viewType) {
        HolderParser parser = HOLDER.INSTANCE;
        try {
            Integer layoutID = parser.mViewType2LayoutID.get(viewType);
            Class<? extends CusBaseHolder> clazz = parser.mViewType2Clazz.get(viewType);
            Constructor<? extends CusBaseHolder> constructor = clazz.getDeclaredConstructor(View.class);

            if (layoutID == null || layoutID == 0) {//需要创建临时Holder，获取layoutID
                View tempView = new View(parent.getContext());
                CusBaseHolder tempHolder = constructor.newInstance(tempView);
                layoutID = tempHolder.layoutID();
                parser.mViewType2LayoutID.put(viewType, layoutID);
            }

            View rootView = LayoutInflater.from(parent.getContext()).inflate(layoutID, parent, false);
            return constructor.newInstance(rootView);
        } catch (Exception ex) {
            ErrorLogger logger = IConfigor.configor().getLogger();
            if (logger != null) {
                logger.log(ex);
            }
            Integer errorType = parser.mShowType2ViewType.get(DefaultHolders.Error.showType());
            Integer errorLayoutId = parser.mViewType2LayoutID.get(errorType);

            if (errorLayoutId == null || errorLayoutId == 0) {
                View tempView = new View(parent.getContext());
                ErrorHolder tempHolder = new ErrorHolder(tempView);
                errorLayoutId = tempHolder.layoutID();
                parser.mViewType2LayoutID.put(errorType, errorLayoutId);
            }

            View errorView = LayoutInflater.from(parent.getContext()).inflate(errorLayoutId, parent, false);
            return new ErrorHolder(errorView);
        }
    }

    private void add(String showType, Class<? extends CusBaseHolder> clazz) {
        mShowType2ViewType.put(showType, mCurViewType);
        mViewType2Clazz.put(mCurViewType, clazz);
        mCurViewType++;
    }
}
