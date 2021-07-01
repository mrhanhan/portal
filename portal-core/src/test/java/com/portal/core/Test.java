package com.portal.core;

import com.portal.core.service.MethodDelegateService;
import com.portal.core.service.SimpleServiceContainer;
import lombok.SneakyThrows;

/**
 * Test
 *
 * @author Mrhan
 * @date 2021/6/10 11:49
 */
public class Test {
    @SneakyThrows
    public static void main(String[] args) {
        SimpleServiceContainer container = new SimpleServiceContainer();
        container.registerService(new MethodDelegateService("print", new FileService(), "print"));

        System.out.println(container.getService("print").invoke(null));
    }
}
