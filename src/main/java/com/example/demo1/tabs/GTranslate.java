package com.example.demo1.tabs;


import com.example.demo1.helpers.GtransHelper;
import com.example.demo1.helpers.UIDicHelper;
import com.example.demo1.Loading;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class GTranslate implements Initializable {

    @FXML
    ImageView im1;
    @FXML
    ImageView im2;
    @FXML
    TextArea text1;
    @FXML
    TextArea text2;
    Image engIm;
    Image vietIm;

    @FXML
    private Pane load;

    private GtransHelper helper;
    private UIDicHelper speak_helper;

    String language1 = "en";
    String language2 = "vi";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        load.setVisible(false);
        InputStream stream1, stream2;
        try {
            stream1 = new FileInputStream("src/main/resources/picss/eng.png");
            stream2 = new FileInputStream("src/main/resources/picss/viet.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        engIm = new Image(stream1);
        vietIm = new Image(stream2);
    }

    public void Change(ActionEvent event) throws Exception {
        String tmp = language1;
        language1 = language2;
        language2 = tmp;
        if (Objects.equals(language1, "vi")) {
            im1.setImage(vietIm);
            im2.setImage(engIm);
        }
        else {
            im1.setImage(engIm);
            im2.setImage(vietIm);
        }
    }

    public void Enter(ActionEvent event) throws Exception {
        if(text1.getText().trim().isEmpty()) return;
        String text = text1.getText();

        helper = new GtransHelper(text, language1, language2);
        helper.valueProperty().addListener((observable, oldValue, newValue) -> text2.setText(String.valueOf(newValue)));
        helper.valueProperty().addListener((observable, oldValue, newValue) ->  History.AddToSQL(text,String.valueOf(newValue)));

        Thread th = new Thread(helper);

        th.start();

        Loading loader = new Loading();
        loader.run();

    }

    private void Speak(TextArea text1, String language1) {
        if(Objects.equals(text1.getText(), "")) return;
        String text = text1.getText();

        speak_helper = new UIDicHelper(text, language1);
        speak_helper.valueProperty().addListener((observable, oldValue, newValue) -> newValue.start());

        Thread th = new Thread(speak_helper);
        th.start();
    }

    public void Speak1(ActionEvent event) throws Exception {
        Speak(text1, language1);
    }

    public void Speak2(ActionEvent event) throws Exception {
        Speak(text2, language2);
    }



}
