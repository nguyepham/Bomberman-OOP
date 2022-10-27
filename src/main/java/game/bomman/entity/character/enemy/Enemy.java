package game.bomman.entity.character.enemy;

import game.bomman.component.InteractionHandler;
import game.bomman.entity.Entity;
import game.bomman.entity.character.Bomber;
import game.bomman.entity.character.Character;
import game.bomman.entity.immobileEntity.Bomb;
import game.bomman.entity.immobileEntity.Flame;
import game.bomman.map.Cell;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Enemy extends Character {
    public static final double GO_AHEAD_TIME = 3f;
    protected double goAheadTimer = 0;
    protected ArrayList<Step> traces = new ArrayList<>();
    protected double dyingTimer = 0;
    protected int dyingFrameIndex = 0;


    public void resetGoAheadTimer() { goAheadTimer = 0; }

    public void addGoAheadTimer(double time) { goAheadTimer += time; }

    public boolean timerUp() {
        return goAheadTimer >= GO_AHEAD_TIME;
    }

    public int getFacingDirectionIndex() { return facingDirectionIndex; }

    public void setFacingDirectionIndex(int value) { facingDirectionIndex = value; }

    public int findBomber() {
        traces.clear();
        int direction = -1;

        LinkedList<Step> queue = new LinkedList<>();
        Step found = new Step(getPosOnMapX(), getPosOnMapY(), -1);
        queue.add(found);
        traces.add(found);

        while (queue.isEmpty() == false) {
            Step step = queue.pop();
            int posX = step.getPosX();
            int posY = step.getPosY();

            if (posX == 2 && posY == 1) {
                direction += 0;
            }

            Bomber bomber = InteractionHandler.getBomber();

            if (bomber.isBrickPassing() || bomber.isWaiting()) {
                break;
            }
            if (posX == bomber.getPosOnMapX() && posY == bomber.getPosOnMapY()) {
                found = step;
                break;
            }

            Cell upCell = map.getCell(posX, posY - 1);
            if (upCell.isBlocking(this) == false) {
                if (getStepTrace(posX, posY - 1) == null) {
                    Step newStep = new Step(posX, posY - 1, 0);
                    queue.add(newStep);
                    traces.add(newStep);
                }
            }
            Cell rightCell = map.getCell(posX + 1, posY);
            if (rightCell.isBlocking(this) == false) {
                if (getStepTrace(posX + 1, posY) == null) {
                    Step newStep = new Step(posX + 1, posY, 1);
                    queue.add(newStep);
                    traces.add(newStep);
                }
            }
            Cell downCell = map.getCell(posX, posY + 1);
            if (downCell.isBlocking(this) == false) {
                if (getStepTrace(posX, posY + 1) == null) {
                    Step newStep = new Step(posX, posY + 1, 2);
                    queue.add(newStep);
                    traces.add(newStep);
                }
            }
            Cell leftCell = map.getCell(posX - 1, posY);
            if (leftCell.isBlocking(this) == false) {
                if (getStepTrace(posX - 1, posY) == null) {
                    Step newStep = new Step(posX - 1, posY, 3);
                    queue.add(newStep);
                    traces.add(newStep);
                }
            }
        }

        if (found.getTrace() != -1) {
            while (found.getTrace() != -1) {
                direction = found.getTrace();
                switch (direction) {
                    case 0 -> {
                        found = getStepTrace(found.getPosX(), found.getPosY() + 1);
                        if (found == null) {
                            System.out.println("Null at: " + found.getPosX() + " " + found.getPosY());
                        }
                    }
                    case 1 -> {
                        found = getStepTrace(found.getPosX() - 1, found.getPosY());
                        if (found == null) {
                            System.out.println("Null at: " + found.getPosX() + " " + found.getPosY());
                        }
                    }
                    case 2 -> {
                        found = getStepTrace(found.getPosX(), found.getPosY() - 1);
                        if (found == null) {
                            System.out.println("Null at: " + found.getPosX() + " " + found.getPosY());
                        }
                    }
                    case 3 -> {
                        found = getStepTrace(found.getPosX() + 1, found.getPosY());
                        if (found == null) {
                            System.out.println("Null at: " + found.getPosX() + " " + found.getPosY());
                        }
                    }
                }
            }
        }

        return direction;
    }

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
}
