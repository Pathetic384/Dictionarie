package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import java.io.File;
import java.net.URL;
import java.util.*;

public class Game extends SwitchScene implements Initializable {

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
    private TextArea meaning;
    @FXML
    private Pane butts;
    @FXML
    private Label yay;


    private int right;
    private int wrong;
    private String word;
    private String setText;

    private ArrayList<String> words = new ArrayList<String>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Scanner sc = null;
        try {
            sc = new Scanner(new File("src/main/resources/gamewords.txt"));
        } catch (Exception e) {

        }
        while(sc.hasNextLine()) words.add(sc.nextLine());

        e1.setVisible(false);
        e2.setVisible(false);
        e3.setVisible(false);
        e4.setVisible(false);
        e5.setVisible(false);
        e6.setVisible(false);
        e7.setVisible(false);
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
        int i = random.nextInt(words.size());
        String word = words.get(i);
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
                meaning.setText(MainUI.testing.FindWord(word.toLowerCase()));
            }
            else if(wrong ==5) e5.setVisible(true);
            else if(wrong ==6) e6.setVisible(true);
            else if(wrong ==7) {
                e7.setVisible(true);
                butts.setDisable(true);
                yay.setText("u stpid ? ,':/ ");
            }
        }
    }



    public void Replay(ActionEvent event) throws Exception {
        Switch("game.fxml", MainUI.glob);
    }

}
