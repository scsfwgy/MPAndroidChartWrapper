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
    fun add(number1: Any?, number2: Any?): Double {
        return calculate(0, true, number1, number2)
    }

    /**
     * double 保证精度相减
     */
    fun subtract(number1: Any?, number2: Any?): Double {
        return calculate(1, true, number1, number2)
    }

    /**
     * double 保证精度相乘
     */
    fun multiply(number1: Any?, number2: Any?): Double {
        return calculate(2, true, number1, number2)
    }

    /**
     * 保证精度相除
     */
    @JvmStatic
    fun divide(number: Any?, nums: Any): Double {
        return calculate(3, true, number, nums)
    }

    private fun calculate(type: Int, safe: Boolean = true, number1: Any?, number2: Any?): Double {
        return if (safe) {
            try {
                mathData(type, number1, number2)
            } catch (e: Exception) {
                e.printStackTrace()
                0.0
            }
        } else {
            mathData(type,number1, number2)
        }
    }

    private fun mathData(type: Int, number1: Any?, number2: Any?): Double {
        val num1 = number1?.toString() ?: "0.0"
        val num2 = number2?.toString() ?: "0.0"
        val bigDecimal1 = BigDecimal(num1)
        val bigDecimal2 = BigDecimal(num2)
        val result = when (type) {
            0 -> {
                bigDecimal1.add(bigDecimal2)
            }
            1 -> {
                bigDecimal1.subtract(bigDecimal2)
            }
            2 -> {
                bigDecimal1.multiply(bigDecimal2)
            }
            3 -> {
                bigDecimal1.divide(bigDecimal2, 10, BigDecimal.ROUND_HALF_UP)
            }
            else -> throw IllegalArgumentException("参数不合法")
        }
        return result.toDouble()
    }
}