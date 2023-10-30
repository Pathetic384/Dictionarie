package com.example.demo1;

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
        String ans = translate(language1, language2, text);
        History.AddToSQL(text,ans);
        text2.setText(ans);
    }

    public String translate(String language1, String language2, String text) throws Exception {
        // INSERT YOU URL HERE
        //https://script.google.com/macros/s/AKfycbxImVKCoyddpnSJGhq8RL_J0kkwlNXx90GY0yE_IsB835G1_BWdQS9vsuX14zOgRyXB/exec
        String urlStr = "https://script.google.com/macros/s/AKfycbxImVKCoyddpnSJGhq8RL_J0kkwlNXx90GY0yE_IsB835G1_BWdQS9vsuX14zOgRyXB/exec" +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + language2 +
                "&source=" + language1;
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
}
