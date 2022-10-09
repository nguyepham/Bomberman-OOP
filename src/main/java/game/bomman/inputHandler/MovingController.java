package game.bomman.inputHandler;

import game.bomman.command.*;
import game.bomman.entity.character.Character;
import game.bomman.entity.character.enemy.Enemy;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MovingController {
    private static Command moveLeft = new MoveLeft();
    private static Command moveDown = new MoveDown();
    private static Command moveRight = new MoveRight();
    private static Command moveUp = new MoveUp();
    private static Command removeLeft = new RemoveLeft();
    private static Command removeDown = new RemoveDown();
    private static Command removeRight = new RemoveRight();
    private static Command removeUp = new RemoveUp();
    private static Character bomber;
    private static Canvas canvas;

    public MovingController(Canvas v1, Character v2) {
        canvas = v1;
        bomber = v2;
    }

    public static void setUpForBomber() {
//        System.out.println("byKeyboard invoked.");
        EventHandler<KeyEvent> moveByKey = (event) -> {
//        System.out.println("Key event detected.");
            if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                switch (event.getCode()) {
                    case DOWN -> moveDown.executeOn(bomber);
                    case UP -> moveUp.executeOn(bomber);
                    case RIGHT -> moveRight.executeOn(bomber);
                    case LEFT -> moveLeft.executeOn(bomber);
                }
            } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
//                System.out.println(event.getCode());
                switch (event.getCode()) {
                    case DOWN -> removeDown.executeOn(bomber);
                    case UP -> removeUp.executeOn(bomber);
                    case RIGHT -> removeRight.executeOn(bomber);
                    case LEFT -> removeLeft.executeOn(bomber);
                }
            }
        };
        canvas.addEventHandler(KeyEvent.KEY_PRESSED, moveByKey);
        canvas.addEventHandler(KeyEvent.KEY_RELEASED, moveByKey);
    }

    public void ByAI(Enemy enemy) {

    }
}
