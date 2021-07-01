//package com.example.simple;
//
//import com.example.simple.model.User;
//import com.example.simple.service.AccountService;
//import com.example.simple.service.impl.AccountServiceImpl;
//import com.portal.core.context.ConnectionMonitor;
//import com.portal.core.server.ServerSocketPortal;
//import com.portal.core.server.monitor.socket.ServerSocketConnectionMonitor;
//import com.portal.core.service.BeanDelegateService;
//
///**
// * SimpleExampleServer
// *
// * @author Mrhan
// * @date 2021/6/17 19:00
// */
//public class SimpleAccountServer extends ServerSocketPortal {
//
//
//    @Override
//    protected ConnectionMonitor createConnectionMonitor() {
//        return new ServerSocketConnectionMonitor(this, 1721);
//    }
//
//    @Override
//    protected void onStart() throws Exception {
//        super.onStart();
//        // 注册服务
//        BeanDelegateService service = new BeanDelegateService("accountService", new AccountServiceImpl(), AccountService.class);
//        service.register("createAccount", User.class);
//        register(service);
//    }
//    public static void main(String[] args) {
//        try {
//            new SimpleAccountServer().startUp();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
