package com.portal.core.protocol;

import com.portal.core.server.ProtocolDataHandler;

/**
 * IProtocol
 * 传送门传输协议
 *  1. 描述数据传输的协议。
 *  2. 协议必须提供序列号和反序列化数据的能力
 *  3. 协议也必须提供对此协议支持的连接工具
 *
 * @author Mrhan
 * @date 2021/6/9 16:09
 */
public interface Protocol<T extends Data> {
    /**
     * 协议名称
     * @return  返回协议名称
     */
    String getName();

    /**
     * 获取协议版本
     * @return  返回协议版本
     */
    String getVersion();

    /**
     * 此协议是否向下兼容
     * @return  是否兼容
     */
    boolean isCompatibleOld();

    /**
     * 协议所在的层 TCP/IP 模型
     * @return 协议所在层的描述
     */
    int getLevel();

    /**
     * 获取协议的数据处理程序
     * @return  协议的数据处理程序
     */
    ProtocolDataHandler<T> getProtocolDataHandler();
}
