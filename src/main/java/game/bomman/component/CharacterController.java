package game.bomman.component;

import game.bomman.command.*;
import game.bomman.command.movingCommand.*;
import game.bomman.entity.Entity;
import game.bomman.entity.character.enemy.Balloon;
import game.bomman.entity.character.enemy.Enemy;
import game.bomman.map.Cell;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

import java.util.Random;

public class CharacterController extends Component {
    public static Command moveLeft = new MoveLeft();
    public static Command moveDown = new MoveDown();
    public static Command moveRight = new MoveRight();
    public static Command moveUp = new MoveUp();
    public static Command removeLeft = new RemoveLeft();
    public static Command removeDown = new RemoveDown();
    public static Command removeRight = new RemoveRight();
    public static Command removeUp = new RemoveUp();
    public static Random enemyMovingCommand = new Random();

    public static void activateInputReader() {
        EventHandler<KeyEvent> moveByKey = (event) -> {
            if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                switch (event.getCode()) {
                    case DOWN -> moveDown.executeOn(bomber);
                    case UP -> moveUp.executeOn(bomber);
                    case RIGHT -> moveRight.executeOn(bomber);
                    case LEFT -> moveLeft.executeOn(bomber);
                }
            } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                switch (event.getCode()) {
                    case DOWN -> removeDown.executeOn(bomber);
                    case UP -> removeUp.executeOn(bomber);
                    case RIGHT -> removeRight.executeOn(bomber);
                    case LEFT -> removeLeft.executeOn(bomber);
                }
            }
        };
        characterCanvas.addEventHandler(KeyEvent.KEY_PRESSED, moveByKey);
        characterCanvas.addEventHandler(KeyEvent.KEY_RELEASED, moveByKey);
    }

    private static void changeMovingDirection(Enemy enemy) {
        int cnt = 0;
        while (enemy.isBlocked()) {
            if (cnt == 120) {
                break;
            }
            int newIndex = enemyMovingCommand.nextInt(4);
            while (newIndex == enemy.getFacingDirectionIndex()) {
                newIndex = enemyMovingCommand.nextInt(4);
            }
            enemy.setFacingDirectionIndex(newIndex);

            switch (newIndex) {
                case 0 -> {
                    enemy.setMovingCommand(moveUp);
                }
                case 1 -> {
                    enemy.setMovingCommand(moveRight);
                }
                case 2 -> {
                    enemy.setMovingCommand(moveDown);
                }
                case 3 -> {
                    enemy.setMovingCommand(moveLeft);
                }
            }
            ++cnt;
        }
    }

    private static void runAI(double elapsedTime) {
        for (Enemy enemy : enemyList) {
            if (enemy instanceof Balloon) {
                if (enemy.isBlocked()) {
                    changeMovingDirection(enemy);
                }
                continue;
            }
        }
    }

    public static void update(double elapsedTime) {
        runAI(elapsedTime);

        for (int i = 0; i < enemyList.size(); ++i) {
            Enemy enemy = enemyList.get(i);
            enemy.update(elapsedTime);
        }
        bomber.update(elapsedTime);
    }

    public static void draw() {
        /// Transparently clear the characterCanvas and draw everything on that characterCanvas once again.
        characterCanvas.getGraphicsContext2D().clearRect(Entity.SIDE, Entity.SIDE,characterCanvas.getWidth(), characterCanvas.getHeight());
        for (int i = 0; i < enemyList.size(); ++i) {
            Enemy enemy = enemyList.get(i);
            enemy.draw();
        }
        bomber.draw();
    }
}
