package game.bomman.entity.character.enemy;

import game.bomman.component.InteractionHandler;
import game.bomman.entity.Entity;
import game.bomman.entity.immobileEntity.Flame;
import game.bomman.map.Cell;
import game.bomman.map.Map;
import javafx.scene.image.Image;

import java.util.Random;

/**
 * Kiểu di chuyển này là
 * khi gặp vật cản thì đổi hướng đi ngẫu nhiên.
 */
public class FirstTypeOfMovement extends Enemy {
    private final double movingSpriteDuration;
    private final int nMovingSprites;
    private final double dyingSpriteDuration;
    private final int nDyingSprites;
    private double dyingTimer = 0;
    private int dyingFrameIndex = 0;
    private final Image walkingImage;
    private final Image dyingImage;
    private int numOfLives = 1;
    // The flame that hits this enemy
    private Flame hitFlame = null;
    private double setSpeed = 100;

    public FirstTypeOfMovement(
            Image walkingImage, Image dyingImage, int nMovingSprites, int nDyingSprites,
            double movingSpriteDuration, double dyingSpriteDuration,
            Map map, double loadingPosX, double loadingPosY
    ) {
        this.walkingImage = walkingImage;
        this.dyingImage = dyingImage;
        this.nMovingSprites = nMovingSprites;
        this.nDyingSprites = nDyingSprites;
        this.movingSpriteDuration = movingSpriteDuration;
        this.dyingSpriteDuration = dyingSpriteDuration;

        timer = new Random().nextDouble(movingSpriteDuration);
        speed = 100;
        this.map = map;
        initHitBox(loadingPosX, loadingPosY, SIDE, SIDE);
    }

    private void dying() {
        if (dyingTimer >= dyingSpriteDuration) {
            dyingTimer = 0;
            ++dyingFrameIndex;
            if (dyingFrameIndex == nDyingSprites) {
                InteractionHandler.removeEnemy(this);
            }
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

    @Override
    public void draw() {
        if (!isAlive) {
            gc.drawImage(dyingImage,
                    SIDE * dyingFrameIndex, 0, SIDE, SIDE,
                    hitBox.getMinX(), hitBox.getMinY(), SIDE, SIDE);
            return;
        }

        gc.drawImage(walkingImage,
                SIDE * frameIndex, 0, SIDE, SIDE,
                hitBox.getMinX(), hitBox.getMinY(), SIDE, SIDE);
    }

    @Override
    public void layingBomb() {

    }

    @Override
    public void moveDown() {
        facingDirectionIndex = 2;
    }

    @Override
    public void moveLeft() {
        facingDirectionIndex = 3;
    }

    @Override
    public void moveRight() {
        facingDirectionIndex = 1;
    }

    @Override
    public void moveUp() {
        facingDirectionIndex = 0;
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
