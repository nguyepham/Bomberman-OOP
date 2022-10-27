package game.bomman.entity.character.enemy;

import game.bomman.map.Map;
import javafx.scene.image.Image;

/**
 * Kiểu di chuyển này là: có thể đi qua cả Brick và Steel.
 */
public class SecondTypeOfMovement extends Enemy {
    public SecondTypeOfMovement(Image walkingImage, Image dyingImage, int nMovingSprites, int nDyingSprites, double movingSpriteDuration, double dyingSpriteDuration, Map map, double loadingPosX, double loadingPosY) {
        super(walkingImage, dyingImage, nMovingSprites, nDyingSprites, movingSpriteDuration, dyingSpriteDuration, map, loadingPosX, loadingPosY);
    }

    @Override
    public void layingBomb() {

    }

    @Override
    public void removeDown() {

    }

    @Override
    public void removeLeft() {

    }

    @Override
    public void removeRight() {

    }

    @Override
    public void removeUp() {

    }
}
