package com.matt.mpwrapper.view

import android.content.Context
import android.util.AttributeSet
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.Entry
import com.matt.mpwrapper.bean.KViewData
import com.matt.mpwrapper.bean.Price
import com.matt.mpwrapper.view.base.BaseKView
import com.matt.mpwrapper.view.delegate.BaseKViewDelegate
import com.matt.mpwrapper.view.delegate.MasterViewDelegate
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
        mMasterViewDelegate.initChart()
    }

    override fun getChartViewDelegate(): BaseKViewDelegate {
        return mMasterViewDelegate
    }

    override fun renderTemplateItemView(
        realIndex: Int,
        newDataIndex: Int,
        it: KViewData,
        reload: Boolean,
        append: Boolean,
        loadMore: Boolean
    ) {
        val masterViewDelegate = mMasterViewDelegate
        val masterViewType = masterViewDelegate.mMasterViewType
        val p = it.price ?: throw IllegalArgumentException("price字段为null,不允许为null")
        //x轴采用下标索引
        val xValue = realIndex.toFloat()
        //渲染主图
        renderMainView(p, realIndex, false)
        renderMainIndicators(masterViewType, it, masterViewDelegate, xValue)
    }

    private fun renderMainIndicators(
        masterViewType: MasterViewType,
        it: KViewData,
        masterViewDelegate: MasterViewDelegate,
        xValue: Float
    ) {
        val invalidData = FinancialAlgorithm.invalidData
        if (masterViewType == MasterViewType.CANDLE) {
            val masterData = it.masterData
            val ma = masterData?.ma
            val boll = masterData?.boll
            if (ma != null) {
                val maEntryListArr = masterViewDelegate.mMaLineDataSetArr
                if (ma.ma5 != invalidData) {
                    maEntryListArr[0].addEntry(Entry(xValue, ma.ma5))
                }
                if (ma.ma10 != invalidData) {
                    maEntryListArr[1].addEntry(Entry(xValue, ma.ma10))
                }
                if (ma.ma20 != invalidData) {
                    maEntryListArr[2].addEntry(Entry(xValue, ma.ma20))
                }
            }
            if (boll != null) {
                val bollEntryListArr = masterViewDelegate.mBollLineDataSetArr
                if (boll.up != invalidData) {
                    bollEntryListArr[0].addEntry(Entry(xValue, boll.up))
                }
                if (boll.mb != invalidData) {
                    bollEntryListArr[1].addEntry(Entry(xValue, boll.mb))
                }
                if (boll.dn != invalidData) {
                    bollEntryListArr[2].addEntry(Entry(xValue, boll.dn))
                }
            }
        }
    }


    fun renderMainView(p: Price?, index: Int, refresh: Boolean = false) {
        if (p == null) return
        val xValue = index.toFloat()
        val masterViewDelegate = mMasterViewDelegate
        val candleDataSet = masterViewDelegate.mCandleDataSet
        val lineDataSet = masterViewDelegate.mTimeSharingDataSet
        val masterViewType = masterViewDelegate.mMasterViewType
        if (masterViewType == MasterViewType.CANDLE) {
            if (refresh) {
                val entryForIndex = candleDataSet.getEntryForIndex(index)
                entryForIndex.x = xValue
                entryForIndex.high = p.h
                entryForIndex.low = p.l
                entryForIndex.open = p.o
                entryForIndex.close = p.c
            } else {
                val candleEntry =
                    CandleEntry(xValue, p.h, p.l, p.o, p.c)
                candleDataSet.addEntry(candleEntry)
            }
        } else if (masterViewType == MasterViewType.TIMESHARING) {
            if (refresh) {
                val entryForIndex = lineDataSet.getEntryForIndex(index)
                entryForIndex.y = p.c
                entryForIndex.x = xValue
            } else {
                val entry = Entry(xValue, p.c)
                lineDataSet.addEntry(entry)
            }

        }
    }

    override fun refreshData(kViewData: KViewData) {
        val kViewDataList = mBaseInit.kViewDataList()
        val index = kViewDataList.size - 1
        renderMainView(kViewData.price, index, true)
        val c = kViewData.price?.c
        if (c != null && c != 0.0f) {
            mMasterViewDelegate.refreshLimit(c)
        }
        invalidate()
    }

    override fun renderTemplateFinal(
        kViewDataList: List<KViewData>,
        reload: Boolean,
        loadMore: Boolean,
        pushData: Boolean
    ) {
        val masterViewDelegate = mMasterViewDelegate
        val masterViewType = masterViewDelegate.mMasterViewType
        val combinedData = masterViewDelegate.mCombinedData
        val lineData = masterViewDelegate.mLineData
        if (masterViewType == MasterViewType.CANDLE) {
            val candleData = masterViewDelegate.mCandleData
            val candleDataSet = masterViewDelegate.mCandleDataSet
            //显示对应指标
            masterViewDelegate.showIndicatorType(false)
            candleData.addDataSet(candleDataSet)
            combinedData.setData(lineData)
            combinedData.setData(candleData)
        } else if (masterViewType == MasterViewType.TIMESHARING) {
            val lineDataSet = masterViewDelegate.mTimeSharingDataSet
            lineData.addDataSet(lineDataSet)
            combinedData.setData(lineData)
        }
        val c = kViewDataList.last().price?.c
        if (c != null && c != 0.0f) {
            masterViewDelegate.refreshLimit(c)
        }
    }
}