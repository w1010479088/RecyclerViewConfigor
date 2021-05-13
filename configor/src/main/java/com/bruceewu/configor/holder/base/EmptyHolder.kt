package com.bruceewu.configor.holder.base

import android.view.View
import com.bruceewu.configor.IConfigor
import com.bruceewu.configor.R
import com.bruceewu.configor.entity.CusOnClickListener
import com.bruceewu.configor.entity.DisplayItem

class EmptyHolder(itemView: View) : CusBaseHolder(itemView) {

    override fun layoutID() = R.layout.holder_empty

    override fun renderUI(item: DisplayItem, listener: CusOnClickListener) {
        val height = (item.getExtra("height") ?: 400) as Int
        val icon = (item.getExtra("icon") ?: 0) as Int
        val tip = (item.getExtra("tip") ?: "暂无数据") as String
        mHelper.setImage(R.id.img, IConfigor.configor.defaultEmptyIcon())
        mHelper.setHeight(R.id.root, height)
        mHelper.setText(R.id.tip, tip)
        if (icon == 0) {
            mHelper.setVisibility(R.id.img, false)
        } else {
            mHelper.setVisibility(R.id.img, true)
            mHelper.setImage(R.id.img, icon)
        }
    }
}