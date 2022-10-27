package game.bomman.entity.character.enemy;

import game.bomman.map.Map;
import javafx.scene.image.Image;

/**
 * Kiểu di chuyển này là: có thể đi qua cả Brick và Steel.
 */
public class SecondTypeOfMovement extends Enemy {
    public static final double GO_AHEAD_TIME = 3f;
    protected double goAheadTimer = 0;

    public SecondTypeOfMovement(Image walkingImage, Image dyingImage, int nMovingSprites, int nDyingSprites, double movingSpriteDuration, double dyingSpriteDuration, Map map, double loadingPosX, double loadingPosY) {
        super(walkingImage, dyingImage, nMovingSprites, nDyingSprites, movingSpriteDuration, dyingSpriteDuration, map, loadingPosX, loadingPosY);
    }

    public void resetGoAheadTimer() { goAheadTimer = 0; }

    public void addGoAheadTimer(double time) { goAheadTimer += time; }

    public boolean timerUp() {
        return goAheadTimer >= GO_AHEAD_TIME;
    }


    @Override
    public void runAI(double elapsedTime) {
        this.addGoAheadTimer(elapsedTime);
        if (this.isBlocked() || (this.timerUp() && this.fitInThatCell())) {
            movementController.changeMovingDirection();
            if (this.timerUp()) {
                this.resetGoAheadTimer();
            }
        }
    }
}
