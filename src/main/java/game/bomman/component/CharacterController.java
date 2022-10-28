package game.bomman.component;

import game.bomman.command.Command;
import game.bomman.command.movingCommand.*;
import game.bomman.entity.Entity;
import game.bomman.entity.character.enemy.Enemy;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

import java.io.FileNotFoundException;

public class CharacterController extends GamePlayComponent {
    public static Command moveLeft = new MoveLeft();
    public static Command moveDown = new MoveDown();
    public static Command moveRight = new MoveRight();
    public static Command moveUp = new MoveUp();
    public static Command removeLeft = new RemoveLeft();
    public static Command removeDown = new RemoveDown();
    public static Command removeRight = new RemoveRight();
    public static Command removeUp = new RemoveUp();

    public static void activateInputReader() {
        EventHandler<KeyEvent> moveByKey = (event) -> {
            if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                switch (event.getCode()) {
                    case DOWN -> moveDown.executeOn(bomber);
                    case UP -> moveUp.executeOn(bomber);
                    case RIGHT -> moveRight.executeOn(bomber);
                    case LEFT -> moveLeft.executeOn(bomber);
                    case B -> bomber.detonate();
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

    private static void runAI(double elapsedTime) {
        for (Enemy enemy : enemyList) {
            enemy.runAI(elapsedTime);
        }
    }

    public static void update(double elapsedTime) throws FileNotFoundException {
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
