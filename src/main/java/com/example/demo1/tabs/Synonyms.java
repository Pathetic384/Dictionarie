package com.example.demo1.tabs;

import com.jfoenix.controls.JFXTextField;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.WordNetDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class Synonyms{

    @FXML
    JFXTextField text1;
    @FXML
    TextArea text2;


    public void Enter(ActionEvent event) throws Exception {
        String text = text1.getText();
        String ans = Syn(text);
        text2.setText(ans);
    }


    public String Syn(String text) {

        String res = "";

        File f = new File("src/main/resources/dict");
        System.setProperty("wordnet.database.dir", f.toString());


        WordNetDatabase database = WordNetDatabase.getFileInstance();
        Synset[] synsets = database.getSynsets(text);


        if (synsets.length > 0) {
            ArrayList<String> al = new ArrayList<String>();

            HashSet hs = new HashSet();
            for (int i = 0; i < synsets.length; i++) {
                String[] wordForms = synsets[i].getWordForms();
                for (int j = 0; j < wordForms.length; j++) {
                    al.add(wordForms[j]);
                }


                hs.addAll(al);
                al.clear();
                al.addAll(hs);
            }


            for (int j = 0; j < al.size(); j++) {
                res += al.get(j);
                res += '\n';
            }

        } else {
            res = "No synonyms for this word";
        }
        return res;
    }
}