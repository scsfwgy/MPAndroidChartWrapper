package com.matt.mpwrapper.utils

import java.math.BigDecimal

/**
 * 测试用例：[XFormatUtilTest],修改这里的方法注意跑一下测试用例，因为修改会影响整个应用
 */
object XFormatUtil {
    val TAG = XFormatUtil::class.java.simpleName
    private const val DEF_GLOBAL_FORMAT = "0.00"

    /**
     * 账户资金相关计算显示
     */
    private fun double2BigDecimalByScale(
            num: String,
            scale: Int,
            stripTrailingZeros: Boolean = true,
            roundDown: Boolean = true
    ): BigDecimal {
        val roundType = if (roundDown) {
            BigDecimal.ROUND_DOWN
        } else {
            BigDecimal.ROUND_UP
        }
        val decimal = BigDecimal(num)
        val scaled =
                decimal.setScale(scale, roundType)
        return if (stripTrailingZeros) {
            scaled.stripTrailingZeros()
        } else {
            scaled
        }
    }

    private fun double2BigDecimalByScaleToString(
            num: String,
            scale: Int,
            stripTrailingZeros: Boolean = true,
            roundDown: Boolean = true,
            safe: Boolean = false
    ): String {
        if (!safe) {
            return double2BigDecimalByScale(num, scale, stripTrailingZeros, roundDown).toPlainString().toString()
        }
        return try {
            double2BigDecimalByScale(num, scale, stripTrailingZeros, roundDown).toPlainString().toString()
        } catch (e: Exception) {
            e.printStackTrace()
            DEF_GLOBAL_FORMAT
        }
    }

    @JvmOverloads
    @JvmStatic
    fun globalFormat(number: Any?, scale: Int = 2,
                     stripTrailingZeros: Boolean = true,
                     roundDown: Boolean = true
    ): String {
        if (number == null) return DEF_GLOBAL_FORMAT
        val finalParam = when (number) {
            is String -> {
                number
            }
            is Number -> {
                number.toString()
            }
            else -> {
                DEF_GLOBAL_FORMAT
            }
        }
        return double2BigDecimalByScaleToString(finalParam, scale, stripTrailingZeros, roundDown, true)
    }
}