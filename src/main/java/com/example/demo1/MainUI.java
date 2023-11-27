package com.example.demo1;

import com.example.demo1.WordsManagement.Dictionary;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MainUI extends SwitchScene implements Initializable {

    @FXML
    private JFXHamburger hamburger;
    @FXML
    private JFXDrawer drawer;
    @FXML
    public AnchorPane root;
    public static AnchorPane glob = new AnchorPane();

    public final static Dictionary testing = new Dictionary();
    private final String path = "src/main/resources/dictest.txt";

    public static ArrayList<String> words = new ArrayList<String>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Scanner sc = null;
        glob = root;
        VBox box = null;
        try {Switch("dict.fxml", root);
            testing.LoadFile(path);
            sc = new Scanner(new File("src/main/resources/game.txt"));
            box = FXMLLoader.load(getClass().getResource("menu.fxml"));
        } catch (Exception e) {}
        while(sc.hasNextLine()) words.add(sc.nextLine());

        HamburgerSlideCloseTransition closing = new HamburgerSlideCloseTransition(hamburger);
        closing.setRate(-1);
        drawer.setDisable(true);
        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            closing.setRate(closing.getRate()*-1);
            closing.play();
            if (drawer.isShown())
            {
                drawer.close();
                drawer.setDisable(true);
            }
            else
            {
                drawer.open();
                drawer.setDisable(false);
            }
        });


        drawer.setSidePane(box);
        for (Node node : box.getChildren()) {
            if (node.getAccessibleText() != null) {
                node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                    switch (node.getAccessibleText()) {
                        case "dict":
                            try {Switch("dict.fxml", root);}
                            catch (Exception ex) {}
                            break;
                        case "game":
                            try {Switch("selectgame.fxml", root);}
                            catch (Exception ex) {}
                            break;
                        case "history":
                            try {Switch("history.fxml", root);}
                            catch (Exception ex) {}
                            break;
                        case "newword":
                            try {Switch("addword.fxml", root);}
                            catch (Exception ex) {}
                            break;
                        case "synonyms":
                            try {Switch("synonyms.fxml", root);}
                            catch (Exception ex) {}
                            break;
                        case "googletranslate":
                            try {Switch("googletranslate.fxml", root);}
                            catch (Exception ex) {}
                            break;
                    }
                    drawer.close();
                    drawer.setDisable(true);
                    closing.setRate(closing.getRate()*-1);
                    closing.play();
                });
            }
        }

    }

    public void Exit(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        stage.close();

        System.exit(0);
    }

}
