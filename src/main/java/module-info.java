module game.bomman {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;


    opens game.bomman to javafx.fxml;
    exports game.bomman;
    exports game.bomman.inputHandler;
    opens game.bomman.inputHandler to javafx.fxml;
    exports game.bomman.inputHandler.command;
    opens game.bomman.inputHandler.command to javafx.fxml;
    exports game.bomman.map;
    opens game.bomman.map to javafx.fxml;
    exports game.bomman.entity;
    opens game.bomman.entity to javafx.fxml;
}