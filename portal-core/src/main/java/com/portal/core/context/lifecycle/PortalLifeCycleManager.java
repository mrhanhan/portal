package com.portal.core.context.lifecycle;

import com.portal.core.context.PortalContext;
import com.portal.core.context.PortalLifeCycle;

import java.util.ArrayList;
import java.util.List;

/**
 * PortalLifeCycleManager
 *
 * @author Mrhan
 * @date 2021/7/1 15:28
 */
public class PortalLifeCycleManager implements PortalLifeCycle {

    private List<PortalLifeCycle> portalLifeCycleList;

    public PortalLifeCycleManager() {
        portalLifeCycleList = new ArrayList<>();
    }

    public void registerLifeCycle(PortalLifeCycle portalLifeCycle) {
        this.portalLifeCycleList.add(portalLifeCycle);
    }

    @Override
    public void onInitialize(PortalContext context) {
        for (PortalLifeCycle portalLifeCycle : portalLifeCycleList) {
            portalLifeCycle.onInitialize(context);
        }
    }

    @Override
    public void onStartup(PortalContext context) {
        for (PortalLifeCycle portalLifeCycle : portalLifeCycleList) {
            portalLifeCycle.onStartup(context);
        }
    }

    @Override
    public void onShutDown(PortalContext context) {
        for (PortalLifeCycle portalLifeCycle : portalLifeCycleList) {
            portalLifeCycle.onShutDown(context);
        }
    }
}
