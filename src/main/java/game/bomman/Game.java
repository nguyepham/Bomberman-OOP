package game.bomman;

import game.bomman.gameState.PlayingState;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class Game {
    private static Stage stage;
    private static PlayingState playingState;
    public static final String LEVEL_1_MAP = "src/main/resources/game/bomman/assets/map1.txt";
    public static final String LEVEL_2_MAP = "src/main/resources/game/bomman/assets/map2.txt";
    public static final String LEVEL_3_MAP = "src/main/resources/game/bomman/assets/map3.txt";

    public static void init(Stage stage_) throws FileNotFoundException {
        stage = stage_;
        playingState = new PlayingState();
    }

    public static void levelUp() throws FileNotFoundException {
        playingState.loadNextLevelMap();
        stage.setScene(playingState.getScene());
        stage.sizeToScene();
        setPosition(stage);
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
        stage.show();
    }
}
