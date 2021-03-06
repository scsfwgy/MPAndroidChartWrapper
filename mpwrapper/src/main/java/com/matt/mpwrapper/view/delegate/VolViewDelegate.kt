package com.matt.mpwrapper.view.delegate

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.matt.mpwrapper.R
import com.matt.mpwrapper.bean.Vol
import com.matt.mpwrapper.bean.VolData
import com.matt.mpwrapper.utils.TimeUtils
import com.matt.mpwrapper.view.VolView
import com.matt.mpwrapper.view.type.VolIndicatorType
import com.matt.mpwrapper.view.type.VolType

/**
 * ============================================================
 * 作 者 :    matt
 * 更新时间 ：2020/03/07 15:14
 * 描 述 ：
 * ============================================================
 */
class VolViewDelegate(val mVolView: VolView) : BaseKViewDelegate(mVolView) {

    var mVolIndicatorType: VolIndicatorType = VolIndicatorType.VOL_MA

    val mVolBarColorArr by lazy {
        arrayOf(
            mUpColor,
            mDownColor
        )
    }

    val mVolMaColorArr by lazy {
        arrayOf(
            getColor(R.color.mp_volview_ma5),
            getColor(R.color.mp_volview_ma10)
        )
    }


    val mVolBarDataSetArr by lazy {
        mCombinedDataControl.getBarDataSets(
            arrayOf(
                VolType.VOL.toString() + 0,
                VolType.VOL.toString() + 1
            )
        ).mapIndexed { index, baseLineDataSet ->
            baseLineDataSet.color = mVolBarColorArr[index]
            baseLineDataSet
        }.toTypedArray()
    }

    val mVolMaLineDataSetArr by lazy {
        mCombinedDataControl.getLineDataSets(
            arrayOf(
                VolType.MA5.toString(),
                VolType.MA10.toString()
            )
        ).mapIndexed { index, baseLineDataSet ->
            baseLineDataSet.color = mVolMaColorArr[index]
            baseLineDataSet
        }.toTypedArray()
    }


    override fun initChart() {
        super.initChart()
        mVolView.run {
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
                        getVolDataByIndex(mBaseKView.mBaseInit.kViewDataList().size - 1),
                        false
                    )
                }

                override fun onValueSelected(e: Entry, h: Highlight) {
                    //这样使用的前提是设置值的时候x轴是用的list的索引
                    val index = e.x.toInt()
                    showLegend(getVolDataByIndex(index), true)
                }

            })
        }
    }

    fun showLegend(volData: VolData?, press: Boolean) {
        val volView = mVolView
        val vol = volData?.vol ?: throw IllegalArgumentException("VolData or vol is null")
        val legend = generateLegend(volView.legend)
        val legendEntryArr = getVolLegend(vol, press)
        legend.isEnabled = true
        legend.setCustom(legendEntryArr)
        volView.legendRenderer.computeLegend(mBaseKView.data)
    }

    fun getVolLegend(vol: Vol, press: Boolean): Array<LegendEntry> {
        return if (press) {
            val dif =
                generateLegendEntry(mVolMaColorArr[0], "MA5 " + numFormat(vol.volMa5))
            val dea = generateLegendEntry(mVolMaColorArr[1], "MA10 " + numFormat(vol.volMa10))
            val vols = vol.vol ?: 0.0f
            val volData =
                generateLegendEntry(
                    if (vols > 0.0f) mVolBarColorArr[0] else mVolBarColorArr[1],
                    "VOL " + numFormat(vols)
                )
            arrayOf(dif, dea, volData)
        } else {
            getUnPressLegend("VOL,MA(5,10)")
        }
    }

    fun setVolDataSetArrVisible(visible: Boolean) {
        setLineDataSetArrVisible(mVolMaLineDataSetArr, visible)
        setBarDataSetArrVisible(mVolBarDataSetArr, visible)
    }

    fun showIndicatorType(toNext: Boolean) {
        setVolDataSetArrVisible(true)
        //默认高亮线对应的line一定要显示
        setLineDataSetArrVisible(arrayOf(mShowHighlightLineData), true)
    }
}