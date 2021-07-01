package com.portal.core.context.monitor;

import com.portal.core.connect.Connection;
import com.portal.core.context.Monitor;
import com.portal.core.context.send.SendResultData;

/**
 * DataMonitor
 * 数据检测项目
 * @author Mrhan
 * @date 2021/7/1 16:15
 */
public interface DataMonitor extends Monitor {
    /**
     * 获取检测的连接
     * @return  获取检测的连接
     */
    Connection getConnection();

    /**
     * 获取检测项的发送响应数据功能
     * @return  SendResultData
     */
    SendResultData getSendResultData();
}
