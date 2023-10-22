package com.example.demo1;

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.voicerss.tts.*;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class UI_Dic extends SwitchScene implements Initializable {


    @FXML
    private JFXTextField search;
    @FXML
    private JFXListView<String> recommend;
    @FXML
    private TextArea result;
    @FXML
    private Label TheWord;


    Connection connection = null;
    PreparedStatement psInsert = null;

    ObservableList<String> list = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        TreeMap<String, Word> map = MainUI.testing.map;
        System.out.println(map.size());
        search.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String finding = search.getText().trim().toLowerCase();
                for(String i : map.keySet()) {
                    if(i.startsWith(finding)) {
                        list.add(i);
                    }
                    else{}
                }
                recommend.getItems().clear();
                recommend.getItems().addAll(list);
                list.clear();
                if(finding.isEmpty()) {
                    // recommend.getItems().clear();
                }
            }
        });
        recommend.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String select = recommend.getSelectionModel().getSelectedItem();
                String ans = MainUI.testing.FindWord(select);
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
        if(MainUI.testing.FindWord(text) != null) {
            String ans = MainUI.testing.FindWord(text);
            result.setText(ans);
            TheWord.setText(text);
            AddToSQL(text, ans);
        }
    }

    public void read(ActionEvent event) throws Exception {
        if (Objects.equals(search.getText(), "")) return;
        String PATH = "src/main/resources/audio.wav";
        VoiceProvider tts = new VoiceProvider("147452064f4d4c8e9217c6863b45990f");
        VoiceParameters params = new VoiceParameters(TheWord.getText(), Languages.English_UnitedStates);
        params.setCodec(AudioCodec.WAV);
        params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
        params.setBase64(false);
        params.setSSML(false);
        params.setRate(0);
        byte[] voice = tts.speech(params);
        FileOutputStream fos = new FileOutputStream(PATH);
        fos.write(voice, 0, voice.length);
        fos.flush();
        fos.close();
        File audioFile = new File(PATH);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
    }


    public void delete(ActionEvent event) throws Exception {
        if (Objects.equals(TheWord.getText(), "")) return;
        String conf = "Do you really want to delete the word: " + TheWord.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("really?");
        alert.setContentText("U sure?");
        alert.setHeaderText(conf);
        Optional<ButtonType> alertResult = alert.showAndWait();
        if(alertResult.get() != ButtonType.OK) {
            return;
        }
        Deleted();
    }

    public void Deleted() throws Exception {

        String del = TheWord.getText();
        MainUI.testing.map.remove(del);
        MainUI.testing.SaveFile();

        Switch("dict.fxml", MainUI.glob);
    }

    public void AddToSQL (String word, String meaning) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/sqlite.db");
            psInsert = connection.prepareStatement("INSERT INTO history VALUES (?, ?, ?)");
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