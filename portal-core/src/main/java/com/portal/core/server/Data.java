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
public interface Data<T extends Data<?>> {
    /**
     * 调用ID
     * @return
     */
    String getId();

    /**
     * 获取协议名称
     * @return  获取协议名称
     */
    Protocol<T> getProtocol();

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
    /**
     * 获取结果
     * @return  结果
     */
    Param getResult();

    /**
     * 使用相同协议构建响应数据包
     * @param param 响应数据
     * @return  返回响应数据
     */
    T result(Param param);

    /**
     * 是否是响应数据
     * @return  是否是响应数据
     */
    default boolean isResultData() {
        return getResult() != null;
    }
}
