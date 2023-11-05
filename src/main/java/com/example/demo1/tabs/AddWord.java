package com.example.demo1.tabs;
import com.example.demo1.Alerts;
import com.example.demo1.MainUI;
import com.example.demo1.WordsManagement.Dictionary;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class AddWord extends Alerts implements Initializable {

    @FXML
    private JFXTextField adding;
    @FXML
    private JFXTextArea meaning;

    Dictionary testing = new Dictionary();


    public void wordAdd(ActionEvent event) throws Exception {
        if(Objects.equals(adding.getText(), "") || Objects.equals(meaning.getText(), "")) {
            Alert alert = makeAlert("You must fill out both word and meaning!!!"
                    ,"","error");
            alert.show();
            return;
        }
        String conf;
        String conf2;
        if(MainUI.testing.Exist(adding.getText())) {
            conf = "   Are you sure about adding the word: " + adding.getText() + " which already exist?";
            conf2 = "Definition: " + MainUI.testing.FindMeaning(adding.getText());
        }
        else {
            conf = "Do you really want to add the word: " + adding.getText();
            conf2 = "Definition: " + meaning.getText();
        }
        Alert alert = makeAlert( conf2, conf, "confirm");

        Optional<ButtonType> alertResult = alert.showAndWait();
        if(alertResult.get() != ButtonType.OK) {
            return;
        }
        addWord();
    }

    public void addWord() throws Exception {
        testing = MainUI.testing;
        testing.insertWord(adding.getText(), meaning.getText());
        testing.SaveFile();
        System.out.println("Done!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
