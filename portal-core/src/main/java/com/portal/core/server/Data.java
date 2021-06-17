package com.portal.core.server;

import com.portal.core.connect.Connection;
import com.portal.core.protocol.Protocol;
import com.portal.core.protocol.param.Param;

/**
 * Data
 *
 * @author Mrhan
 * @date 2021/6/15 17:41
 */
public interface Data {
    /**
     * 获取协议名称
     * @return  获取协议名称
     */
    Protocol<? extends Data> getProtocol();

    /**
     * 获取连接对象
     * @return  获取连接对象
     */
    Connection getConnection();

    /**
     * 获取调用的服务
     * @return  获取调用的服务
     */
    String getService();

    /**
     * 获取服务 调用的ID
     * @return  获取调用服务的ID
     */
    String getServiceId();

    /**
     * 参数数组
     * @return  获取参数数组
     */
    Param[] getParamArray();

}
