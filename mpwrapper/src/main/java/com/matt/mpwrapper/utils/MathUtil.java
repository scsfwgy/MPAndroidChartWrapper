package com.matt.mpwrapper.utils;

import android.util.Log;

import com.matt.mpwrapper.BuildConfig;

import java.math.BigDecimal;


/**
 * 格式转换类
 *
 * @author blue
 */
public class MathUtil {
    private static final String TAG = "MathUtil";

    /**
     * 四舍五入小数点长度格式化
     *
     * @param number 要转换的字符
     * @param length 要保留的位数
     * @return 格式化后的字符
     */
    public static String doubleLengthFormate(Object number, int length) {
        return doubleLengthFormate(number, length, true);
    }

    public static String doubleLengthFormate(Object number, int length, boolean round) {
        return doubleLengthFormate(null, number, length, round);
    }

    public static String doubleLengthFormate(String format, Object number, int length, boolean round) {
        if (length < 0) {
            length = 0;
        }
        if (number == null) {
            number = 0.0;
        }
        if (format == null) {
            format = "%." + length + "f";
        }

        if (!round) {
            number = cutLenthToString(number, length);
        }
        return String.format(format, NumberUtils.toDouble(number.toString()));
    }

    /**
     * 截取至小数点后几位,不补零(非四舍五入)
     */
    public static String cutLenthToString(Object num, int length) {
        if (length < 0) {
            length = 0;
        }
        if (num == null) {
            num = 0;
        }
        String s = num.toString();
        if (s.contains(".")) {
            int index = s.indexOf(".") + 1;
            if (s.length() - index > length) {
                s = s.substring(0, index + (length == 0 ? -1 : length));
            }
        }
        return s;
    }

    public static double cutLenthToDouble(Object num, int length) {
        return NumberUtils.toDouble(cutLenthToString(num, length));
    }

    /**
     * double 保证精度相加
     */
    public static double add(Object num, Object... nums) {
        try {
            if (num == null || ObjectUtils.isEmpty(num.toString())) {
                num = 0;
            }
            if (nums == null) {
                return NumberUtils.toDouble(num.toString());
            }
            BigDecimal numBase = new BigDecimal(num.toString());
            if (ObjectUtils.isNotEmpty(nums)) {
                for (Object obj : nums) {
                    if (ObjectUtils.isNotEmpty(obj.toString())) {
                        numBase = numBase.add(new BigDecimal(obj.toString()));
                    }
                }
            }
            return numBase.doubleValue();
        } catch (Exception e) {
            if (BuildConfig.DEBUG) Log.e(TAG, e.toString());
            return 0;
        }
    }

    /**
     * double 保证精度相减
     */
    public static double subtract(Object num, Object... nums) {
        try {
            if (num == null || ObjectUtils.isEmpty(num.toString())) {
                num = 0;
            }
            if (nums == null) {
                return NumberUtils.toDouble(num.toString());
            }
            BigDecimal numBase = new BigDecimal(num.toString());
            if (ObjectUtils.isNotEmpty(nums)) {
                for (Object obj : nums) {
                    if (ObjectUtils.isNotEmpty(obj.toString())) {
                        numBase = numBase.subtract(new BigDecimal(obj.toString()));
                    }
                }
            }
            return numBase.doubleValue();
        } catch (Exception e) {
            if (BuildConfig.DEBUG) Log.e(TAG, e.toString());
            return 0;
        }
    }

    /**
     * double 保证精度相乘
     */
    public static double multiply(Object num, Object... nums) {
        try {
            if (num == null || ObjectUtils.isEmpty(num.toString()) || nums == null) {
                return 0;
            }
            BigDecimal numBase = new BigDecimal(num.toString());
            if (ObjectUtils.isNotEmpty(nums)) {
                for (Object obj : nums) {
                    if (ObjectUtils.isNotEmpty(obj.toString())) {
                        numBase = numBase.multiply(new BigDecimal(obj.toString()));
                    } else {
                        return 0;
                    }
                }
            }
            return numBase.doubleValue();
        } catch (Exception e) {
            if (BuildConfig.DEBUG) Log.e(TAG, e.toString());
            return 0;
        }
    }

    /**
     * 保证精度相除
     */
    public static double divide(Object num, Object... nums) {
        try {
            if (num == null || NumberUtils.toDouble(num.toString()) == 0 || nums == null) {
                return 0;
            }
            //要先转化为String 不然没有效果
            BigDecimal numBase = new BigDecimal(num.toString());
            if (ObjectUtils.isNotEmpty(nums)) {
                for (Object obj : nums) {
                    if (NumberUtils.toDouble(obj.toString()) != 0) {
                        //ROUND_HALF_UP 四舍五入模式  默认精度为10
                        numBase = numBase.divide(new BigDecimal(obj.toString()), 10, BigDecimal.ROUND_HALF_UP);
                    } else {
                        return 0;
                    }
                }
            }
            return numBase.doubleValue();
        } catch (Exception e) {
            if (BuildConfig.DEBUG) Log.e(TAG, e.toString());
            return 0;
        }
    }

    public static String format(Object num, int scale) {
        return format(num, scale, false);
    }

    /**
     * @param number 要处理的字符
     * @param scale  保留位数
     * @param round  是否四舍五入 默认false
     */
    public static String format(Object number, int scale, boolean round) {
        if (number == null) {
            number = 0.0;
        }
        if (round) {
            return doubleLengthFormate(number, scale);
        }
        return doubleLengthFormate(cutLenthToString(number, scale), scale);
    }
}
