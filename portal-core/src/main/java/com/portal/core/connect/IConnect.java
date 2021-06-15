package com.portal.core.connect;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * IConnect
 *
 * @author Mrhan
 * @date 2021/6/9 16:27
 */
public interface IConnect {
    /**
     * 获取输入
     * @return  输入
     */
    InputStream getInput();

    /**
     * 获取输出
     * @return  获取输出
     */
    OutputStream getOutput();
}
