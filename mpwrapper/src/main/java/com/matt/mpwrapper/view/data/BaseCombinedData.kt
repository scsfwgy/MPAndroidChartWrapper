package com.matt.mpwrapper.view.data

import com.github.mikephil.charting.data.*

/**
 *
 * Author : wgyscsf@163.com
 * Github : https://github.com/scsfwgy
 * Date   : 2020/7/26 7:08 PM
 * 描 述 ：
 *
 **/
class BaseCombinedData : CombinedData() {

    fun setAllData(
        lineData: LineData? = null,
        candleData: CandleData? = null,
        barData: BarData? = null,
        scatterData: ScatterData? = null,
        bubbleData: BubbleData? = null
    ) {
        setData(lineData)
        setData(candleData)
        setData(barData)
        setData(scatterData)
        setData(bubbleData)
        notifyDataChanged2()
    }

    override fun notifyDataChanged() {
        //super.notifyDataChanged()
    }

    fun notifyDataChanged2() {
        lineData?.notifyDataChanged()
        candleData?.notifyDataChanged()
        barData?.notifyDataChanged()
        scatterData?.notifyDataChanged()
        bubbleData?.notifyDataChanged()
        calcMinMax()
    }
}