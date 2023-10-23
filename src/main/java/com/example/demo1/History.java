package com.example.demo1;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class History extends SwitchScene implements Initializable {

    @FXML
    private ListView<String> searched;
    @FXML
    private Label word;
    @FXML
    private TextArea meaning;

    ObservableList<String> list = FXCollections.observableArrayList();

    Connection connection = null;
    PreparedStatement prepare = null;
    ResultSet resultset = null;


    public final String linky = "jdbc:sqlite:src/main/resources/sqlite.db";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){


        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(linky);
            prepare = connection.prepareStatement("SELECT * FROM history");
            resultset = prepare.executeQuery();

            while (resultset.next()) {
                String time = resultset.getString(1);
                String word = resultset.getString(2);
                String meaning = resultset.getString(3);
                String adding = time + "|    " + word;
                list.add(adding);
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
                String ans = MainUI.testing.FindMeaning(text2);
                meaning.setText(ans);
                word.setText(text2);

            }
        });

    }


    public void DeleteHistory(ActionEvent event) throws Exception {

        String text = word.getText();

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(linky);
            prepare = connection.prepareStatement("DELETE FROM history WHERE word = ?");
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

        Switch("history.fxml", MainUI.glob);
    }

    public void ClearHistory(ActionEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("really?");
        alert.setContentText("U sure?");
        alert.setHeaderText("Do you really want to clear the searching history?");
        Optional<ButtonType> alertResult = alert.showAndWait();
        if(alertResult.get() != ButtonType.OK) {
            return;
        }
        Cleared();
    }

    public void Cleared() throws Exception {

        String text = word.getText();

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(linky);
            prepare = connection.prepareStatement("DELETE FROM history");
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

        Switch("history.fxml", MainUI.glob);
    }

}
