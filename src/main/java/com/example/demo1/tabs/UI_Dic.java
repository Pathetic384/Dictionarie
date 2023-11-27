package com.example.demo1.tabs;

import com.example.demo1.Alerts;
import com.example.demo1.MainUI;
import com.example.demo1.SwitchScene;
import com.example.demo1.helpers.UIDicHelper;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class UI_Dic extends Alerts implements Initializable {

    @FXML
    private JFXTextField search;
    @FXML
    private ListView<String> recommend;
    @FXML
    private TextArea result;
    @FXML
    private TextArea synonyms;
    @FXML
    private Label TheWord;

    private UIDicHelper helper;

    ObservableList<String> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

        search.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String finding = search.getText().trim().toLowerCase();

                list = MainUI.testing.advanceSearch(finding);
                recommend.getItems().clear();
                recommend.getItems().addAll(list);
                list.clear();
                if(finding.isEmpty()) {
                     recommend.getItems().clear();
                }
            }
        });
        recommend.getSelectionModel().selectedItemProperty().addListener(this::selectionChanged);

    }

    private void selectionChanged(ObservableValue<? extends String> Observable, String oldVal, String newVal){
        String select = recommend.getSelectionModel().getSelectedItem();
        if(select == null) return;
        String ans = MainUI.testing.FindMeaning(select);
        result.setText(ans);
        synonyms.setText("  Synonyms:  " + Synonyms.Syn(select));
        TheWord.setText(select);
        History.AddToSQL(select, ans);
    }

//    public void confirm(ActionEvent event) throws Exception {
//
//        String text = search.getText();
//        if(MainUI.testing.FindMeaning(text) != null) {
//            String ans = MainUI.testing.FindMeaning(text);
//            result.setText(ans);
//            TheWord.setText(text);
//            History.AddToSQL(text, ans)
//        }
//    }

    public void read(ActionEvent event) throws Exception {
        if (Objects.equals(TheWord.getText(), "")) return;

        helper = new UIDicHelper(TheWord.getText(),"en");

        helper.valueProperty().addListener((observable, oldValue, newValue) -> newValue.start());

        Thread th = new Thread(helper);
        th.start();
    }


    public void delete(ActionEvent event) throws Exception {
        if (Objects.equals(TheWord.getText(), "")) return;
        String conf = "Do you really want to delete the word: " + TheWord.getText();
        Alert alert = makeAlert( conf, "  U sure?", "confirm");
        Optional<ButtonType> alertResult = alert.showAndWait();
        if(alertResult.get() != ButtonType.OK) {
            return;
        }
        Deleted();
    }

    public void Deleted() throws Exception {

        String del = TheWord.getText();
        MainUI.testing.DeleteWord(del);
        MainUI.testing.SaveFile();

        SwitchScene s = new SwitchScene();
        s.Switch("dict.fxml", MainUI.glob);
    }


}