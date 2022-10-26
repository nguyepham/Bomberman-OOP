package game.bomman.gameState;

import game.bomman.Game;
import game.bomman.component.GamePlayComponent;
import game.bomman.component.InteractionHandler;
import game.bomman.entity.Entity;
import game.bomman.component.CharacterController;
import game.bomman.map.Map;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;

import java.io.FileNotFoundException;

public class PlayingState extends GameState {
    private Canvas mapCanvas = new Canvas();
    private Canvas characterCanvas;
    private Canvas bombCanvas;
    private Canvas itemCanvas;
    private static Map gameMap;
    private AnimationTimer playingStateTimer;

    public PlayingState() throws FileNotFoundException {
        gameMap = new Map();
        gameMap.readFromFile(Game.LEVEL_1_MAP);
        characterCanvas = new Canvas(Entity.SIDE * gameMap.getWidth(), Entity.SIDE * gameMap.getHeight());
        bombCanvas = new Canvas(Entity.SIDE * gameMap.getWidth(), Entity.SIDE * gameMap.getHeight());
        itemCanvas = new Canvas(Entity.SIDE * gameMap.getWidth(), Entity.SIDE * gameMap.getHeight());
    }

    public void loadNextLevelMap() throws FileNotFoundException {
        characterCanvas.getGraphicsContext2D().clearRect(
                Entity.SIDE, Entity.SIDE,
                Entity.SIDE * gameMap.getWidth(),
                Entity.SIDE * gameMap.getHeight()
        );
        bombCanvas.getGraphicsContext2D().clearRect(
                Entity.SIDE, Entity.SIDE,
                Entity.SIDE * gameMap.getWidth(),
                Entity.SIDE * gameMap.getHeight()
        );
        itemCanvas.getGraphicsContext2D().clearRect(
                Entity.SIDE, Entity.SIDE,
                Entity.SIDE * gameMap.getWidth(),
                Entity.SIDE * gameMap.getHeight()
        );

        GamePlayComponent.resetBomberPosition();
        GamePlayComponent.clearEnemyList();
        InteractionHandler.clearEntityList();
        gameMap.readFromFile(Game.LEVEL_2_MAP);
        gameMap.setUp(mapCanvas);
        characterCanvas.setWidth(Entity.SIDE * gameMap.getWidth());
        characterCanvas.setHeight(Entity.SIDE * gameMap.getHeight());
        bombCanvas.setWidth(Entity.SIDE * gameMap.getWidth());
        bombCanvas.setHeight(Entity.SIDE * gameMap.getHeight());
        itemCanvas.setWidth(Entity.SIDE * gameMap.getWidth());
        itemCanvas.setHeight(Entity.SIDE * gameMap.getHeight());
        Group root = new Group();
        root.getChildren().add(mapCanvas);
        root.getChildren().add(itemCanvas);
        root.getChildren().add(bombCanvas);
        root.getChildren().add(characterCanvas);
        scene.setRoot(root);
        gameMap.loadEntities();
    }

    public void setUp() throws FileNotFoundException {
        Group root = new Group();
        /// Set up game canvases.
        gameMap.setUp(mapCanvas);
        root.getChildren().add(mapCanvas);
        root.getChildren().add(itemCanvas);
        root.getChildren().add(bombCanvas);
        root.getChildren().add(characterCanvas);
        scene = new Scene(root);
        characterCanvas.requestFocus();

        /// Set up game components.
        GamePlayComponent.init(characterCanvas, gameMap);
        CharacterController.activateInputReader();
        InteractionHandler.init(bombCanvas, itemCanvas);
        InteractionHandler.activateInputReader();
        gameMap.loadEntities();
    }

    public void run() {
        playingStateTimer = new AnimationTimer() {
            long lastTimestamp = System.nanoTime();

            @Override
            public void handle(long currentTimestamp) {
                double elapsedTime = (currentTimestamp - lastTimestamp) / 1000000000.0;
                lastTimestamp = currentTimestamp;

                try {
                    this.update(elapsedTime);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                this.draw();
            }

            private void update(double elapsedTime) throws FileNotFoundException {
                CharacterController.update(elapsedTime);
                InteractionHandler.update(elapsedTime);
            }

            private void draw() {
                CharacterController.draw();
                InteractionHandler.draw();
            }
        };
        playingStateTimer.start();
    }

    public AnimationTimer getPlayingStateTimer() {
        return playingStateTimer;
    }
}
