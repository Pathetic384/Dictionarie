package com.example.demo1;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

public class UI_Dic implements Initializable {

    @FXML
    private TextField search;
    @FXML
    private ListView<String> recommend;
    @FXML
    private TextArea result;
    @FXML
    private Button enter;


    private Stage stage;
    private Scene scene;
    private Parent root;

    Dictionary testing = new Dictionary();
    ObservableList<String> list = FXCollections.observableArrayList();

    private final String path = "src\\main\\resources\\dictest.txt";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        try {testing.LoadFile(path);}
        catch (Exception e) {}

        TreeMap<String, Word> map = testing.map;
        System.out.println(map.size());

        search.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String finding = search.getText().trim();
               // System.out.println(finding);

                for(String i : map.keySet()) {
                    if(i.startsWith(finding)) {
                        //System.out.println("found");
                        list.add(i);

                    }
                    else{
                        //list.clear();
                    }
                }

                recommend.getItems().clear();
                recommend.getItems().addAll(list);
                list.clear();

                if(finding.isEmpty()) {
                    recommend.getItems().clear();
                }

            }
        });

        recommend.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String select = recommend.getSelectionModel().getSelectedItem();
                String ans = testing.FindWord(select);
                result.setText(ans);
            }
        });

    }


    public void confirm(ActionEvent event) throws Exception {

        String text = search.getText();
        String ans = testing.FindWord(text);
        result.setText(ans);

    }

    public void newWord(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("addword.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
