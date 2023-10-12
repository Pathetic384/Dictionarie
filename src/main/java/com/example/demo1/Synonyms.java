package com.example.demo1;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.WordNetDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class Synonyms {
    @FXML
    Label label1;
    @FXML
    Label label2;
    @FXML
    TextArea text1;
    @FXML
    TextArea text2;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void Enter(ActionEvent event) throws Exception {
        String text = text1.getText();
        String ans = Syn(text);
        text2.setText(ans);
    }

    public void Back(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("dict.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public String Syn(String text) {
        //  Get the synsets containing the word form=capicity
        String res = "";

        File f = new File("/Users/phamngocthachha/Downloads/WordNet-3.0/dict/");
        System.setProperty("wordnet.database.dir", f.toString());
        //setting path for the WordNet Directory

        WordNetDatabase database = WordNetDatabase.getFileInstance();
        Synset[] synsets = database.getSynsets(text);
        //  Display the word forms and definitions for synsets retrieved

        if (synsets.length > 0) {
            ArrayList<String> al = new ArrayList<String>();
            // add elements to al, including duplicates
            HashSet hs = new HashSet();
            for (int i = 0; i < synsets.length; i++) {
                String[] wordForms = synsets[i].getWordForms();
                for (int j = 0; j < wordForms.length; j++) {
                    al.add(wordForms[j]);
                }

                //removing duplicates
                hs.addAll(al);
                al.clear();
                al.addAll(hs);
            }

            //showing all synsets
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