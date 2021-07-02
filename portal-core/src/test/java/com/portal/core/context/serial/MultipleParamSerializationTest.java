package com.portal.core.context.serial;

import com.portal.core.model.Param;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class MultipleParamSerializationTest {

    MultipleParamSerialization multipleParamSerialization = new MultipleParamSerialization();
    MultipleObjectSerialization multipleObjectSerialization = new MultipleObjectSerialization();

    @Before
    public void init() {

        multipleParamSerialization.add(new NumberParamSerialization());
        multipleParamSerialization.add(new StringParamSerialization());
        multipleParamSerialization.add(new ArrayParamSerialization(multipleParamSerialization));
        multipleParamSerialization.add(new CollectionParamSerialization(multipleParamSerialization));
        multipleParamSerialization.add(new ObjectParamSerialization(multipleParamSerialization));

        multipleObjectSerialization.add(new NumberObjectSerialization());
        multipleObjectSerialization.add(new StringObjectSerialization());
        multipleObjectSerialization.add(new ArrayObjectSerialization(multipleObjectSerialization));
        multipleObjectSerialization.add(new CollectionObjectSerialization(multipleObjectSerialization));
        multipleObjectSerialization.add(new ObjectObjectSerialization(multipleObjectSerialization));

    }
    @Test
    public void testNumber() {
        System.out.println("PARAM");
        Param serial = multipleParamSerialization.serial(1);
        System.out.println(serial);
        System.out.println("Number");
        System.out.println(multipleObjectSerialization.serial(serial, Integer.class));
        System.out.println(multipleObjectSerialization.serial(serial, Byte.class));
        System.out.println(multipleObjectSerialization.serial(serial, Long.class));
        System.out.println(multipleObjectSerialization.serial(serial, Short.class));
        System.out.println("Array");
        serial = multipleParamSerialization.serial(new int[]{1, 2, 3});
        System.out.println(serial);
        System.out.println(multipleObjectSerialization.serial(serial, int[].class));
        System.out.println(multipleObjectSerialization.serial(serial, List.class));
        System.out.println("Object");
        User user = new User();
        Account account = new Account();
        account.setBalance(100);
        user.setUsername("test");
        user.setPassword("123456");
        user.setAccount(account);
        serial = multipleParamSerialization.serial(user);
        System.out.println(serial);
        System.out.println(multipleObjectSerialization.serial(serial, User.class));

    }
}