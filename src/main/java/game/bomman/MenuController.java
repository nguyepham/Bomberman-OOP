package game.bomman;

import game.bomman.component.SoundPlayer;
import javafx.fxml.FXML;

import java.io.FileNotFoundException;

public class MenuController {
    @FXML
    void startGameClicked() throws FileNotFoundException {
        Game.init(MainApplication.primaryStage);
        SoundPlayer.playGameStartSound();
        Game.run();
    }

    @FXML
    void exitClicked() {
        MainApplication.quitGame();
    }

    @FXML
    void instructionClicked() {
        if (!MainApplication.instructionStage.isShowing()) {
            MainApplication.instructionStage.show();
        }
    }
}
