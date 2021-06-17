package com.portal.core.server;

import com.portal.core.service.MethodDelegateService;
import lombok.Data;
import lombok.SneakyThrows;
import org.junit.Test;

import java.io.Serializable;

public class ServerSocketServerTest {


    @Data
    public static class UserInfo implements Serializable {
        public String sex;
    }

    public Object add(String name, int age, UserInfo info) {
        System.out.println("Invoke: " + name + " : " + age + ": " + info);
        return "Hello";
    }

    @SneakyThrows
    public static void main(String[] args) {
        ServerSocketPortal server = new ServerSocketPortal() {
            @Override
            protected void onStart() throws Exception {
                super.onStart();
                register(new MethodDelegateService("user", new ServerSocketServerTest(), "add", String.class, int.class, UserInfo.class));
            }
        };

        server.startUp();
    }

    @Test
    public void client() {
        SimpleClient client = new SimpleClient(1720);
        System.out.println(client.call("os"));
    }
    @Test
    public void jsonClient() {
        JsonDataClient client = new JsonDataClient(1720);
        UserInfo info = new UserInfo();
        info.sex = "女生";
        System.out.println(client.call("user", "add", "小王", 23, info));
    }
}