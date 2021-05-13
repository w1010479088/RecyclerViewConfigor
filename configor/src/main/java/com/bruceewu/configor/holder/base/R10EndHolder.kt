package com.bruceewu.configor.holder.base

import android.view.View
import com.bruceewu.configor.R
import com.bruceewu.configor.entity.CusOnClickListener
import com.bruceewu.configor.entity.DisplayItem

class R10EndHolder(itemView: View) : CusBaseHolder(itemView) {

    override fun layoutID(): Int = R.layout.holder_r10_end

    override fun renderUI(item: DisplayItem, listener: CusOnClickListener) {
        mHelper.setWidth(R.id.root, item.width())
        mHelper.setHeight(R.id.root, item.height())
    }
}