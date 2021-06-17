package com.portal.core.server.invoker;

import com.portal.core.protocol.param.DefaultParamResolve;
import com.portal.core.protocol.param.ParamResolve;
import com.portal.core.service.ServiceContainer;

/**
 * DefaultInvoker
 *
 * @author Mrhan
 * @date 2021/6/17 15:34
 */
public class DefaultInvoker extends AbstractInvoker{

    public DefaultInvoker(ServiceContainer serviceContainer) {
        super(serviceContainer);
    }

    @Override
    protected ParamResolve getParamResolve() {
        return new DefaultParamResolve();
    }
}
