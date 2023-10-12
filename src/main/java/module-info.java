module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires freetts;
    requires java.sql;
    requires jaws;
    requires com.jfoenix;


    opens com.example.demo1 to javafx.fxml;
    exports com.example.demo1;
}