package com.bruceewu.configor.holder.base

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bruceewu.configor.IConfigor
import com.bruceewu.configor.R
import com.bruceewu.configor.entity.CusOnClickListener
import com.bruceewu.configor.entity.DisplayItem
import com.bruceewu.configor.entity.IDivider

class R10TopHolder(itemView: View) : CusBaseHolder(itemView), IDivider {

    override fun layoutID() = R.layout.holder_r10_top

    override fun renderUI(item: DisplayItem, listener: CusOnClickListener) {
    }

    override fun getItemOffsets(outRect: Rect, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        outRect.top = IConfigor.configor.dip2px(10)
    }
}