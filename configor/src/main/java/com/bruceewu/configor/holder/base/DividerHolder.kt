package com.bruceewu.configor.holder.base

import android.view.View
import com.bruceewu.configor.IConfigor
import com.bruceewu.configor.R
import com.bruceewu.configor.entity.CusOnClickListener
import com.bruceewu.configor.entity.DisplayItem

class DividerHolder(itemView: View) : CusBaseHolder(itemView) {
    private val defaultfColor = IConfigor.configor.defaultBgColor()
    private val defaultHeight = IConfigor.configor.dip2px(10)

    override fun layoutID() = R.layout.holder_divider

    override fun renderUI(item: DisplayItem, listener: CusOnClickListener) {
        val height = item.height()
        val width = item.width()
        val colorObj = item.getExtra("bg_color")
        val marginStartObj = item.getExtra("margin_start")
        val marginEndObj = item.getExtra("margin_end")

        if (colorObj != null) {
            val color = colorObj as Int
            mHelper.setBGColor(R.id.root, color)
        } else {
            mHelper.setBGColor(R.id.root, defaultfColor)
        }

        if (width != 0) {
            mHelper.setWidth(R.id.root, width)
        }

        if (height != 0) {
            mHelper.setHeight(R.id.root, height)
        } else {
            mHelper.setHeight(R.id.root, defaultHeight)
        }

        if (marginStartObj != null) {
            val marginStart = marginStartObj as Int
            mHelper.setMarginStart(R.id.root, marginStart)
        } else {
            mHelper.setMarginStart(R.id.root, 0)
        }
        if (marginEndObj != null) {
            val marginEnd = marginEndObj as Int
            mHelper.setMarginEnd(R.id.root, marginEnd)
        } else {
            mHelper.setMarginEnd(R.id.root, 0)
        }
    }
}