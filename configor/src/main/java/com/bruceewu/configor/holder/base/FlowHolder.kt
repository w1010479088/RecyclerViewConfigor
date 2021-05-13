package com.bruceewu.configor.holder.base

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bruceewu.configor.IConfigor
import com.bruceewu.configor.R
import com.bruceewu.configor.entity.CusOnClickListener
import com.bruceewu.configor.entity.DisplayItem
import com.bruceewu.configor.entity.IDivider

class FlowHolder(itemView: View) : CusBaseHolder(itemView), IDivider {
    private val space = IConfigor.configor.dip2px(15)

    override fun layoutID() = R.layout.holder_flow

    override fun renderUI(item: DisplayItem, listener: CusOnClickListener) {
        mHelper.getView<FlowLayout>(R.id.flow_layout).set(15, 10, item.children(), listener)
    }

    override fun getItemOffsets(outRect: Rect, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        outRect.left = space
    }
}