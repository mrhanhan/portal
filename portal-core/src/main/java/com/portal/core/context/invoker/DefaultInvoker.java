package com.portal.core.context.invoker;


import com.portal.core.context.ObjectSerialization;
import com.portal.core.context.ParamSerialization;
import com.portal.core.service.ServiceContainer;

/**
 * DefaultInvoker
 *
 * @author Mrhan
 * @date 2021/6/17 15:34
 */
public class DefaultInvoker extends AbstractInvoker{

    private ServiceContainer serviceContainer;

    public DefaultInvoker(ServiceContainer serviceContainer, ObjectSerialization objectSerialization, ParamSerialization paramSerialization) {
        super(objectSerialization, paramSerialization);
        this.serviceContainer = serviceContainer;
    }

    @Override
    public ServiceContainer getServiceContainer() {
        return serviceContainer;
    }
}
