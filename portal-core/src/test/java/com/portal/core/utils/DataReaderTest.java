package com.portal.core.utils;

import com.portal.core.model.Data;
import com.portal.core.model.Param;
import com.portal.core.model.ParamTypeEnum;

import static org.junit.Assert.*;

public class DataReaderTest {

    public static void main(String[] args) {
        Data data = new Data();
        data.setId("1");
        data.setServiceName("indexService");
        data.setServiceId("login");
        data.setOperate(1);
        data.setParams(new Param[]{
                new Param().setType(ParamTypeEnum.NUMBER)
                .setQuote(false)
                        .setException(false)
                        .setFiledName(null)
                .setData(ByteVisit.intToBytes(1)),

                new Param().setType(ParamTypeEnum.NUMBER)
                .setQuote(true)
                        .setException(false)
                        .setQuoteService("quoteS")
                        .setChildren(new Param[]{
                                new Param().setType(ParamTypeEnum.NUMBER)
                                        .setQuote(false)
                                        .setException(false)
                                        .setFiledName("index")
                                        .setData(ByteVisit.intToBytes(1))
                        })
                        .setFiledName(null)
                .setData(ByteVisit.intToBytes(1)),

        });

        DataWriter writer = new DataWriter();
        writer.writeData(data);
        byte[] bytes = writer.toByteArray();
        System.out.println(bytes.length);
        System.out.println(new DataReader(bytes).readData());
    }
}