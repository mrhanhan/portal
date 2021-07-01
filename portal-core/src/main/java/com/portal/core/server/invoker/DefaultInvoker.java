package com.portal.core.server.invoker;

import com.portal.core.protocol.param.ParamResolve;
import com.portal.core.service.ServiceContainer;
import lombok.Setter;

/**
 * DefaultInvoker
 *
 * @author Mrhan
 * @date 2021/6/17 15:34
 */
public class DefaultInvoker extends AbstractInvoker{

    @Setter
    private ParamResolve paramResolve;

    public DefaultInvoker(ServiceContainer serviceContainer,  ParamResolve paramResolve) {
        super(serviceContainer);
        this.paramResolve = paramResolve;
    }

    @Override
    protected ParamResolve getParamResolve() {
        return paramResolve;
    }
}
