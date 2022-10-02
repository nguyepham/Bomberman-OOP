module com.example.bomman {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;


    opens game.bomman to javafx.fxml;
    exports game.bomman;
    exports game.bomman.inputHandler;
    opens game.bomman.inputHandler to javafx.fxml;
}