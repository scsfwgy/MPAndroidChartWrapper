package com.matt.mpwrapper.view.type

import java.util.*

/**
 * ============================================================
 * 作 者 :    freer-2
 * 更新时间 ：2019/09/09 17:41
 * 描 述 ：
 * ============================================================
 */
enum class BinKType(val id: Int, val apiKey: String, val label: String) {
    K_TIMESHARE(1, "分时", "1m"),
    K_1M(2, "1分", "1m"),
    K_3M(3, "3分", "3m"),
    K_5M(4, "5分", "5m"),
    K_15M(5, "15分", "15m"),
    K_30M(6, "30分", "30m"),
    K_1H(7, "1小时", "1h"),
    K_2H(8, "2小时", "2h"),
    K_4H(9, "4小时", "4h"),
    K_6H(10, "6小时", "6h"),
    K_8H(11, "8小时", "8h"),
    K_12H(12, "12小时", "12h"),
    K_1D(13, "1天", "1d"),
    K_3D(14, "3天", "3d"),
    K_1W(15, "1周", "1w"),
    K_1MONTH(16, "1月", "1M");

    companion object {

        fun binKTypeList(): List<BinKType> {
            val kTypeList: MutableList<BinKType> = ArrayList()
            kTypeList.add(K_TIMESHARE)
            kTypeList.add(K_1M)
            kTypeList.add(K_3M)
            kTypeList.add(K_5M)
            kTypeList.add(K_15M)
            kTypeList.add(K_30M)
            kTypeList.add(K_1H)
            kTypeList.add(K_2H)
            kTypeList.add(K_4H)
            kTypeList.add(K_6H)
            kTypeList.add(K_8H)
            kTypeList.add(K_12H)
            kTypeList.add(K_1D)
            kTypeList.add(K_3D)
            kTypeList.add(K_1W)
            kTypeList.add(K_1MONTH)
            return kTypeList
        }

        fun getBinKTypeTitleList(): List<String> {
            val binKTypeList = binKTypeList()
            return binKTypeList.map { it.label }
        }
    }

}