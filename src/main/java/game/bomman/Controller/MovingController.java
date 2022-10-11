package game.bomman.Controller;

import game.bomman.command.*;
import game.bomman.entity.character.Character;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
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
    private static Character character;
    private static Canvas canvas;

    public static void init(Canvas v1, Character v2) {
        canvas = v1;
        character = v2;
    }

    public static void activateInputReader() {
//        System.out.println("byKeyboard invoked.");
        EventHandler<KeyEvent> moveByKey = (event) -> {
//        System.out.println("Key event detected.");
            if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                switch (event.getCode()) {
                    case DOWN -> moveDown.executeOn(character);
                    case UP -> moveUp.executeOn(character);
                    case RIGHT -> moveRight.executeOn(character);
                    case LEFT -> moveLeft.executeOn(character);
                }
            } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
//                System.out.println(event.getCode());
                switch (event.getCode()) {
                    case DOWN -> removeDown.executeOn(character);
                    case UP -> removeUp.executeOn(character);
                    case RIGHT -> removeRight.executeOn(character);
                    case LEFT -> removeLeft.executeOn(character);
                }
            }
        };
        canvas.addEventHandler(KeyEvent.KEY_PRESSED, moveByKey);
        canvas.addEventHandler(KeyEvent.KEY_RELEASED, moveByKey);
    }

    public static void activateAI() {

    }

    public static void run() {
        final long startTimestamp = System.nanoTime();
        final long[] lastNanoTimestamp = new long[] {startTimestamp};

        AnimationTimer bomberTimer = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                double elapsedTime = (currentNanoTime - lastNanoTimestamp[0]) / 1000000000.0;
                lastNanoTimestamp[0] = currentNanoTime;
                double timeSinceStart = (currentNanoTime - startTimestamp) / 1000000000.0;

//                canvas.getGraphicsContext2D().clearRect(Entity.SIDE, Entity.SIDE, canvas.getWidth(), canvas.getHeight());
                character.update(elapsedTime, timeSinceStart);
            }
        };
        bomberTimer.start();
    }
}
