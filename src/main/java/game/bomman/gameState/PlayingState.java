package game.bomman.gameState;

import game.bomman.entity.Entity;
import game.bomman.entity.character.Bomber;
import game.bomman.Controller.MovingController;
import game.bomman.map.Map;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;

import java.io.FileNotFoundException;

public class PlayingState extends GameState {
    private Canvas characterCanvas;
    private Canvas bombCanvas;
    private Canvas itemCanvas;
    private Map gameMap;

    public PlayingState() throws FileNotFoundException {
//        root.getChildren().add(itemCanvas);
//        root.getChildren().add(bombCanvas);
        scene = new Scene(root);
        gameMap = new Map();
        characterCanvas = new Canvas(Entity.SIDE * gameMap.getWidth(), Entity.SIDE * gameMap.getHeight());
        bombCanvas = new Canvas(Entity.SIDE * gameMap.getWidth(), Entity.SIDE * gameMap.getHeight());
    }

    public Scene getScene() { return scene; }

    public void setUp() throws FileNotFoundException {
        /// Set up the game map.
        root.getChildren().add(gameMap.setUp(bombCanvas));
        root.getChildren().add(bombCanvas);

        /// Set up the characters.
        root.getChildren().add(characterCanvas);
        characterCanvas.requestFocus();

        double bomberPositionX  = gameMap.getCell(1, 1).getLoadingPositionX();
        double bomberPositionY  = gameMap.getCell(1, 1).getLoadingPositionY();

        Bomber bomber = new Bomber(gameMap,
                bomberPositionX + 3.0f,
                bomberPositionY,
                characterCanvas.getGraphicsContext2D());

        MovingController.init(characterCanvas, bomber);
        MovingController.activateInputReader();
        MovingController.activateAI();
    }

    public void run() {
        MovingController.run();

//        new AnimationTimer() {
//            @Override
//            public void handle(long ) {
//
//            }
//        }.start();
    }
}
