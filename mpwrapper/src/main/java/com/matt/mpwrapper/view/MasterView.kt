package com.matt.mpwrapper.view

import android.content.Context
import android.util.AttributeSet
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.matt.mpwrapper.bean.MasterData
import com.matt.mpwrapper.utils.TimeUtils
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

    val mMasterDataList by lazy {
        ArrayList<MasterData>()
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


        val xAxis = xAxis
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase): String {
                val timestamp = value.toLong()
                return TimeUtils.millis2String(timestamp, TimeUtils.getFormat("MM-dd HH:mm:ss"))
            }
        }
    }

    fun renderView() {
        val masterViewType = mMasterViewDelegate.mMasterViewType
        when (masterViewType) {
            MasterViewType.CANDLE -> {
                renderCandleView()
            }
            MasterViewType.TIMESHARING -> {
                val timeSharingDataSet = mMasterViewDelegate.mTimeSharingDataSet

            }
        }
    }

    private fun renderCandleView() {
        val combinedData = mMasterViewDelegate.mCombinedData
        val candleData = mMasterViewDelegate.mCandleData
        val candleDataSet = mMasterViewDelegate.mCandleDataSet
        mMasterDataList.subList(0, 10).forEachIndexed { index, it ->
            val price = it.price ?: throw IllegalArgumentException("主图中的price字段为null,不允许为null")
            candleDataSet.addEntry(
                CandleEntry(
                    index.toFloat(),
                    price.h,
                    price.l,
                    price.o,
                    price.c
                )
            )
        }
        //seekBeginAndEndByNewer(candleDataSet.values)
        candleData.addDataSet(candleDataSet)
        combinedData.setData(candleData)
        data = combinedData
    }

}