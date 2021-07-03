package com.portal.core.context.serial;

import com.portal.core.model.Param;
import com.portal.core.model.ParamTypeEnum;
import com.portal.core.utils.ByteVisit;
import com.portal.core.utils.ClassUtil;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * NumberObjectSerialization
 *
 * @author Mrhan
 * @date 2021/7/1 23:51
 */
public class NumberObjectSerialization extends AbstractObjectSerialization<Number> {

    public static final byte[] DECIMAL_FLAG_DATA = NumberParamSerialization.DECIMAL_FLAG_DATA;


    @Override
    public Number serial(Param param, Type type) {
        Class<?> cls = (Class<?>) type;
        Number number;
        byte[] bytes = param.getData();
        byte[] decimalVal = param.getData();
        boolean flag = false;
        int scale = 0;
        if (bytes.length > DECIMAL_FLAG_DATA.length + 4  && ByteVisit.equ(bytes, DECIMAL_FLAG_DATA)) {
            flag = true;
            decimalVal = ByteVisit.get(bytes, DECIMAL_FLAG_DATA.length + 4, bytes.length - (DECIMAL_FLAG_DATA.length + 4));
            scale = ByteVisit.bytesToInt(ByteVisit.get(bytes, DECIMAL_FLAG_DATA.length, 4));
            // 如果不是小数类型，去除小数位子
            bytes = new BigDecimal(new BigInteger(decimalVal), scale).toBigInteger().toByteArray();
        }
        // 判断是否有标志
        if (ClassUtil.isAssignable(cls, Integer.class, int.class)) {
            number = ByteVisit.bytesToInt(bytes);
        } else if (ClassUtil.isAssignable(cls, Long.class, long.class)) {
            number = ByteVisit.bytesToLong(bytes);
        } else if (ClassUtil.isAssignable(cls, Short.class, short.class)) {
            number = (short)ByteVisit.bytesToInt(bytes);
        } else if (ClassUtil.isAssignable(cls, Byte.class, byte.class)) {
            number = bytes.length > 0 ? bytes[0] : 0;
        } else if (ClassUtil.isAssignable(cls, BigInteger.class)) {
            number = new BigInteger(bytes);
        } else if (ClassUtil.isAssignable(cls, BigDecimal.class)) {
            if (flag) {
                number = new BigDecimal(new BigInteger(decimalVal), scale);
            } else {
                number = new BigDecimal(new BigInteger(bytes));
            }
        } else if (ClassUtil.isAssignable(cls, Float.class, float.class)) {
            number = ByteVisit.byteToFloat(param.getData());
        } else if (ClassUtil.isAssignable(cls, Double.class, double.class)) {
            number = ByteVisit.byteToDouble(param.getData());
        } else {
             number = null;
        }
        return number;
    }

    @Override
    public boolean isSupport(Param param, Type type) {
        Class<?> cls;
        if (!(type instanceof Class)) {
            return false;
        }
        cls = (Class<?>) type;
        return param.getType() == ParamTypeEnum.NUMBER && ClassUtil.isAssignable(cls, int.class, Integer.class, long.class, Long.class, short.class, Short.class, byte.class, Byte.class, BigInteger.class, BigDecimal.class, double.class, Double.class, float.class, Float.class ) && !param.isQuote();
    }
}
