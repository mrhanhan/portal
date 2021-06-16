package com.portal.core.server;

import com.portal.core.Support;

/**
 * DataHandler
 * 数据处理程序
 * @author Mrhan
 * @date 2021/6/15 20:25
 */
public interface DataHandler<IN, OUT> extends Support<IN> {
    /**
     * 处理数据
     * @param data  处理数据
     * @return 返回处理后的数据
     */
    OUT onHandler(IN data);

}
