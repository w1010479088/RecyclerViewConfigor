package com.bruceewu.configor.holder.base

import android.view.View
import com.bruceewu.configor.R
import com.bruceewu.configor.entity.CusOnClickListener
import com.bruceewu.configor.entity.DisplayItem

class TagHolder(itemView: View) : CusBaseHolder(itemView) {

    override fun layoutID(): Int = R.layout.holder_tag

    override fun renderUI(item: DisplayItem, listener: CusOnClickListener) {
        mHelper.setText(R.id.title, item.showData())
        mHelper.setClick(R.id.title) {
            listener.onClick(item)
        }
    }
}