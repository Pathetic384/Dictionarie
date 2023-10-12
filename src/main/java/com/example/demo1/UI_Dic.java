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

public class UI_Dic implements Initializable {

    @FXML
    private JFXHamburger hamburger;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXTextField search;
    @FXML
    private JFXListView<String> recommend;
    @FXML
    private TextArea result;
    @FXML
    private Label TheWord;
    @FXML
    private StackPane root;
    @FXML
    private JFXDialog dialog;
    @FXML
    private JFXButton acceptButton;
    @FXML
    private JFXButton declineButton;
    private ActionEvent event;
    @FXML
    private Button butt;

    Connection connection = null;
    PreparedStatement psInsert = null;

    Dictionary testing = new Dictionary();
    ObservableList<String> list = FXCollections.observableArrayList();

    private final String path = "src/main/resources/dictest.txt";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        VBox box = null;
        try {testing.LoadFile(path);
                box = FXMLLoader.load(getClass().getResource("menu.fxml"));}
        catch (Exception e) {}
        TreeMap<String, Word> map = testing.map;
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
                String ans = testing.FindWord(select);
                result.setText(ans);
                TheWord.setText(select);
                if(select != null) {
                    AddToSQL(select, ans);
                }
            }
        });

        dialog.setDialogContainer(root);
        declineButton.setOnAction(actionEvent -> {
            dialog.close();
        });
        acceptButton.setOnAction(actionEvent -> {
            try {
                Deleted();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            dialog.close();
        });

        drawer.setSidePane(box);
            for (Node node : box.getChildren()) {
                if (node.getAccessibleText() != null) {
                    node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                        switch (node.getAccessibleText()) {
                            case "game":
                                try { SwitchScene s = new SwitchScene("game.fxml", e);}
                                catch (Exception ex) {}
                                break;
                            case "history":
                                try { SwitchScene s = new SwitchScene("history.fxml", e);}
                                catch (Exception ex) {}
                                break;
                            case "newword":
                                try { SwitchScene s = new SwitchScene("addword.fxml", e);}
                                catch (Exception ex) {}
                                break;
                            case "synonyms":
                                try { SwitchScene s = new SwitchScene("synonyms.fxml", e);}
                                catch (Exception ex) {}
                                break;
                            case "googletranslate":
                                try { SwitchScene s = new SwitchScene("googletranslate.fxml", e);}
                                catch (Exception ex) {}
                                break;
                        }
                    });
                }
            }
        HamburgerSlideCloseTransition closing = new HamburgerSlideCloseTransition(hamburger);
        closing.setRate(-1);
        drawer.setDisable(true);
        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            closing.setRate(closing.getRate()*-1);
            closing.play();
            if (drawer.isShown())
            {
                drawer.close();
                drawer.setDisable(true);
            }
            else
            {
                drawer.open();
                drawer.setDisable(false);
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

    public void Exit(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void delete(ActionEvent event) throws Exception {
        if (Objects.equals(search.getText(), "")) return;
        dialog.show();
        this.event = event;
    }
    public void Deleted() throws Exception {
        String del = TheWord.getText();
        Word word = new Word(del, result.getText());
        testing.map.remove(del);
        testing.SaveFile();
        SwitchScene s = new SwitchScene("dict.fxml", event);
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