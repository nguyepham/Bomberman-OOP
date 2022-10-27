package game.bomman.gameState;

import game.bomman.entity.Entity;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;

public class InstructionScene extends GameState {
    private static final Image instructionImage;

    static {
        try {
            instructionImage = Entity.loadImage(Entity.IMAGES_PATH + "/keyboard.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public InstructionScene() {
        ImageView imageView = new ImageView(instructionImage);
        scene = new Scene(new Group(imageView), instructionImage.getWidth(), instructionImage.getHeight());
    }
}
