package game.bomman;

import game.bomman.component.SoundPlayer;
import javafx.fxml.FXML;

public class GamePausedController {
    @FXML
    public void turnSoundOnOffClicked() {
        SoundPlayer.turnSoundOnOff();
    }

    @FXML
    public void resumeClicked() {
        Game.load();
    }

    @FXML
    public void exitToMenuClicked() {
        MainApplication.getBackToMenu();
    }

    @FXML
    public void highScoresClicked() {
        if (!MainApplication.highScoreStage.isShowing()) {
            MainApplication.highScoreStage.show();
        }
    }

    @FXML
    public void exitGameClicked() {
        MainApplication.quitGame();
    }

    @FXML
    public void instructionClicked() {
        if (!MainApplication.instructionStage.isShowing())
            MainApplication.instructionStage.show();
    }
}
