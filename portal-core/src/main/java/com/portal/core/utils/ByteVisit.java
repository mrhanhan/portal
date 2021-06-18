package com.portal.core.utils;

import lombok.experimental.UtilityClass;


/**
 * ByteVisit
 *
 * @author Mrhan
 * @date 2021/6/16 13:45
 */
@UtilityClass
public class ByteVisit {


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
        byte[] bytes = new byte[4];
        bytes[3] = (byte) (integer >> 24);
        bytes[2] = (byte) (integer >> 16);
        bytes[1] = (byte) (integer >> 8);
        bytes[0] = (byte) integer;
        return bytes;
    }

    public int bytesToInt(byte[] bytes) {
        //如果不与0xff进行按位与操作，转换结果将出错，有兴趣的同学可以试一下。
        int int1 = bytes[0] & 0xff;
        int int2 = (bytes[1] & 0xff) << 8;
        int int3 = (bytes[2] & 0xff) << 16;
        int int4 = (bytes[3] & 0xff) << 24;
        return int1 | int2 | int3 | int4;
    }

    public static byte[] longToBytes(long l) {
        byte[] result = new byte[8];
        for (int i = 7; i >= 0; i--) {
            result[i] = (byte) (l & 0xFF);
            l >>= 8;
        }
        return result;

    }

    public static long bytesToLong(byte[] b) {
        long result = 0;
        for (int i = 0; i < 8; i++) {
            result <<= 8;
            result |= (b[i] & 0xFF);

        }
        return result;
    }

    public static byte[] charToByte(char c) {
        byte[] b = new byte[2];
        b[0] = (byte) ((c & 0xFF00) >> 8);
        b[1] = (byte) (c & 0xFF);
        return b;
    }

    public static char byteToChar(byte[] b) {
        int hi = (b[0] & 0xFF) << 8;
        int lo = b[1] & 0xFF;
        return (char) (hi | lo);
    }

    public static byte[] floatToByte(float data)
    {
        int intBits = Float.floatToIntBits(data);
        return intToBytes(intBits);
    }

    public static byte[] doubleToByte(double data)
    {
        long intBits = Double.doubleToLongBits(data);
        return longToBytes(intBits);
    }

    public static float byteToFloat(byte[] bytes)
    {
        return Float.intBitsToFloat(bytesToInt(bytes));
    }

    public static double byteToDouble(byte[] bytes)
    {
        long l = bytesToLong(bytes);
        return Double.longBitsToDouble(l);
    }

    public static byte[] join(byte[] ...bytes) {
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
}
