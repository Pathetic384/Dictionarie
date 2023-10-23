package com.example.demo1;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;

public abstract class Alerts {

    public Alert makeAlert(String title, String content, String header, String type) {
        Alert alert = null;
        if(type.equals("confirm")) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
        }
        else if(type.equals("error")) {
            alert = new Alert(Alert.AlertType.ERROR);
        }
        else if(type.equals("warning")) {
            alert = new Alert(Alert.AlertType.WARNING);
        }

        alert.setTitle(title);
        alert.setContentText(content);
        alert.setHeaderText(header);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/alerts.css").toString());
        dialogPane.getStyleClass().add("dialog-pane");

        return alert;
    }
}
