package com.matt.mpwrapper.utils

object NumberUtils {
    @JvmOverloads
    fun toInt(str: String?, defaultValue: Int = 0): Int {
        return if (str == null) {
            defaultValue
        } else {
            try {
                str.toInt()
            } catch (numberFormatException: NumberFormatException) {
                defaultValue
            }
        }
    }

    @JvmOverloads
    fun toLong(str: String?, defaultValue: Long = 0L): Long {
        return if (str == null) {
            defaultValue
        } else {
            try {
                str.toLong()
            } catch (numberFormatException: NumberFormatException) {
                defaultValue
            }
        }
    }

    @JvmOverloads
    fun toFloat(str: String?, defaultValue: Float = 0.0f): Float {
        return if (str == null) {
            defaultValue
        } else {
            try {
                str.toFloat()
            } catch (numberFormatException: NumberFormatException) {
                defaultValue
            }
        }
    }

    @JvmOverloads
    fun toDouble(str: String?, defaultValue: Double = 0.0): Double {
        return if (str == null) {
            defaultValue
        } else {
            try {
                str.toDouble()
            } catch (numberFormatException: NumberFormatException) {
                defaultValue
            }
        }
    }

    @JvmOverloads
    fun toByte(str: String?, defaultValue: Byte = 0.toByte()): Byte {
        return if (str == null) {
            defaultValue
        } else {
            try {
                str.toByte()
            } catch (numberFormatException: NumberFormatException) {
                defaultValue
            }
        }
    }

    @JvmOverloads
    fun toShort(str: String?, defaultValue: Short = 0.toShort()): Short {
        return if (str == null) {
            defaultValue
        } else {
            try {
                str.toShort()
            } catch (numberFormatException: NumberFormatException) {
                defaultValue
            }
        }
    }
}