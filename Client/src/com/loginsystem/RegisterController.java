package com.loginsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javafx.event.ActionEvent;
import javafx.scene.Node;

public class RegisterController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label errorLabel;
    @FXML
    private Label successLabel;

    @FXML
    private void handleRegister() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String confirmPassword = confirmPasswordField.getText().trim();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showError("请填写所有必填字段");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showError("两次输入的密码不一致");
            return;
        }

        if (username.length() < 3) {
            showError("用户名至少需要3个字符");
            return;
        }

        if (password.length() < 6) {
            showError("密码至少需要6个字符");
            return;
        }

        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(ClientInfo.socket.getOutputStream()));
            bw.write("REGISTER");
            bw.newLine();
            bw.write(username);
            bw.newLine();
            bw.write(password);
            bw.newLine();
            bw.flush();
            while (true) {
                if (ClientInfo.responseList.isEmpty())
                    continue;
                if (ClientInfo.responseList.get(0).command.equals("REGISTER")) {
                    String feedBack = ClientInfo.responseList.removeFirst().success;
                    if (feedBack.equals("false")) {
                        showError("用户名已存在");
                        return;
                    }
                    showSuccess("注册成功！请返回登录界面登录");
                    clearFields();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            switchToMain(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void switchToMain(ActionEvent event) throws Exception {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/com/resources/fxml/main.fxml"));
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/com/resources/css/styles.css").toExternalForm());
        stage.setTitle("ChatDude");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    private void showError(String message) {
        errorLabel.setText(message);
        successLabel.setText("");
    }

    private void showSuccess(String message) {
        successLabel.setText(message);
        errorLabel.setText("");
    }

    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
    }

}