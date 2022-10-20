package game.bomman.entity.character.enemy;

import game.bomman.command.Command;
import game.bomman.entity.character.Character;
import game.bomman.map.Cell;

public abstract class Enemy extends Character {
    public static final double GO_AHEAD_TIME = 2f;
    protected Command movingCommand;
    protected double goAheadTimer = 0;

    public void resetGoAheadTimer() { goAheadTimer = 0; }

    public void addGoAheadTimer(double time) { goAheadTimer += time; }

    public boolean timerUp() {
        System.out.println(goAheadTimer); return goAheadTimer >= 3f; }

    public int getFacingDirectionIndex() { return facingDirectionIndex; }

    public void setFacingDirectionIndex(int value) { facingDirectionIndex = value; }

    public void setMovingCommand(Command command) {
        movingCommand = command;
    }

    protected void updatePosition(double elapsedTime) {
        Cell thisCell = map.getCell(positionOnMapX, positionOnMapY);
        Cell aheadCell;

        switch (facingDirectionIndex) {
            case 0 -> {
                aheadCell = map.getCell(positionOnMapX, positionOnMapY - 1);
                if (this.gotInto(aheadCell) == true) {
                    thisCell.removeEntity(this);
                    aheadCell.addEntity(this);
                    --positionOnMapY;
                    break;
                }
                newLoadingY -= elapsedTime * speed;

                if (this.isBlocked()) {
                    newLoadingY = thisCell.getLoadingPositionY();
                }
            }
            case 1 -> {
                aheadCell = map.getCell(positionOnMapX + 1, positionOnMapY);
                if (this.gotInto(aheadCell) == true) {
                    thisCell.removeEntity(this);
                    aheadCell.addEntity(this);
                    ++positionOnMapX;
                    break;
                }
                newLoadingX += elapsedTime * speed;

                if (this.isBlocked()) {
                    newLoadingX = thisCell.getLoadingPositionX();
                }
            }
            case 2 -> {
                aheadCell = map.getCell(positionOnMapX, positionOnMapY + 1);
                if (this.gotInto(aheadCell) == true) {
                    thisCell.removeEntity(this);
                    aheadCell.addEntity(this);
                    ++positionOnMapY;
                    break;
                }
                newLoadingY += elapsedTime * speed;

                if (this.isBlocked()) {
                    newLoadingY = thisCell.getLoadingPositionY();
                }
            }
            case 3 -> {
                aheadCell = map.getCell(positionOnMapX - 1, positionOnMapY);
                if (this.gotInto(aheadCell) == true) {
                    thisCell.removeEntity(this);
                    aheadCell.addEntity(this);
                    --positionOnMapX;
                    break;
                }
                newLoadingX -= elapsedTime * speed;

                if (this.isBlocked()) {
                    newLoadingX = thisCell.getLoadingPositionX();
                }
            }
        }
    }

    public boolean isBlocked() {
        return hitObstacle();
    }

    public boolean fitInThatCell() {
        Cell thisCell = map.getCell(positionOnMapX, positionOnMapY);
        boolean fit = false;
        switch (facingDirectionIndex) {
            case 0 -> {
                if (hitBox.getMinY() < thisCell.getLoadingPositionY()) {
                    hitBox.setMinY(thisCell.getLoadingPositionY());
                    newLoadingY = thisCell.getLoadingPositionY();
                    fit = true;
                }
            }
            case 1 -> {
                if (hitBox.getMinX() > thisCell.getLoadingPositionX()) {
                    hitBox.setMinY(thisCell.getLoadingPositionX());
                    newLoadingX = thisCell.getLoadingPositionX();
                    fit = true;
                }
            }
            case 2 -> {
                if (hitBox.getMinY() > thisCell.getLoadingPositionY()) {
                    hitBox.setMinY(thisCell.getLoadingPositionY());
                    newLoadingY = thisCell.getLoadingPositionY();
                    fit = true;
                }
            }
            case 3 -> {
                if (hitBox.getMinX() < thisCell.getLoadingPositionX()) {
                    hitBox.setMinY(thisCell.getLoadingPositionX());
                    newLoadingX = thisCell.getLoadingPositionX();
                    fit = true;
                }
            }
        }
        return fit;
    }

    public boolean hitObstacle() {
        boolean blocked = false;
        Cell thisCell = map.getCell(positionOnMapX, positionOnMapY);

        switch (movingCommand.getLabel()) {
            case "d" -> {
                blocked = map.getCell(positionOnMapX, positionOnMapY + 1).isBlocking()
                        && hitBox.getMinY() >= thisCell.getLoadingPositionY();
            }
            case "l" -> {
                blocked = map.getCell(positionOnMapX - 1, positionOnMapY).isBlocking()
                        && hitBox.getMinX() <= thisCell.getLoadingPositionX();
            }
            case "r" -> {
                blocked = map.getCell(positionOnMapX + 1, positionOnMapY).isBlocking()
                        && hitBox.getMinX() >= thisCell.getLoadingPositionX();
            }
            case "u" -> {
                blocked = map.getCell(positionOnMapX, positionOnMapY - 1).isBlocking()
                        && hitBox.getMinY() <= thisCell.getLoadingPositionY();
            }
        }
        return blocked;
    }

    public boolean reachedMapEege() {
        boolean reached = false;
        Cell thisCell = map.getCell(positionOnMapX, positionOnMapY);

        switch (movingCommand.getLabel()) {
            case "d" -> {
                reached = thisCell.getPosOnMapY() == map.getHeight() - 2
                        && hitBox.getMinY() >= thisCell.getLoadingPositionY();
            }
            case "l" -> {
                reached = thisCell.getPosOnMapX() == 1
                        && hitBox.getMinX() <= thisCell.getLoadingPositionX();
            }
            case "r" -> {
                reached = thisCell.getPosOnMapX() == map.getWidth() - 2
                        && hitBox.getMinX() >= thisCell.getLoadingPositionX();
            }
            case "u" -> {
                reached = thisCell.getPosOnMapY() == 1
                        && hitBox.getMinY() <= thisCell.getLoadingPositionY();
            }
        }
        return reached;
    }
}
