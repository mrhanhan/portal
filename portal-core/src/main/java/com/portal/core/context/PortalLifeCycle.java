package com.portal.core.context;

/**
 * PortalLifeCycle
 * 传送门生命周期
 * @author Mrhan
 * @date 2021/7/1 11:19
 */
public interface PortalLifeCycle {


    /**
     * 初始化
     * @param context Context
     */
    void onInitialize(PortalContext context);

    /**
     * 启动
     * @param context Context
     */
    void onStartup(PortalContext context);

    /**
     * 关闭
     * @param context Context
     */
    void onShutDown(PortalContext context);
}
