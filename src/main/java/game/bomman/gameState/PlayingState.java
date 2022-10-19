package game.bomman.gameState;

import game.bomman.component.Component;
import game.bomman.component.InteractionHandler;
import game.bomman.entity.Entity;
import game.bomman.component.CharacterController;
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

        /// Set up game canvases.
        root.getChildren().add(gameMap.setUp());
        root.getChildren().add(bombCanvas);
        root.getChildren().add(characterCanvas);
        characterCanvas.requestFocus();

        /// Set up game components.
        Component.init(characterCanvas, gameMap);
        CharacterController.activateInputReader();
        InteractionHandler.init(bombCanvas);
        InteractionHandler.activateInputReader();
        gameMap.loadEntities();
    }

    public void run() {
        AnimationTimer playingStateTimer = new AnimationTimer() {
            long lastTimestamp = System.nanoTime();

            @Override
            public void handle(long currentTimestamp) {
                double elapsedTime = (currentTimestamp - lastTimestamp) / 1000000000.0;
                lastTimestamp = currentTimestamp;

                this.update(elapsedTime);
                this.draw();
            }

            private void update(double elapsedTime) {
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
}
