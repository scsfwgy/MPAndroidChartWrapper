package com.matt.mpwrapper.ktx

import com.github.mikephil.charting.utils.Utils

/**
 * ============================================================
 * 作 者 :    matt
 * 更新时间 ：2020/03/07 13:06
 * 描 述 ：
 * ============================================================
 */

fun Any.dip2px(dp: Float): Float {
    return Utils.convertDpToPixel(dp)
}

fun Any.px2dp(px: Float): Float {
    return Utils.convertPixelsToDp(px)
}