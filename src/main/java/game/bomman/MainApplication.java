package game.bomman;

import game.bomman.entity.character.Bomber;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.Arrays;

import static game.bomman.entity.character.Character.NOT_MOVING;

public class MainApplication extends Application {

    public void loadBomberSample(Stage stage) {
        Group root = new Group();
        Scene scene = new Scene(root, 620, 260);
        stage.setScene(scene);

        Canvas canvas = new Canvas(620, 260);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // currently allow only one pressed key which is
        // about moving direction to be handled at a time.
        // wrap keyPressed around a single element array
        // to pass it in a lambda function
        final String[] keyPressed = {NOT_MOVING};
        KeyCode[] allowedKeys = {
                KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT
        };

        scene.setOnKeyPressed(event -> {
            if (Arrays.asList(allowedKeys).contains(event.getCode())
                && keyPressed[0].equals(NOT_MOVING)) {
                keyPressed[0] = event.getCode().toString();
            }
        });

        scene.setOnKeyReleased(event -> {
            if (Arrays.asList(allowedKeys).contains(event.getCode())
                && keyPressed[0].equals(event.getCode().toString())) {
                keyPressed[0] = NOT_MOVING;
            }
        });

        Bomber b = new Bomber();

        final long startTime = System.nanoTime();
        // wrapped in a single-element array to
        // be able to pass into a lambda function
        final long[] lastNanoTime = {startTime};

        double speed = 100;

        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                double elapsedTime = (currentNanoTime - lastNanoTime[0]) / 1000000000.0;
                lastNanoTime[0] = currentNanoTime;
                double timeSinceStart = (currentNanoTime - startTime) / 1000000000.0;

                b.setVelocity(0,0);
                switch (keyPressed[0]) {
                    case "UP" -> b.addVelocity(0, -speed);
                    case "DOWN" -> b.addVelocity(0, speed);
                    case "LEFT" -> b.addVelocity(-speed, 0);
                    case "RIGHT" -> b.addVelocity(speed, 0);
                }
                b.update(elapsedTime, timeSinceStart, keyPressed[0]);

                // clears the canvas
                gc.clearRect(0 , 0, canvas.getWidth(), canvas.getHeight());
                b.render(gc);
            }
        }.start();
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Bomberman");
        stage.setResizable(false);

        loadBomberSample(stage);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}