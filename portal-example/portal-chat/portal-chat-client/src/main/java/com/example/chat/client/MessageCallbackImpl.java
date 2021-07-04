package com.example.chat.client;

import com.example.chat.model.Message;
import com.example.chat.model.MessageCallback;
import com.example.chat.model.UserInfo;
import lombok.Getter;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

/**
 * MessageCallbackImpl
 *
 * @author Mrhan
 * @date 2021/7/4 13:28
 */
public class MessageCallbackImpl implements MessageCallback {

    private List<Message> messageList = new ArrayList<>();
    @Getter
    private JTable table;
    private Object[] columns = {"用户", "消息"};

    public MessageCallbackImpl(Container panel) {
        //字段
        Object[][] data = new Object[][]{columns};
        DefaultTableModel model = new DefaultTableModel(data, columns);
        table = new JTable(model);
        panel.add(table).setBounds(0, 50, 1024, 500);
    }

    @Override
    public void messageCallback(Message message) {
        messageList.add(message);
        System.out.println("Message:" + message);
        refresh();
    }

    @Override
    public void userCallback(UserInfo userInfo) {

    }

    public void refresh() {
        Object[][] data = new Object[messageList.size() + 1][2];
        data[0] = columns;
        for (int i = 0; i < messageList.size(); i++) {
            Message message = messageList.get(i);
            String username = message.getUserInfo().getUsername();
            data[i + 1] = new Object[]{username, message.getContent()};
        }
        DefaultTableModel tableModel = new DefaultTableModel(data, columns);
        table.setModel(tableModel);
        table.updateUI();
        table.setEnabled(true);
    }
}
