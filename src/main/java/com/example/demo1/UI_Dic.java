package com.example.demo1;

import com.jfoenix.controls.*;
import com.voicerss.tts.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;
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
    private Label TheWord;


    Connection connection = null;
    PreparedStatement psInsert = null;

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
        TheWord.setText(select);
        AddToSQL(select, ans);
    }

    public void confirm(ActionEvent event) throws Exception {

        String text = search.getText();
        if(MainUI.testing.FindMeaning(text) != null) {
            String ans = MainUI.testing.FindMeaning(text);
            result.setText(ans);
            TheWord.setText(text);
            AddToSQL(text, ans);
        }
    }

    public void read(ActionEvent event) throws Exception {
        if (Objects.equals(TheWord.getText(), "")) return;
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
        Alert alert = makeAlert("really?", conf, "U sure?", "confirm");
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