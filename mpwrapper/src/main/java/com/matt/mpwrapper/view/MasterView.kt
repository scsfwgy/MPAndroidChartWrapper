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
        val masterViewDelegate = mMasterViewDelegate
        val masterViewType = masterViewDelegate.mMasterViewType
        val combinedData = masterViewDelegate.mCombinedData
        val lineData = masterViewDelegate.mLineData
        val lineDataSet = masterViewDelegate.mTimeSharingDataSet
        val candleData = masterViewDelegate.mCandleData
        val candleDataSet = masterViewDelegate.mCandleDataSet
        val kViewDataList = mBaseInit.kViewData()
        kViewDataList.forEachIndexed { index, kViewData ->
            val p = kViewData.price ?: throw IllegalArgumentException("price字段为null,不允许为null")
            val xValue = index.toFloat()
            val invalidData = FinancialAlgorithm.invalidData
            when (masterViewType) {
                MasterViewType.CANDLE -> {
                    val masterData = kViewData.masterData
                    val ma = masterData?.ma
                    val boll = masterData?.boll
                    if (ma != null) {
                        if (ma.ma5 != invalidData) {
                            masterViewDelegate.mMa5EntryList.add(Entry(xValue, ma.ma5))
                        }
                        if (ma.ma10 != invalidData) {
                            masterViewDelegate.mMa10EntryList.add(Entry(xValue, ma.ma10))
                        }
                        if (ma.ma20 != invalidData) {
                            masterViewDelegate.mMa20EntryList.add(Entry(xValue, ma.ma20))
                        }
                    }
                    if (boll != null) {
                        if (boll.up != invalidData) {
                            masterViewDelegate.mBollUpEntryList.add(Entry(xValue, boll.up))
                        }
                        if (boll.mb != invalidData) {
                            masterViewDelegate.mBollMbEntryList.add(Entry(xValue, boll.mb))
                        }
                        if (boll.dn != invalidData) {
                            masterViewDelegate.mBollDnEntryList.add(Entry(xValue, boll.dn))
                        }
                    }
                    val candleEntry =
                        CandleEntry(xValue, p.h, p.l, p.o, p.c)
                    candleDataSet.addEntry(candleEntry)
                }
                MasterViewType.TIMESHARING -> {
                    val entry = Entry(xValue, p.c)
                    lineDataSet.addEntry(entry)
                }
            }
        }
        if (masterViewType == MasterViewType.CANDLE) {
            //显示对应指标
            masterViewDelegate.showIndicatorType(false)
            candleData.addDataSet(candleDataSet)
            combinedData.setData(lineData)
            combinedData.setData(candleData)
        } else if (masterViewType == MasterViewType.TIMESHARING) {
            lineData.addDataSet(lineDataSet)
            combinedData.setData(lineData)
        }
        setKViewData(combinedData, kViewDataList.size)
    }
}