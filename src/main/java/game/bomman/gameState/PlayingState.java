package game.bomman.gameState;

import game.bomman.entity.Entity;
import game.bomman.entity.character.Bomber;
import game.bomman.component.MovingController;
import game.bomman.entity.character.Character;
import game.bomman.entity.immobileEntity.ImmobileEntity;
import game.bomman.map.Map;
import javafx.scene.Scene;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;

import java.io.FileNotFoundException;

public class PlayingState extends GameState {
    private Canvas characterCanvas;
    private Canvas bombCanvas;
    private Canvas itemCanvas;
    private Map gameMap;

    public PlayingState() throws FileNotFoundException {
        scene = new Scene(root);
        gameMap = new Map();
        characterCanvas = new Canvas(Entity.SIDE * gameMap.getWidth(), Entity.SIDE * gameMap.getHeight());
        bombCanvas = new Canvas(Entity.SIDE * gameMap.getWidth(), Entity.SIDE * gameMap.getHeight());
    }

    public Scene getScene() { return scene; }

    public void setUp() throws FileNotFoundException {
        /// Set the graphic context for entities.
        ImmobileEntity.setCanvas(bombCanvas.getGraphicsContext2D());
        Character.setCanvas(characterCanvas.getGraphicsContext2D());

        /// Set up the game map.
        root.getChildren().add(gameMap.setUp());
        root.getChildren().add(bombCanvas);

        /// Set up the characters.
        root.getChildren().add(characterCanvas);
        characterCanvas.requestFocus();

        double bomberPositionX  = gameMap.getCell(1, 1).getLoadingPositionX();
        double bomberPositionY  = gameMap.getCell(1, 1).getLoadingPositionY();

        Bomber bomber = new Bomber(gameMap,
                bomberPositionX + 3.0f,
                bomberPositionY);

        MovingController.init(characterCanvas, bomber);
        MovingController.activateInputReader();
        MovingController.activateAI();
    }

    public void run() {
        final long startTimestamp = System.nanoTime();
        final long[] lastTimestamp = new long[] {startTimestamp};

        AnimationTimer playingStateTimer = new AnimationTimer() {
            @Override
            public void handle(long currentTimestamp) {
                double elapsedTime = (currentTimestamp - lastTimestamp[0]) / 1000000000.0;
                lastTimestamp[0] = currentTimestamp;
                double timeSinceStart = (currentTimestamp - startTimestamp) / 1000000000.0;

                MovingController.update(elapsedTime, timeSinceStart);
                MovingController.draw();
            }
        };
        playingStateTimer.start();
    }
}
