module com.example.bomman {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.bomman to javafx.fxml;
    exports com.example.bomman;
}