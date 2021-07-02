package com.portal.core.utils;


import java.math.BigInteger;

/**
 * 唯一标识符生成器
 * created by Mr han 2019/10/13
 * @author 77
 */
public class UniqueCodeGen {
    /**
     * ID 生成器
     */
    private final static Snowflake GEN_ID = new Snowflake(1);
    private final static Snowflake GEN_ORDER_NUMBER = new Snowflake(2);

    /**
     * 生成ID
     * @return     返回生成的ID
     */
    public static  long genId(){
        return GEN_ID.generate();
    }

    public static String genNumber(){return GEN_ORDER_NUMBER.generate() + "";}

    /**
     * 获取一个16进制的字符
     * @return  返回16进制数字
     */
    public static String genHex(){
        return BigInteger.valueOf(genId()).toString(16);
    }

    public static void main(String[] args) {
        System.out.println(genHex());
    }
}
