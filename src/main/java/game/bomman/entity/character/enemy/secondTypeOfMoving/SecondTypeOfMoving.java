package game.bomman.entity.character.enemy.secondTypeOfMoving;

import game.bomman.entity.character.enemy.Enemy;
import game.bomman.map.Map;
import javafx.scene.image.Image;

/**
 * Kiểu di chuyển này là: có thể đi qua cả Brick và Steel.
 */
public class SecondTypeOfMoving extends Enemy {
    public SecondTypeOfMoving(Image walkingImage, Image dyingImage, int nMovingSprites, int nDyingSprites, double movingSpriteDuration, double dyingSpriteDuration, Map map, double loadingPosX, double loadingPosY, int score) {
        super(walkingImage, dyingImage, nMovingSprites, nDyingSprites, movingSpriteDuration, dyingSpriteDuration, map, loadingPosX, loadingPosY, score);
        brickPassing = true;
        steelPassing = true;
    }

    @Override
    public void runAI(double elapsedTime) {
        movementController.changeMovingDirection();
    }
}
