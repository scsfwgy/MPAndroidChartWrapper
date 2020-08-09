package com.matt.mpwrapper.view

import android.content.Context
import android.util.AttributeSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.matt.mpwrapper.view.base.BaseKView
import com.matt.mpwrapper.view.delegate.BaseKViewDelegate
import com.matt.mpwrapper.view.delegate.MinorViewDelegate

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
        mMinorViewDelegate.initChart()
    }

    override fun getChartViewDelegate(): BaseKViewDelegate {
        return mMinorViewDelegate
    }

    fun renderView() {
        val invalidData = FinancialAlgorithm.invalidData
        val minorViewDelegate = mMinorViewDelegate
        val combinedData = minorViewDelegate.mCombinedData
        val lineData = minorViewDelegate.mLineData
        val barData = minorViewDelegate.mBarData

        val kViewDataList = mBaseInit.kViewDataList()
        kViewDataList.forEachIndexed { index, kViewData ->
            val minorData =
                kViewData.minorData ?: throw IllegalArgumentException("minorData字段为null,不允许为null")
            val xValue = index.toFloat()

            //展示高亮线的
            minorViewDelegate.mShowHighlightLineData.addEntry(Entry(xValue, 0f))

            val macd = minorData.macd
            if (macd != null) {
                val dif = macd.dif
                if (dif != invalidData) {
                    minorViewDelegate.mMacdLineDataSetArr[0].addEntry(Entry(xValue, dif))
                }
                val dea = macd.dea
                if (dea != invalidData) {
                    minorViewDelegate.mMacdLineDataSetArr[1].addEntry(Entry(xValue, dea))
                }
                val m = macd.macd
                if (m != invalidData) {
                    val macdBarEntryListArr = minorViewDelegate.mMacdBarDataSetArr
                    if (m >= 0) {
                        macdBarEntryListArr[0].addEntry(BarEntry(xValue, m))
                    } else {
                        macdBarEntryListArr[1].addEntry(BarEntry(xValue, m))
                    }
                }

            }

            val kdj = minorData.kdj
            if (kdj != null) {
                val k = kdj.k
                if (k != invalidData) {
                    minorViewDelegate.mKdjLineDataSetArr[0].addEntry(Entry(xValue, k))
                }
                val d = kdj.d
                if (d != invalidData) {
                    minorViewDelegate.mKdjLineDataSetArr[1].addEntry(Entry(xValue, d))
                }
                val j = kdj.j
                if (j != invalidData) {
                    minorViewDelegate.mKdjLineDataSetArr[2].addEntry(Entry(xValue, j))
                }
            }

            val rsi = minorData.rsi
            if (rsi != null) {
                val rsi6 = rsi.rsi6
                if (rsi6 != invalidData) {
                    minorViewDelegate.mRsiLineDataSetArr[0].addEntry(Entry(xValue, rsi6))
                }
                val rsi12 = rsi.rsi12
                if (rsi12 != invalidData) {
                    minorViewDelegate.mRsiLineDataSetArr[1].addEntry(Entry(xValue, rsi12))
                }
                val rsi24 = rsi.rsi24
                if (rsi24 != invalidData) {
                    minorViewDelegate.mRsiLineDataSetArr[2].addEntry(Entry(xValue, rsi24))
                }
            }
        }

        minorViewDelegate.showIndicatorType(false)
        combinedData.setData(lineData)
        combinedData.setData(barData)
        setKViewData(combinedData, kViewDataList.size)

        //触发值未选择，进而触发未选择值对应的Legend
        mSelectionListener.onNothingSelected()
    }
}