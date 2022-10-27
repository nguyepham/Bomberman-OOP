package game.bomman.entity.character.enemy;

import game.bomman.component.InteractionHandler;
import game.bomman.entity.Entity;
import game.bomman.entity.character.Bomber;
import game.bomman.entity.character.Character;
import game.bomman.entity.character.Direction;
import game.bomman.entity.immobileEntity.Flame;
import game.bomman.map.Cell;
import game.bomman.map.Map;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import static game.bomman.entity.character.Direction.*;

public abstract class Enemy extends Character {
    public static final double GO_AHEAD_TIME = 3f;
    protected double goAheadTimer = 0;
    // tất cả các cell có thể đến được từ chỗ Enemy đang đứng
    // cùng hướng đi để đến nó
    protected ArrayList<Step> traces = new ArrayList<>();
    protected double dyingTimer = 0;
    protected int dyingFrameIndex = 0;
    protected final double movingSpriteDuration;
    protected final int nMovingSprites;
    private final double dyingSpriteDuration;
    private final int nDyingSprites;
    private final Image walkingImage;
    private final Image dyingImage;

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


    public void resetGoAheadTimer() { goAheadTimer = 0; }

    public void addGoAheadTimer(double time) { goAheadTimer += time; }

    public boolean timerUp() {
        return goAheadTimer >= GO_AHEAD_TIME;
    }

    public int getFacingDirectionIndex() { return facingDirectionIndex; }

    public void setFacingDirectionIndex(int value) { facingDirectionIndex = value; }

    public Direction findBomberUsingBFS() {
        traces.clear();
        Direction direction;

        LinkedList<Step> queue = new LinkedList<>();
        Step found = new Step(getPosOnMapX(), getPosOnMapY(), STAY);
        queue.add(found);
        traces.add(found);

        while (!queue.isEmpty()) {
            Step step = queue.pop();
            int posX = step.getPosX();
            int posY = step.getPosY();

            Bomber bomber = InteractionHandler.getBomber();

            if (bomber.isBrickPassing() || bomber.isWaiting()) {
                break;
            }
            if (posX == bomber.getPosOnMapX() && posY == bomber.getPosOnMapY()) {
                found = step;
                break;
            }

            Cell upCell = map.getCell(posX, posY - 1);
            if (!upCell.isBlocking(this)) {
                if (getStepTrace(posX, posY - 1) == null) {
                    Step newStep = new Step(posX, posY - 1, UP);
                    queue.add(newStep);
                    traces.add(newStep);
                }
            }
            Cell rightCell = map.getCell(posX + 1, posY);
            if (!rightCell.isBlocking(this)) {
                if (getStepTrace(posX + 1, posY) == null) {
                    Step newStep = new Step(posX + 1, posY, RIGHT);
                    queue.add(newStep);
                    traces.add(newStep);
                }
            }
            Cell downCell = map.getCell(posX, posY + 1);
            if (!downCell.isBlocking(this)) {
                if (getStepTrace(posX, posY + 1) == null) {
                    Step newStep = new Step(posX, posY + 1, DOWN);
                    queue.add(newStep);
                    traces.add(newStep);
                }
            }
            Cell leftCell = map.getCell(posX - 1, posY);
            if (!leftCell.isBlocking(this)) {
                if (getStepTrace(posX - 1, posY) == null) {
                    Step newStep = new Step(posX - 1, posY, LEFT);
                    queue.add(newStep);
                    traces.add(newStep);
                }
            }
        }

        direction = getDirection(found);

        return direction;
    }

    /**
     * Traces down the path to Bomber and return the next direction.
     *
     * @param found the final Step to get to Bomber.
     * @return the next direction for this Enemy to move in.
     */
    private Direction getDirection(Step found) {
        Direction direction = STAY;
        while (found.getTrace() != STAY) {
            direction = found.getTrace();
            switch (direction) {
                case UP -> found = getStepTrace(found.getPosX(), found.getPosY() + 1);
                case RIGHT -> found = getStepTrace(found.getPosX() - 1, found.getPosY());
                case DOWN -> found = getStepTrace(found.getPosX(), found.getPosY() - 1);
                case LEFT -> found = getStepTrace(found.getPosX() + 1, found.getPosY());
            }
            if (found == null) {
                throw new RuntimeException("found is null while tracing the path to get to Bomber.");
            }
        }
        return direction;
    }

    public Direction findBomberUsingDFS() {
        traces.clear();
        // next direction for this Enemy to move in
        Direction direction = STAY;

        Step currentPosition = new Step(getPosOnMapX(), getPosOnMapY(), STAY);
        Step found = dfs(currentPosition);

        if (found != null) {
            direction = getDirection(found);
            System.out.println("direction: " + direction);
        }

        return direction;
    }

    /**
     * Finds a way to get to Bomber.
     *
     * @param step the current Step in the finding process.
     * @return the Step that leads to Bomber.
     *         null if this Enemy cannot come to Bomber.
     */
    private Step dfs(Step step) {
        traces.add(step);
        int posX = step.getPosX();
        int posY = step.getPosY();

        Bomber bomber = InteractionHandler.getBomber();
        if (bomber.isBrickPassing() || bomber.isWaiting()) {
            return null;
        }
        if (posX == bomber.getPosOnMapX() && posY == bomber.getPosOnMapY()) {
            return step;
        }

        Cell upCell = map.getCell(posX, posY - 1);
        if (!upCell.isBlocking(this)) {
            if (getStepTrace(posX, posY - 1) == null) {
                Step newStep = new Step(posX, posY - 1, UP);
                Step found = dfs(newStep);
                if (found != null) return found;
            }
        }
        Cell rightCell = map.getCell(posX + 1, posY);
        if (!rightCell.isBlocking(this)) {
            if (getStepTrace(posX + 1, posY) == null) {
                Step newStep = new Step(posX + 1, posY, RIGHT);
                Step found = dfs(newStep);
                if (found != null) return found;
            }
        }
        Cell downCell = map.getCell(posX, posY + 1);
        if (!downCell.isBlocking(this)) {
            if (getStepTrace(posX, posY + 1) == null) {
                Step newStep = new Step(posX, posY + 1, DOWN);
                Step found = dfs(newStep);
                if (found != null) return found;
            }
        }
        Cell leftCell = map.getCell(posX - 1, posY);
        if (!leftCell.isBlocking(this)) {
            if (getStepTrace(posX - 1, posY) == null) {
                Step newStep = new Step(posX - 1, posY, LEFT);
                Step found = dfs(newStep);
                if (found != null) return found;
            }
        }

        return null;
    }

    /**
     * return the first Step in traces[] whose
     * target cell is at (posX, posY),
     * or null if there's no such Step.
     */
    protected Step getStepTrace(int posX, int posY) {
        for (Step step : traces) {
            if (step.getPosX() == posX && step.getPosY() == posY) {
                return step;
            }
        }
        return null;
    }

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
        boolean blocked = false;

        Cell thisCell = map.getCell(getPosOnMapX(), getPosOnMapY());

        blocked = getAheadCell().isBlocking(this);

        switch (facingDirectionIndex) {
            case 2 -> {
                blocked = blocked && hitBox.getMinY() >= thisCell.getLoadingPositionY();
            }
            case 3 -> {
                blocked = blocked && hitBox.getMinX() <= thisCell.getLoadingPositionX();
            }
            case 1 -> {
                blocked = blocked && hitBox.getMinX() >= thisCell.getLoadingPositionX();
            }
            case 0 -> {
                blocked = blocked && hitBox.getMinY() <= thisCell.getLoadingPositionY();
            }
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
}
