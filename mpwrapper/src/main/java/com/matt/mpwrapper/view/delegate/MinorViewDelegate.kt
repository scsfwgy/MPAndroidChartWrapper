package com.matt.mpwrapper.view.delegate

import android.graphics.Color
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.matt.mpwrapper.R
import com.matt.mpwrapper.bean.Kdj
import com.matt.mpwrapper.bean.Macd
import com.matt.mpwrapper.bean.MinorData
import com.matt.mpwrapper.bean.Rsi
import com.matt.mpwrapper.utils.TimeUtils
import com.matt.mpwrapper.view.MinorView
import com.matt.mpwrapper.view.data.BaseBarDataSet
import com.matt.mpwrapper.view.data.BaseLineDataSet
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

    val mShowHighlightEntryList by lazy {
        ArrayList<Entry>()
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

    val mShowHighlightLineData by lazy {
        val baseLineDataSet = BaseLineDataSet(
            mShowHighlightEntryList,
            "show line"
        )
        baseLineDataSet.isHighlightEnabled = true
        baseLineDataSet.highLightColor = mBaseHighLightColor
        baseLineDataSet.color = Color.TRANSPARENT
        baseLineDataSet
    }

    val mMacdBarDataSetArr by lazy {
        mMacdBarEntryListArr.mapIndexed { index, mutableList ->
            val barDataSet = BaseBarDataSet(mutableList, MacdType.MACD.toString() + index)
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

    fun initMinorChart() {
        mMinorView.run {
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

            setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                override fun onNothingSelected() {
                    showLegend(
                        getMinorDataByIndex(mBaseKView.mBaseInit.kViewDataList().size - 1),
                        false
                    )
                }

                override fun onValueSelected(e: Entry, h: Highlight) {
                    //这样使用的前提是设置值的时候x轴是用的list的索引
                    val index = e.x.toInt()
                    showLegend(getMinorDataByIndex(index), true)
                }

            })
        }
    }

    fun showLegend(minorData: MinorData?, press: Boolean) {
        val minorView = mMinorView
        val minorIndicatorType = mMinorIndicatorType
        val legend = generateLegend(minorView.legend)
        val legendEntryArr = when (minorIndicatorType) {
            MinorIndicatorType.MACD -> {
                val macd = minorData?.macd
                if (macd != null) {
                    getMacdaLegend(macd, press)
                } else {
                    null
                }
            }
            MinorIndicatorType.RSI -> {
                val rsi = minorData?.rsi
                if (rsi != null) {
                    getRsiLegend(rsi, press)
                } else {
                    null
                }
            }
            MinorIndicatorType.KDJ -> {
                val kdj = minorData?.kdj
                if (kdj != null) {
                    getKdjLegend(kdj, press)
                } else {
                    null
                }
            }
        }
        legend.isEnabled = true
        legend.setCustom(legendEntryArr)
        minorView.legendRenderer.computeLegend(mBaseKView.data)
    }

    fun getMacdaLegend(macd: Macd, press: Boolean): Array<LegendEntry> {
        val macd1 = macd.macd
        return if (press) {
            val dif =
                generateLegendEntry(mMacdColorArr[0], "DIF " + numFormat(macd.dif))
            val dea = generateLegendEntry(mMacdColorArr[1], "DEA " + numFormat(macd.dea))
            val mixMacd =
                generateLegendEntry(
                    if (macd1 > 0) mMacdBarColorArr[0] else mMacdBarColorArr[1],
                    "MACD " + numFormat(macd1)
                )
            arrayOf(dif, dea, mixMacd)
        } else {
            getUnPressLegend("MACD(12,26,9)")
        }
    }

    private fun getRsiLegend(rsi: Rsi, press: Boolean): Array<LegendEntry> {
        return if (press) {
            val k =
                generateLegendEntry(mRsiColorArr[0], "RSI6 " + numFormat(rsi.rsi6))
            val d = generateLegendEntry(mRsiColorArr[1], "RSI12 " + numFormat(rsi.rsi12))
            val j = generateLegendEntry(mRsiColorArr[2], "RSI24 " + numFormat(rsi.rsi24))
            arrayOf(k, d, j)
        } else {
            getUnPressLegend("RSI(6,12,24)")
        }
    }

    private fun getKdjLegend(kdj: Kdj, press: Boolean): Array<LegendEntry> {
        return if (press) {
            val k =
                generateLegendEntry(mKdjColorArr[0], "K " + numFormat(kdj.k))
            val d = generateLegendEntry(mKdjColorArr[1], "D " + numFormat(kdj.d))
            val j = generateLegendEntry(mKdjColorArr[2], "J " + numFormat(kdj.j))
            arrayOf(k, d, j)
        } else {
            getUnPressLegend("KDJ(9,3,3)")
        }
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
        //默认高亮线对应的line一定要显示
        setLineDataSetArrVisible(arrayOf(mShowHighlightLineData), true)

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