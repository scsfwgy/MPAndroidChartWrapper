package com.matt.mpwrapper.view.data

import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet

/**
 * ============================================================
 * 作 者 :    matt@163.com
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
        axisDependency = YAxis.AxisDependency.RIGHT
        /**
         * 设置单个蜡烛图value的值，一般都设置不显示
         */
        //设置单个蜡烛文字
        valueTextSize = 10f
        setDrawValues(false)
        setDrawIcons(false)
        /**
         * 线涨跌相关设置
         */
        //模式
        mode = Mode.LINEAR
        setDrawCircles(false)
        setDrawCircleHole(false)
        circleRadius = 3f
        circleHoleRadius = 1f
        lineWidth = 0.75f
        //长按高亮十字线
        isHighlightEnabled = false
        highlightLineWidth = 0.5f
        //设置x、y轴指示器
        setDrawHighlightIndicators(false)
        //设置x轴指示器
        setDrawHorizontalHighlightIndicator(false)
        //设置y轴指示器
        setDrawVerticalHighlightIndicator(true)
    }
}