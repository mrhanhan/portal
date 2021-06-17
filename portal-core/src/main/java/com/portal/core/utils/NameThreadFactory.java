package com.portal.core.utils;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.ThreadFactory;

/**
 * NameThreadFactory
 *
 * @author Mrhan
 * @date 2021/6/17 17:47
 */
@RequiredArgsConstructor
public class NameThreadFactory implements ThreadFactory {

    private final String name;

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName(name);
        return thread;
    }
}
