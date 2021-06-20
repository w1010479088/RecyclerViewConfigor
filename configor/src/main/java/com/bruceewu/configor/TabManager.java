package com.bruceewu.configor;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.webkit.ValueCallback;

import java.util.List;

public class TabManager {
    private final TabLayout tab_layout;
    private final ViewPager view_pager;
    private final FragmentManager manager;
    private ICusView cusView;

    public TabManager(TabLayout tab_layout, ViewPager view_pager, boolean tabScroll, FragmentManager manager) {
        this.tab_layout = tab_layout;
        this.view_pager = view_pager;
        this.manager = manager;
        tab_layout.setTabMode(tabScroll ? TabLayout.MODE_SCROLLABLE : TabLayout.MODE_FIXED);
    }

    public void setCusView(ICusView cusView) {
        this.cusView = cusView;
    }

    public void config(List<Pair<String, Fragment>> items, ValueCallback<Integer> tabSelListener) {
        SimpleViewPagerAdapter adapter = new SimpleViewPagerAdapter(items, manager);
        view_pager.setAdapter(adapter);
        view_pager.setOffscreenPageLimit(items.size());
        view_pager.setCurrentItem(0);
        configTabs(items, tabSelListener);
    }

    private void configTabs(List<Pair<String, Fragment>> items, ValueCallback<Integer> tabSelListener) {
        tab_layout.removeAllTabs();
        tab_layout.setupWithViewPager(view_pager);
        if (cusView == null) {
            tab_layout.setTabIndicatorFullWidth(false);
            tab_layout.setSelectedTabIndicatorColor(color(IConfigor.configor().colorIndicator()));
            tab_layout.setTabTextColors(color(IConfigor.configor().colorUnselTabText()), color(IConfigor.configor().colorSelTabText()));
            tab_layout.setSelectedTabIndicatorHeight(IConfigor.configor().dip2px(3));
        } else {
            tab_layout.setSelectedTabIndicatorHeight(0);
        }
        for (int i = 0; i < items.size(); i++) {
            TabLayout.Tab tab = tab_layout.getTabAt(i);
            if (cusView != null) {
                View cusView = this.cusView.createCusView(tab_layout.getContext());
                tab.setCustomView(cusView);
            }
        }

        for (int i = 0; i < items.size(); i++) {
            TabLayout.Tab tab = tab_layout.getTabAt(i);
            String title = items.get(i).first;
            if (cusView == null) {
                tab.setText(title);
            } else {
                View customView = tab.getCustomView();
                cusView.setTitle(customView, title);
                cusView.tabSel(customView, i == 0);
            }
        }
        tab_layout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tabSelListener != null) {
                    tabSelListener.onReceiveValue(tab.getPosition());
                }
                if (cusView != null) {
                    cusView.tabSel(tab.getCustomView(), true);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (cusView != null) {
                    cusView.tabSel(tab.getCustomView(), false);
                }
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
            TabLayout.Tab tab = tab_layout.getTabAt(i);
            if (tab != null) {
                if (cusView == null) {
                    if (tab.getText() != null) {
                        String curTabName = tab.getText().toString();
                        if (TextUtils.equals(curTabName, name)) {
                            setCurTab(i);
                            break;
                        }
                    }
                } else {
                    View customView = tab.getCustomView();
                    String tabTitle = cusView.getTabTitle(customView);
                    if (TextUtils.equals(tabTitle, name)) {
                        setCurTab(i);
                        break;
                    }
                }
            }
        }
    }

    public String getTabName(int pos) {
        if (pos >= 0 && pos < tab_layout.getTabCount()) {
            TabLayout.Tab tab = tab_layout.getTabAt(pos);
            if (tab != null) {
                if (cusView == null) {
                    if (tab.getText() != null) {
                        return tab.getText().toString();
                    }
                } else {
                    View customView = tab.getCustomView();
                    return cusView.getTabTitle(customView);
                }
            }
        }
        return "";
    }

    private int color(int color) {
        return tab_layout.getContext().getResources().getColor(color);
    }

    public interface ICusView {
        View createCusView(Context context);

        void setTitle(View cusView, String title);

        void tabSel(View cusView, boolean sel);

        String getTabTitle(View cusView);
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