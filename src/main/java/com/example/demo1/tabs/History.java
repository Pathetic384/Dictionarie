package com.example.demo1.tabs;

import com.example.demo1.Alerts;
import com.example.demo1.MainUI;
import com.example.demo1.SwitchScene;
import com.jfoenix.controls.JFXTextField;
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
import java.time.LocalDate;
import java.util.*;

public class History extends Alerts implements Initializable {

    @FXML
    private ListView<String> searched;
    @FXML
    private JFXTextField word;
    @FXML
    private TextArea meaning;

    List<String> list = new ArrayList<>();

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
                String adding = time + "|    " + word;
                list.add(adding);
            }
        }
        catch (Exception e) {}
        finally{
            if( resultset!=null ) {try {resultset.close();} catch (Exception e) {}}
            if( connection!=null ) {try {connection.close();} catch (Exception e) {}}}

        Collections.reverse(list);
        searched.getItems().addAll(list);

        searched.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {

                String select = searched.getSelectionModel().getSelectedItem();

                String[] text = select.split("\\|", 3);
                String text1 = text[0].trim();
                String text2 = text[1].trim();
                String ans = "";
                try {
                    Class.forName("org.sqlite.JDBC");
                    connection = DriverManager.getConnection(linky);
                    String prep = "SELECT meaning FROM history WHERE time = '"
                            + text1 + "' AND word = '" + text2 + "';" ;
                    prepare = connection.prepareStatement(prep);
                    resultset = prepare.executeQuery();


                    ans = resultset.getString(1);

                }
                catch (Exception e) {}
                finally{
                    if( resultset!=null ) {try {resultset.close();} catch (Exception e) {}}
                    if( connection!=null ) {try {connection.close();} catch (Exception e) {}}}

                meaning.setText(ans);
                word.setText(text2);

            }
        });

    }
    public static void AddToSQL (String word, String meaning) {
        Connection connect = null;
        PreparedStatement psInsert = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection("jdbc:sqlite:src/main/resources/sqlite.db");
            psInsert = connect.prepareStatement("INSERT INTO history VALUES (?, ?, ?)");
            LocalDate now = LocalDate.now();
            psInsert.setString(1, now.toString());
            psInsert.setString(2, word);
            psInsert.setString(3,meaning);
            psInsert.executeUpdate();
        }
        catch (Exception e) {}
        finally{
            if( psInsert!=null ) {try {psInsert.close();} catch (Exception e) {}}
            if( connect!=null ) {try {connect.close();} catch (Exception e) {}}}
    }

    public void DeleteHistory(ActionEvent event) throws Exception {

        String text = word.getText().trim();

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(linky);
            prepare = connection.prepareStatement("DELETE FROM history WHERE word = ?");
            prepare.setString(1, text);
            prepare.executeUpdate();

        }
        catch (Exception e) {}
        finally{
            if( prepare!=null ) {try {prepare.close();} catch (Exception e) {}}
            if( connection!=null ) {try {connection.close();} catch (Exception e) {}}}
        SwitchScene s = new SwitchScene();
        s.Switch("history.fxml", MainUI.glob);
    }

    public void ClearHistory(ActionEvent event) throws Exception {
        Alert alert = makeAlert( "U sure?"
                , "  Do u really want to clear the searching history?", "confirm");
        Optional<ButtonType> alertResult = alert.showAndWait();
        if(alertResult.get() != ButtonType.OK) {
            return;
        }
        Cleared();
    }

    public void Cleared() throws Exception {


        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(linky);
            prepare = connection.prepareStatement("DELETE FROM history");
            prepare.executeUpdate();
        }
        catch (Exception e) {}
        finally{
            if( prepare!=null ) {try {prepare.close();} catch (Exception e) {}}
            if( connection!=null ) {try {connection.close();} catch (Exception e) {}}}
        SwitchScene s = new SwitchScene();
        s.Switch("history.fxml", MainUI.glob);
    }

}
