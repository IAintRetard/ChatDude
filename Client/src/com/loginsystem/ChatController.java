package com.loginsystem;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ChatController {

    @FXML
    private TextArea messageInput;
    @FXML
    private Button sendBtn;
    @FXML
    private Button settingsBtn;
    @FXML
    private VBox messageContainer;
    @FXML
    private ScrollPane chatScrollPane;

    @FXML
    public void initialize() {

        Thread ChatListener = new Thread(() -> {
            while (true) {
                if (ClientInfo.responseList.isEmpty() || ClientInfo.stop)
                    continue;
                if (ClientInfo.responseList.get(0).command.equals("CHAT")) {
                    String sender = ClientInfo.responseList.get(0).sender;
                    String message = ClientInfo.responseList.get(0).content;
                    Platform.runLater(() -> {
                        addMessage(sender, message, false);
                    });
                    ClientInfo.responseList.remove(0);
                }
            }
        });
        ChatListener.setDaemon(true);
        ChatListener.start();
    }

    @FXML
    private void handleSend() {
        String message = messageInput.getText().trim();
        if (!message.isEmpty()) {
            addMessage(ClientInfo.name, message, true);
            messageInput.clear();

            try {
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(ClientInfo.socket.getOutputStream()));
                bw.write("CHAT");
                bw.newLine();
                bw.write(ClientInfo.name);
                bw.newLine();
                bw.write(message);
                bw.newLine();
                bw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (event.isShiftDown()) {
                // Shift+Enter 换行
                messageInput.appendText("\n");
            } else {
                // Enter 发送
                handleSend();
                event.consume();
            }
        }
    }

    @FXML
    private void handleSettings() {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/resources/fxml/settings/settings.fxml"));
            Scene scene = new Scene(root, 600, 400);
            // 确保设置窗口也使用全局样式表
            scene.getStylesheets().add(getClass().getResource("/com/resources/css/styles.css").toExternalForm());
            stage.setTitle("设置");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addMessage(String sender, String content, boolean isCurrentUser) {
        Text messageText = new Text(sender + ": " + content);
        messageText.setWrappingWidth(chatScrollPane.getWidth() - 40);

        // 可以根据发送者设置不同的样式
        if (isCurrentUser) {
            messageText.setStyle("-fx-fill: blue; -fx-font-weight: bold;");
        } else if (sender.equals("系统")) {
            messageText.setStyle("-fx-fill: green; -fx-font-style: italic;");
        }

        messageContainer.getChildren().add(messageText);

        // 自动滚动到底部
        chatScrollPane.setVvalue(1.0);
    }
}