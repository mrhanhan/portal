package com.portal.core.context.serial;

import com.portal.core.model.Param;
import com.portal.core.model.ParamTypeEnum;
import com.portal.core.service.BeanDelegateService;
import com.portal.core.service.ServiceContainer;
import com.portal.core.utils.ClassUtil;
import com.portal.core.utils.UniqueCodeGen;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

/**
 * QuoteObjectParamSerialization
 *
 * @author Mrhan
 * @date 2021/7/4 10:34
 */
public class QuoteObjectParamSerialization extends AbstractParamSerialization<Object> {

    @Override
    public boolean isSupport(Object o, SerializationOptions options) {
        return o != null && !(o instanceof Serializable) && options.getServiceContainer() != null;
    }

    @Override
    public Param serial(Object data, SerializationOptions options) {
        Param param = createParam(ParamTypeEnum.OBJECT);
        // 设置为引用类型
        param.setQuote(true);
        // 注册服务
        ServiceContainer serviceContainer = options.getServiceContainer();
        String tmpServiceName = UniqueCodeGen.genNumber();
        BeanDelegateService service = new BeanDelegateService(tmpServiceName, data, data.getClass());
        List<Method> allMethods = ClassUtil.getAllMethods(data.getClass());
        for (Method method : allMethods) {
            service.register(getMethodName(method), method);
        }
        serviceContainer.registerService(service);
        return param;
    }

    @Override
    public int getDeep() {
        return 0;
    }
    /**
     * 获取方法名称
     */
    public static String getMethodName(Method method) {
        String methodName = method.getName();
        StringBuilder sb = new StringBuilder(methodName);
        Class<?>[] parameterTypes = method.getParameterTypes();
        sb.append("_").append(parameterTypes.length).append("_");
        return sb.toString();
    }
}
