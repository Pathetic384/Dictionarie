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
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ResourceBundle;

public class GTranslate implements Initializable {

    @FXML
    Label label1;
    @FXML
    Label label2;
    @FXML
    TextArea text1;
    @FXML
    TextArea text2;

    String language1 = "en";
    String language2 = "vi";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        label1.setText("EN");
        label2.setText("VN");
    }

    public void Back(ActionEvent event) throws Exception {
        SwitchScene s = new SwitchScene("dict.fxml", event);
    }

    public void Change(ActionEvent event) throws Exception {
        String tmp = language1;
        language1 = language2;
        language2 = tmp;
        if (language1 == "vi") {
            label1.setText("VN");
            label2.setText("EN");
        }
        else {
            label1.setText("EN");
            label2.setText("VN");
        }
    }

    public void Enter(ActionEvent event) throws Exception {
        String text = text1.getText();
        String ans = translate(language1, language2, text);
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
