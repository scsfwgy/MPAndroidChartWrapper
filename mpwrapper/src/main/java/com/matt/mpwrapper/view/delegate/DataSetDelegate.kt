package com.matt.mpwrapper.view.delegate

import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleDataSet
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineScatterCandleRadarDataSet

/**
 *
 * Author : wgyscsf@163.com
 * Github : https://github.com/scsfwgy
 * Date   : 2020/7/12 12:03 PM
 * 描 述 ：
 *
 **/
class DataSetDelegate {
    fun init(dataSet: BarLineScatterCandleBubbleDataSet<out Entry>) {
        dataSet.run {
            //y轴模式
            axisDependency = YAxis.AxisDependency.RIGHT

            //每个item上绘制的值
            valueTextSize = 10f
            setDrawValues(false)
            setDrawIcons(false)

            //高亮线
            isHighlightEnabled = false
        }
        if (dataSet is LineScatterCandleRadarDataSet) {
            dataSet.run {
                setDrawHorizontalHighlightIndicator(false)
                setDrawVerticalHighlightIndicator(true)
                highlightLineWidth = 0.5f
            }
        }
    }
}