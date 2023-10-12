package com.example.demo1;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
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
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class UI_Dic implements Initializable {

    private double x = 0;
    private double y = 0;
    @FXML
    private TextField search;
    @FXML
    private ListView<String> recommend;
    @FXML
    private TextArea result;
    @FXML
    private Button enter;
    @FXML
    private Label TheWord;

    private Stage stage;
    private Scene scene;

    Connection connection = null;
    PreparedStatement psInsert = null;

    Dictionary testing = new Dictionary();
    ObservableList<String> list = FXCollections.observableArrayList();

    private final String path = "src/main/resources/dictest.txt";

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

                    }
                }

                recommend.getItems().clear();
                recommend.getItems().addAll(list);
                list.clear();

                if(finding.isEmpty()) {
                    //recommend.getItems().clear();
                }

            }
        });

        recommend.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String select = recommend.getSelectionModel().getSelectedItem();
                String ans = testing.FindWord(select);
                result.setText(ans);
                TheWord.setText(select);
                if(select != null) {
                    AddToSQL(select, ans);
                }
            }
        });

    }

    public void confirm(ActionEvent event) throws Exception {

        String text = search.getText();
        if(testing.FindWord(text) != null) {
            String ans = testing.FindWord(text);
            result.setText(ans);
            TheWord.setText(text);
            AddToSQL(text, ans);
        }
    }

    public void synonyms(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("synonyms.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void read(ActionEvent event) throws Exception {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        Voice voice = VoiceManager.getInstance().getVoice("kevin16");
        if (!Objects.equals(TheWord.getText(), "")) {
            if (voice != null) {
                voice.allocate();
                voice.speak(TheWord.getText());
            } else throw new IllegalStateException("Cannot find voice: kevin16");
        }
    }

    public void newWord(ActionEvent event) throws Exception {
        SwitchScene s = new SwitchScene("addword.fxml", event);
    }

    public void Gtranslate(ActionEvent event) throws Exception {
        SwitchScene s = new SwitchScene("googletranslate.fxml", event);
    }

    public void ShowHistory(ActionEvent event) throws Exception {
        SwitchScene s = new SwitchScene("history.fxml", event);
    }

    public void ShowGame(ActionEvent event) throws Exception {
        SwitchScene s = new SwitchScene("game.fxml", event);
    }

    public void Exit(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void delete(ActionEvent event) throws Exception {
        if(Objects.equals(TheWord.getText(), "")) return;
        String conf = "Do you really want to delete the word: " + TheWord.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("really?");
        alert.setContentText("U sure?");
        alert.setHeaderText(conf);
        Optional<ButtonType> alertResult = alert.showAndWait();
        if(alertResult.get() != ButtonType.OK) {
            return;
        }
        String del = TheWord.getText();
        Word word = new Word(del, result.getText());
        testing.map.remove(del);

        testing.SaveFile();

        SwitchScene s = new SwitchScene("dict.fxml", event);
    }

    public void AddToSQL (String word, String meaning) {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/history", "root", "papcusun");
            psInsert = connection.prepareStatement("INSERT INTO save (time, word, meaning) VALUES (?, ?, ?)");
            LocalDate now = LocalDate.now();
            psInsert.setString(1, now.toString());
            psInsert.setString(2, word);
            psInsert.setString(3,meaning);
            psInsert.executeUpdate();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally{
            if( psInsert!=null ) {
                try {
                    psInsert.close();
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
    }
}