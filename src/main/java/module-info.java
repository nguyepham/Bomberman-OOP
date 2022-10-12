module game.bomman {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;


    opens game.bomman to javafx.fxml;
    exports game.bomman;
    exports game.bomman.component;
    opens game.bomman.component to javafx.fxml;
    exports game.bomman.command;
    opens game.bomman.command to javafx.fxml;
    exports game.bomman.map;
    opens game.bomman.map to javafx.fxml;
    exports game.bomman.entity;
    opens game.bomman.entity to javafx.fxml;
    exports game.bomman.command.movingCommand;
    opens game.bomman.command.movingCommand to javafx.fxml;
}