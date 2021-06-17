package com.portal.core.protocol.param;

import com.alibaba.fastjson.JSON;
import com.portal.core.service.ServiceContainer;
import com.portal.core.utils.ByteVisit;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

/**
 * DefaultParamResolve
 * 默认参数解析
 *
 * @author Mrhan
 * @date 2021/6/17 9:52
 */
public class DefaultParamResolve implements ParamResolve {


    public DefaultParamResolve() {

    }

    @Override
    public <T> T resolve(Param param, Type cls) {
        // TODO
        switch (param.getType()) {
            case NUMBER:
                return (T) serialNumber(param, cls);
            case STRING:
                return (T) serialString(param, cls);
            case OBJECT:
                return (T) serialObject(param, cls);
            default:
                return null;
        }
    }

    /***
     * 序列化对象
     * @param param
     * @param cls
     * @return
     */
    protected Object serialObject(Param param, Type cls) {
        if (param.isQuote()) {
            return null;
        } else {
            return JSON.parseObject(param.getData(), cls);
        }
    }

    /**
     * Param
     *
     * @param param Param
     * @param cls   类型
     * @return 返回String
     */
    protected Object serialString(Param param, Type cls) {
        if (cls == StringBuilder.class) {
            return new StringBuilder(new String(param.getData(), StandardCharsets.UTF_8));
        }
        if (cls == StringBuffer.class) {
            return new StringBuffer(new String(param.getData(), StandardCharsets.UTF_8));
        }
        return new String(param.getData(), StandardCharsets.UTF_8);
    }

    /**
     * 序列化数字
     *
     * @param param 参数
     * @param cls   返回cls
     * @return 返回cls
     */
    protected Object serialNumber(Param param, Type cls) {
        String val = new String(param.getData(), StandardCharsets.UTF_8);
        if (cls == int.class || cls == Integer.class) {
            return Integer.valueOf(val);
        }
        if (cls == long.class || cls == Long.class) {
            return Long.valueOf(val);
        }
        if (cls == byte.class || cls == Byte.class) {
            return Byte.valueOf(val);
        }
        if (cls == char.class || cls == Character.class) {
            return (char) Short.valueOf(val).shortValue();
        }

        if (cls == short.class || cls == Short.class) {
            return (short) ByteVisit.bytesToInt(param.getData());
        }
        //
        if (cls == float.class || cls == Float.class) {
            return Float.valueOf(val);
        }
        //
        if (cls == double.class || cls == Double.class) {
            return Double.valueOf(val);
        }
        // BigInteger
        if (cls == BigInteger.class) {
            return new BigInteger(val);
        }
        // BigDecimal
        if (cls == BigDecimal.class) {
            return new BigDecimal(val);
        }
        return null;
    }

    @Override
    public Param resolve(Object obj, ServiceContainer serviceContainer) {
        // 序列化字符串
        Param result = descNumber(obj);
        if (result != null) {
            return result;
        }
        // 序列化字符串
        result = descString(obj);
        if (result != null) {
            return result;
        }
        // 序列化数组
        result = descArray(obj, serviceContainer);
        if (result != null) {
            return result;
        }
        // 序列化对象
        result = descObject(obj, serviceContainer);
        if (result != null) {
            return result;
        }

        return new Param().setType(ParamTypeEnum.NULL);
    }

    /**
     * 序列化对象
     *
     * @param obj 对象
     * @return 序列化对象
     */
    protected Param descObject(Object obj, ServiceContainer serviceContainer) {
        Param param = new Param();
        param.setType(ParamTypeEnum.OBJECT);
        // 判断是否是 需要传递引用对象
        if (checkQuote(obj)) {

        } else {
            param.setData(JSON.toJSONBytes(obj));
        }
        return param;
    }

    /**
     * 检测对象是否是使用引用传递
     * @param obj   引用对象传递
     * @return      返回是否使用引用对象传递
     */
    protected boolean checkQuote(Object obj) {

        return !(obj instanceof Serializable);
    }

    /**
     * 序列化数组
     *
     * @param obj 对象
     * @return 序列化对象
     */
    protected Param descArray(Object obj, ServiceContainer serviceContainer) {
        Param param = new Param();
        param.setType(ParamTypeEnum.ARRAY);
        // 是否是一个集合
        if (obj instanceof Collection) {
            Collection collection = (Collection) obj;
            Param[] children = new Param[collection.size()];
            int p = 0;
            for (Object o : collection) {
                children[p++] = resolve(o, serviceContainer);
            }
            return param.setChildren(children);
        }
        // 是否是一个数组
        if (obj.getClass().isArray()) {
            if (obj.getClass() == int[].class) {
                int[] array = (int[]) obj;
                Param[] children = new Param[array.length];
                int p = 0;
                for (Object o : array) {
                    children[p++] = resolve(o, serviceContainer);
                }
                return param.setChildren(children);
            } else if (obj.getClass() == long[].class) {
                long[] array = (long[]) obj;
                Param[] children = new Param[array.length];
                int p = 0;
                for (Object o : array) {
                    children[p++] = resolve(o, serviceContainer);
                }
                return param.setChildren(children);
            } else if (obj.getClass() == short[].class) {
                short[] array = (short[]) obj;
                Param[] children = new Param[array.length];
                int p = 0;
                for (Object o : array) {
                    children[p++] = resolve(o, serviceContainer);
                }
                return param.setChildren(children);
            } else if (obj.getClass() == byte[].class) {
                byte[] array = (byte[]) obj;
                Param[] children = new Param[array.length];
                int p = 0;
                for (Object o : array) {
                    children[p++] = resolve(o, serviceContainer);
                }
                return param.setChildren(children);
            } else if (obj.getClass() == float[].class) {
                float[] array = (float[]) obj;
                Param[] children = new Param[array.length];
                int p = 0;
                for (Object o : array) {
                    children[p++] = resolve(o, serviceContainer);
                }
                return param.setChildren(children);
            } else if (obj.getClass() == double[].class) {
                double[] array = (double[]) obj;
                Param[] children = new Param[array.length];
                int p = 0;
                for (Object o : array) {
                    children[p++] = resolve(o, serviceContainer);
                }
                return param.setChildren(children);
            } else if (obj.getClass() == char[].class) {
                char[] array = (char[]) obj;
                Param[] children = new Param[array.length];
                int p = 0;
                for (Object o : array) {
                    children[p++] = resolve(o, serviceContainer);
                }
                return param.setChildren(children);
            } else {
                Object[] array = (Object[]) obj;
                Param[] children = new Param[array.length];
                int p = 0;
                for (Object o : array) {
                    children[p++] = resolve(o, serviceContainer);
                }
                return param.setChildren(children);
            }
        }

        // 判断是否是
        return null;
    }

    /**
     * 字符串
     */
    protected Param descString(Object obj) {
        Param param = new Param();
        param.setType(ParamTypeEnum.STRING);
        if (obj instanceof String || obj instanceof StringBuffer || obj instanceof StringBuilder) {
            return param.setData(obj.toString().getBytes(StandardCharsets.UTF_8));
        }

        return null;
    }

    /**
     * 反序列化数字
     *
     * @param obj
     * @return
     */
    protected Param descNumber(Object obj) {
        Param param = new Param();
        param.setType(ParamTypeEnum.NUMBER);
        param.setQuote(false);
        Class<?> cls = obj.getClass();

        if (cls == Integer.class) {
            return param.setData(obj.toString().getBytes(StandardCharsets.UTF_8));
        }
        if (cls == Long.class) {
            return param.setData(obj.toString().getBytes(StandardCharsets.UTF_8));
        }
        if (cls == Byte.class) {
            return param.setData(obj.toString().getBytes(StandardCharsets.UTF_8));
        }
        if (cls == Character.class) {
            return param.setData(obj.toString().getBytes(StandardCharsets.UTF_8));
        }
        if (cls == Short.class) {
            return param.setData(obj.toString().getBytes(StandardCharsets.UTF_8));
        }
        //
        if (cls == Float.class) {
            return param.setData(obj.toString().getBytes(StandardCharsets.UTF_8));
        }
        //
        if (cls == Double.class) {
            return param.setData(obj.toString().getBytes(StandardCharsets.UTF_8));
        }
        // BigInteger
        if (cls == BigInteger.class) {
            return param.setData(obj.toString().getBytes(StandardCharsets.UTF_8));
        }
        // BigDecimal
        if (cls == BigDecimal.class) {
            return param.setData(obj.toString().getBytes(StandardCharsets.UTF_8));
        }
        return null;
    }
}
