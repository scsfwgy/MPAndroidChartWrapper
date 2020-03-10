package com.wgyscsf.mpwrapper.bean

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 更新时间 ：2018/09/14 15:22
 * 描 述 ：
 * ============================================================
 */
class Macd {
    //macd
    var dif: Double? = null
    var dea: Double? = null
    var macd: Double? = null
    val isDifAvailable: Boolean
        get() = dif != KViewConstant.VALUE_DEF.toDouble()

    val isDeaAvailable: Boolean
        get() = dea != KViewConstant.VALUE_DEF.toDouble()

    val isMacdAvailable: Boolean
        get() = macd != KViewConstant.VALUE_DEF.toDouble()
}