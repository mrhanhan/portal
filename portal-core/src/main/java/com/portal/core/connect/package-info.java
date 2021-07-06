/**
 * 用于定义连接的模块
 * 所有的数据都通过此模块的{@link com.portal.core.connect.Connection} 进行通信
 * {@link com.portal.core.connect.Connection} 抽象了通信的基本诉求，输入/输出
 * 除此之外还提供了这个连接的回调管理器、连接管理器、会话容器、会话容器.临时服务容器
 *
 * socket 包是基于socket进行连接的实现
 * http 包是基于javax.servlet api 进行通信
 *
 * @see com.portal.core.connect.socket
 * @see com.portal.core.connect.http
 */
package com.portal.core.connect;