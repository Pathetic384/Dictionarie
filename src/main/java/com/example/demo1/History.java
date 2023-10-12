package com.example.demo1;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class History implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ListView<String> searched;
    @FXML
    private Label word;
    @FXML
    private TextArea meaning;

    Dictionary testing = new Dictionary();
    ObservableList<String> list = FXCollections.observableArrayList();

    Connection connection = null;
    PreparedStatement prepare = null;
    ResultSet resultset = null;

    private final String path = "src/main/resources/dictest.txt";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        try {testing.LoadFile(path);}
        catch (Exception e) {}


        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/history", "root", "papcusun");
            prepare = connection.prepareStatement("SELECT * FROM save");
            resultset = prepare.executeQuery();

            while (resultset.next()) {
                String time = resultset.getString(1);
                String word = resultset.getString(2);
                String meaning = resultset.getString(3);
                String adding = time + "|    " + word;
                list.add(adding);
                //System.out.println(time + " " + word + " " + meaning);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally{
            if( resultset!=null ) {
                try {
                    resultset.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if( connection!=null ) {
                try {
                    connection.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        searched.getItems().addAll(list);

        searched.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {

                String select = searched.getSelectionModel().getSelectedItem();
                System.out.println(select);
                String[] text = select.split("\\|", 3);
                String text2 = text[1];
                text2 = text2.trim();
                System.out.println(text2);
                String ans = testing.FindWord(text2);
                meaning.setText(ans);
                word.setText(text2);

            }
        });

    }

    public void Return(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("dict.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void DeleteHistory(ActionEvent event) throws Exception {

        String text = word.getText();

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/history", "root", "papcusun");
            prepare = connection.prepareStatement("DELETE FROM save WHERE word = ?");
            prepare.setString(1, text);
            prepare.executeUpdate();

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally{
            if( resultset!=null ) {
                try {
                    resultset.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if( connection!=null ) {
                try {
                    connection.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        Parent root = FXMLLoader.load(getClass().getResource("history.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
