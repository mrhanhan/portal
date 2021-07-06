package com.portal.core.connect.http;

import com.portal.core.connect.AbstractConnection;
import com.portal.core.connect.Connection;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * HttpConnect
 * Http 请求连接
 * @author Mrhan
 * @date 2021/6/10 11:52
 */
public class HttpRequestConnect extends AbstractConnection implements Connection {


    @Getter
    @Setter
    private HttpServletRequest request;

    @Getter
    @Setter
    private HttpServletResponse response;


    @Override
    public InputStream getInput() {
        return null;
    }

    @Override
    public OutputStream getOutput() {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}
