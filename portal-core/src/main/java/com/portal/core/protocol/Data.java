package com.portal.core.protocol;

import com.portal.core.connect.Connection;

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
    Protocol getProtocol();

    /**
     * 获取连接对象
     * @return  获取连接对象
     */
    Connection getConnection();

}
