package com.loginsystem;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SettingsAccountController {

    // 在这里写账户信息选项卡的按钮对应的详细页面
    @FXML
    private void modifyName(ActionEvent event) {
        try {
            Parent root = FXMLLoader
                    .load(getClass().getResource("/com/resources/fxml/settings/settings_account/modify_name.fxml"));
            Scene scene = new Scene(root, 600, 400);
            Stage stage = new Stage();
            scene.getStylesheets().add(getClass().getResource("/com/resources/css/styles.css").toExternalForm());
            stage.setTitle("修改用户名");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}