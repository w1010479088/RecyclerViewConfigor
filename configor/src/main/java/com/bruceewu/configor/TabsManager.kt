package com.bruceewu.configor

import android.text.TextUtils
import android.webkit.ValueCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class TabsManager(private val tab_layout: TabLayout, private val view_pager: ViewPager, tabScroll: Boolean, private val manager: FragmentManager) {
    init {
        tab_layout.tabMode = if (tabScroll) TabLayout.MODE_SCROLLABLE else TabLayout.MODE_FIXED
        tab_layout.setupWithViewPager(view_pager)
    }

    fun config(items: List<Pair<String, Fragment>>, tabSelListener: ValueCallback<Int>?) {
        configTab()
        items.forEach { _ ->
            tab_layout.addTab(tab_layout.newTab())
        }
        val adapter = SimpleViewPagerAdapter(items, manager)
        view_pager.adapter = adapter
        view_pager.offscreenPageLimit = items.size
        items.forEachIndexed { index, pair ->
            tab_layout.getTabAt(index)?.text = pair.first
        }
        view_pager.currentItem = 0
        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                tabSelListener?.onReceiveValue(tab.position)
            }
        })
    }

    fun setCurTab(pos: Int) {
        if (pos <= tab_layout.tabCount - 1) {
            view_pager.setCurrentItem(pos, true)
        }
    }

    fun setCurTab(name: String) {
        for (i in 0 until tab_layout.tabCount) {
            val curName = tab_layout.getTabAt(i)?.text
            if (TextUtils.equals(curName, name)) {
                setCurTab(i)
                break
            }
        }
    }

    fun getTabName(pos: Int): String {
        if (pos >= 0 && pos < tab_layout.tabCount) {
            return tab_layout.getTabAt(pos)?.text?.toString() ?: ""
        } else {
            return ""
        }
    }

    private fun configTab() {
        tab_layout.removeAllTabs()
        tab_layout.isTabIndicatorFullWidth = false
        tab_layout.setSelectedTabIndicatorColor(color(IConfigor.configor.colorIndicator()))
        tab_layout.setTabTextColors(color(IConfigor.configor.colorUnselTabText()), color(IConfigor.configor.colorIndicator()))
        tab_layout.setSelectedTabIndicatorHeight(IConfigor.configor.dip2px(3))
    }

    private fun color(color: Int) = tab_layout.context.resources.getColor(color)
}

private class SimpleViewPagerAdapter(private val items: List<Pair<String, Fragment>>, fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int) = items[position].second

    override fun getCount() = items.size
}