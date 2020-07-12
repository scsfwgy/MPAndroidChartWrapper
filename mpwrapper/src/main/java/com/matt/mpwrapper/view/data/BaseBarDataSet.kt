package com.matt.mpwrapper.view.data

import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

/**
 * ============================================================
 * 作 者 :    matt@163.com
 * 更新时间 ：2018/09/28 18:17
 * 描 述 ：线性数据集合的基类，设置一些通用属性。
 * ============================================================
 */
class BaseBarDataSet(yValue: List<BarEntry>, label: String) : BarDataSet(yValue, label) {

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

        //长按高亮十字线
        isHighlightEnabled = false
    }

    override fun getEntryIndex(e: BarEntry): Int {
        return mValues.indexOf(e);
    }
}