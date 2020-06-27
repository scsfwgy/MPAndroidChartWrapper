package com.matt.mpwrapper.view.delegate

import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.matt.mpwrapper.R
import com.matt.mpwrapper.view.MinorView
import com.matt.mpwrapper.view.base.BaseLineDataSet
import com.matt.mpwrapper.view.type.KdjType
import com.matt.mpwrapper.view.type.MacdType
import com.matt.mpwrapper.view.type.MinorIndicatorType
import com.matt.mpwrapper.view.type.RsiType

/**
 * ============================================================
 * 作 者 :    matt
 * 更新时间 ：2020/03/07 15:14
 * 描 述 ：
 * ============================================================
 */
class MinorViewDelegate(minorView: MinorView) : BaseKViewDelegate(minorView) {
    val mMinorView by lazy {
        minorView
    }

    var mMinorIndicatorType: MinorIndicatorType = MinorIndicatorType.MACD

    val mMacdBarColorArr by lazy {
        arrayOf(
            mUpColor,
            mDownColor
        )
    }

    val mMacdColorArr by lazy {
        arrayOf(
            getColor(R.color.mp_minorview_dif),
            getColor(R.color.mp_minorview_dea)
        )
    }

    val mKdjColorArr by lazy {
        arrayOf(
            getColor(R.color.mp_minorview_k),
            getColor(R.color.mp_minorview_d),
            getColor(R.color.mp_minorview_j)
        )
    }

    val mRsiColorArr by lazy {
        arrayOf(
            getColor(R.color.mp_minorview_rsi6),
            getColor(R.color.mp_minorview_rsi12),
            getColor(R.color.mp_minorview_rsi24)
        )
    }

    val mMacdBarEntryListArr: Array<out MutableList<BarEntry>> by lazy {
        arrayOf(ArrayList<BarEntry>(), ArrayList<BarEntry>())
    }

    val mMacdEntryListArr: Array<out MutableList<Entry>> by lazy {
        arrayOf(ArrayList<Entry>(), ArrayList())
    }

    val mRsiEntryListArr: Array<out MutableList<Entry>> by lazy {
        arrayOf(ArrayList<Entry>(), ArrayList(), ArrayList())
    }

    val mKdjEntryListArr: Array<out MutableList<Entry>> by lazy {
        arrayOf(ArrayList<Entry>(), ArrayList(), ArrayList())
    }

    val mMacdBarDataSetArr by lazy {
        mMacdBarEntryListArr.mapIndexed { index, mutableList ->
            val barDataSet =
                BarDataSet(mutableList, MacdType.MACD.toString() + index)
            barDataSet.setDrawValues(false)
            barDataSet.setDrawIcons(false)
            barDataSet.axisDependency = YAxis.AxisDependency.LEFT
            barDataSet.highLightColor = mBaseHighLightColor
            barDataSet.color = mMacdBarColorArr[index]
            barDataSet
        }.toTypedArray()
    }

    val mMacdLineDataSetArr by lazy {
        mMacdEntryListArr.mapIndexed { index, mutableList ->
            val baseLineDataSet = BaseLineDataSet(
                mutableList,
                when (index) {
                    0 -> {
                        MacdType.DIF.toString()
                    }
                    1 -> {
                        MacdType.DEA.toString()
                    }
                    else -> {
                        throw IllegalArgumentException("参数错误")
                    }
                }
            )
            baseLineDataSet.color = mMacdColorArr[index]
            baseLineDataSet
        }.toTypedArray()
    }

    val mKdjLineDataSetArr by lazy {
        mKdjEntryListArr.mapIndexed { index, mutableList ->
            val baseLineDataSet = BaseLineDataSet(
                mutableList,
                when (index) {
                    0 -> {
                        KdjType.K.toString()
                    }
                    1 -> {
                        KdjType.D.toString()
                    }
                    2 -> {
                        KdjType.J.toString()
                    }
                    else -> {
                        throw IllegalArgumentException("参数错误")
                    }
                }
            )
            baseLineDataSet.color = mKdjColorArr[index]
            baseLineDataSet
        }.toTypedArray()
    }

    val mRsiLineDataSetArr by lazy {
        mRsiEntryListArr.mapIndexed { index, mutableList ->
            val baseLineDataSet = BaseLineDataSet(
                mutableList,
                when (index) {
                    0 -> {
                        RsiType.RSI6.toString()
                    }
                    1 -> {
                        RsiType.RSI12.toString()
                    }
                    2 -> {
                        RsiType.RSI24.toString()
                    }
                    else -> {
                        throw IllegalArgumentException("参数错误")
                    }
                }
            )
            baseLineDataSet.color = mRsiColorArr[index]
            baseLineDataSet
        }.toTypedArray()
    }

    fun showIndicatorType(toNext: Boolean = false) {
        if (toNext) {
            mMinorIndicatorType = when (mMinorIndicatorType) {
                MinorIndicatorType.MACD -> {
                    MinorIndicatorType.KDJ
                }
                MinorIndicatorType.KDJ -> {
                    MinorIndicatorType.RSI
                }
                MinorIndicatorType.RSI -> {
                    MinorIndicatorType.MACD
                }
            }
        }
        val minorIndicatorType: MinorIndicatorType = mMinorIndicatorType
        when {
            minorIndicatorType === MinorIndicatorType.MACD -> {
                setMacdDataSetArrVisible(true)
                setKdjDataSetArrVisible(false)
                setRsiDataSetArrVisible(false)
            }
            minorIndicatorType === MinorIndicatorType.KDJ -> {
                setMacdDataSetArrVisible(false)
                setKdjDataSetArrVisible(true)
                setRsiDataSetArrVisible(false)
            }
            minorIndicatorType === MinorIndicatorType.RSI -> {
                setMacdDataSetArrVisible(false)
                setKdjDataSetArrVisible(false)
                setRsiDataSetArrVisible(true)
            }
            else -> {
                throw  IllegalArgumentException("MasterIndicatrixType 类型错误")
            }
        }
    }

    fun setMacdDataSetArrVisible(visible: Boolean) {
        setLineDataSetArrVisible(mMacdLineDataSetArr, visible)
        setBarDataSetArrVisible(mMacdBarDataSetArr, visible)
    }

    fun setKdjDataSetArrVisible(visible: Boolean) {
        setLineDataSetArrVisible(mKdjLineDataSetArr, visible)
    }

    fun setRsiDataSetArrVisible(visible: Boolean) {
        setLineDataSetArrVisible(mRsiLineDataSetArr, visible)
    }

}