package com.loginsystem;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

import javafx.application.Platform;
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
        messageInput.setText(ClientInfo.name);
    }

    @FXML
    private void Finish() {
        String newName = messageInput.getText().trim();
        if (newName.isEmpty()) {
            errorMessage.setText("用户名不能为空!");
            return;
        }
        if (newName.equals(ClientInfo.name)) {
            errorMessage.setText("新用户名不能与当前用户名相同!");
            return;
        }
        if (newName.length() < 3) {
            errorMessage.setText("用户名至少需要3个字符!");
            return;
        }
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(ClientInfo.socket.getOutputStream()));
            bw.write("MODIFY_NAME");
            bw.newLine();
            bw.write(ClientInfo.name);
            bw.newLine();
            bw.write(newName);
            bw.newLine();
            bw.flush();
            while (true) {
                if (ClientInfo.responseList.isEmpty())
                    continue;
                if (ClientInfo.responseList.get(0).command.equals("MODIFY_NAME")) {
                    String success = ClientInfo.responseList.removeFirst().success;
                    if (success.equals("false")) {
                        errorMessage.setText("用户名已存在！");
                    } else {
                        Platform.runLater(() -> {
                            ClientInfo.name = newName;
                        });
                        ((Stage) messageInput.getScene().getWindow()).close();
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
