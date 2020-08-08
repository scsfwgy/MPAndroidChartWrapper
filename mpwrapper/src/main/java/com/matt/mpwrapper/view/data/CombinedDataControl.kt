package com.matt.mpwrapper.view.data

import com.github.mikephil.charting.data.*

/**
 *
 * Author : wgyscsf@163.com
 * Github : https://github.com/scsfwgy
 * Date   : 2020/8/8 5:03 PM
 * 描 述 ：数据源控制器，保证数据源唯一且可控
 *
 **/
class CombinedDataControl(baseCombinedData: BaseCombinedData) {

    val combinedData by lazy {
        baseCombinedData
    }
    val lineData by lazy {
        LineData()
    }
    val candleData by lazy {
        CandleData()
    }
    val barData by lazy {
        BarData()
    }
    val scatterData by lazy {
        ScatterData()
    }
    val bubbleData by lazy {
        BubbleData()
    }
    val lineDataSetMap by lazy {
        HashMap<String, List<LineDataSet>>()
    }
    val candleDataSetMap by lazy {
        HashMap<String, List<CandleDataSet>>()
    }
    val barDataSetMap by lazy {
        HashMap<String, List<BarDataSet>>()
    }
    val scatterDataSetMap by lazy {
        HashMap<String, List<ScatterDataSet>>()
    }
    val bubbleDataSetMap by lazy {
        HashMap<String, List<BubbleDataSet>>()
    }

    init {
        baseCombinedData.setAllData(lineData, candleData, barData, scatterData, bubbleData)
    }

}