package com.example.demo1.tabs;

import com.example.demo1.MainUI;
import com.example.demo1.SwitchScene;
import javafx.event.ActionEvent;

public class SelectGame {
    public void Game1(ActionEvent event) throws Exception {
        SwitchScene s = new SwitchScene();
        s.Switch("game2.fxml", MainUI.glob);
    }
    public void Game2(ActionEvent event) throws Exception {
        SwitchScene s = new SwitchScene();
        s.Switch("game.fxml", MainUI.glob);
    }
    public void Game3(ActionEvent event) throws Exception {
        SwitchScene s = new SwitchScene();
        s.Switch("game2.fxml", MainUI.glob);
    }
}
