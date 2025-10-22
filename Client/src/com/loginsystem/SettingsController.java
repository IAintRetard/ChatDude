// SettingsController.java
package com.loginsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.Node;

public class SettingsController {

    @FXML
    private void handleClose(ActionEvent event) throws Exception {
        // 关闭设置窗口
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
}