package game.bomman;

import game.bomman.gameState.PlayingState;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class Game {
    private static Stage stage;
    private static PlayingState playingState;

    public static void init(Stage stage_) throws FileNotFoundException {
        stage = stage_;
        playingState = new PlayingState();
    }

    public static void run() throws FileNotFoundException {
        playingState.setUp();
        playingState.run();

        stage.setTitle("Bomberman");
        stage.setResizable(false);
        stage.setScene(playingState.getScene());
        stage.show();
    }
}
