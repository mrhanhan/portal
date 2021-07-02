package com.portal.core.utils;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.BigInteger;


/**
 * ByteVisit
 *
 * @author Mrhan
 * @date 2021/6/16 13:45
 */
@UtilityClass
public class ByteVisit {

    public static final byte[] DECIMAL_FLAG_DATA = new byte[]{0x0, 0xA, 0xB, 0x0};
    /**
     * 开始位置
     *
     * @param bytes 对于数据
     * @param data  开始位置
     * @return 开始位置对比
     */
    public boolean equ(byte[] bytes, byte... data) {
        return equ(0, bytes, data);
    }

    /**
     * 开始位置
     *
     * @param bytes 对于数据
     * @param data  开始位置
     * @return 开始位置对比
     */
    public boolean equ(int start, byte[] bytes, byte... data) {
        for (byte d : data) {
            if (bytes[start++] != d) {
                return false;
            }
        }
        return true;
    }

    public byte[] intToBytes(int integer) {
        return BigInteger.valueOf(integer).toByteArray();
    }

    public byte[] intToBytes(int integer, int length) {
        return fill(BigInteger.valueOf(integer).toByteArray(), 4);
    }

    public byte[] fill(byte[] source, int length) {
        if (source.length > length) {
            return get(source, 0, length);
        } else if (source.length == length) {
            return source;
        } else {
            byte[] result = new byte[length];
            // 复制到尾部
            int i = 0;
            int fillCount = length - source.length;
            for (int i1 = 0; i1 < fillCount; i1++) {
                result[i++] = 0;
            }
            for (byte b : source) {
                result[i++] = b;
            }
            return result;
        }
    }


    public int bytesToInt(byte[] bytes) {
        return new BigInteger(bytes).intValue();
    }

    public static byte[] longToBytes(long l) {
        return BigInteger.valueOf(l).toByteArray();
    }

    public static byte[] longToBytes(long l, int length) {
        return fill(longToBytes(l), length);
    }

    public static long bytesToLong(byte[] bytes) {
        return new BigInteger(bytes).longValue();
    }


    public static byte[] charToByte(char c) {
        return intToBytes(c);
    }

    public static byte[] charToByte(char c, int length) {
        return fill(intToBytes(c), length);
    }


    public static char byteToChar(byte[] bytes) {
        return (char) new BigInteger(bytes).intValue();
    }

    public static byte[] floatToByte(float data) {
        return serialBytes(BigDecimal.valueOf(data));
    }

    public static byte[] doubleToByte(double data) {
        return serialBytes(BigDecimal.valueOf(data));
    }


    public static float byteToFloat(byte[] bytes) {
        return serialDecimal(bytes).floatValue();
    }

    public static double byteToDouble(byte[] bytes) {
        return serialDecimal(bytes).doubleValue();
    }

    public static byte[] join(byte[]... bytes) {
        int length = 0;
        for (byte[] data : bytes) {
            length += data.length;
        }
        byte[] result = new byte[length];
        int p = 0;
        for (byte[] data : bytes) {
            for (byte b : data) {
                result[p++] = b;
            }
        }
        return result;
    }

    public static byte[] get(byte[] source, int start, int length) {
        byte[] data = new byte[length];
        for (int i = 0; i < length; i++) {
            data[i] = source[start + i];
        }
        return data;
    }
    /**
     * 序列化BigDecimal
     * @param decimal   BigDecimal
     * @return  bytes
     */
    public byte[] serialBytes(BigDecimal decimal) {
        // 没有标记的decimal
        // 前二位填充小数位长度，4个字节，4个子节的标记标记为
        byte[] d = decimal.unscaledValue().toByteArray();
        return ByteVisit.join(DECIMAL_FLAG_DATA, ByteVisit.intToBytes(decimal.scale(), 4), d);
    }

    /**
     * 序列化为BigDecimal
     * @param bytes bytes
     * @return BigDecimal
     */
    public BigDecimal serialDecimal(byte[] bytes) {
        byte[] decimalVal = bytes;
        boolean flag = false;
        int scale = 0;
        if (bytes.length + 4 > DECIMAL_FLAG_DATA.length && ByteVisit.equ(bytes, DECIMAL_FLAG_DATA)) {
            flag = true;
            decimalVal = ByteVisit.get(bytes, DECIMAL_FLAG_DATA.length + 4, bytes.length - (DECIMAL_FLAG_DATA.length + 4));
            scale = ByteVisit.bytesToInt(ByteVisit.get(bytes, DECIMAL_FLAG_DATA.length, 4));
        }
        if (flag) {
            return new BigDecimal(new BigInteger(decimalVal), scale);
        } else {
            return new BigDecimal(new BigInteger(bytes));
        }
    }
}
