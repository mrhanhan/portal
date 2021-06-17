package com.portal.core.connect.socket;

import com.portal.core.connect.AbstractConnection;
import com.portal.core.connect.Connection;
import com.portal.core.connect.ConnectionSession;
import com.portal.core.connect.SessionKey;
import com.portal.core.server.send.MapCallingManager;
import com.portal.core.service.SimpleServiceContainer;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * SocketConnect
 * Socket 的连接
 * @author Mrhan
 * @date 2021/6/10 11:51
 */
public class SocketConnect extends AbstractConnection implements Connection, ConnectionSession {

    private final Socket socket;
    private final InputStream input;
    private final OutputStream output;

    @Getter
    private SimpleServiceContainer serviceContainer;


    @SneakyThrows
    public SocketConnect(Socket socket) {
        this.socket = socket;
        input = socket.getInputStream();
        output = socket.getOutputStream();
        setCallingManager(new MapCallingManager());

    }

    @Override
    public SessionKey getSessionKey() {
        return null;
    }

    @Override
    public InputStream getInput() {
        return input;
    }

    @Override
    public OutputStream getOutput() {
        return output;
    }

    @Override
    public void close() throws IOException {
        input.close();
        output.close();
        socket.close();
    }

    @Override
    public boolean isAvailable() {
        return socket.isConnected();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"socket\":")
                .append(socket);
        sb.append('}');
        return sb.toString();
    }
}
