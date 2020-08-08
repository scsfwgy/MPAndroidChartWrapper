package com.matt.mpwrapper.view.data

/**
 *
 * Author : wgyscsf@163.com
 * Github : https://github.com/scsfwgy
 * Date   : 2020/8/8 5:03 PM
 * 描 述 ：数据源控制器，保证数据源唯一且可控
 *
 **/
class CombinedDataControl {

    val lineDataSetMap by lazy {
        HashMap<String, BaseLineDataSet>()
    }
    val candleDataSetMap by lazy {
        HashMap<String, BaseCandleDataSet>()
    }
    val barDataSetMap by lazy {
        HashMap<String, BaseBarDataSet>()
    }

    fun getLineDataSets(labels: Array<String>): List<BaseLineDataSet> {
        return labels.map {
            getLineDataSet(it)
        }
    }


    fun getLineDataSet(label: String, key: String? = null): BaseLineDataSet {
        val realKey = key ?: label
        val baseLineDataSet = lineDataSetMap[realKey]
        return if (baseLineDataSet == null) {
            val newBaseLineDataSet = BaseLineDataSet(ArrayList(), label)
            lineDataSetMap[realKey] = newBaseLineDataSet
            newBaseLineDataSet
        } else {
            baseLineDataSet
        }
    }

    fun getCandleDataSet(label: String, key: String? = null): BaseCandleDataSet {
        val realKey = key ?: label
        val baseLineDataSet = candleDataSetMap[realKey]
        return if (baseLineDataSet == null) {
            val newBaseLineDataSet = BaseCandleDataSet(ArrayList(), label)
            candleDataSetMap[realKey] = newBaseLineDataSet
            newBaseLineDataSet
        } else {
            baseLineDataSet
        }
    }

    fun getBarDataSets(labels: Array<String>): List<BaseBarDataSet> {
        return labels.map {
            getBarDataSet(it)
        }
    }

    fun getBarDataSet(label: String, key: String? = null): BaseBarDataSet {
        val realKey = key ?: label
        val baseLineDataSet = barDataSetMap[realKey]
        return if (baseLineDataSet == null) {
            val newBaseLineDataSet = BaseBarDataSet(ArrayList(), label)
            barDataSetMap[realKey] = newBaseLineDataSet
            newBaseLineDataSet
        } else {
            baseLineDataSet
        }
    }

    fun resetAll() {
        //清空数据，但是数据结构保留
        lineDataSetMap.forEach { entry ->
            entry.value.clear()
        }
        candleDataSetMap.forEach { entry ->
            entry.value.clear()
        }
        barDataSetMap.forEach { entry ->
            entry.value.clear()
        }
    }

}