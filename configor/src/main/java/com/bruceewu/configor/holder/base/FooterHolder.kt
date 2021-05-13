package com.bruceewu.configor.holder.base

import android.view.View
import com.bruceewu.configor.R
import com.bruceewu.configor.entity.CusOnClickListener
import com.bruceewu.configor.entity.DisplayItem
import com.bruceewu.configor.entity.HasMoreWrapperEntity

class FooterHolder(itemView: View) : CusBaseHolder(itemView) {

    override fun layoutID() = R.layout.holder_footer

    override fun renderUI(item: DisplayItem, listener: CusOnClickListener) {
        val reserved = item.reserved()
        if (reserved != null && reserved is HasMoreWrapperEntity) {
            val hasMore = reserved.hasMore()
            mHelper.setInVisibility(R.id.loading, hasMore)
            mHelper.setInVisibility(R.id.tip, hasMore)
        }
        listener.onClick(item)
    }
}