package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class SwitchScene {


    public void Switch(String path, AnchorPane root) throws Exception {
        AnchorPane pane = FXMLLoader.load(getClass().getResource(path));
        root.getChildren().setAll(pane);
    }
}
