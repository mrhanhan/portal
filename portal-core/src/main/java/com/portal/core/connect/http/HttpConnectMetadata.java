package com.portal.core.connect.http;

import com.portal.core.connect.ConnectMetadata;
import lombok.Data;

/**
 * HttpConnectMetadata
 *
 * @author Mrhan
 * @date 2021/7/6 10:15
 */
@Data
public class HttpConnectMetadata implements ConnectMetadata {

    /**
     * 主机
     */
    private String host;
    /**
     * 通讯路径
     */
    private String path;

}
