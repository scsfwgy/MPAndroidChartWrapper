//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wgyscsf.mpwrapper.utils;


import java.lang.reflect.Array;
import java.math.BigInteger;

import static android.text.TextUtils.isEmpty;

public class NumberUtils {
    public static final Long LONG_ZERO = 0L;
    public static final Long LONG_ONE = 1L;
    public static final Long LONG_MINUS_ONE = -1L;
    public static final Integer INTEGER_ZERO = 0;
    public static final Integer INTEGER_ONE = 1;
    public static final Integer INTEGER_MINUS_ONE = -1;
    public static final Short SHORT_ZERO = (short) 0;
    public static final Short SHORT_ONE = (short) 1;
    public static final Short SHORT_MINUS_ONE = (short) -1;
    public static final Byte BYTE_ZERO = (byte) 0;
    public static final Byte BYTE_ONE = (byte) 1;
    public static final Byte BYTE_MINUS_ONE = (byte) -1;
    public static final Double DOUBLE_ZERO = 0.0D;
    public static final Double DOUBLE_ONE = 1.0D;
    public static final Double DOUBLE_MINUS_ONE = -1.0D;
    public static final Float FLOAT_ZERO = 0.0F;
    public static final Float FLOAT_ONE = 1.0F;
    public static final Float FLOAT_MINUS_ONE = -1.0F;

    public NumberUtils() {
    }

    public static int toInt(String str) {
        return toInt(str, 0);
    }

    public static int toInt(String str, int defaultValue) {
        if (str == null) {
            return defaultValue;
        } else {
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException var3) {
                return defaultValue;
            }
        }
    }

    public static long toLong(String str) {
        return toLong(str, 0L);
    }

    public static long toLong(String str, long defaultValue) {
        if (str == null) {
            return defaultValue;
        } else {
            try {
                return Long.parseLong(str);
            } catch (NumberFormatException var4) {
                return defaultValue;
            }
        }
    }

    public static float toFloat(String str) {
        return toFloat(str, 0.0F);
    }

    public static float toFloat(String str, float defaultValue) {
        if (str == null) {
            return defaultValue;
        } else {
            try {
                return Float.parseFloat(str);
            } catch (NumberFormatException var3) {
                return defaultValue;
            }
        }
    }

    public static double toDouble(String str) {
        return toDouble(str, 0.0D);
    }

    public static double toDouble(String str, double defaultValue) {
        if (str == null) {
            return defaultValue;
        } else {
            try {
                return Double.parseDouble(str);
            } catch (NumberFormatException var4) {
                return defaultValue;
            }
        }
    }

    public static byte toByte(final String str) {
        return toByte(str, (byte) 0);
    }

    public static byte toByte(String str, byte defaultValue) {
        if (str == null) {
            return defaultValue;
        } else {
            try {
                return Byte.parseByte(str);
            } catch (NumberFormatException var3) {
                return defaultValue;
            }
        }
    }

    public static short toShort(String str) {
        return toShort(str, (short) 0);
    }

    public static short toShort(String str, short defaultValue) {
        if (str == null) {
            return defaultValue;
        } else {
            try {
                return Short.parseShort(str);
            } catch (NumberFormatException var3) {
                return defaultValue;
            }
        }
    }


    public static Float createFloat(String str) {
        return str == null ? null : Float.valueOf(str);
    }

    public static Double createDouble(String str) {
        return str == null ? null : Double.valueOf(str);
    }

    public static Integer createInteger(String str) {
        return str == null ? null : Integer.decode(str);
    }

    public static Long createLong(String str) {
        return str == null ? null : Long.decode(str);
    }

    public static BigInteger createBigInteger(String str) {
        if (str == null) {
            return null;
        } else {
            int pos = 0;
            int radix = 10;
            boolean negate = false;
            if (str.startsWith("-")) {
                negate = true;
                pos = 1;
            }

            if (!str.startsWith("0x", pos) && !str.startsWith("0X", pos)) {
                if (str.startsWith("#", pos)) {
                    radix = 16;
                    ++pos;
                } else if (str.startsWith("0", pos) && str.length() > pos + 1) {
                    radix = 8;
                    ++pos;
                }
            } else {
                radix = 16;
                pos += 2;
            }

            BigInteger value = new BigInteger(str.substring(pos), radix);
            return negate ? value.negate() : value;
        }
    }


    public static long min(long... array) {
        validateArray(array);
        long min = array[0];

        for (int i = 1; i < array.length; ++i) {
            if (array[i] < min) {
                min = array[i];
            }
        }

        return min;
    }

    public static int min(int... array) {
        validateArray(array);
        int min = array[0];

        for (int j = 1; j < array.length; ++j) {
            if (array[j] < min) {
                min = array[j];
            }
        }

        return min;
    }

    public static short min(short... array) {
        validateArray(array);
        short min = array[0];

        for (int i = 1; i < array.length; ++i) {
            if (array[i] < min) {
                min = array[i];
            }
        }

        return min;
    }

    public static byte min(byte... array) {
        validateArray(array);
        byte min = array[0];

        for (int i = 1; i < array.length; ++i) {
            if (array[i] < min) {
                min = array[i];
            }
        }

        return min;
    }

    public static double min(double... array) {
        validateArray(array);
        double min = array[0];

        for (int i = 1; i < array.length; ++i) {
            if (Double.isNaN(array[i])) {
                return 0.0D / 0.0;
            }

            if (array[i] < min) {
                min = array[i];
            }
        }

        return min;
    }

    public static float min(float... array) {
        validateArray(array);
        float min = array[0];

        for (int i = 1; i < array.length; ++i) {
            if (Float.isNaN(array[i])) {
                return (float) (0.0F / 0.0);
            }

            if (array[i] < min) {
                min = array[i];
            }
        }

        return min;
    }

    public static long max(long... array) {
        validateArray(array);
        long max = array[0];

        for (int j = 1; j < array.length; ++j) {
            if (array[j] > max) {
                max = array[j];
            }
        }

        return max;
    }

    public static int max(int... array) {
        validateArray(array);
        int max = array[0];

        for (int j = 1; j < array.length; ++j) {
            if (array[j] > max) {
                max = array[j];
            }
        }

        return max;
    }

    public static short max(short... array) {
        validateArray(array);
        short max = array[0];

        for (int i = 1; i < array.length; ++i) {
            if (array[i] > max) {
                max = array[i];
            }
        }

        return max;
    }

    public static byte max(byte... array) {
        validateArray(array);
        byte max = array[0];

        for (int i = 1; i < array.length; ++i) {
            if (array[i] > max) {
                max = array[i];
            }
        }

        return max;
    }

    public static double max(double... array) {
        validateArray(array);
        double max = array[0];

        for (int j = 1; j < array.length; ++j) {
            if (Double.isNaN(array[j])) {
                return 0.0D / 0.0;
            }

            if (array[j] > max) {
                max = array[j];
            }
        }

        return max;
    }

    public static float max(float... array) {
        validateArray(array);
        float max = array[0];

        for (int j = 1; j < array.length; ++j) {
            if (Float.isNaN(array[j])) {
                return (float) (0.0F / 0.0);
            }

            if (array[j] > max) {
                max = array[j];
            }
        }

        return max;
    }

    public static void isTrue(boolean expression, String message, Object... values) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(message, values));
        }
    }

    public static void isTrue(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException("The validated expression is false");
        }
    }

    private static void validateArray(Object array) {
        isTrue(array != null, "The Array must not be null");
        isTrue(Array.getLength(array) != 0, "Array cannot be empty.");
    }

    public static long min(long a, long b, long c) {
        if (b < a) {
            a = b;
        }

        if (c < a) {
            a = c;
        }

        return a;
    }

    public static int min(int a, int b, int c) {
        if (b < a) {
            a = b;
        }

        if (c < a) {
            a = c;
        }

        return a;
    }

    public static short min(short a, short b, short c) {
        if (b < a) {
            a = b;
        }

        if (c < a) {
            a = c;
        }

        return a;
    }

    public static byte min(byte a, byte b, byte c) {
        if (b < a) {
            a = b;
        }

        if (c < a) {
            a = c;
        }

        return a;
    }

    public static double min(double a, double b, double c) {
        return Math.min(Math.min(a, b), c);
    }

    public static float min(float a, float b, float c) {
        return Math.min(Math.min(a, b), c);
    }

    public static long max(long a, long b, long c) {
        if (b > a) {
            a = b;
        }

        if (c > a) {
            a = c;
        }

        return a;
    }

    public static int max(int a, int b, int c) {
        if (b > a) {
            a = b;
        }

        if (c > a) {
            a = c;
        }

        return a;
    }

    public static short max(short a, short b, short c) {
        if (b > a) {
            a = b;
        }

        if (c > a) {
            a = c;
        }

        return a;
    }

    public static byte max(byte a, byte b, byte c) {
        if (b > a) {
            a = b;
        }

        if (c > a) {
            a = c;
        }

        return a;
    }

    public static double max(double a, double b, double c) {
        return Math.max(Math.max(a, b), c);
    }

    public static float max(float a, float b, float c) {
        return Math.max(Math.max(a, b), c);
    }

    public static boolean isParsable(String str) {
        return !isEmpty(str) && (str.charAt(str.length() - 1) != 46 && (str.charAt(0) == 45 ? (str.length() != 1 && withDecimalsParsing(str, 1)) : withDecimalsParsing(str, 0)));
    }

    private static boolean withDecimalsParsing(String str, int beginIdx) {
        int decimalPoints = 0;

        for (int i = beginIdx; i < str.length(); ++i) {
            boolean isDecimalPoint = str.charAt(i) == 46;
            if (isDecimalPoint) {
                ++decimalPoints;
            }

            if (decimalPoints > 1) {
                return false;
            }

            if (!isDecimalPoint && !Character.isDigit(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public static int compare(int x, int y) {
        return x == y ? 0 : (x < y ? -1 : 1);
    }

    public static int compare(long x, long y) {
        return x == y ? 0 : (x < y ? -1 : 1);
    }

    public static int compare(short x, short y) {
        return x == y ? 0 : (x < y ? -1 : 1);
    }

    public static int compare(byte x, byte y) {
        return x - y;
    }
}