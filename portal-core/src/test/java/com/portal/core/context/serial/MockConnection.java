package com.portal.core.context.serial;

import com.portal.core.connect.ConnectMetadata;
import com.portal.core.connect.Connection;
import com.portal.core.connect.ConnectionManager;
import com.portal.core.connect.ConnectionSession;
import com.portal.core.connect.SessionKey;
import com.portal.core.context.send.CallingManager;
import com.portal.core.context.send.MapCallingManager;
import com.portal.core.service.ServiceContainer;
import com.portal.core.service.SimpleServiceContainer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * MockConnection
 *
 * @author Mrhan
 * @date 2021/7/4 11:29
 */
public class MockConnection implements Connection {
    @Override
    public InputStream getInput() {
        return new ByteArrayInputStream(new byte[]{});
    }

    @Override
    public OutputStream getOutput() {
        return new ByteArrayOutputStream();
    }

    @Override
    public ConnectionManager getManager() {
        return null;
    }

    @Override
    public ConnectMetadata getMetadata() {
        return null;
    }

    @Override
    public ConnectionSession getSession() {
        return new ConnectionSession() {
            @Override
            public SessionKey getSessionKey() {
                return null;
            }

            @Override
            public ServiceContainer getServiceContainer() {
                return new SimpleServiceContainer();
            }
        };
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public CallingManager getCallingManager() {
        return new MapCallingManager();
    }

    @Override
    public void close() throws Exception {

    }
}
