package com.portal.core.context.serial;

import com.portal.core.context.ObjectSerialization;
import com.portal.core.context.ParamSerialization;
import com.portal.core.model.Data;
import com.portal.core.model.Param;
import com.portal.core.model.ParamTypeEnum;
import com.portal.core.utils.UniqueCodeGen;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.Type;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * QuoteObjectObjectSerialization
 *
 * @author Mrhan
 * @date 2021/7/4 10:34
 */
@AllArgsConstructor
public class QuoteObjectObjectSerialization extends AbstractObjectSerialization<Object> {
    @Getter @Setter
    private ObjectSerialization<Object> childrenObjectSerialization;
    @Getter @Setter
    private ParamSerialization<Object> childrenParamSerialization;

    @Override
    public Object serial(Param param, SerializationOptions options) {
        Type serialType = options.getSerialType();
        Class<?> cls = (Class<?>) serialType;
        String quoteService = param.getQuoteService();
        // cglib 代理
        Enhancer enhancer = new Enhancer();
        if (cls.isInterface()) {
            enhancer.setInterfaces(new Class[]{cls});
        } else {
            enhancer.setSuperclass(cls);
        }
        enhancer.setCallback((InvocationHandler) (o, method, objects) -> {
            Data data = new Data();
            data.setConnection(data.getConnection());
            data.setOperate(Data.CALL);
            data.setServiceName(quoteService);
            data.setServiceId(QuoteObjectParamSerialization.getMethodName(method));
            data.setId(UniqueCodeGen.genNumber());
            if (objects != null) {
                Param[] params = new Param[objects.length];
                for (int i = 0; i < objects.length; i++) {
                    Object object = objects[i];
                    Type type = object == null ? null : object.getClass();
                    params[i] = childrenParamSerialization.serial(object, options.copy().setSerialType(type));
                }
                data.setParams(params);
            } else {
                data.setParams(new Param[0]);
            }
            AtomicBoolean status = new AtomicBoolean(false);
            Object[] result = new Object[1];
            Object lock = new Object();
            // System.out.println("发起调用:" + data);
            options.getSendData().send(data, (resultData) -> {
                Param[] params = resultData.getParams();
                if (params.length > 0) {
                    result[0] = childrenObjectSerialization.serial(params[0], options.copy().setSerialType(method.getReturnType()));
                }
                status.set(true);
                synchronized (lock) {
                    lock.notifyAll();
                }
            });
            if (!status.get()) {
                synchronized (lock) {
                    lock.wait();
                }
            }
            return result[0];
        });
        return enhancer.create();
    }

    @Override
    public boolean isSupport(Param param, SerializationOptions options) {
        return param.getType() == ParamTypeEnum.OBJECT && param.isQuote() && Objects.nonNull(options.getSerialType());
    }

    @Override
    public int getDeep() {
        return 0;
    }
}
