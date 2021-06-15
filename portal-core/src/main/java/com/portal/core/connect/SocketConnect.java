package com.portal.core.connect;

import lombok.SneakyThrows;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * SocketConnect
 * Socket 的连接
 * @author Mrhan
 * @date 2021/6/10 11:51
 */
public class SocketConnect implements IConnect{

    private final Socket socket;
    private final InputStream input;
    private final OutputStream output;

    @SneakyThrows
    public SocketConnect(Socket socket) {
        this.socket = socket;
        input = socket.getInputStream();
        output = socket.getOutputStream();
    }

    @Override
    public InputStream getInput() {
        return input;
    }

    @Override
    public OutputStream getOutput() {
        return output;
    }
}
