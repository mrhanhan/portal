package com.portal.core.protocol.param;

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
     * 引用的服务ID信息
     */
    private String quoteServiceId;
    /**
     * 如果是数组参数，数组内部的元素信息
     */
    private Param[] children;
    /**
     * 数组长度
     */
    private int arrayLength;
    /**
     * 是否是异常
     */
    private boolean exception;



}
