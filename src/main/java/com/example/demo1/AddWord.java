package com.example.demo1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;

public class AddWord {

    @FXML
    private TextArea adding;
    @FXML
    private TextArea meaning;

    Dictionary testing = new Dictionary();
    private final String path = "src\\main\\resources\\dictest.txt";

    public void Return(ActionEvent event) throws Exception {
        SwitchScene s = new SwitchScene("dict.fxml", event);
    }

    public void wordAdd(ActionEvent event) throws Exception {
        if(Objects.equals(adding.getText(), "") || Objects.equals(meaning.getText(), "")) return;
        String conf = "Do you really want to add the word: " + adding.getText();
        String conf2 = "Definition: " + meaning.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("really?");
        alert.setContentText(conf2);
        alert.setHeaderText(conf);
        Optional<ButtonType> alertResult = alert.showAndWait();
        if(alertResult.get() != ButtonType.OK) {
            return;
        }

        testing.LoadFile(path);
        TreeMap<String, Word> temp = testing.map;
        Word tmp = new Word (adding.getText(), meaning.getText());
        temp.put(adding.getText(), tmp);
        testing.map = temp;
        testing.SaveFile();
        System.out.println("Done!");
    }
}
