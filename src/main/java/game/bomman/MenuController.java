package game.bomman;

import game.bomman.component.SoundPlayer;
import game.bomman.gameState.InstructionScene;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class MenuController {
    @FXML
    void startGameClicked() throws FileNotFoundException {
        Game.init(MainApplication.stage);
        SoundPlayer.playGameStartSound();
        Game.run();
    }

    @FXML
    void exitClicked() {
        MainApplication.stage.close();
    }

    @FXML
    void instructionClicked() {
        Stage instructionStage = new Stage();
        InstructionScene instructionScene = new InstructionScene();
        instructionStage.setScene(instructionScene.getScene());
        instructionStage.setResizable(false);
        instructionStage.show();
    }
}
