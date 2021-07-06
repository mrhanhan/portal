package com.example.image;

import com.example.image.api.ImageService;
import com.portal.core.connect.socket.ClientSocketConnectionManager;
import com.portal.core.connect.socket.SocketConnectMetadata;
import com.portal.core.discovery.DefaultServiceDiscovery;
import lombok.SneakyThrows;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Graphics;

/**
 * Client
 *
 * @author Mrhan
 * @date 2021/7/6 10:37
 */
public class Client {


    private ImageService imageService;
    DefaultServiceDiscovery serviceDiscovery;
    public void before() {
        ClientSocketConnectionManager manager = new ClientSocketConnectionManager();
        serviceDiscovery = new DefaultServiceDiscovery(manager, (c) -> SocketConnectMetadata.createSocketMetadata("localhost", 1720));
        imageService = serviceDiscovery.getService("imageService", ImageService.class);
    }

    public void startUp() {
        JFrame jFrame = new JFrame();
        jFrame.setBounds(100, 100, 300, 300);
        Canvas canvas = new Canvas(){
            @SneakyThrows
            @Override
            public void paint(Graphics g) {
                g.drawImage(imageService.image(), 0, 0, null);
            }
        };
        jFrame.getContentPane().add(canvas);
        canvas.setBounds(jFrame.getBounds());
        jFrame.setVisible(true);
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.before();
        client.startUp();
    }
}
