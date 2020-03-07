package com.wgyscsf.mpwrapper.view.delegate

import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CombinedData
import com.github.mikephil.charting.data.LineData
import com.wgyscsf.mpwrapper.view.base.BaseKView

/**
 * ============================================================
 * 作 者 :    wgyscsf
 * 更新时间 ：2020/03/07 15:08
 * 描 述 ：
 * ============================================================
 */
open class BaseKViewDelegate(baseKView: BaseKView) {

    val mBaseKView by lazy {
        baseKView
    }

    val mCombinedData by lazy {
        CombinedData()
    }
    val mCandleData by lazy {
        CandleData()
    }
    val mBarData by lazy {
        BarData()
    }
    val mLineData by lazy {
        LineData()
    }
}