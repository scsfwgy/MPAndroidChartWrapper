package com.matt.mpwrapper.view.data

import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.matt.mpwrapper.view.delegate.DataSetDelegate

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
        DataSetDelegate().init(this)

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
    }
}