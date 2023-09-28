package com.example.demo1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.util.TreeMap;

public class AddWord {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextArea adding;
    @FXML
    private TextArea meaning;

    Dictionary testing = new Dictionary();
    private final String path = "src\\main\\resources\\dictest.txt";

    public void Return(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("dict.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void wordAdd(ActionEvent event) throws Exception {
        testing.LoadFile(path);
        TreeMap<String, Word> temp = testing.map;
        Word tmp = new Word (adding.getText(), meaning.getText());
        temp.put(adding.getText(), tmp);
        testing.map = temp;
        testing.SaveFile();
        System.out.println("Done!");
    }
}
