package com.portal.core.server.invoker;

import com.portal.core.Portal;
import com.portal.core.protocol.param.DefaultParamResolve;
import com.portal.core.protocol.param.ParamResolve;

/**
 * DefaultInvoker
 *
 * @author Mrhan
 * @date 2021/6/17 15:34
 */
public class DefaultInvoker extends AbstractInvoker{

    public DefaultInvoker(Portal server) {
        super(server);
    }

    @Override
    protected ParamResolve getParamResolve() {
        return new DefaultParamResolve();
    }
}
