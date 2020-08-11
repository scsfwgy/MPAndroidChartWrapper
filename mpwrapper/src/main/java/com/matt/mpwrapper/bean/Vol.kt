package com.matt.mpwrapper.bean

/**
 * ============================================================
 * 作 者 :    matt@163.com
 * 更新时间 ：2018/09/14 15:28
 * 描 述 ：量图，对于量图有两条ma线，作用和主图的ma基本一致。
 * ============================================================
 */
data class Vol(var vol: Float, val volMa5: Float, val volMa10: Float)