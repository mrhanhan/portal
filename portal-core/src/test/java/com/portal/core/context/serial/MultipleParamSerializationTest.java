package com.portal.core.context.serial;

import com.portal.core.model.Data;
import com.portal.core.model.Param;
import com.portal.core.utils.DataReader;
import com.portal.core.utils.DataWriter;
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
        Param[] paramArray = new Param[3];
        Param serial = multipleParamSerialization.serial(1);
        paramArray[0] = serial;
        System.out.println(serial);
        System.out.println("Number");
        System.out.println(multipleObjectSerialization.serial(serial, Integer.class));
        System.out.println(multipleObjectSerialization.serial(serial, Byte.class));
        System.out.println(multipleObjectSerialization.serial(serial, Long.class));
        System.out.println(multipleObjectSerialization.serial(serial, Short.class));

        System.out.println("Array");
        serial = multipleParamSerialization.serial(new int[]{1, 2, 3});
        paramArray[1] = serial;
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
        paramArray[2] = serial;
        System.out.println(serial);
        System.out.println(multipleObjectSerialization.serial(serial, User.class));

        System.out.println("Data");
        Data data = new Data();
        data.setId("1");
        data.setServiceName("indexService");
        data.setServiceId("login");
        data.setOperate(1);
        data.setParams(paramArray);
        DataWriter writer = new DataWriter();
        writer.writeData(data);
        byte[] bytes = writer.toByteArray();
        System.out.println(bytes.length);
        System.out.println(new DataReader(bytes).readData());
        System.out.println(multipleObjectSerialization.serial(data.getParams()[0], Integer.class));
        System.out.println(multipleObjectSerialization.serial(data.getParams()[1], List.class));
        System.out.println(multipleObjectSerialization.serial(data.getParams()[2], User.class));
    }
}