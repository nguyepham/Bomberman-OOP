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
    private Command moveLeft = new MoveLeft();
    private Command moveDown = new MoveDown();
    private Command moveRight = new MoveRight();
    private Command moveUp = new MoveUp();
    private Command removeLeft = new RemoveLeft();
    private Command removeDown = new RemoveDown();
    private Command removeRight = new RemoveRight();
    private Command removeUp = new RemoveUp();
    private Character bomber;
    private Canvas canvas;

    public MovingController(Canvas v1, Character v2) {
        canvas = v1;
        bomber = v2;
    }

    public void byKeyboard() {
        EventHandler<KeyEvent> moveByKey = (event) -> {
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
