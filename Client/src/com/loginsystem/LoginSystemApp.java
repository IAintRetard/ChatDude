package com.loginsystem;

import java.net.Socket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginSystemApp extends Application {

    @Override
    public void init() throws Exception {
        try {
            super.init();
            ClientInfo.socket = new Socket("127.0.0.1", 114);
            // 监听线程，启动！
            Thread Listener = new Thread(new Listen());
            Listener.setDaemon(true);
            Listener.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Platform.setImplicitExit(true);
        Parent root = FXMLLoader.load(getClass().getResource("/com/resources/fxml/main.fxml"));
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/com/resources/css/styles.css").toExternalForm());
        primaryStage.setTitle("ChatDude");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void stop() {

        try {
            if (ClientInfo.socket != null) {
                ClientInfo.socket.getInputStream().close();
                ClientInfo.socket.getOutputStream().close();
                ClientInfo.socket.close();
                super.stop();
            }
        } catch (Exception e) {
            System.out.println("Disconnected from server");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}