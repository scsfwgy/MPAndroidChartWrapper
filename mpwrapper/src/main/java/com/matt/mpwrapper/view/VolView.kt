package com.matt.mpwrapper.view

import android.content.Context
import android.util.AttributeSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.matt.mpwrapper.view.base.BaseKView
import com.matt.mpwrapper.view.delegate.VolViewDelegate

/**
 * ============================================================
 * 作 者 :    matt
 * 更新时间 ：2020/03/07 12:22
 * 描 述 ：
 * ============================================================
 */
class VolView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : BaseKView(context, attributeSet, defStyle) {

    val mVolViewDelegate by lazy {
        VolViewDelegate(this)
    }

    init {
        mVolViewDelegate.initMinorChart()
    }

    fun renderView() {
        val invalidData = FinancialAlgorithm.invalidData
        val volViewDelegate = mVolViewDelegate
        val combinedData = volViewDelegate.mCombinedData
        val lineData = volViewDelegate.mLineData
        val barData = volViewDelegate.mBarData

        //reset
        volViewDelegate.mCombinedDataControl.resetAll()

        val kViewDataList = mBaseInit.kViewDataList()
        kViewDataList.forEachIndexed { index, kViewData ->
            val price = kViewData.price ?: throw IllegalArgumentException("price字段为null,不允许为null")
            val volData =
                kViewData.volData ?: throw IllegalArgumentException("volData字段为null,不允许为null")
            val xValue = index.toFloat()

            //展示高亮线的
            volViewDelegate.mShowHighlightLineData.addEntry(Entry(xValue, 0f))

            val vol = volData.vol ?: throw IllegalArgumentException("volData.vol字段为null,不允许为null")
            val volMa5 = vol.volMa5
            if (volMa5 != invalidData) {
                volViewDelegate.mVolMaLineDataSetArr[0].addEntry(Entry(xValue, volMa5))
            }
            val volMa10 = vol.volMa10
            if (volMa10 != invalidData) {
                volViewDelegate.mVolMaLineDataSetArr[1].addEntry(Entry(xValue, volMa10))
            }
            val up = if (index != 0) {
                val c = kViewDataList[index - 1].price?.c ?: 0f
                price.c - c >= 0
            } else {
                false
            }
            val m = vol.vol
            if (m != invalidData) {
                val volBarEntryListArr = volViewDelegate.mVolBarDataSetArr
                if (up) {
                    volBarEntryListArr[0].addEntry(BarEntry(xValue, m))
                } else {
                    volBarEntryListArr[1].addEntry(BarEntry(xValue, m))
                }
            }

        }

        volViewDelegate.showIndicatorType(false)
        combinedData.setData(lineData)
        combinedData.setData(barData)
        setKViewData(combinedData, kViewDataList.size)

        //触发值未选择，进而触发未选择值对应的Legend
        mSelectionListener.onNothingSelected()
    }
}