package com.portal.core.context.serial;

import com.portal.core.utils.ByteVisit;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

public class StringParamSerializationTest {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(BigInteger.valueOf(256L).toByteArray()));;
        System.out.println(Arrays.toString(ByteVisit.intToBytes(256)));
        byte[] intV = ByteVisit.intToBytes(255);
        byte[] longV = ByteVisit.longToBytes(25888888888885L);
        byte[] floatV = ByteVisit.floatToByte(255.2f);
        byte[] doubleV = ByteVisit.doubleToByte(255.11);

        byte[] data = BigDecimal.valueOf(1.111).unscaledValue().toByteArray();

        System.out.println(ByteVisit.bytesToInt(intV));
        System.out.println(ByteVisit.bytesToLong(longV));
        System.out.println(ByteVisit.byteToFloat(floatV));
        System.out.println(ByteVisit.byteToDouble(doubleV));
        System.out.println(new BigDecimal(new BigInteger(data), 3));
    }

}