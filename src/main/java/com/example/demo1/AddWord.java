package com.example.demo1;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class AddWord extends SwitchScene implements Initializable {

    @FXML
    private TextArea adding;
    @FXML
    private TextArea meaning;


    Dictionary testing = new Dictionary();


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
        addWord();
    }

    public void addWord() throws Exception {
        testing = MainUI.testing;
        TreeMap<String, Word> temp = testing.map;
        Word tmp = new Word (adding.getText(), meaning.getText());
        temp.put(adding.getText(), tmp);
        testing.map = temp;
        testing.SaveFile();
        System.out.println("Done!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
