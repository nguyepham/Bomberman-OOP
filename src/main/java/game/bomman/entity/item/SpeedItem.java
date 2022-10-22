package game.bomman.entity.item;

import game.bomman.component.InteractionHandler;
import game.bomman.entity.Entity;
import game.bomman.entity.character.Bomber;
import game.bomman.entity.immobileEntity.Flame;
import game.bomman.map.Map;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;

public class SpeedItem extends Item {
    private static final Image image;

    static {
        try {
            image = loadImage(IMAGES_PATH + "/item/bonus_speed@2.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public SpeedItem(Map map, double loadingPosX, double loadingPosY, int posOnMapX, int posOnMapY) {
        this.map = map;
        positionOnMapX = posOnMapX;
        positionOnMapY = posOnMapY;
        initHitBox(loadingPosX, loadingPosY, SIDE, SIDE);
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
