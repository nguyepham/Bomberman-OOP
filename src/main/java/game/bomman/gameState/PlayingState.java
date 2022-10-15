package game.bomman.gameState;

import game.bomman.component.InteractionHandler;
import game.bomman.entity.Entity;
import game.bomman.entity.character.Bomber;
import game.bomman.component.MovingController;
import game.bomman.entity.character.Character;
import game.bomman.entity.immobileEntity.ImmobileEntity;
import game.bomman.map.Cell;
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

        Cell firstCell = gameMap.getCell(1, 1);
        double bomberPositionX  = firstCell.getLoadingPositionX();
        double bomberPositionY  = firstCell.getLoadingPositionY();

        Bomber bomber = new Bomber(
                gameMap,
                bomberPositionX + 3.0f,
                bomberPositionY
        );
        firstCell.addEntity(bomber);

        /// Set up game components.
        MovingController.init(characterCanvas, bomber);
        MovingController.activateInputReader();
        MovingController.activateAI();

        InteractionHandler.init(bombCanvas, characterCanvas, bomber);
        InteractionHandler.activateInputReader();
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
                MovingController.update(elapsedTime);
                InteractionHandler.update(elapsedTime);
            }

            private void draw() {
                MovingController.draw();
                InteractionHandler.draw();
            }
        };
        playingStateTimer.start();
    }
}
