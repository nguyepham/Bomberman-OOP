package game.bomman.entity.item;

import game.bomman.component.InteractionHandler;
import game.bomman.entity.Entity;
import game.bomman.entity.character.Bomber;
import game.bomman.entity.immobileEntity.Flame;
import game.bomman.map.Map;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;

public class FlameItem extends Item {
    private static final Image image;

    static {
        try {
            image = loadImage(IMAGES_PATH + "/item/bonus_flames@2.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public FlameItem(Map map, double loadingPosX, double loadingPosY, int posOnMapX, int posOnMapY) {
        this.map = map;
        positionOnMapX = posOnMapX;
        positionOnMapY = posOnMapY;
        initHitBox(loadingPosX, loadingPosY, SIDE, SIDE);
    }

    @Override
    public void interactWith(Entity other) {
        if (other instanceof Bomber || other instanceof Flame) {
            disappear();
            if (other instanceof Bomber) {
                ((Bomber) other).increaseFlameLength();
            }
        }
    }

    @Override
    public void update(double elapsedTime) {
        InteractionHandler.handleInteraction(this, map.getCell(positionOnMapX, positionOnMapY));

        timer += elapsedTime;
        if (timer >= SPRITE_DURATION) {
            timer = 0;
            ++frameIndex;
            if (frameIndex == N_SPRITE) {
                frameIndex = 0;
            }
        }
        if (countdownStarted == false) {
            return;
        }

        countdownTimer -= elapsedTime;
        if (countdownTimer <= 0) {
            disappear();
        }
    }

    @Override
    public void draw() {
        gc.drawImage(image,
                SIDE * frameIndex, 0, SIDE, SIDE,
                hitBox.getMinX(), hitBox.getMinY(), SIDE, SIDE);
    }
}
