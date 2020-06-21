package com.matt.mpwrapper.view

import android.content.Context
import android.util.AttributeSet
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.Entry
import com.matt.mpwrapper.view.base.BaseKView
import com.matt.mpwrapper.view.delegate.MasterViewDelegate
import com.matt.mpwrapper.view.marker.MasterViewMarker
import com.matt.mpwrapper.view.type.MasterViewType

/**
 * ============================================================
 * 作 者 :    matt
 * 更新时间 ：2020/03/07 12:22
 * 描 述 ：
 * ============================================================
 */

class MasterView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) :
    BaseKView(context, attributeSet, defStyle) {

    val mMasterViewDelegate by lazy {
        MasterViewDelegate(this)
    }

    init {
        initMasterChart()
    }

    private fun initMasterChart() {
        //marker
        val masterViewMarker = MasterViewMarker(this, mContext)
        masterViewMarker.chartView = this
        marker = masterViewMarker
        setDrawMarkers(true)
    }

    fun renderView() {
        val masterViewType = mMasterViewDelegate.mMasterViewType
        val combinedData = mMasterViewDelegate.mCombinedData
        val lineData = mMasterViewDelegate.mLineData
        val lineDataSet = mMasterViewDelegate.mTimeSharingDataSet
        val candleData = mMasterViewDelegate.mCandleData
        val candleDataSet = mMasterViewDelegate.mCandleDataSet
        val kViewDataList = mBaseInit.kViewData()
        kViewDataList.forEachIndexed { index, kViewData ->
            val p = kViewData.price ?: throw IllegalArgumentException("price字段为null,不允许为null")
            when (masterViewType) {
                MasterViewType.CANDLE -> {
                    val candleEntry =
                        CandleEntry(index.toFloat(), p.h, p.l, p.o, p.c)
                    candleDataSet.addEntry(candleEntry)
                }
                MasterViewType.TIMESHARING -> {
                    val entry = Entry(index.toFloat(), p.c)
                    lineDataSet.addEntry(entry)
                }
            }
        }
        if (masterViewType == MasterViewType.CANDLE) {
            candleData.addDataSet(candleDataSet)
            combinedData.setData(candleData)
        } else if (masterViewType == MasterViewType.TIMESHARING) {
            lineData.addDataSet(lineDataSet)
            combinedData.setData(lineData)
        }
        setKViewData(combinedData, kViewDataList.size)
    }
}