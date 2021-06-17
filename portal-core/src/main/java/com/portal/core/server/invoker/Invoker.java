package com.portal.core.server.invoker;

import com.portal.core.protocol.param.Param;
import com.portal.core.server.Data;

/**
 * Invoker
 * 执行Data数据
 * @author Mrhan
 * @date 2021/6/16 17:52
 */
public interface Invoker {

    /**
     * 执行调用
     * 并且将响应的数据返回
     * @param data  执行调用
     * @return Param
     */
    Param invoke(Data<?> data);

}
