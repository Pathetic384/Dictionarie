package com.example.demo1.tabs;

import com.example.demo1.MainUI;
import com.example.demo1.SwitchScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class Game3 implements Initializable {

    @FXML
    private Pane e1;
    @FXML
    private Pane e2;
    @FXML
    private Pane e3;
    @FXML
    private Pane e4;
    @FXML
    private Pane e5;
    @FXML
    private Pane e6;
    @FXML
    private Pane e7;
    @FXML
    private Text text;
    @FXML
    private Pane butts;
    @FXML
    private Label yay;
    private int right;
    private String word;
    private String setText;
    private String filePath1 = "/Users/phamngocthachha/Documents/GitHub/Dictionarie/src/main/resources/hapi.wav";
    private Sound hapi = new Sound(filePath1);
    private String filePath2 = "/Users/phamngocthachha/Documents/GitHub/Dictionarie/src/main/resources/cry.wav";
    private Sound cry = new Sound(filePath2);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        e1.setVisible(false);
        e2.setVisible(false);
        e3.setVisible(false);
        e4.setVisible(false);
        e7.setVisible(false);
        text.setText("");


        right = 0;


        word = getRandomWord();


        System.out.println(word);
        word = word.toUpperCase();
        setText = "";


        Random rand = new Random();
        int k = rand.nextInt(word.length() - 1);


        for(int i=0;i< word.length()*2;i++) {
            if(i % 2 == 0){

                if(k == ((double) i / 2) - 1) {
                    setText += "_";
                    setText += " ";
                } else {
                    setText += word.charAt(i/2);
                    setText += " ";
                }
            }
        }
        text.setText(setText);
    }


    public String getRandomWord(){
        Random random = new Random();
        int i = random.nextInt(MainUI.words.size());
        String word = MainUI.words.get(i);
        return word;
    }

    public void clicked(ActionEvent event) throws Exception {
        int k = 0;
        for(int i = 0; i < word.length() * 2; i++) {
            if(i % 2 == 0) {
                if (setText.charAt(i) == '_') {
                    k = i / 2;
                }
            }
        }

        ((Button) event.getSource()).setDisable(true);
        String letter = ((Button)event.getSource()).getText();
        if(word.charAt(k) == letter.charAt(0)) {
            int i = word.indexOf(letter);
            if (i != -1) {
                String tmp = setText.substring(0, k * 2) + letter + setText.substring(k * 2 + 1);
                setText = tmp;
                text.setText(setText);
                right++;
            }
            if (right == 1) {
                e7.setVisible(true);
                e4.setVisible(true);
                e2.setVisible(true);
                yay.setText("Congratulations ,':/ ");
                hapi.loop();
            }
        }
        else {
            e1.setVisible(true);
            e3.setVisible(true);
            butts.setDisable(true);
            yay.setText("u stpid ? ,':/ ");
            cry.loop();
        }
    }


    public void Replay(ActionEvent event) throws Exception {
        SwitchScene s = new SwitchScene();
        s.Switch("game3.fxml", MainUI.glob);
        hapi.stop();
        hapi.running = false;
        hapi.looping = false;
        hapi.pause();

        cry.stop();
        cry.running = false;
        cry.looping = false;
        cry.pause();
    }

    public void Back(ActionEvent event) throws Exception {
        SwitchScene s = new SwitchScene();
        s.Switch("selectgame.fxml", MainUI.glob);

        hapi.stop();
        hapi.running = false;
        hapi.looping = false;
        hapi.pause();

        cry.stop();
        cry.running = false;
        cry.looping = false;
        cry.pause();
    }

}
