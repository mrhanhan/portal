package com.portal.core.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Param
 * 参数
 * @author Mrhan
 * @date 2021/6/16 14:40
 */
@Data
@Accessors(chain = true)
public class Param implements Serializable {
    /**
     * 参数类型
     */
    private ParamTypeEnum type;
    /**
     * 是否是引用参数
     */
    private boolean quote;
    /**
     * 参数数据
     */
    private byte[] data;
    /**
     * 引用的临时服务信息
     */
    private String quoteService;
    /**
     * 如果如何是数组和对象
     */
    private Param[] children;
    /**
     * 是否是异常
     */
    private boolean exception;
    /**
     * 字段名称
     */
    private String filedName;

}
