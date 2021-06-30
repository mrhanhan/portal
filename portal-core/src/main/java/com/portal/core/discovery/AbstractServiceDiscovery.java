package com.portal.core.discovery;

import com.portal.core.connect.Connection;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;

/**
 * AbstractServiceDiscovery
 *
 * @author Mrhan
 * @date 2021/6/30 16:30
 */
public abstract class AbstractServiceDiscovery implements ServiceDiscovery{

    private ProxyInvokeSend proxyInvokeSend;

    public AbstractServiceDiscovery() {
        proxyInvokeSend = new ProxyInvokeSend();
    }

    @Override
    public <T> T getService(String name, Class<T> cls) {
        Enhancer enhancer = new Enhancer();
        if (cls.isInterface()) {
            enhancer.setInterfaces(new Class[]{cls});
        } else {
            enhancer.setSuperclass(cls);
        }
        Connection connection = getConnection();
        enhancer.setCallback(new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                return proxyInvokeSend.invokeSend(connection, name, method.getName(), method.getReturnType(), objects);
            }
        });
        return (T) enhancer.create();
    }

    /**
     * 获取发现的目标服务器连接
     * @return  返回连接对象
     */
    protected abstract Connection getConnection();

    @Override
    public void close() throws Exception {
        proxyInvokeSend.close();
    }
}
