package game.bomman.gameState;

import game.bomman.Game;
import game.bomman.component.CharacterController;
import game.bomman.component.DashboardHandler;
import game.bomman.component.GamePlayComponent;
import game.bomman.component.InteractionHandler;
import game.bomman.entity.Entity;
import game.bomman.map.Map;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;

import static game.bomman.Game.currentMap;
import static game.bomman.Game.gameLost;

public class PlayingState extends GameState {
    private final Canvas mapCanvas = new Canvas();
    private final Canvas characterCanvas;
    private final Canvas bombCanvas;
    private final Canvas itemCanvas;
    private static Map gameMap;
    private AnimationTimer playingStateTimer;
    // player has 5 minutes to win the game
    private final double FIXED_TIME = 5 * 60;
    private double gameTimer = FIXED_TIME;

    public PlayingState() throws FileNotFoundException {
        gameMap = new Map();
        gameMap.readFromFile(Game.levels[0]);
        characterCanvas = new Canvas(Entity.SIDE * gameMap.getWidth(), Entity.SIDE * gameMap.getHeight());
        bombCanvas = new Canvas(Entity.SIDE * gameMap.getWidth(), Entity.SIDE * gameMap.getHeight());
        itemCanvas = new Canvas(Entity.SIDE * gameMap.getWidth(), Entity.SIDE * gameMap.getHeight());
        DashboardHandler.initialize();
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
        gameMap.readFromFile(Game.levels[currentMap]);
        gameMap.setUp(mapCanvas);
        characterCanvas.setWidth(Entity.SIDE * gameMap.getWidth());
        characterCanvas.setHeight(Entity.SIDE * gameMap.getHeight());
        bombCanvas.setWidth(Entity.SIDE * gameMap.getWidth());
        bombCanvas.setHeight(Entity.SIDE * gameMap.getHeight());
        itemCanvas.setWidth(Entity.SIDE * gameMap.getWidth());
        itemCanvas.setHeight(Entity.SIDE * gameMap.getHeight());
        gameMap.loadEntities();
    }

    public void setUp() throws FileNotFoundException {
        /// Set up game canvases.
        gameMap.setUp(mapCanvas);
        scene = new Scene(newGameRoot());
        characterCanvas.requestFocus();

        /// Set up game components.
        GamePlayComponent.init(characterCanvas, gameMap);
        CharacterController.activateInputReader();
        InteractionHandler.init(bombCanvas, itemCanvas);
        InteractionHandler.activateInputReader();
        gameMap.loadEntities();
        DashboardHandler.setUp();
    }

    public VBox newGameRoot() {
        Group gameCanvases = new Group(mapCanvas, itemCanvas, bombCanvas, characterCanvas);
        VBox vbox = new VBox(DashboardHandler.getDashboardRoot(), gameCanvases);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }

    public void run() {
        playingStateTimer = new AnimationTimer() {
            long lastTimestamp = System.nanoTime();

            @Override
            public void handle(long currentTimestamp) {
                double elapsedTime = (currentTimestamp - lastTimestamp) / 1000000000.0;
                lastTimestamp = currentTimestamp;

                if (!Game.hasPaused()) {
                    gameTimer -= elapsedTime;
                    DashboardHandler.updateTime();
                    if (gameTimer <= 0) {
                        gameLost();
                        return;
                    }
                    try {
                        this.update(elapsedTime);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    this.draw();
                }
            }

            private void update(double elapsedTime) throws FileNotFoundException {
                InteractionHandler.update(elapsedTime);
                CharacterController.update(elapsedTime);
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

    public String getTime() {
        int minute = (int) (gameTimer / 60);
        int second = (int) (gameTimer % 60);
        String paddingMin = minute < 10 ? "0" : "";
        String paddingSecond = second < 10 ? "0" : "";
        return paddingMin + minute + ":" + paddingSecond + second;
    }

    public void resetGameTimer() {
        this.gameTimer = FIXED_TIME;
    }

    public double getGameTimer() {
        return gameTimer;
    }
}
