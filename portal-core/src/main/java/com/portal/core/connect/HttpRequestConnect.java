package com.portal.core.connect;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * HttpConnect
 *
 * @author Mrhan
 * @date 2021/6/10 11:52
 */
public class HttpRequestConnect extends AbstractConnect implements Connection {


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
