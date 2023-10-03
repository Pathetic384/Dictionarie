package com.example.demo1;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) throws Exception{
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Testin");

        Parent root = FXMLLoader.load(getClass().getResource("dict.fxml"));
        Scene scene = new Scene(root);

        Image icon = new Image("pic.png");
        stage.getIcons().add(icon);

        stage.setScene(scene);
        stage.show();
    }

}