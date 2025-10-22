// MainController.java
package com.loginsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class MainController {

    @FXML
    private void handleLogin(ActionEvent event) {
        try {
            switchScene("/com/resources/fxml/login.fxml", event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        try {
            switchScene("/com/resources/fxml/register.fxml", event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void switchScene(String fxmlPath, ActionEvent event) throws Exception {
        Stage stage = new Stage();
        // Ensure absolute path for resource
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/com/resources/css/styles.css").toExternalForm());
        stage.setTitle("ChatDude");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        // Close current window using event source
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
}