package game.bomman;

import game.bomman.component.SoundPlayer;
import game.bomman.gameState.InstructionScene;
import game.bomman.gameState.Menu;
import game.bomman.gameState.scores.HighScore;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    public static Stage stage;
    public static Stage instructionStage;
    public static Stage highScoreStage;

    @Override
    public void start(Stage stage) throws IOException {
        // Khởi tạo các thuộc tính
        MainApplication.stage = stage;

        instructionStage = new Stage();
        instructionStage.setTitle("Instruction");
        instructionStage.setScene(new InstructionScene().getScene());
        instructionStage.setResizable(false);

        highScoreStage = new Stage();
        highScoreStage.setTitle("High scores");
        highScoreStage.setScene(HighScore.newHighScoreScene());
        highScoreStage.setResizable(false);
        highScoreStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE || event.getCode() == KeyCode.H) {
                highScoreStage.close();
            }
        });

        // Activate keyboard shortcut key handlers
        activatePauseLoadKeyHandler();
        SoundPlayer.activateMuteHandler(stage);
        activateQuitKeyHandler(stage);
        activateBackToMenuKeyHandler();
        activateHighScoreKeyHandler();

        // Set the scene as a menu, play music and show the stage.
        stage.setTitle("Bomberman");
        stage.setResizable(false);
        stage.setScene(Menu.newMenuScene());
        SoundPlayer.playStageStartSound();
        stage.show();
    }

    // Bấm Q để thoát khỏi game.
    public void activateQuitKeyHandler(Stage stage) {
        EventHandler<KeyEvent> quitHandler = event -> {
            if (event.getCode() == KeyCode.Q) {
                stage.close();
            }
        };
        stage.addEventHandler(KeyEvent.KEY_PRESSED, quitHandler);
    }

    // Bấm ESC để quay về menu.
    public void activateBackToMenuKeyHandler() {
        EventHandler<KeyEvent> backToMenuKeyHandler = event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                if (Game.hasStarted()) {
                    Game.endGame();

                    try {
                        stage.setScene(Menu.newMenuScene());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    Game.setPosition(stage);
                }
            }
        };
        stage.addEventHandler(KeyEvent.KEY_PRESSED, backToMenuKeyHandler);
    }

    // Bấm H để hiện các điểm cao
    public void activateHighScoreKeyHandler() {
        EventHandler<KeyEvent> highScoreKeyHandler = event -> {
            if (event.getCode() == KeyCode.H) {
                if (!highScoreStage.isShowing()) {
                    highScoreStage.show();
                }
            }
        };
        stage.addEventHandler(KeyEvent.KEY_PRESSED, highScoreKeyHandler);
    }

    // Bấm P để pause, L để tiếp tục game
    public void activatePauseLoadKeyHandler() {
        EventHandler<KeyEvent> pauseLoadKeyHandler = event -> {
            if (Game.hasStarted()) {
                if (event.getCode() == KeyCode.P) {
                    Game.pause();
                }
                else if (event.getCode() == KeyCode.L) {
                    Game.load();
                }
            }
        };
        stage.addEventHandler(KeyEvent.KEY_PRESSED, pauseLoadKeyHandler);
    }

    public static void main(String[] args) {
        launch();
    }
}
