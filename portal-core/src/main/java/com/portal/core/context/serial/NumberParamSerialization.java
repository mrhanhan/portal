package com.portal.core.context.serial;

import com.portal.core.model.Param;
import com.portal.core.model.ParamTypeEnum;
import com.portal.core.utils.ByteVisit;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * AbstractParamSerialization
 *
 * @author Mrhan
 * @date 2021/7/1 23:51
 */
public class NumberParamSerialization extends AbstractParamSerialization<Number> {

    public static final byte[] DECIMAL_FLAG_DATA = new byte[]{0x0, 0xA, 0xB, 0x0};

    @Override
    public Param serial(Number data) {
        Param param = createParam(ParamTypeEnum.NUMBER);
        byte[] bytes = null;
        if (data instanceof BigInteger) {
            bytes = ((BigInteger) data).toByteArray();
        } else if (data instanceof BigDecimal){
            BigDecimal bd = (BigDecimal) data;
            bytes = ByteVisit.serialBytes(bd);
        } else if (data instanceof Long || data instanceof Integer || data instanceof Byte || data instanceof Short) {
            bytes = BigInteger.valueOf(data.longValue()).toByteArray();
        } else if (data instanceof Double || data instanceof Float) {
            bytes = ByteVisit.serialBytes(new BigDecimal(data.toString()));
        } else {
            bytes = BigInteger.valueOf(data.longValue()).toByteArray();
        }
        param.setData(bytes);
        return param;
    }


}
