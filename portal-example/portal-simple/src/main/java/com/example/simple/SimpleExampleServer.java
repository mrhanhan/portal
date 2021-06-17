package com.example.simple;

import com.example.simple.model.User;
import com.example.simple.service.UserService;
import com.example.simple.service.impl.UserServiceImpl;
import com.portal.core.server.ServerSocketPortal;
import com.portal.core.service.BeanDelegateService;

/**
 * SimpleExampleServer
 *
 * @author Mrhan
 * @date 2021/6/17 19:00
 */
public class SimpleExampleServer extends ServerSocketPortal {

    @Override
    protected void onStart() throws Exception {
        super.onStart();
        // 注册服务
        register(new BeanDelegateService("userService", new UserServiceImpl(), UserService.class)
                         .register("login", User.class));
    }
    public static void main(String[] args) {
        try {
            new SimpleExampleServer().startUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}