package game.bomman.component;

import game.bomman.command.*;
import game.bomman.command.movingCommand.*;
import game.bomman.entity.Entity;
import game.bomman.entity.character.Bomber;
import game.bomman.entity.character.Character;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MovingController {
    private static Command moveLeft = new MoveLeft();
    private static Command moveDown = new MoveDown();
    private static Command moveRight = new MoveRight();
    private static Command moveUp = new MoveUp();
    private static Command removeLeft = new RemoveLeft();
    private static Command removeDown = new RemoveDown();
    private static Command removeRight = new RemoveRight();
    private static Command removeUp = new RemoveUp();
    private static Bomber bomber;
    private static List<Character> characterList = new ArrayList<>();
    private static Canvas canvas;

    public static void init(Canvas v1, Bomber v2) {
        canvas = v1;
        bomber = v2;
    }

    public static void activateInputReader() {
        EventHandler<KeyEvent> moveByKey = (event) -> {
            canvas.requestFocus();
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
        canvas.addEventHandler(KeyEvent.KEY_PRESSED, moveByKey);
        canvas.addEventHandler(KeyEvent.KEY_RELEASED, moveByKey);
    }

    public static void activateAI() {

    }

    public static void update(double elapsedTime) {
        bomber.update(elapsedTime);
    }

    public static void draw() {
        /// Transparently clear the canvas and draw everything on that canvas once again.
        canvas.getGraphicsContext2D().clearRect(Entity.SIDE, Entity.SIDE,canvas.getWidth(), canvas.getHeight());
        bomber.draw();
    }
}
