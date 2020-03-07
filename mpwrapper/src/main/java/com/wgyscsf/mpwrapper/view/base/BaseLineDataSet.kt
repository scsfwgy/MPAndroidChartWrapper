package com.wgyscsf.mpwrapper.view.base

import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 更新时间 ：2018/09/28 18:17
 * 描 述 ：线性数据集合的基类，设置一些通用属性。
 * ============================================================
 */
class BaseLineDataSet(
    yValue: List<Entry>,
    label: String
) : LineDataSet(yValue, label) {

    init {
        initAttrs()
    }

    private fun initAttrs() {
        //setDrawIcons(false);
        axisDependency = YAxis.AxisDependency.RIGHT
        /**
         * 设置单个蜡烛图value的值，一般都设置不显示
         */
        //设置单个蜡烛文字
        valueTextSize = 10f
        setDrawValues(false)
        /**
         * 线涨跌相关设置
         */
        //模式
        mode = Mode.LINEAR
        setDrawCircles(false)
        lineWidth = 0.75f
        //长按高亮十字线
        isHighlightEnabled = false
        highlightLineWidth = 0.5f
        //是否显示指示线
        setDrawHighlightIndicators(false)
        setDrawHorizontalHighlightIndicator(false)
        setDrawVerticalHighlightIndicator(false)
    }
}