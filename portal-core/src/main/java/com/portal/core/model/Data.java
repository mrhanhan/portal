package com.portal.core.model;

/**
 * Data
 * 调用ID
 * @author Mrhan
 * @date 2021/7/1 16:44
 */
@lombok.Data
public class Data {

    public static final int CALL = 1;
    public static final int RETURN = 0;
    /**
     * 服务名称
     */
    private String id;
    /**
     * 服务名称
     */
    private String serviceName;
    /**
     * 服务ID
     */
    private String serviceId;
    /**
     * 参数数据
     */
    private Param[] params;
    /**
     * 调用操作
     * 1 调用 0 响应
     */
    private int operate;
}
