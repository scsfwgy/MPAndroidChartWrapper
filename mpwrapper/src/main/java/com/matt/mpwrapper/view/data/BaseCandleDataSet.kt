package com.matt.mpwrapper.view.data

import android.graphics.Paint
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.matt.mpwrapper.view.delegate.DataSetDelegate

/**
 * ============================================================
 * 作 者 :    matt@163.com
 * 更新时间 ：2018/09/28 18:17
 * 描 述 ：线性数据集合的基类，设置一些通用属性。
 * ============================================================
 */
class BaseCandleDataSet(yValue: List<CandleEntry>, label: String) : CandleDataSet(yValue, label) {

    init {
        initAttrs()
    }

    private fun initAttrs() {
        DataSetDelegate().init(this)
        //允许高亮线
        isHighlightEnabled = true
        //允许x轴绘制高亮线
        setDrawHorizontalHighlightIndicator(true)

        shadowColorSameAsCandle = true
        shadowWidth = 1f

        decreasingPaintStyle = Paint.Style.FILL
        increasingPaintStyle = Paint.Style.FILL
    }

    override fun getEntryIndex(e: CandleEntry): Int {
        return mValues.indexOf(e);
    }
}