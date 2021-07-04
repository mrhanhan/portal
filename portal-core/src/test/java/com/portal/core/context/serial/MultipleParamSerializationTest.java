package com.portal.core.context.serial;

import com.portal.core.context.DataHandler;
import com.portal.core.context.monitor.DataMonitor;
import com.portal.core.context.monitor.DefaultDataMonitor;
import com.portal.core.context.send.SendData;
import com.portal.core.model.Data;
import com.portal.core.model.Param;
import com.portal.core.utils.DataReader;
import com.portal.core.utils.DataWriter;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.function.Consumer;

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
        SerializationOptions options = new SerializationOptions(new MockConnection());
        options.setSendData(new SendData() {
            @Override
            public void send(Data data, Consumer<Data> callback) {

            }
        });
        Param serial = multipleParamSerialization.serial(1, options.copy().parseType(1));
        paramArray[0] = serial;
        System.out.println(serial);
        System.out.println("Number");
        System.out.println(multipleObjectSerialization.serial(serial, options.copy().setSerialType(Integer.class)));
        System.out.println(multipleObjectSerialization.serial(serial, options.copy().setSerialType(Byte.class)));
        System.out.println(multipleObjectSerialization.serial(serial, options.copy().setSerialType(Long.class)));
        System.out.println(multipleObjectSerialization.serial(serial, options.copy().setSerialType(Short.class)));

        System.out.println("Array");
        serial = multipleParamSerialization.serial(new int[]{1, 2, 3}, options.copy().setSerialType(int[].class));
        paramArray[1] = serial;
        System.out.println(serial);
        System.out.println(multipleObjectSerialization.serial(serial, options.copy().setSerialType(int[].class)));
        System.out.println(multipleObjectSerialization.serial(serial, options.copy().setSerialType(List.class)));

        System.out.println("Object");
        User user = new User();
        Account account = new Account();
        account.setBalance(100);
        user.setUsername("test");
        user.setPassword("123456");
        user.setAccount(account);
        serial = multipleParamSerialization.serial(user, options.copy().setSerialType(User.class));
        paramArray[2] = serial;
        System.out.println(serial);
        System.out.println(multipleObjectSerialization.serial(serial, options.copy().setSerialType(User.class)));

        System.out.println("Data");
        Data data = new Data();
        data.setConnection(new MockConnection());
        data.setDataMonitor(new DefaultDataMonitor(data.getConnection(), new DataHandler() {
            @Override
            public void onHandler(DataMonitor monitor, Data data) {

            }
        }));
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
        System.out.println(multipleObjectSerialization.serial(data.getParams()[0], options.copy().setSerialType(Integer.class)));
        System.out.println(multipleObjectSerialization.serial(data.getParams()[1], options.copy().setSerialType(List.class)));
        System.out.println(multipleObjectSerialization.serial(data.getParams()[2], options.copy().setSerialType(User.class)));
    }
}