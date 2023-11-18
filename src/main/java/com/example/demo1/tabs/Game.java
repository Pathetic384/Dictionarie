package com.example.demo1.tabs;

import com.example.demo1.MainUI;
import com.example.demo1.SwitchScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import java.io.File;
import java.net.URL;
import java.util.*;

public class Game implements Initializable {

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
    private ImageView ded;
    @FXML
    private ImageView rip;
    @FXML
    private ImageView norip;
    @FXML
    private Text text;
    @FXML
    private TextArea meaning;
    @FXML
    private Pane butts;
    @FXML
    private Label yay;


    private int right;
    private int wrong;
    private String word;
    private String setText;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        e1.setVisible(false);
        e2.setVisible(false);
        e3.setVisible(false);
        e4.setVisible(false);
        e5.setVisible(false);
        e6.setVisible(false);
        e7.setVisible(false);
        ded.setVisible(false);
        text.setText("");

        right = 0;
        wrong = 0;

        word = getRandomWord();

        System.out.println(word);
        word = word.toUpperCase();
        setText = "";

        for(int i=0;i< word.length()*2;i++) {
            if(i%2==0) setText += "_";
            else setText += " ";
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

        ((Button) event.getSource()).setDisable(true);
        String letter = ((Button)event.getSource()).getText();
        if(word.contains(letter)) {
            int i = word.indexOf(letter);
            while (i != -1) {
                String tmp = setText.substring(0, i * 2) + letter + setText.substring(i * 2 + 1);
                setText = tmp;
                text.setText(setText);
                i = word.indexOf(letter, i+1);
                right++;
            }
            if (right == word.length()) {
                yay.setText("CONGRATULATION :DDD");
                butts.setDisable(true);
                norip.setVisible(true);
            }
        }
        else {
            wrong++;
            if(wrong == 1) e1.setVisible(true);
            else if(wrong ==2) e2.setVisible(true);
            else if(wrong ==3) e3.setVisible(true);
            else if(wrong ==4)
            {
                e4.setVisible(true);
                meaning.setText(MainUI.testing.FindMeaning(word.toLowerCase()));
            }
            else if(wrong ==5) e5.setVisible(true);
            else if(wrong ==6) e6.setVisible(true);
            else if(wrong ==7) {
                e7.setVisible(true);
                butts.setDisable(true);
                ded.setVisible(true);
                rip.setVisible(true);
                yay.setText("u stpid ? ,':/ ");
            }
        }
    }

        if(hint.isSelected()) {
            String wordie = MainUI.testing.FindMeaning(word.toLowerCase());
            String word = "";
            int count = 0;
            Scanner scanner = new Scanner(wordie);
            while (scanner.hasNextLine() && count != 3) {
                String line = scanner.nextLine();
                word += line + '\n';
                count++;
            }
            scanner.close();
            meaning.setText(word);
        }
        else {
            meaning.setText("Need a hint?");
        }


    public void Replay(ActionEvent event) throws Exception {
        SwitchScene s = new SwitchScene();
        s.Switch("game.fxml", MainUI.glob);
    }

}
