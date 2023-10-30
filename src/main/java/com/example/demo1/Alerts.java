package com.example.demo1;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.stage.StageStyle;

public abstract class Alerts {
    private double x = 0;
    private double y = 0;

    public Alert makeAlert(String content, String header, String type) {
        Alert alert;
        if(type.equals("confirm")) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
        }
        else if(type.equals("error")) {
            alert = new Alert(Alert.AlertType.ERROR);
        }
        else if(type.equals("warning")) {
            alert = new Alert(Alert.AlertType.WARNING);
        } else {
            alert = null;
        }

        alert.setContentText(content);
        alert.setHeaderText(header);
        alert.initStyle(StageStyle.TRANSPARENT);
        ImageView icon = new ImageView(new Image(String.valueOf(this.getClass().getResource("/picss/rock.png"))));
        icon.setFitHeight(60);
        icon.setFitWidth(60);
        alert.getDialogPane().setGraphic(icon);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        Parent root = alert.getDialogPane();
        root.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getY();
        });
        root.setOnMouseDragged(mouseEvent -> {
            alert.setX(mouseEvent.getScreenX() - x);
            alert.setY(mouseEvent.getScreenY() - y);
        });

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/alerts.css").toString());
        if(!type.equals("error")) {
            dialogPane.getStyleClass().add("dialog-pane");
        }
        else dialogPane.getStyleClass().add("error-pane");

        return alert;
    }

}
