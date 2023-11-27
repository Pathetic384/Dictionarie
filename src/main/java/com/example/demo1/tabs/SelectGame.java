package com.example.demo1.tabs;

import com.example.demo1.MainUI;
import com.example.demo1.SwitchScene;
import com.example.demo1.Sound;
import javafx.event.ActionEvent;

public class SelectGame {
    public String word;
    public final String filePath1 = "src/main/resources/hapi.wav";
    public final Sound hapi = new Sound(filePath1);
    public final String filePath2 = "src/main/resources/cry.wav";
    public final Sound cry = new Sound(filePath2);


    private void Games(String path) throws Exception{
        MainUI.draw.close();
        MainUI.draw.setDisable(true);
        MainUI.ham.setDisable(true);
        SwitchScene s = new SwitchScene();
        s.Switch(path, MainUI.glob);
    }
    public void Game1(ActionEvent event) throws Exception {
        Games("game3.fxml");
    }

    public void Game2(ActionEvent event) throws Exception {
        Games("game.fxml");
    }
    public void Game3(ActionEvent event) throws Exception {
        Games("game2.fxml");
    }

    public static void Back(Sound hapi, Sound cry, String path) throws Exception {
        SwitchScene s = new SwitchScene();
        s.Switch(path, MainUI.glob);

        hapi.stop();
        hapi.running = false;
        hapi.looping = false;
        hapi.pause();
        cry.stop();
        cry.running = false;
        cry.looping = false;
        cry.pause();
    }

}
