package com.example.chat.client;

import com.example.chat.api.RegisterClientService;
import com.example.chat.api.SendMessageService;
import com.example.chat.model.Message;
import com.example.chat.model.UserInfo;
import com.portal.core.connect.socket.ClientSocketConnectionManager;
import com.portal.core.connect.socket.SocketConnectMetadata;
import com.portal.core.discovery.DefaultServiceDiscovery;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Client
 *
 * @author Mrhan
 * @date 2021/7/4 13:15
 */
public class Client {

    private RegisterClientService registerClientService;
    private SendMessageService sendMessageService;

    private void onBefore() {
        ClientSocketConnectionManager manager = new ClientSocketConnectionManager();
        DefaultServiceDiscovery discovery = new DefaultServiceDiscovery(manager, (c) -> SocketConnectMetadata.createSocketMetadata("localhost", 1720));
        registerClientService = discovery.getService("registerClientService", RegisterClientService.class);
        sendMessageService = discovery.getService("sendMessageService", SendMessageService.class);
    }

    public void start() {

        onBefore();

        JFrame frame = new JFrame("Client");
        frame.setBounds(100, 100, 1024, 700);
        Container pane = frame.getContentPane();
        pane.setLayout(null);
        pane.setBackground(Color.CYAN);
        // 初始化列表
        MessageCallbackImpl callback = new MessageCallbackImpl(pane);

        // 初始化输入框
        JTextField textField = new JTextField();
        pane.add(textField).setBounds(0, 0, 924, 50);
        JButton connectBtn = new JButton();
        connectBtn.setText("连接");
        connectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerClientService.register(new UserInfo(textField.getText()), callback);
            }
        });
        pane.add(connectBtn).setBounds(924, 0, 100, 50);

        frame.add(callback.getTable()).setBounds(0, 50, 1024, 500);

        JTextField message = new JTextField();
        pane.add(message).setBounds(0, 550, 924, 50);
        JButton sendBtn = new JButton();
        sendBtn.setText("发送");
        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = message.getText();
                Message msg = new Message();
                msg.setContent(text);
                msg.setUserInfo(new UserInfo(textField.getText()));
                sendMessageService.send(msg);
            }
        });
        pane.add(sendBtn).setBounds(924, 550, 100, 50);

        frame.setVisible(true);


    }

    public static void main(String[] args) {
        new Client().start();
    }
}
