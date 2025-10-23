package com.loginsystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    @FXML
    private void handleLogin() {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(LoginSystemApp.socket.getOutputStream()));
            BufferedReader br = new BufferedReader(new InputStreamReader(LoginSystemApp.socket.getInputStream()));
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            // 输入验证
            if (username.isEmpty() || password.isEmpty()) {
                errorLabel.setText("用户名和密码不能为空");
                return;
            }

            // 验证登录
            bw.write("login");
            bw.newLine();
            bw.write(username);
            bw.newLine();
            bw.write(password);
            bw.newLine();
            bw.flush();
            String feedBack = br.readLine();
            if (feedBack.equals("true")) {
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/com/resources/fxml/chat.fxml"));
                Scene scene = new Scene(root, 800, 600);
                scene.getStylesheets().add(getClass().getResource("/com/resources/css/styles.css").toExternalForm());
                stage.setTitle("ChatDude");
                stage.setScene(scene);
                stage.show();
                Stage currentStage = (Stage) usernameField.getScene().getWindow();
                currentStage.close();
                ChatController.currentUser = username;
            } else {
                errorLabel.setText("用户名或密码错误");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack() {
        try {
            switchToMain();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void switchToMain() throws Exception {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/com/resources/fxml/main.fxml"));
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/com/resources/css/styles.css").toExternalForm());
        stage.setTitle("ChatDude");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        // 关闭当前窗口
        Stage currentStage = (Stage) errorLabel.getScene().getWindow();
        currentStage.close();
    }
}