package com.example.demo1.tabs;
import com.example.demo1.MainUI;
import com.example.demo1.SwitchScene;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.*;

public class Game2 implements Initializable {
    @FXML
    private JFXTextField scrambled;
    @FXML
    private JFXTextArea meaning;
    @FXML
    private JFXTextField ans;
    @FXML
    private ImageView img;
    @FXML
    private ImageView img1;
    @FXML
    private JFXButton enter;
    @FXML
    private ImageView e1;
    @FXML
    private ImageView e2;
    @FXML
    private ImageView e3;
    @FXML
    private Label res;
    private String word;
    private int error;

    public String getRandomWord(){
        Random random = new Random();
        int i = random.nextInt(MainUI.words.size());
        String word = MainUI.words.get(i);
        return word;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        word = getRandomWord();
        System.out.println(word);
        error = 3;

        int n = word.length();
        List<Character> v = new ArrayList<Character>();
        for(int i=0;i<n;i++) v.add(word.charAt(i));

        String scram = "";
        while(n!=0) {
            Random rand = new Random();
            int i = rand.nextInt(n);
            scram = scram + v.get(i).toString().toUpperCase() + " | ";
            v.remove(i);
            n--;
        }
        scram = scram.substring(0, scram.length() - 2);
        scrambled.setText(scram);
    }
    public void Check(ActionEvent event) throws Exception {
        if(Objects.equals(ans.getText(), "")) return;
        if(ans.getText().trim().equals(word)) {
            Image im = new Image(new File("src/main/resources/picss/happy.gif").toURI().toString());
            img.setImage(im);
            img1.setImage(im);
            enter.setDisable(true);
            res.setText("CONGRATULATION!! :DDD");
        }
        else error--;
        if(error == 2) e3.setVisible(false);
        if(error == 1) {
            e2.setVisible(false);
            String wordle = MainUI.testing.FindMeaning(word.toLowerCase());
            String word = "";
            int count = 0;
            Scanner scanner = new Scanner(wordle);
            while (scanner.hasNextLine() && count != 3) {
                String line = scanner.nextLine();
                word += line + '\n';
                count++;
            }
            scanner.close();
            meaning.setText(word);
        }
        if(error == 0) {
            e1.setVisible(false);
            Image im = new Image(new File("src/main/resources/picss/sad.gif").toURI().toString());
            img.setImage(im);
            img1.setImage(im);
            enter.setDisable(true);
            String result = "The word was: " + word;
            res.setText(result);
        }
    }
    public void Replay(ActionEvent event) throws Exception {
        SwitchScene s = new SwitchScene();
        s.Switch("game2.fxml", MainUI.glob);
    }
    public void Back(ActionEvent event) throws Exception {
        SwitchScene s = new SwitchScene();
        s.Switch("selectgame.fxml", MainUI.glob);
    }
}
