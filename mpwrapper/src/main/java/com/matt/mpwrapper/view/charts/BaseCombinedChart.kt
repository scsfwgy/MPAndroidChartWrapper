package com.matt.mpwrapper.view.charts

import android.content.Context
import android.util.AttributeSet
import com.github.mikephil.charting.charts.CombinedChart

/**
 * ============================================================
 * 作 者 :    matt
 * 更新时间 ：2020/03/07 11:16
 * 描 述 ：
 * ============================================================
 */
abstract class BaseCombinedChart(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) :
    CombinedChart(context, attributeSet, defStyle) {

    val TAG by lazy {
        javaClass.simpleName
    }
    val mContext by lazy {
        getContext() ?: throw IllegalArgumentException("getContext() cannot be null")
    }


    fun updateAll() {
        //刷新数据
        data?.notifyDataChanged()
        //刷新view
        notifyDataSetChanged()
        invalidate()
    }
}