package com.example.demo1;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class SwitchScene {

    public void Switch(String path, AnchorPane root) throws Exception {
        AnchorPane pane = FXMLLoader.load(getClass().getResource(path));
        root.getChildren().setAll(pane);
    }
}
