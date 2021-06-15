package com.portal.core.socket1;

import com.portal.core.service.MethodDelegateService;

/**
 * Test
 *
 * @author Mrhan
 * @date 2021/6/15 16:27
 */
public class Test {

    @org.junit.Test
    public void startUpServer() {
        Server server = new Server(1720);
        server.register(new MethodDelegateService("this", this, "service"));
        server.register(new MethodDelegateService("os", this, "os"));
        server.start();

    }

    public String service() {
        return "this";
    };

    public String os() {
        return System.getProperties().toString();
    }


    public static void main(String[] args) {
        Client client = new Client(1720);
        System.out.println(client.call("os"));
    }
}
