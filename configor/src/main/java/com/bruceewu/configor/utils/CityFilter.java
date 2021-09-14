package com.bruceewu.configor.utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityFilter {
    private String rootID;
    private final Map<String, CityItem> citys = new HashMap<>();
    private final Map<String, String> names = new HashMap<>();
    private final Map<String, List<CityItem>> children = new HashMap<>();

    public void config(String rootID, List<CityItem> items) {
        this.rootID = rootID;
        convert(items);
    }

    public List<CityItem> children(String id) {
        return children.get(id);
    }

    public CityItem city(String id) {
        if (TextUtils.equals(id, rootID)) {
            return null;
        } else {
            return citys.get(id);
        }
    }

    public String idByName(String name) {
        if (TextUtils.isEmpty(name)) {
            return null;
        } else {
            return names.get(name);
        }
    }

    private void convert(List<CityItem> items) {
        for (CityItem item : items) {
            String id = item.id;
            citys.put(id, item);
            names.put(item.name, id);

            String parentId = item.parent_id;
            if (!TextUtils.equals(parentId, rootID)) {//不是顶层,说明有parent
                List<CityItem> child = children.get(parentId);
                if (child == null) {
                    child = new ArrayList<>();
                    children.put(parentId, child);
                }
                child.add(item);
            }
        }
    }

    public static class CityItem {
        private String id;
        private String parent_id;
        private String name;

        public static CityItem newInstance(String id, String parent_id, String name) {
            CityItem item = new CityItem();
            item.id = id;
            item.parent_id = parent_id;
            item.name = name;
            return item;
        }

        public String id() {
            return id;
        }

        public String parentID() {
            return parent_id;
        }

        public String name() {
            return name;
        }
    }
}
