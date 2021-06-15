package com.portal.core;

/**
 * ISupport
 *
 * @author Mrhan
 * @date 2021/6/15 16:47
 */
public interface ISupport<T> {
    /**
     * 子类是否支持 T
     * @param t T
     * @return  如果支持返回true
     */
    boolean isSupport(T t);

}
