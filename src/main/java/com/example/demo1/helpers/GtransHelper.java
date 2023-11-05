package com.example.demo1.helpers;

import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class GtransHelper extends Task<String> {
    public GtransHelper(String text, String language1, String language2) {
        this.language1 = language1;
        this.language2 = language2;
        this.text = text;
    }

    private String text;
    private String language1;
    private String language2;

    @Override
    protected String call() throws Exception {
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
