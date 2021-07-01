package com.example.simple.service.impl;

import com.example.simple.model.Account;
import com.example.simple.model.NoSerial;
import com.example.simple.model.User;
import com.example.simple.service.AccountService;
import com.example.simple.service.UserService;
import com.portal.core.connect.socket.ClientSocketConnectionManager;
import com.portal.core.connect.socket.SocketConnectMetadata;
import com.portal.core.discovery.DefaultServiceDiscovery;
import com.portal.core.discovery.ServiceDiscovery;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;

/**
 * UserServiceImpl
 *
 * @author Mrhan
 * @date 2021/6/17 19:02
 */
public class UserServiceImpl implements UserService {

    private int count = 0;

    private ByteArrayOutputStream output = new ByteArrayOutputStream();

    private AccountService accountService;

    public UserServiceImpl() {
        ClientSocketConnectionManager manager = new ClientSocketConnectionManager();
        ServiceDiscovery discovery = new DefaultServiceDiscovery(manager, (c) -> SocketConnectMetadata.createSocketMetadata("localhost", 1721));
        // 发现账户服务
        accountService = discovery.getService("accountService", AccountService.class);
    }

    @Override
    public User login(User user) {
        user.setMoney(1000000);
        user.setOrder(1000000);
        user.setRole("超管");
        return user;
    }

    @Override
    public User getUserInfo(Long id) {
        User user = new User();
        user.setUsername("新增用户-" + (count ++ ));
        user.setPassword("123456");
        user.setMoney((int) System.currentTimeMillis());
        user.setOrder(Math.toIntExact(id));
        user.setRole("超管");
        user.setNoSerial(new NoSerial());
        // 远程调用
        Account account = accountService.createAccount(user);
        user.setAccount(account);
        System.out.println("用户服务被调用-" + count);
        return user;
    }

    @Override
    public NoSerial test() {
        return new NoSerial();
    }

    @Override
    public File file() {
        return new File("E:\\NutProject\\portal\\portal-example\\portal-simple\\src\\main\\java\\com\\example\\simple\\service\\UserService.java");
    }

    @Override
    public OutputStream output() {
        return output;
    }

    @Override
    public String printOutput() {
        return new String(output.toByteArray());
    }
}
