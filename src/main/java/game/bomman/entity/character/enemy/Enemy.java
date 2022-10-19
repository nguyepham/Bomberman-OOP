package game.bomman.entity.character.enemy;

import game.bomman.command.Command;
import game.bomman.entity.character.Character;
import game.bomman.map.Cell;

public abstract class Enemy extends Character {
    public static final double GO_AHEAD_TIME = 2f;
    protected Command movingCommand;
    protected double goAheadTimer = 0;

    protected void updatePosition(double elapsedTime) {
        Cell thisCell = map.getCell(positionOnMapX, positionOnMapY);
        Cell aheadCell = thisCell;

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

                if (aheadCell.isBlocking()) {
                    if (newLoadingY < thisCell.getLoadingPositionY()) {
                        newLoadingY = thisCell.getLoadingPositionY();
                    }
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

                if (aheadCell.isBlocking()) {
                    if (newLoadingX > thisCell.getLoadingPositionX()) {
                        newLoadingX = thisCell.getLoadingPositionX();
                    }
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

                if (aheadCell.isBlocking()) {
                    if (newLoadingY > thisCell.getLoadingPositionY()) {
                        newLoadingY = thisCell.getLoadingPositionY();
                    }
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

                if (aheadCell.isBlocking()) {
                    if (newLoadingX < thisCell.getLoadingPositionX()) {
                        newLoadingX = thisCell.getLoadingPositionX();
                    }
                }
            }
        }

    }

    public double getGoAheadTimer() { return goAheadTimer; }

    public void addGoAheadTimer(double time) { goAheadTimer += time; }

    public int getFacingDirectionIndex() { return facingDirectionIndex; }

    public void setFacingDirectionIndex(int value) { facingDirectionIndex = value; }

    public void setMovingCommand(Command command) {
        movingCommand = command;
    }

    public boolean isBlocked() {
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
}
