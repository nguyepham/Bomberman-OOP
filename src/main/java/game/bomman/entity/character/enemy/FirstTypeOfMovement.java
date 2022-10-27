package game.bomman.entity.character.enemy;

import game.bomman.component.InteractionHandler;
import game.bomman.entity.Entity;
import game.bomman.entity.immobileEntity.Flame;
import game.bomman.map.Cell;
import game.bomman.map.Map;
import javafx.scene.image.Image;

/**
 * Kiểu di chuyển này là
 * khi gặp vật cản thì đổi hướng đi ngẫu nhiên.
 */
public class FirstTypeOfMovement extends Enemy {
    private int numOfLives = 1;
    // The flame that hits this enemy
    private Flame hitFlame = null;
    private double setSpeed = 100;

    public FirstTypeOfMovement(
            Image walkingImage, Image dyingImage, int nMovingSprites, int nDyingSprites,
            double movingSpriteDuration, double dyingSpriteDuration,
            Map map, double loadingPosX, double loadingPosY
    ) {
        super(walkingImage, dyingImage, nMovingSprites, nDyingSprites, movingSpriteDuration, dyingSpriteDuration, map, loadingPosX, loadingPosY);
    }

    @Override
    public void runAI(double elapsedTime) {
        if (this.isBlocked()) {
            movementController.changeMovingDirection();
        }
    }

    @Override
    public void update(double elapsedTime) {
        Cell thisCell = map.getCell(getPosOnMapX(), getPosOnMapY());
        /// Handle interaction between Bomber and other entities.
        InteractionHandler.handleInteraction(this, thisCell);

        if (hitFlame != null) {
            if (hitFlame.isDisappeared()) {
                hitFlame = null;
                --numOfLives;
                speed = setSpeed;
            } else {
                speed = 0;
            }
        }

        if (!isAlive) {
            dyingTimer += elapsedTime;
            dying();
            return;
        }

        if (speed != 0) {
            setSpeed = speed;
        }

        timer += elapsedTime;
        if (timer >= movingSpriteDuration) {
            timer = 0;
            ++frameIndex;
            if (frameIndex == nMovingSprites) {
                frameIndex = 0;
            }
        }

        updatePosition(elapsedTime);
    }

    public void setNumOfLives(int numOfLives) {
        if (numOfLives > 0) {
            this.numOfLives = numOfLives;
        }
    }

    @Override
    public void interactWith(Entity other) {
        if (other instanceof Flame flame) {
            if (numOfLives <= 1) {
                hitFlame = null;
                die();
            }
            else {
                hitFlame = flame;
            }
        }
    }
}
