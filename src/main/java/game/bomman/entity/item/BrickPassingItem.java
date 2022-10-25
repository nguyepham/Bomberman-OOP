package game.bomman.entity.item;

import game.bomman.entity.Entity;
import game.bomman.entity.character.Bomber;
import game.bomman.map.Map;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;

public class BrickPassingItem extends Item {
    private static final Image image;

    static {
        try {
            image = loadImage(IMAGES_PATH + "/item/bonus_brick_pass@2.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public BrickPassingItem(Map map, double loadingPosX, double loadingPosY) {
        this.map = map;
        initHitBox(loadingPosX, loadingPosY, SIDE, SIDE);
    }

    @Override
    public void interactWith(Entity other) {
        if (countdownStarted == false) {
            return;
        }
        super.interactWith(other);
        if (other instanceof Bomber) {
            Bomber bomber = (Bomber) other;
            if (bomber.isDetonator()) return;
            bomber.setBrickPassing();
            disappear();
        }
    }

    @Override
    public void draw() {
        if (isExploding == true) {
            super.draw();
            return;
        }
        gc.drawImage(image,
                SIDE * frameIndex, 0, SIDE, SIDE,
                hitBox.getMinX(), hitBox.getMinY(), SIDE, SIDE);
    }
}
