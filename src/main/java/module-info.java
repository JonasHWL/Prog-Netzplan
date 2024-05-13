module com.example.line {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.line to javafx.fxml;
    exports com.example.line;
}