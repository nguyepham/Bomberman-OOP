module game.bomman {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;
    requires javafx.media;


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
    exports game.bomman.entity.immobileEntity;
    opens game.bomman.entity.immobileEntity to javafx.fxml;
    exports game.bomman.gameState;
    opens game.bomman.gameState to javafx.fxml;
    exports game.bomman.gameState.scores;
    opens game.bomman.gameState.scores to javafx.fxml;
    exports game.bomman.entity.character.enemy;
    opens game.bomman.entity.character.enemy to javafx.fxml;
    exports game.bomman.entity.character.enemy.firstTypeOfMoving;
    opens game.bomman.entity.character.enemy.firstTypeOfMoving to javafx.fxml;
    exports game.bomman.entity.character.enemy.secondTypeOfMoving;
    opens game.bomman.entity.character.enemy.secondTypeOfMoving to javafx.fxml;
    exports game.bomman.entity.character.enemy.thirdTypeOfMoving;
    opens game.bomman.entity.character.enemy.thirdTypeOfMoving to javafx.fxml;
    exports game.bomman.entity.character.enemy.fourthTypeOfMoving;
    opens game.bomman.entity.character.enemy.fourthTypeOfMoving to javafx.fxml;
}