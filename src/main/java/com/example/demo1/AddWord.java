package com.example.demo1;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class AddWord implements Initializable {

    @FXML
    private TextArea adding;
    @FXML
    private TextArea meaning;
    @FXML
    private StackPane root;
    @FXML
    private JFXDialog dialog;
    @FXML
    private JFXButton acceptButton;
    @FXML
    private JFXButton declineButton;


    Dictionary testing = new Dictionary();
    private final String path = "src/main/resources/dictest.txt";

    public void Return(ActionEvent event) throws Exception {
        SwitchScene s = new SwitchScene("dict.fxml", event);
    }

    public void wordAdd(ActionEvent event) {
        if(Objects.equals(adding.getText(), "") || Objects.equals(meaning.getText(), "")) return;
        dialog.show();
    }

    public void addWord() throws Exception {
        testing.LoadFile(path);
        TreeMap<String, Word> temp = testing.map;
        Word tmp = new Word (adding.getText(), meaning.getText());
        temp.put(adding.getText(), tmp);
        testing.map = temp;
        testing.SaveFile();
        System.out.println("Done!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dialog.setDialogContainer(root);

        declineButton.setOnAction(actionEvent -> {
            dialog.close();
        });

        acceptButton.setOnAction(actionEvent -> {
            try {
                addWord();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            dialog.close();
        });
    }
}
