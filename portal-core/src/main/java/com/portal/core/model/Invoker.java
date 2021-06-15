package com.portal.core.model;

/**
 * Invoker
 *
 * @author Mrhan
 * @date 2021/6/10 11:18
 */
public interface Invoker<T extends Invoke> {

    /**
     * 获取调用信息
     * @return  Invoke
     */
    T getInvoke();
}
