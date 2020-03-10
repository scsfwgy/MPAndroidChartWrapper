package com.wgyscsf.mpwrapper.view.delegate

import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.data.*
import com.wgyscsf.mpwrapper.bean.KViewConstant
import com.wgyscsf.mpwrapper.utils.FormatUtil
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

    fun generateLegendEntry(formColor: Int, label: String): LegendEntry {
        val legendEntry = LegendEntry()
        legendEntry.form = Legend.LegendForm.CIRCLE
        //设置图例的大小
        legendEntry.formSize = 4f
        legendEntry.formColor = formColor
        legendEntry.label = label
        return legendEntry
    }

    fun generateLegend(legend: Legend): Legend {
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        legend.xOffset = 8f
        legend.yOffset = 18f
        return legend
    }

    fun numFormat(data: Any?, digits: Int = mBaseKView.mDigit): String {
        if (data == null) return KViewConstant.VALUE_NULL_PLACEHOLDER
        return FormatUtil.numFormat(data, digits)
    }


    fun setDataSetArrVisible(arr: Array<out LineDataSet>, visible: Boolean) {
        val lineData = mLineData
        arr.forEach {
            if (visible) {
                if (!lineData.contains(it)) lineData.addDataSet(it)
            } else {
                if (lineData.contains(it)) lineData.removeDataSet(it)
            }
        }
    }
}