package com.matt.mpwrapper.utils

import android.util.Log
import java.math.BigDecimal
import java.util.*

object FormatUtil {
    /**
     * 浮点数格式化
     *
     * @param isPercentage 是否为分数
     * @param needSign     是否需要正号
     * @param isMoney      是否使用金钱格式(每3位用","分隔)
     * @param digit        需要保留的位数
     * @param num          需要格式化的值(int、float、double、String、byte均可)
     */
    fun format(
        isPercentage: Boolean,
        needSign: Boolean,
        isMoney: Boolean,
        digit: Int,
        num: Any
    ): String {
        val digits = if (digit < 0) 0 else digit
        val converted: Double = try {
            num.toString().toDouble()
        } catch (e: Throwable) {
            Log.e("FormatErr", "Input number is not kind number")
            0.0
        } //需求大多四舍五入  float保留位数会舍去后面的
        val sb = StringBuilder("%")
        if (needSign && converted > 0) {
            sb.append("+")
        }
        if (isMoney) {
            sb.append(",")
        }
        sb.append(".").append(digits).append("f")
        if (isPercentage) {
            sb.append("%%")
        }
        return String.format(Locale.getDefault(), sb.toString(), converted)
    }

    @JvmStatic
    fun numFormat(num: Any, digit: Int): String {
        return numFormat(false, digit, num)
    }

    @JvmStatic
    fun numFormat(needSign: Boolean, digit: Int, num: Any): String {
        return format(false, needSign, false, digit, num)
    }

    /**
     * 带%格式化
     */
    fun percentageFormat(num: Any): String {
        return percentageFormat(true, num)
    }

    fun percentageFormat(needSign: Boolean, num: Any): String {
        return percentageFormat(needSign, 2, num)
    }

    fun percentageFormat(digit: Int, num: Any): String {
        return percentageFormat(false, digit, num)
    }

    @JvmStatic
    fun percentageFormat(needSign: Boolean, digit: Int, num: Any): String {
        return format(true, needSign, false, digit, num)
    }

    /**
     * 以金钱格式表示的数字
     */
    fun moneyFormat(num: Any): String {
        return moneyFormat(true, num)
    }

    fun moneyFormat(isPercentage: Boolean, num: Any): String {
        return moneyFormat(isPercentage, 2, num)
    }

    fun moneyFormat(isPercentage: Boolean, digit: Int, num: Any): String {
        return format(isPercentage, needSign = false, isMoney = true, digit = digit, num = num)
    }


    /**
     * 不四舍五入取n位小数
     */
    fun formatBySubString(obj: Any, digit: Int): String {
        var digits = if (digit < 0) 0 else digit
        var num = obj.toString()
        var i = num.indexOf(".")
        if (i >= 0) {
            if (num.length - ++i > digits) {
                num = num.substring(0, i + digits)
            }
        }
        return num
    }

    /**
     * 进位处理
     *
     * @param scale 保留几位
     */
    fun roundUp(scale: Int, num: Any): String {
        val decimal = BigDecimal(num.toString())
        return decimal.setScale(scale, BigDecimal.ROUND_UP).toString()
    }

    /**
     * 直接舍弃多余小数
     */
    fun roundDown(scale: Int, num: Any): String {
        val decimal = BigDecimal(num.toString())
        return decimal.setScale(scale, BigDecimal.ROUND_DOWN).toString()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val num1 = -123456.789000
        val num2 = 0.0
        val num3 = 1.00
        val num4 = 1444444444.0
        val num5 = 123.123
        val num6 = 123.123
        val num7 = 123.123
        val num8 = 123.123
        val num9 = 123.123
        val num10 = 123.123
        val numStr1 = format(false, false, false, 5, num1)
        val numStr2 = format(true, false, false, 5, num1)
        val numStr3 = format(false, true, false, 5, num1)
        val numStr4 = format(false, false, true, 5, num1)
        println(numStr1)
        println(numStr2)
        println(numStr3)
        println(numStr4)
    }
}