package com.matt.mpwrapper.utils

import java.math.BigDecimal


object XFormatUtil {
    val TAG = XFormatUtil::class.java.simpleName
    private const val DEF_GLOBAL_FORMAT = "0.00"

    /**
     * 账户资金相关计算显示：正数舍一；负数进一。原则是"舍去"
     */
    private fun double2BigDecimalByScale(
        num: Double,
        scale: Int,
        stripTrailingZeros: Boolean = true
    ): BigDecimal {
        val decimal = BigDecimal(num.toString())
        val scaled =
            decimal.setScale(scale, if (num >= 0) BigDecimal.ROUND_DOWN else BigDecimal.ROUND_UP)
        return if (stripTrailingZeros) {
            scaled.stripTrailingZeros()
        } else {
            scaled
        }
    }

    private fun double2BigDecimalByScaleToString(
        num: Double,
        scale: Int,
        stripTrailingZeros: Boolean = true
    ): String {
        return double2BigDecimalByScale(num, scale, stripTrailingZeros).toPlainString().toString()
    }

    /**
     * 截取数字：
     * 1. 整数部分大于等于{scale}位只显示整数；
     * 2. 否则整数部分+小数部分小于等于{scale}位。
     */
    private fun subNumberLengthByScale(num: String, scale: Int): String {
        if ('.' != num[num.length - 1] && num.contains(".")) {
            val split: Array<String> = num.split(".").toTypedArray()
            if (split.size == 2) {
                val part1 = split[0]
                val part2 = split[1]
                val length1 = part1.length
                val length2 = part2.length
                val len = scale - length1
                return if (len > 0) {
                    part1 + "." + part2.substring(0, if (len > length2) length2 else len)
                } else {
                    part1
                }
            }
        }
        return num
    }


    /**
     * 数字格式化：自定义精度
     */
    @JvmOverloads
    @JvmStatic
    fun globalFormat(number: Double, scale: Int = 2, stripTrailingZeros: Boolean = false): String {
        if (number == 0.0) return DEF_GLOBAL_FORMAT
        return double2BigDecimalByScaleToString(number, scale, stripTrailingZeros)
    }

    fun globalFormat(number: String, scale: Int = 2): String {
        return globalFormat(NumberUtils.toDouble(number), scale)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(XFormatUtil.globalFormat(1.000, 2))
        println(System.currentTimeMillis() * 1f / 100)
    }
}