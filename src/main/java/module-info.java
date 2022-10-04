module com.example.bomman {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;


    opens game.application to javafx.fxml;
    exports game.application;
}