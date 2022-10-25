package game.bomman.gameState;

import game.bomman.MainApplication;
import game.bomman.entity.Entity;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;

public class EndingState extends GameState {
    private static final Image loseImage;
    private static final Image winImage;

    static {
        try {
            loseImage = Entity.loadImage(Entity.IMAGES_PATH + "/game_over.png");
            winImage = Entity.loadImage(Entity.IMAGES_PATH + "/you_win.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public EndingState(boolean win) {
        Image image = win ? winImage : loseImage;
        Canvas canvas = new Canvas(image.getWidth(), image.getHeight());
        Group root = new Group(canvas);
        canvas.getGraphicsContext2D().drawImage(image, 0, 0);
        scene = new Scene(root);
        scene.setOnKeyPressed(event -> {
            MainApplication.stage.close();
        });
    }

    public Scene getScene() { return scene; }
}
