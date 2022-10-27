package game.bomman.entity.character.enemy;

import game.bomman.component.InteractionHandler;
import game.bomman.entity.Entity;
import game.bomman.entity.character.Bomber;
import game.bomman.entity.character.Character;
import game.bomman.entity.immobileEntity.Flame;
import game.bomman.map.Cell;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Enemy extends Character {
    public static final double GO_AHEAD_TIME = 3f;
    protected double goAheadTimer = 0;
    // tất cả các cell có thể đến được từ chỗ Enemy đang đứng
    // cùng hướng đi để đến nó
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

    public int findBomberUsingBFS() {
        traces.clear();
        int direction = -1;

        LinkedList<Step> queue = new LinkedList<>();
        Step found = new Step(getPosOnMapX(), getPosOnMapY(), -1);
        queue.add(found);
        traces.add(found);

        while (!queue.isEmpty()) {
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
            if (!upCell.isBlocking(this)) {
                if (getStepTrace(posX, posY - 1) == null) {
                    Step newStep = new Step(posX, posY - 1, 0);
                    queue.add(newStep);
                    traces.add(newStep);
                }
            }
            Cell rightCell = map.getCell(posX + 1, posY);
            if (!rightCell.isBlocking(this)) {
                if (getStepTrace(posX + 1, posY) == null) {
                    Step newStep = new Step(posX + 1, posY, 1);
                    queue.add(newStep);
                    traces.add(newStep);
                }
            }
            Cell downCell = map.getCell(posX, posY + 1);
            if (!downCell.isBlocking(this)) {
                if (getStepTrace(posX, posY + 1) == null) {
                    Step newStep = new Step(posX, posY + 1, 2);
                    queue.add(newStep);
                    traces.add(newStep);
                }
            }
            Cell leftCell = map.getCell(posX - 1, posY);
            if (!leftCell.isBlocking(this)) {
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
                            // nếu found đã null thì không có found.getPosX(), found.getPosY()
                            // vì vậy ở đây nên throw
                            //System.out.println("Null at: " + found.getPosX() + " " + found.getPosY());
                        }
                    }
                    case 1 -> {
                        found = getStepTrace(found.getPosX() - 1, found.getPosY());
                        //if (found == null) {
                            //System.out.println("Null at: " + found.getPosX() + " " + found.getPosY());
                        //}
                    }
                    case 2 -> {
                        found = getStepTrace(found.getPosX(), found.getPosY() - 1);
                        //if (found == null) {
                            //System.out.println("Null at: " + found.getPosX() + " " + found.getPosY());
                        //}
                    }
                    case 3 -> {
                        found = getStepTrace(found.getPosX() + 1, found.getPosY());
                        //if (found == null) {
                            //System.out.println("Null at: " + found.getPosX() + " " + found.getPosY());
                        //}
                    }
                }
                if (found == null) {
                    throw new RuntimeException("found is null while tracing the path to get to Bomber.");
                }
            }
        }

        return direction;
    }

    public int findBomberUsingDFS() {
        traces.clear();
        // next direction for this Enemy to move in
        int direction = -1;

        Step currentPosition = new Step(getPosOnMapX(), getPosOnMapY(), -1);
        Step found = dfs(currentPosition);

        if (found != null) {
            while (found.getTrace() != -1) {
                direction = found.getTrace();
                switch (direction) {
                    case 0 -> found = getStepTrace(found.getPosX(), found.getPosY() + 1);
                    case 1 -> found = getStepTrace(found.getPosX() - 1, found.getPosY());
                    case 2 -> found = getStepTrace(found.getPosX(), found.getPosY() - 1);
                    case 3 -> found = getStepTrace(found.getPosX() + 1, found.getPosY());
                }
                if (found == null) {
                    throw new RuntimeException("found is null while tracing the path to get to Bomber.");
                }
            }
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
                Step newStep = new Step(posX, posY - 1, 0);
                Step found = dfs(newStep);
                if (found != null) return found;
            }
        }
        Cell rightCell = map.getCell(posX + 1, posY);
        if (!rightCell.isBlocking(this)) {
            if (getStepTrace(posX + 1, posY) == null) {
                Step newStep = new Step(posX + 1, posY, 1);
                Step found = dfs(newStep);
                if (found != null) return found;
            }
        }
        Cell downCell = map.getCell(posX, posY + 1);
        if (!downCell.isBlocking(this)) {
            if (getStepTrace(posX, posY + 1) == null) {
                Step newStep = new Step(posX, posY + 1, 2);
                Step found = dfs(newStep);
                if (found != null) return found;
            }
        }
        Cell leftCell = map.getCell(posX - 1, posY);
        if (!leftCell.isBlocking(this)) {
            if (getStepTrace(posX - 1, posY) == null) {
                Step newStep = new Step(posX - 1, posY, 3);
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
}
