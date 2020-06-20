package com.matt.mpwrapper.bean

/**
 * ============================================================
 * 作 者 :    matt@163.com
 * 更新时间 ：2018/09/14 15:28
 * 描 述 ：量图，对于量图有两条ma线，作用和主图的ma基本一致。
 * ============================================================
 */
class Vol {
    //vol 量，可选
    var vol: Double? = null
    //vol
    var volMa5: Double? = null
    var volMa10: Double? = null
    override fun toString(): String {
        return "Vol{" +
                "vol=" + vol +
                ", volMa5=" + volMa5 +
                ", volMa10=" + volMa10 +
                '}'
    }
}