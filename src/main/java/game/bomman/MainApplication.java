package game.bomman;

import game.bomman.component.SoundPlayer;
import game.bomman.gameState.InstructionScene;
import game.bomman.gameState.Menu;
import game.bomman.gameState.PausedScene;
import game.bomman.gameState.scores.HighScore;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    public static Stage primaryStage;
    public static Stage instructionStage;
    public static Stage highScoreStage;
    public static Stage pausedStage;

    @Override
    public void start(Stage stage) throws IOException {
        // Khởi tạo các thuộc tính
        MainApplication.primaryStage = stage;
        primaryStage.setOnCloseRequest(windowEvent -> quitGame());

        instructionStage = new Stage();
        instructionStage.setTitle("Instruction");
        instructionStage.setScene(new InstructionScene().getScene());
        instructionStage.setResizable(false);
        instructionStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                instructionStage.close();
            }
        });

        highScoreStage = new Stage();
        highScoreStage.setTitle("High Scores");
        highScoreStage.setScene(HighScore.newHighScoreScene());
        highScoreStage.setResizable(false);
        highScoreStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE || event.getCode() == KeyCode.H) {
                highScoreStage.close();
            }
        });

        pausedStage = new Stage();
        pausedStage.setTitle("Game Paused");
        pausedStage.setScene(PausedScene.newPausedScene());
        pausedStage.setResizable(false);
        pausedStage.setOnCloseRequest(windowEvent -> Game.load());
        pausedStage.initModality(Modality.APPLICATION_MODAL);

        // Activate keyboard shortcut key handlers
        activatePauseLoadKeyHandler(stage);
        activatePauseLoadKeyHandler(pausedStage);
        SoundPlayer.activateMuteHandler(stage);
        SoundPlayer.activateMuteHandler(instructionStage);
        SoundPlayer.activateMuteHandler(highScoreStage);
        SoundPlayer.activateMuteHandler(pausedStage);
        SoundPlayer.activateMusicHandler(stage);
        SoundPlayer.activateMusicHandler(instructionStage);
        SoundPlayer.activateMusicHandler(highScoreStage);
        SoundPlayer.activateMusicHandler(pausedStage);
        activateQuitKeyHandler(stage);
        activateQuitKeyHandler(instructionStage);
        activateQuitKeyHandler(highScoreStage);
        activateQuitKeyHandler(pausedStage);
        activateBackToMenuKeyHandler(stage);
        activateBackToMenuKeyHandler(pausedStage);
        activateHighScoreKeyHandler(stage);
        activateHighScoreKeyHandler(instructionStage);
        activateHighScoreKeyHandler(pausedStage);

        // Set the scene as a menu, play music and show the stage.
        stage.setTitle("Bomberman");
        stage.setResizable(false);
        stage.setScene(Menu.newMenuScene());
        SoundPlayer.playMusic();
        stage.show();
    }

    public static void quitGame() {
        primaryStage.close();
        instructionStage.close();
        highScoreStage.close();
        pausedStage.close();
    }

    // Bấm Q để thoát khỏi game.
    public void activateQuitKeyHandler(Stage stage) {
        EventHandler<KeyEvent> quitHandler = event -> {
            if (event.getCode() == KeyCode.Q) {
                quitGame();
            }
        };
        stage.addEventHandler(KeyEvent.KEY_PRESSED, quitHandler);
    }

    public static void getBackToMenu() {
        if (Game.hasStarted()) {
            Game.endGame();
        }
        try {
            primaryStage.setScene(Menu.newMenuScene());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Game.setPosition(primaryStage);

        if (pausedStage.isShowing()) pausedStage.close();
    }

    // Bấm ESC để quay về menu.
    public void activateBackToMenuKeyHandler(Stage stage) {
        EventHandler<KeyEvent> backToMenuKeyHandler = event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                getBackToMenu();
            }
        };
        stage.addEventHandler(KeyEvent.KEY_PRESSED, backToMenuKeyHandler);
    }

    // Bấm H để hiện các điểm cao
    public void activateHighScoreKeyHandler(Stage stage) {
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
    public void activatePauseLoadKeyHandler(Stage stage) {
        EventHandler<KeyEvent> pauseLoadKeyHandler = event -> {
            if (event.getCode() == KeyCode.P) {
                Game.pause();
            }
            else if (event.getCode() == KeyCode.L) {
                Game.load();
            }
        };
        stage.addEventHandler(KeyEvent.KEY_PRESSED, pauseLoadKeyHandler);
    }

    public static void main(String[] args) {
        launch();
    }
}
