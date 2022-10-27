package game.bomman.entity.character.enemy;

import game.bomman.component.AI;
import game.bomman.component.InteractionHandler;
import game.bomman.entity.Entity;
import game.bomman.entity.character.Character;
import game.bomman.entity.immobileEntity.Flame;
import game.bomman.map.Cell;
import game.bomman.map.Map;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;
import java.util.Random;

public abstract class Enemy extends Character {
    protected double dyingTimer = 0;
    protected int dyingFrameIndex = 0;
    protected final double movingSpriteDuration;
    protected final int nMovingSprites;
    private final double dyingSpriteDuration;
    private final int nDyingSprites;
    private final Image walkingImage;
    private final Image dyingImage;
    protected AI movementController = new AI(this);

    public Enemy(
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
        this.map = map;
        initHitBox(loadingPosX, loadingPosY, SIDE, SIDE);
        speed = 100;
    }

    public abstract void runAI(double elapsedTime);

    public int getFacingDirectionIndex() { return facingDirectionIndex; }

    public void setFacingDirectionIndex(int value) { facingDirectionIndex = value; }

    @Override
    public void interactWith(Entity other) {
        if (other instanceof Flame) {
            die();
        }
    }

    protected void updatePosition(double elapsedTime) {
        Cell thisCell = map.getCell(getPosOnMapX(), getPosOnMapY());
        double cellMinX = thisCell.getLoadingPositionX();
        double cellMinY = thisCell.getLoadingPositionY();

        double currentX = hitBox.getMinX();
        double currentY = hitBox.getMinY();

        switch (facingDirectionIndex) {
            case 0 -> {
                hitBox.setMinY(currentY - elapsedTime * speed);

                if (this.isBlocked() || (currentY > cellMinY && hitBox.getMinY() < cellMinY)) {
                    hitBox.setMinY(thisCell.getLoadingPositionY());
                }
            }
            case 1 -> {
                hitBox.setMinX(currentX + elapsedTime * speed);

                if (this.isBlocked() || (currentX < cellMinX && hitBox.getMinX() > cellMinX)) {
                    hitBox.setMinX(thisCell.getLoadingPositionX());
                }
            }
            case 2 -> {
                hitBox.setMinY(currentY + elapsedTime * speed);

                if (this.isBlocked() || (currentY < cellMinY && hitBox.getMinY() > cellMinY)) {
                    hitBox.setMinY(thisCell.getLoadingPositionY());
                }
            }
            case 3 -> {
                hitBox.setMinX(currentX - elapsedTime * speed);

                if (this.isBlocked() || (currentX > cellMinX && hitBox.getMinX() < cellMinX)) {
                    hitBox.setMinX(thisCell.getLoadingPositionX());
                }
            }
        }
    }

    public boolean isBlocked() {
        return hitObstacle();
    }

    public boolean hitObstacle() {
        boolean blocked;

        Cell thisCell = map.getCell(getPosOnMapX(), getPosOnMapY());

        blocked = getAheadCell().isBlocking(this);

        switch (facingDirectionIndex) {
            case 2 -> blocked = blocked && hitBox.getMinY() >= thisCell.getLoadingPositionY();
            case 3 -> blocked = blocked && hitBox.getMinX() <= thisCell.getLoadingPositionX();
            case 1 -> blocked = blocked && hitBox.getMinX() >= thisCell.getLoadingPositionX();
            case 0 -> blocked = blocked && hitBox.getMinY() <= thisCell.getLoadingPositionY();
        }
        return blocked;
    }

    @Override
    public void update(double elapsedTime) throws FileNotFoundException {
        if (!isAlive) {
            dyingTimer += elapsedTime;
            dying();
            return;
        }

        Cell thisCell = map.getCell(getPosOnMapX(), getPosOnMapY());
        /// Handle interaction between Bomber and other entities.
        InteractionHandler.handleInteraction(this, thisCell);

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

    protected void dying() {
        if (dyingTimer >= dyingSpriteDuration) {
            dyingTimer = 0;
            ++dyingFrameIndex;
            if (dyingFrameIndex == nDyingSprites) {
                InteractionHandler.removeEnemy(this);
            }
        }
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
