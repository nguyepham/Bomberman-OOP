package game.bomman.gameState;

import javafx.scene.Group;
import javafx.scene.Scene;

public abstract class GameState {
    protected Scene scene;
    protected Group root = new Group();

    public GameState() {}
}
