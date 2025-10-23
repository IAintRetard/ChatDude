package com.loginsystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class modifyNameController {
    @FXML
    private TextArea messageInput;

    @FXML
    private Label errorMessage;

    @FXML
    private void initialize() {
        messageInput.setText(ChatController.currentUser);
    }

    @FXML
    private void Finish() {
        String newName = messageInput.getText().trim();
        if (newName.isEmpty()) {
            errorMessage.setText("用户名不能为空！");
        } else if (newName.equals(ChatController.currentUser)) {
            errorMessage.setText("新用户名不能与当前用户名相同！");
        } else if (newName.length() < 3) {
            errorMessage.setText("用户名至少需要3个字符！");
        } else {
            // 服务器可以响应并修改用户名，客户端也可以收到服务端的响应，但是之后会卡死
            try {
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(LoginSystemApp.socket.getOutputStream()));
                BufferedReader br = new BufferedReader(new InputStreamReader(LoginSystemApp.socket.getInputStream()));
                bw.write("modify_name");
                bw.newLine();
                bw.write(ChatController.currentUser);
                bw.newLine();
                bw.write(newName);
                bw.newLine();
                bw.flush();
                String response = br.readLine();
                if (response.equals("false")) {
                    errorMessage.setText("用户名已存在！");
                } else {
                    ChatController.currentUser = newName;
                    ((Stage) messageInput.getScene().getWindow()).close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
