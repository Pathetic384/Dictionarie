package com.example.demo1.tabs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.ResourceBundle;
import com.example.demo1.helpers.GtransHelper;

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

    private GtransHelper helper;

    String language1 = "en";
    String language2 = "vi";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
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
        th.setDaemon(true);
        th.start();



    }


}
