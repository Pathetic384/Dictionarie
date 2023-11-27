package com.example.demo1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class Main extends Application {
    private double x = 0;
    private double y = 0;

    public static void main(String[] args) throws Exception{

        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("mainui.fxml"));
        Scene scene = new Scene(root);

        InputStream stream = new FileInputStream("src/main/resources/picss/gato.jpg");
        Image icon = new Image(stream);
        stage.getIcons().add(icon);
        stage.setTitle("Dictionarie B)");

        stage.initStyle(StageStyle.TRANSPARENT);
        root.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getY();
        });
        root.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - x);
            stage.setY(mouseEvent.getScreenY() - y);
        });
        stage.setScene(scene);
        stage.show();
    }
}