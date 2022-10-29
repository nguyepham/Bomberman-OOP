package game.bomman.entity.character;

import game.bomman.entity.Entity;
import game.bomman.map.Cell;
import javafx.scene.canvas.GraphicsContext;

public abstract class Character extends Entity {
    protected int numOfLives;
    protected int facingDirectionIndex = 2;
    protected double speed;
    protected double brickPassingTimer = 0;
    protected boolean isAlive = true;
    protected boolean bombPassing = false;
    protected boolean brickPassing = false;
    protected boolean steelPassing = false;
    protected static GraphicsContext gc;

    public boolean isAlive() { return isAlive; }

    public boolean isBrickPassing() { return brickPassing; }
    public boolean isBombPassing() { return bombPassing; }

    public void setBrickPassing() {
        brickPassing = true;
        brickPassingTimer = 8.0f;
    }

    public boolean getSteelPassing() {return steelPassing; }

    protected Cell getAheadCell() {
        Cell thisCell = map.getCell(getPosOnMapX(), getPosOnMapY());
        switch (facingDirectionIndex) {
            case 0 -> thisCell = map.getCell(getPosOnMapX(), getPosOnMapY() - 1);
            case 1 -> thisCell = map.getCell(getPosOnMapX() + 1, getPosOnMapY());
            case 2 -> thisCell = map.getCell(getPosOnMapX(), getPosOnMapY() + 1);
            case 3 -> thisCell = map.getCell(getPosOnMapX() - 1, getPosOnMapY());
        }
        return thisCell;
    }

    public boolean fitInThatCell() {
        Cell thisCell = map.getCell(getPosOnMapX(), getPosOnMapY());
        boolean fit;

        if (facingDirectionIndex % 2 == 0) {
            fit = hitBox.getMinY() == thisCell.getLoadingPositionY();
        } else {
            fit = hitBox.getMinX() == thisCell.getLoadingPositionX();
        }
        return fit;
    }

    public static void setCanvas(GraphicsContext value) {
        gc = value;
    }

    public abstract void layingBomb();

    public abstract void moveDown();

    public abstract void moveLeft();

    public abstract void moveRight();

    public abstract void moveUp();

    public abstract void removeDown();

    public abstract void removeLeft();

    public abstract void removeRight();

    public abstract void removeUp();

    protected void die() { isAlive = false; }
}
