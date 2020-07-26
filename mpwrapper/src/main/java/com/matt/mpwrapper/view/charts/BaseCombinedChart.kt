package com.matt.mpwrapper.view.charts

import android.content.Context
import android.util.AttributeSet
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CombinedData
import com.github.mikephil.charting.data.LineData

/**
 * ============================================================
 * 作 者 :    matt
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

    override fun getCombinedData(): CombinedData {
        val combinedData = super.getCombinedData()
        return combinedData ?: CombinedData()
    }

    override fun getLineData(): LineData {
        val lineData = super.getLineData()
        return lineData ?: LineData()
    }

    override fun getCandleData(): CandleData {
        val candleData = super.getCandleData()
        return candleData ?: CandleData()
    }

    override fun getBarData(): BarData {
        val barData = super.getBarData()
        return barData ?: BarData()
    }

    fun updateAll() {
        //刷新数据
        data?.notifyDataChanged()
        //刷新view
        notifyDataSetChanged()
        invalidate()
    }
}