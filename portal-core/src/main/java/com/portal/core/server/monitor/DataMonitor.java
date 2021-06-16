package com.portal.core.server.monitor;

import com.portal.core.connect.Connection;
import com.portal.core.server.Monitor;

/**
 * DataMonitor
 * 数据检测项，检测不同的不同连接的数据信息
 * @author Mrhan
 * @date 2021/6/15 20:22
 */
public interface DataMonitor extends Monitor {
    /**
     * 获取连接信息
     * @return  返回检测的连接西信息
     */
    Connection getConnection();
}
