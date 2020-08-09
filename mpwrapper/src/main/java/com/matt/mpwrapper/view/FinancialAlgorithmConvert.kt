package com.matt.mpwrapper.view

import com.matt.mpwrapper.bean.*

/**
 *
 * Author : wgyscsf@163.com
 * Github : https://github.com/scsfwgy
 * Date   : 2020/8/9 3:14 PM
 * 描 述 ：
 *
 **/
object FinancialAlgorithmConvert {

    fun simpleDataList2KViewDataList(
        priceList: List<Price>,
        volList: List<Float>? = null
    ): List<KViewData> {
        val map = priceList.map { it.c }
        //主图
        val calculateMA5 = FinancialAlgorithm.calculateMA(map, 5)
        val calculateMA10 = FinancialAlgorithm.calculateMA(map, 10)
        val calculateMA20 = FinancialAlgorithm.calculateMA(map, 20)
        val calculateBOLL = FinancialAlgorithm.calculateBOLL(map)
        //副图
        val calculateMACD = FinancialAlgorithm.calculateMACD(map)
        val calculateRSI6 = FinancialAlgorithm.calculateRSI(map, 6)
        val calculateRSI12 = FinancialAlgorithm.calculateRSI(map, 12)
        val calculateRSI24 = FinancialAlgorithm.calculateRSI(map, 24)
        val calculateKDJ = FinancialAlgorithm.calculateKDJ(map)
        //量图
        val calculateVolMA5 = if (volList != null) {
            FinancialAlgorithm.calculateMA(volList, 5)
        } else {
            null
        }

        val calculateVolMA10 = if (volList != null) {
            FinancialAlgorithm.calculateMA(volList, 10)
        } else {
            null
        }

        val kViewDataList = ArrayList<KViewData>(priceList.size)
        priceList.forEachIndexed { index, it ->
            val ma5 = calculateMA5[index]
            val ma10 = calculateMA10[index]
            val ma20 = calculateMA20[index]
            val boll = calculateBOLL[index]
            val kViewData = KViewData()
            kViewData.price = it

            //主图
            val masterData = MasterData()
            masterData.ma = Ma(ma5, ma10, ma20)
            masterData.boll = boll
            kViewData.masterData = masterData

            //副图
            val minData = MinorData()
            minData.macd = calculateMACD[index]
            val rsi6 = calculateRSI6[index]
            val rsi12 = calculateRSI12[index]
            val rsi24 = calculateRSI24[index]
            minData.rsi = Rsi(rsi6, rsi12, rsi24)
            minData.kdj = calculateKDJ[index]
            kViewData.minorData = minData

            //量图
            val vols = volList?.get(index)
            val volMa5 = calculateVolMA5?.get(index)
            val volMa10 = calculateVolMA10?.get(index)
            if (vols != null && volMa5 != null && volMa10 != null) {
                val volData = VolData()
                volData.vol = Vol(vols, volMa5, volMa10)
                kViewData.volData = volData
            }
            kViewDataList.add(kViewData)
        }
        return kViewDataList
    }
}