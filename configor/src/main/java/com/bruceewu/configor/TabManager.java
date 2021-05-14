package com.bruceewu.configor;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Pair;
import android.webkit.ValueCallback;

import java.util.List;

public class TabManager {
    private final TabLayout tab_layout;
    private final ViewPager view_pager;
    private final FragmentManager manager;

    public TabManager(TabLayout tab_layout, ViewPager view_pager, boolean tabScroll, FragmentManager manager) {
        this.tab_layout = tab_layout;
        this.view_pager = view_pager;
        this.manager = manager;
        tab_layout.setTabMode(tabScroll ? TabLayout.MODE_SCROLLABLE : TabLayout.MODE_FIXED);
        tab_layout.setupWithViewPager(view_pager);
    }

    public void config(List<Pair<String, Fragment>> items, ValueCallback<Integer> tabSelListener) {
        configTab();
        for (Pair<String, Fragment> page : items) {
            tab_layout.addTab(tab_layout.newTab());
        }

        SimpleViewPagerAdapter adapter = new SimpleViewPagerAdapter(items, manager);
        view_pager.setAdapter(adapter);
        view_pager.setOffscreenPageLimit(items.size());
        for (int i = 0; i < items.size(); i++) {
            tab_layout.getTabAt(i).setText(items.get(i).first);
        }
        view_pager.setCurrentItem(0);
        tab_layout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tabSelListener != null) {
                    tabSelListener.onReceiveValue(tab.getPosition());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void setCurTab(int pos) {
        if (pos <= tab_layout.getTabCount() - 1) {
            view_pager.setCurrentItem(pos, true);
        }
    }

    public void setCurTab(String name) {
        for (int i = 0; i < tab_layout.getTabCount(); i++) {
            String curTabName = tab_layout.getTabAt(i).getText().toString();
            if (TextUtils.equals(curTabName, name)) {
                setCurTab(i);
                break;
            }
        }
    }

    public String getTabName(int pos) {
        if (pos >= 0 && pos < tab_layout.getTabCount()) {
            return tab_layout.getTabAt(pos).getText().toString();
        } else {
            return "";
        }
    }

    private void configTab() {
        tab_layout.removeAllTabs();
        tab_layout.setTabIndicatorFullWidth(false);
        tab_layout.setSelectedTabIndicatorColor(color(IConfigor.configor().colorIndicator()));
        tab_layout.setTabTextColors(color(IConfigor.configor().colorUnselTabText()), color(IConfigor.configor().colorIndicator()));
        tab_layout.setSelectedTabIndicatorHeight(IConfigor.configor().dip2px(3));
    }

    private int color(int color) {
        return tab_layout.getContext().getResources().getColor(color);
    }
}

class SimpleViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Pair<String, Fragment>> items;

    public SimpleViewPagerAdapter(List<Pair<String, Fragment>> items, FragmentManager fm) {
        super(fm);
        this.items = items;
    }

    @Override
    public Fragment getItem(int pos) {
        return items.get(pos).second;
    }

    @Override
    public int getCount() {
        return items.size();
    }
}