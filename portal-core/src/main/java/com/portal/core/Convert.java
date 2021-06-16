package com.portal.core;

/**
 * ISerialization
 * 序列化数据
 * @author Mrhan
 * @date 2021/6/9 16:24
 */
public interface Convert<IN, OUT> {
    /**
     * 输入数据
     * @param in    in
     * @return    返回输出数据
     */
    OUT convert(IN in);
}
