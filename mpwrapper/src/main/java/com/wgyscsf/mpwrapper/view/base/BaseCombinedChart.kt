package com.wgyscsf.mpwrapper.view.base

import android.content.Context
import android.util.AttributeSet
import com.github.mikephil.charting.charts.CombinedChart

/**
 * ============================================================
 * 作 者 :    wgyscsf
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

    init {

    }
}