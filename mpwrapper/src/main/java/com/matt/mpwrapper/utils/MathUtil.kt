package com.matt.mpwrapper.utils

import java.math.BigDecimal

/**
 *
 * 保证精度加减乘除
 * @author matt
 */
object MathUtil {
    private const val TAG = "MathUtil"

    /**
     * double 保证精度相加
     */
    fun add(number: Any?, vararg nums: Any): Double {
        return calculate(0, true, number, nums)
    }

    /**
     * double 保证精度相减
     */
    fun subtract(number: Any?, vararg nums: Any): Double {
        return calculate(1, true, number, nums)
    }

    /**
     * double 保证精度相乘
     */
    fun multiply(number: Any?, vararg nums: Any): Double {
        return calculate(2, true, number, nums)
    }

    /**
     * 保证精度相除
     */
    @JvmStatic
    fun divide(number: Any?, vararg nums: Any): Double {
        return calculate(3, true, number, nums)
    }

    private fun calculate(type: Int, safe: Boolean = true, number: Any?, vararg nums: Any): Double {
        return if (safe) {
            try {
                mathData(type, number, nums)
            } catch (e: Exception) {
                e.printStackTrace()
                0.0
            }
        } else {
            mathData(type, number, nums)
        }
    }

    private fun mathData(type: Int, number: Any?, vararg nums: Any): Double {
        val num = {
            if (number == null || NumberUtils.toDouble(number.toString()) == 0.0) {
                0.0
            } else {
                number
            }
        }
        var numBase = BigDecimal(num.toString())
        if (nums.isNullOrEmpty()) return numBase.toDouble()
        nums.forEach {
            val data = it.toString()
            val bigDecimal = BigDecimal(data)
            numBase = when (type) {
                0 -> {
                    numBase.add(bigDecimal)
                }
                1 -> {
                    numBase.subtract(bigDecimal)
                }
                2 -> {
                    numBase.multiply(bigDecimal)
                }
                3 -> {
                    numBase.divide(BigDecimal(data), 10, BigDecimal.ROUND_HALF_UP)
                }
                else -> throw IllegalArgumentException("参数不合法")
            }
        }
        return numBase.toDouble()
    }
}