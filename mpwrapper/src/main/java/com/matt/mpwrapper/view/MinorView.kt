package com.matt.mpwrapper.view

import android.content.Context
import android.util.AttributeSet
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.matt.mpwrapper.utils.TimeUtils
import com.matt.mpwrapper.view.base.BaseKView
import com.matt.mpwrapper.view.delegate.MinorViewDelegate
import com.matt.mpwrapper.view.type.MinorIndicatorType

/**
 * ============================================================
 * 作 者 :    matt
 * 更新时间 ：2020/03/07 12:22
 * 描 述 ：
 * ============================================================
 */
class MinorView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : BaseKView(context, attributeSet, defStyle) {

    val mMinorViewDelegate by lazy {
        MinorViewDelegate(this)
    }

    init {
        initMinorChart()
    }

    fun initMinorChart() {
        axisRight.setLabelCount(3, true)

        xAxis.setDrawLabels(true)
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase): String {
                val kViewData = mBaseInit.kViewDataList()
                val valueInt = value.toInt()
                val size = kViewData.size
                val index = if (valueInt < size) valueInt else size - 1
                val price = kViewData[index].price
                    ?: throw IllegalArgumentException("price字段为null,不允许为null")
                return TimeUtils.millis2String(price.t, TimeUtils.getFormat("HH:mm:ss"))
            }
        }
    }

    fun renderView() {
        val invalidData = FinancialAlgorithm.invalidData
        val minorViewDelegate = mMinorViewDelegate
        val minorIndicatorType = minorViewDelegate.mMinorIndicatorType
        val combinedData = minorViewDelegate.mCombinedData
        val lineData = minorViewDelegate.mLineData
        val barData = minorViewDelegate.mBarData
        val kViewDataList = mBaseInit.kViewDataList()
        kViewDataList.forEachIndexed { index, kViewData ->
            val minorData =
                kViewData.minorData ?: throw IllegalArgumentException("minorData字段为null,不允许为null")
            val xValue = index.toFloat()
            when (minorIndicatorType) {
                MinorIndicatorType.MACD -> {
                    val macd = minorData.macd
                    if (macd != null) {
                        val dif = macd.dif
                        if (dif != invalidData) {
                            minorViewDelegate.mMacdEntryListArr[0].add(Entry(xValue, dif))
                        }
                        val dea = macd.dea
                        if (dea != invalidData) {
                            minorViewDelegate.mMacdEntryListArr[1].add(Entry(xValue, dea))
                        }
                        val m = macd.macd
                        if (m != invalidData) {
                            val macdBarEntryListArr = minorViewDelegate.mMacdBarEntryListArr
                            if (m >= 0) {
                                macdBarEntryListArr[0].add(BarEntry(xValue, m))
                            } else {
                                macdBarEntryListArr[1].add(BarEntry(xValue, m))
                            }
                        }

                    }
                }
                MinorIndicatorType.KDJ -> {

                }
                MinorIndicatorType.RSI -> {

                }
            }

        }

        minorViewDelegate.showIndicatorType(false)
        combinedData.setData(lineData)
        combinedData.setData(barData)
        setKViewData(combinedData, kViewDataList.size)
    }
}