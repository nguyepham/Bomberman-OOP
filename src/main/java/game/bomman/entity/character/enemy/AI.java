package game.bomman.entity.character.enemy;

import game.bomman.component.InteractionHandler;
import game.bomman.entity.character.Bomber;
import game.bomman.entity.character.Direction;
import game.bomman.entity.character.enemy.secondTypeOfMoving.SecondTypeOfMoving;
import game.bomman.map.Cell;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import static game.bomman.entity.character.Direction.*;

public class AI {
    // the Enemy using this AI
    private final Enemy enemy;
    // tất cả các cell có thể đến được từ chỗ Enemy đang đứng
    // cùng hướng đi để đến nó
    private final ArrayList<Step> traces = new ArrayList<>();
    private final ArrayList<Step> DFS_traces = new ArrayList<>();
    private final ArrayList<Direction> path = new ArrayList<>();
    private int markedX;
    private int markedY;
    private int pathId = 0;

    public boolean markedCellReached() {
        return enemy.getPosOnMapX() == markedX && enemy.getPosOnMapY() == markedY;
    }
    public static Random enemyMovingCommand = new Random();

    public AI(Enemy enemy) {
        this.enemy = enemy;
        markedX = enemy.getPosOnMapX();
        markedY = enemy.getPosOnMapY();
    }

    public Direction findBomberUsingBFS() {
        traces.clear();
        Direction direction;

        LinkedList<Step> queue = new LinkedList<>();
        Step found = new Step(enemy.getPosOnMapX(), enemy.getPosOnMapY(), STAY);
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

            Cell upCell = enemy.getMap().getCell(posX, posY - 1);
            Cell rightCell = enemy.getMap().getCell(posX + 1, posY);
            Cell downCell = enemy.getMap().getCell(posX, posY + 1);
            Cell leftCell = enemy.getMap().getCell(posX - 1, posY);

            if (!upCell.isBlocking(enemy)) {
                if (getStepTrace(traces, posX, posY - 1) == null) {
                    Step newStep = new Step(posX, posY - 1, UP);
                    queue.add(newStep);
                    traces.add(newStep);
                }
            }
            if (!rightCell.isBlocking(enemy)) {
                if (getStepTrace(traces, posX + 1, posY) == null) {
                    Step newStep = new Step(posX + 1, posY, RIGHT);
                    queue.add(newStep);
                    traces.add(newStep);
                }
            }
            if (!downCell.isBlocking(enemy)) {
                if (getStepTrace(traces, posX, posY + 1) == null) {
                    Step newStep = new Step(posX, posY + 1, DOWN);
                    queue.add(newStep);
                    traces.add(newStep);
                }
            }
            if (!leftCell.isBlocking(enemy)) {
                if (getStepTrace(traces, posX - 1, posY) == null) {
                    Step newStep = new Step(posX - 1, posY, LEFT);
                    queue.add(newStep);
                    traces.add(newStep);
                }
            }
        }

        direction = getDirection(found);

        return direction;
    }

    /// Clear the path when reached marked cell, before generate a new one.
    public void clearPath() {
        path.clear();
        DFS_traces.clear();
        markedX = -1;
        markedY = -1;
        pathId = 0;
    }

    public void tracesThisCell() {
        DFS_traces.add(new Step(enemy.getPosOnMapX(), enemy.getPosOnMapY(), STAY));
    }

    /// If marked cell is defined, move along the path.
    public void moveByDFS() {
        if (pathId >= path.size()) {
            return;
        }

        if (enemy.fitInThatCell()) {
            enemy.setFacingDirectionIndex(path.get(pathId++).ordinal());
        }
    }

    /// if marked cell is undefined, generate the path using DFS.
    public void generatePathUsingDFS(int posX, int posY) {

        Cell upCell = enemy.getMap().getCell(posX, posY - 1);
        Cell rightCell = enemy.getMap().getCell(posX + 1, posY);
        Cell downCell = enemy.getMap().getCell(posX, posY + 1);
        Cell leftCell = enemy.getMap().getCell(posX - 1, posY);

        boolean upAvailable = !upCell.isBlocking(enemy) && getStepTrace(DFS_traces, posX, posY - 1) == null;
        boolean rightAvailable = !rightCell.isBlocking(enemy) && getStepTrace(DFS_traces, posX + 1, posY) == null;
        boolean downAvailable = !downCell.isBlocking(enemy) && getStepTrace(DFS_traces, posX, posY + 1) == null;
        boolean leftAvailable = !leftCell.isBlocking(enemy) && getStepTrace(DFS_traces, posX - 1, posY) == null;

        if (!upAvailable && !rightAvailable && !downAvailable && !leftAvailable) {
            markedX = posX;
            markedY = posY;
            return;
        }

        if (markedX != -1 && markedY != -1) {
            return;
        }
        if (upAvailable) {
            Step newStep = new Step(posX, posY - 1, UP);
            DFS_traces.add(newStep);
            path.add(UP);
            generatePathUsingDFS(posX, posY - 1);
        }

        if (markedX != -1 && markedY != -1) {
            return;
        }
        if (rightAvailable) {
            Step newStep = new Step(posX + 1, posY, RIGHT);
            DFS_traces.add(newStep);
            path.add(RIGHT);
            generatePathUsingDFS(posX + 1, posY);
        }

        if (markedX != -1 && markedY != -1) {
            return;
        }
        if (downAvailable) {
            Step newStep = new Step(posX, posY + 1, DOWN);
            DFS_traces.add(newStep);
            path.add(DOWN);
            generatePathUsingDFS(posX, posY + 1);
        }

        if (markedX != -1 && markedY != -1) {
            return;
        }
        if (leftAvailable) {
            Step newStep = new Step(posX - 1, posY, LEFT);
            DFS_traces.add(newStep);
            path.add(LEFT);
            generatePathUsingDFS(posX - 1, posY);
        }
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
                case UP -> found = getStepTrace(traces, found.getPosX(), found.getPosY() + 1);
                case RIGHT -> found = getStepTrace(traces, found.getPosX() - 1, found.getPosY());
                case DOWN -> found = getStepTrace(traces, found.getPosX(), found.getPosY() - 1);
                case LEFT -> found = getStepTrace(traces, found.getPosX() + 1, found.getPosY());
            }
            if (found == null) {
                throw new RuntimeException("found is null while tracing the path to get to Bomber.");
            }
        }
        return direction;
    }

    /**
     * return the first Step in traces[] whose
     * target cell is at (posX, posY),
     * or null if there's no such Step.
     */
    protected Step getStepTrace(ArrayList<Step> traces, int posX, int posY) {
        for (Step step : traces) {
            if (step.getPosX() == posX && step.getPosY() == posY) {
                return step;
            }
        }
        return null;
    }

    public void changeMovingDirection() {
        if (enemy instanceof SecondTypeOfMoving) {
            getNewDirection();
            return;
        }

        for (int cnt = 0; cnt < 120 && enemy.isBlocked(); ++cnt) {
            getNewDirection();
        }
    }

    private void getNewDirection() {
        int newIndex = enemyMovingCommand.nextInt(4);
        while (newIndex == enemy.getFacingDirectionIndex()) {
            newIndex = enemyMovingCommand.nextInt(4);
        }
        enemy.setFacingDirectionIndex(newIndex);
    }
}
