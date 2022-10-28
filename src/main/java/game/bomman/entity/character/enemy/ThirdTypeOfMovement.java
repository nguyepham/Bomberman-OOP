package game.bomman.entity.character.enemy;

import game.bomman.entity.character.Direction;
import game.bomman.map.Map;
import javafx.scene.image.Image;

/**
 * Kiểu di chuyển này là đuổi theo Bomber.
 */
public class ThirdTypeOfMovement extends Enemy {
    public ThirdTypeOfMovement(Image walkingImage, Image dyingImage, int nMovingSprites, int nDyingSprites, double movingSpriteDuration, double dyingSpriteDuration, Map map, double loadingPosX, double loadingPosY) {
        super(walkingImage, dyingImage, nMovingSprites, nDyingSprites, movingSpriteDuration, dyingSpriteDuration, map, loadingPosX, loadingPosY);
    }

    @Override
    public void runAI(double elapsedTime) {
        Direction newDirection = movementController.findBomberUsingBFS();
        if (newDirection != Direction.STAY) {
            if (this.fitInThatCell()) {
                this.setFacingDirectionIndex(newDirection.ordinal());
            }
        } else {
            if (this.isBlocked()) {
                movementController.changeMovingDirection();
            }
        }
    }
}
