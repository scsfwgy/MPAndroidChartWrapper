package com.matt.mpwrapper.view.data

import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.matt.mpwrapper.view.delegate.DataSetDelegate

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
        DataSetDelegate().init(this)
    }

    override fun getEntryIndex(e: BarEntry): Int {
        return mValues.indexOf(e);
    }
}