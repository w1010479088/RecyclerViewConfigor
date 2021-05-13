package com.bruceewu.configor.holder.base

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.bruceewu.configor.IConfigor
import com.bruceewu.configor.R

class CusIndicatorView @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, def: Int = 0) : RelativeLayout(context, attr, def) {
    private val dots = arrayOf(R.id.dot_1, R.id.dot_2, R.id.dot_3, R.id.dot_4, R.id.dot_5, R.id.dot_6, R.id.dot_7, R.id.dot_8, R.id.dot_9)

    init {
        LayoutInflater.from(context).inflate(R.layout.widget_cus_indicator, this)
    }

    fun setSize(size: Int) {
        dots.forEachIndexed { index, id ->
            findViewById<View>(id).visibility = if (index < size) View.VISIBLE else View.GONE
        }
    }

    fun setCur(pos: Int) {
        dots.forEachIndexed { index, id ->
            findViewById<View>(id).isSelected = (index == pos)
            val view = findViewById<View>(id)
            val params = view.layoutParams
            params.width = if (index == pos) IConfigor.configor.dip2px(12) else IConfigor.configor.dip2px(4)
            view.layoutParams = params
        }
    }
}