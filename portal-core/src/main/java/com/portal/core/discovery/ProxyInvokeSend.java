package com.portal.core.discovery;

import com.portal.core.connect.Connection;
import com.portal.core.protocol.JsonData;
import com.portal.core.protocol.param.DefaultParamResolve;
import com.portal.core.protocol.param.Param;
import com.portal.core.protocol.param.ParamResolve;
import com.portal.core.server.send.DefaultInvokeSend;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * ProxyInvokeSend
 * 代理连接服务
 * @author Mrhan
 * @date 2021/6/30 16:33
 */
public class ProxyInvokeSend extends DefaultInvokeSend {
    @Getter
    @Setter
    private ParamResolve paramResolve;


    public ProxyInvokeSend() {
        paramResolve = new DefaultParamResolve();
    }

    @SneakyThrows
    public Object invokeSend(Connection connection, String serviceName, String id, Type type , Object... args) throws IOException {
        JsonData data = new JsonData();
        data.setService(serviceName);
        data.setServiceId(id);
        Param[] params = new Param[args.length];
        for (int i = 0; i < args.length; i++) {
            params[i] = paramResolve.resolve(args[i], connection.getSession().getServiceContainer());
        }
        data.setParamArray(params);
        Object[] results = new Object[1];
        final Object lock = new Object();
        super.invokeSend(data, connection, (result) -> {
            results[0] = paramResolve.resolve(result, type);
            synchronized (lock) {
                lock.notifyAll();
            }
        });
        if (results[0] == null) {
            synchronized (lock) {
                lock.wait();
            }
        }
        return results[0];
    }
}
