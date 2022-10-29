package game.bomman.gameState;

import game.bomman.MainApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class PausedScene {
    public static Scene newPausedScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("game-paused.fxml"));
        return new Scene(fxmlLoader.load());
    }
}
