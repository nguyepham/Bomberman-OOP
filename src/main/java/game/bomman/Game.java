package game.bomman;

import game.bomman.component.SoundPlayer;
import game.bomman.gameState.EndingState;
import game.bomman.gameState.PlayingState;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

@SuppressWarnings("ClassEscapesDefinedScope")
public class Game {
    private static Stage stage;
    private static PlayingState playingState;
    public static final String[] levels;
    public static int currentMap = 0;
    // Thay đổi số map ở đây khi thêm file map mới:
    private static final int NUMBER_OF_MAPS = 4;

    static {
        levels = new String[NUMBER_OF_MAPS];
        for (int i = 0; i < levels.length; ++i) {
            levels[i] = String.format("src/main/resources/game/bomman/assets/map%d.txt", i + 1);
        }
    }

    public static void init(Stage stage_) throws FileNotFoundException {
        stage = stage_;
        playingState = new PlayingState();
    }

    public static void levelUp() throws FileNotFoundException {
        ++currentMap;
        if (currentMap >= levels.length) {
            EndingState endingState = new EndingState(true);
            Game.getPlayingState().getPlayingStateTimer().stop();
            stage.setScene(endingState.getScene());
            stage.sizeToScene();
            setPosition(stage);
            SoundPlayer.playWinSound();
            return;
        }
        playingState.loadNextLevelMap();
        stage.setScene(playingState.getScene());
        stage.sizeToScene();
        setPosition(stage);
        SoundPlayer.playStageStartSound();
    }

    public static void setPosition(Stage stage) {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        double stageMinX = primaryScreenBounds.getMinX() + (primaryScreenBounds.getWidth() - stage.getWidth()) / 2;
        double stageMinY = primaryScreenBounds.getMinY() + (primaryScreenBounds.getHeight() - stage.getHeight()) / 2;

        stage.setX(stageMinX);
        stage.setY(stageMinY);
    }

    public static void run() throws FileNotFoundException {
        playingState.setUp();
        playingState.run();

        stage.setTitle("Bomberman");
        stage.setResizable(false);
        stage.setScene(playingState.getScene());
        stage.sizeToScene();
        setPosition(stage);
        SoundPlayer.playStageStartSound();
        stage.show();
    }

    public static PlayingState getPlayingState() {
        return playingState;
    }
}
