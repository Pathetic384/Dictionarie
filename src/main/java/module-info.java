module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires freetts;
    requires java.sql;
    requires jaws;
    requires com.jfoenix;
    requires java.desktop;
    requires voicerss.tts;
    requires sqlite.jdbc;


    opens com.example.demo1 to javafx.fxml;
    opens com.example.demo1.tabs to javafx.fxml;
    exports com.example.demo1;

}