package com.loginsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

import java.io.IOException;

public class SettingsController {

    @FXML
    private VBox contentArea;

    @FXML
    private Button btnGeneral;

    @FXML
    private Button btnAccount;

    private Button selectedButton;

    @FXML
    private void initialize() {
        // 在初始化时加载默认内容（账号设置）
        try {
            loadContent("/com/resources/fxml/settings/settings_account.fxml");
            setSelectedButton(btnAccount);
        } catch (IOException e) {
            System.err.println("无法加载默认设置面板: " + e.getMessage());
        }
    }

    @FXML
    private void showGeneral(ActionEvent event) throws IOException {
        loadContent("/com/resources/fxml/settings/settings_general.fxml");
        setSelectedButton(btnGeneral);
    }

    @FXML
    private void showAccount(ActionEvent event) throws IOException {
        loadContent("/com/resources/fxml/settings/settings_account.fxml");
        setSelectedButton(btnAccount);
    }

    // 给选中按钮添加高亮显示
    private void setSelectedButton(Button btn) {
        if (selectedButton != null) {
            selectedButton.getStyleClass().remove("nav-button-selected");
        }
        selectedButton = btn;
        if (selectedButton != null && !selectedButton.getStyleClass().contains("nav-button-selected")) {
            selectedButton.getStyleClass().add("nav-button-selected");
        }
    }

    // 加载设置页面右侧区域选项卡
    private void loadContent(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent node = loader.load();
        contentArea.getChildren().setAll(node);
    }
}