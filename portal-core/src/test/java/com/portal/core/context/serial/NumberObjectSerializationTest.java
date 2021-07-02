package com.portal.core.context.serial;

import com.portal.core.model.Param;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class NumberObjectSerializationTest {

    public static void main(String[] args) {
        NumberParamSerialization nps = new NumberParamSerialization();
        NumberObjectSerialization nos = new NumberObjectSerialization();

        Param serial = nps.serial(1.111f);
        Number serialNumber = nos.serial(serial, BigDecimal.class);

        System.out.println(serialNumber.getClass());
        System.out.println(serialNumber);

        serial = nps.serial(new BigDecimal("1.1111"));
        serialNumber = nos.serial(serial, Double.class);

        System.out.println(serialNumber.getClass());
        System.out.println(serialNumber.toString());

        serialNumber = nos.serial(serial, Byte.class);

        System.out.println(serialNumber.getClass());
        System.out.println(serialNumber.toString());

    }

}