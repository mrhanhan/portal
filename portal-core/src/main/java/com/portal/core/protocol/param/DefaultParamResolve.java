package com.portal.core.protocol.param;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.portal.core.discovery.ProxyInvokeSend;
import com.portal.core.server.Data;
import com.portal.core.service.BeanDelegateService;
import com.portal.core.service.ServiceContainer;
import com.portal.core.utils.ByteVisit;
import com.portal.core.utils.MethodUtil;
import lombok.RequiredArgsConstructor;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * DefaultParamResolve
 * 默认参数解析
 *
 * @author Mrhan
 * @date 2021/6/17 9:52
 */
@RequiredArgsConstructor
public class DefaultParamResolve implements ParamResolve {
    
    private final Charset defaultCharset = StandardCharsets.UTF_8;

    private final ProxyInvokeSend proxyInvokeSend;


    @Override
    public <T> T resolve(Data<?> data, Param param, Type cls) {
        // TODO
        switch (param.getType()) {
            case NUMBER:
                return (T) serialNumber(param, cls);
            case STRING:
                return (T) serialString(param, cls);
            case ARRAY:
                return (T) serialArray(data, param, cls);
            case OBJECT:
                return (T) serialObject(data, param, cls);
            default:
                return null;
        }
    }

    /**
     * 序列化数组
     * @param param 擦描述及
     * @param cls   数组类型
     * @return      返回对象
     */
    protected Object serialArray(Data<?> data, Param param, Type cls) {
        Class<?> c = (Class<?>) cls;
        if (Collection.class.isAssignableFrom(c)) {

        } else if (c.isArray()) {
            Class<?> componentType = c.getComponentType();
            JSONArray array = new JSONArray();
            if (param.getChildren() != null) {
                for (Param child : param.getChildren()) {
                    array.add(JSON.toJSON(resolve(data, child, componentType)));
                }
            }
            return array.toJavaObject(cls);
        }
        return null;
    }

    /***
     * 序列化对象
     * @param param
     * @param cls
     * @return
     */
    protected Object serialObject(Data<?> data, Param param, Type cls) {
        if (param.isQuote()) {
            // 如果是引用对象
            String serviceName = param.getQuoteService();
            if (cls instanceof Class) {
                Class<?> clazz = (Class<?>) cls;
                Enhancer enhancer = new Enhancer();
                if (clazz.isInterface()) {
                    enhancer.setInterfaces(new Class[]{clazz});
                } else {
                    enhancer.setSuperclass(clazz);
                }
                enhancer.setCallback((InvocationHandler) (o, method, objects) -> {
                    System.out.println("临时服务调用: " + serviceName + " _ " + method.getName());
                    return proxyInvokeSend.invokeSend(data.getConnection(), serviceName, method.getName(), method.getReturnType(), objects);
                });
                return enhancer.create();
            }
            return null;
        } else if (param.getData() != null){
            return JSON.parseObject(param.getData(), cls);
        }
        return null;
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
            return new StringBuilder(new String(param.getData(), defaultCharset));
        }
        if (cls == StringBuffer.class) {
            return new StringBuffer(new String(param.getData(), defaultCharset));
        }
        return new String(param.getData(), defaultCharset);
    }

    /**
     * 序列化数字
     *
     * @param param 参数
     * @param cls   返回cls
     * @return 返回cls
     */
    protected Object serialNumber(Param param, Type cls) {
        String val = new String(param.getData(), defaultCharset);
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
        if (obj == null) {
            return new Param().setType(ParamTypeEnum.NULL);
        }
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
            // 注册引用对象服务
            String tempServiceName = UUID.randomUUID().toString();
            // 注册服务
            BeanDelegateService service = new BeanDelegateService(tempServiceName, obj, obj.getClass());
            List<Method> allMethod = MethodUtil.getAllMethod(obj.getClass());
            for (Method method : allMethod) {
                service.register(method.getName(), method);
            }
            serviceContainer.register(service);
            param.setQuoteService(tempServiceName);
            param.setQuote(true);
            System.out.println("注册临时服务调用: " + tempServiceName);
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
            return param.setData(obj.toString().getBytes(defaultCharset));
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
            return param.setData(obj.toString().getBytes(defaultCharset));
        }
        if (cls == Long.class) {
            return param.setData(obj.toString().getBytes(defaultCharset));
        }
        if (cls == Byte.class) {
            return param.setData(obj.toString().getBytes(defaultCharset));
        }
        if (cls == Character.class) {
            return param.setData(obj.toString().getBytes(defaultCharset));
        }
        if (cls == Short.class) {
            return param.setData(obj.toString().getBytes(defaultCharset));
        }
        //
        if (cls == Float.class) {
            return param.setData(obj.toString().getBytes(defaultCharset));
        }
        //
        if (cls == Double.class) {
            return param.setData(obj.toString().getBytes(defaultCharset));
        }
        // BigInteger
        if (cls == BigInteger.class) {
            return param.setData(obj.toString().getBytes(defaultCharset));
        }
        // BigDecimal
        if (cls == BigDecimal.class) {
            return param.setData(obj.toString().getBytes(defaultCharset));
        }
        return null;
    }
}
