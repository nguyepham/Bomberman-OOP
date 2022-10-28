package game.bomman.component;

import game.bomman.entity.character.Bomber;
import game.bomman.entity.character.Direction;
import game.bomman.entity.character.enemy.Enemy;
import game.bomman.entity.character.enemy.SecondTypeOfMovement;
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
    public static Random enemyMovingCommand = new Random();

    public AI(Enemy enemy) {
        this.enemy = enemy;
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
            if (!upCell.isBlocking(enemy)) {
                if (getStepTrace(posX, posY - 1) == null) {
                    Step newStep = new Step(posX, posY - 1, UP);
                    queue.add(newStep);
                    traces.add(newStep);
                }
            }
            Cell rightCell = enemy.getMap().getCell(posX + 1, posY);
            if (!rightCell.isBlocking(enemy)) {
                if (getStepTrace(posX + 1, posY) == null) {
                    Step newStep = new Step(posX + 1, posY, RIGHT);
                    queue.add(newStep);
                    traces.add(newStep);
                }
            }
            Cell downCell = enemy.getMap().getCell(posX, posY + 1);
            if (!downCell.isBlocking(enemy)) {
                if (getStepTrace(posX, posY + 1) == null) {
                    Step newStep = new Step(posX, posY + 1, DOWN);
                    queue.add(newStep);
                    traces.add(newStep);
                }
            }
            Cell leftCell = enemy.getMap().getCell(posX - 1, posY);
            if (!leftCell.isBlocking(enemy)) {
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

    public void changeMovingDirection() {
        if (enemy instanceof SecondTypeOfMovement) {
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
