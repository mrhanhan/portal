package com.portal.core.context;

import com.portal.core.connect.ConnectionManager;


/**
 * ConnectionMonitor
 * 主要检测各种连接，然后检测到后，再交给Server、或者Client
 * @author Mrhan
 * @date 2021/6/15 20:20
 */
public interface ConnectionMonitor extends Monitor {
    /**
     * 返回检测的连接工厂
     * @return ConnectionManager
     */
    ConnectionManager getConnectionManager();
}
