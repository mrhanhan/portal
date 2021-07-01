//package com.example.simple;
//
//import com.example.simple.model.User;
//import com.example.simple.service.UserService;
//import com.example.simple.service.impl.UserServiceImpl;
//import com.portal.core.server.ServerSocketPortal;
//import com.portal.core.service.BeanDelegateService;
//
///**
// * SimpleExampleServer
// *
// * @author Mrhan
// * @date 2021/6/17 19:00
// */
//public class SimpleUserServer extends ServerSocketPortal {
//
//
//
//    @Override
//    protected void onStart() throws Exception {
//        super.onStart();
//        // 注册服务
//        BeanDelegateService service = new BeanDelegateService("userService", new UserServiceImpl(), UserService.class);
//        service.register("login", User.class);
//        service.register("getUserInfo", Long.class);
//        service.register("test");
//        service.register("file");
//        service.register("output");
//        service.register("printOutput");
//        register(service);
//    }
//    public static void main(String[] args) {
//        try {
//            new SimpleUserServer().startUp();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
