package game.bomman.component;

import game.bomman.command.*;
import game.bomman.command.movingCommand.*;
import game.bomman.entity.Entity;
import game.bomman.entity.character.enemy.Enemy;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class MovingController extends Component {
    private static Command moveLeft = new MoveLeft();
    private static Command moveDown = new MoveDown();
    private static Command moveRight = new MoveRight();
    private static Command moveUp = new MoveUp();
    private static Command removeLeft = new RemoveLeft();
    private static Command removeDown = new RemoveDown();
    private static Command removeRight = new RemoveRight();
    private static Command removeUp = new RemoveUp();

    public static void activateInputReader() {
        EventHandler<KeyEvent> moveByKey = (event) -> {
            characterCanvas.requestFocus();
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

    public static void activateAI() {

    }

    public static void update(double elapsedTime) {
        bomber.update(elapsedTime);
        for (int i = 0; i < enemyList.size(); ++i) {
            Enemy enemy = enemyList.get(i);
            enemy.update(elapsedTime);
        }
    }

    public static void draw() {
        /// Transparently clear the characterCanvas and draw everything on that characterCanvas once again.
        characterCanvas.getGraphicsContext2D().clearRect(Entity.SIDE, Entity.SIDE,characterCanvas.getWidth(), characterCanvas.getHeight());
        bomber.draw();
        for (int i = 0; i < enemyList.size(); ++i) {
            Enemy enemy = enemyList.get(i);
            enemy.draw();
        }
    }
}
