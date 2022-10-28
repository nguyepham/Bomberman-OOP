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
        if (!MainApplication.instructionStage.isShowing()) {
            MainApplication.instructionStage.setScene(MainApplication.instructionScene.getScene());
            MainApplication.instructionStage.setResizable(false);
            MainApplication.instructionStage.show();
        }
    }
}
