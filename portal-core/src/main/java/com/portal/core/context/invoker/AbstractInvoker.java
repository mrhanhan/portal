package com.portal.core.context.invoker;

import com.portal.core.context.ObjectSerialization;
import com.portal.core.context.ParamSerialization;
import com.portal.core.exception.ServiceNotFoundException;
import com.portal.core.model.Data;
import com.portal.core.model.Param;
import com.portal.core.service.Service;
import com.portal.core.service.ServiceContainer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Type;
import java.util.Objects;

/**
 * AbstractInvoker
 *
 * @author Mrhan
 * @date 2021/6/17 8:59
 */
@AllArgsConstructor
public abstract class AbstractInvoker implements Invoker {

    @Getter
    @Setter
    private ServiceContainer serviceContainer;

    @Getter
    @Setter
    private ObjectSerialization objectSerialization;

    @Getter
    @Setter
    private ParamSerialization paramSerialization;


    @Override
    public Param[] invoke(Data data) {
        // 获取服务
        String serviceName = data.getServiceName();
        Service service = serviceContainer.getService(serviceName);
        // 获取服务ID
        if (service == null) {
            return new Param[]{paramSerialization.serial(new ServiceNotFoundException(serviceName))};
        }
        // 解析参数
        Object[] args = resolveArguments(service, data);
        // 调用
        Object result = null;
        try {
            result = service.invoke(data.getServiceId(), args);
        } catch (Exception e) {
            return new Param[]{paramSerialization.serial(e)};
        }
        return new Param[]{paramSerialization.serial(result)};
    }

    /**
     * 参数解析
     * @param service   服务对象
     * @param data      data
     * @return          返回解析后得参数
     */
    protected  Object[] resolveArguments(Service service, Data data) {
        Type[] types = service.getParamTypes(data.getServiceId());
        Object[] args = new Object[types.length];
        Param[] params = data.getParams();
        if (Objects.isNull(params)) {
            params = new Param[0];
        }
        for (int i = 0; i < args.length; i++) {
            if (i < params.length) {
                args[i] = objectSerialization.serial(params[i], types[i]);
            } else {
                break;
            }
        }
        return args;
    }

}
