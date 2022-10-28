package game.bomman.component;

import java.io.File;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class SoundPlayer {
    private static final String FILE_PATH = "src/main/resources/game/bomman/assets/sounds";
    private static Media bomb = new Media(new File(FILE_PATH + "/bomb.wav").toURI().toString());
    private static MediaPlayer bombPlayer;
    private static Media bonus = new Media(new File(FILE_PATH + "/bonus.wav").toURI().toString());
    private static MediaPlayer bonusPlayer;
    private static Media dying = new Media(new File(FILE_PATH + "/dying.wav").toURI().toString());
    private static MediaPlayer dyingPlayer;
    private static Media stageStart = new Media(new File(FILE_PATH + "/stage_start.wav").toURI().toString());
    private static MediaPlayer stageStartPlayer;
    private static Media gameStart = new Media(new File(FILE_PATH + "/game_start.wav").toURI().toString());
    private static MediaPlayer gameStartPlayer;
    private static Media gameOver = new Media(new File(FILE_PATH + "/game_over.wav").toURI().toString());
    private static MediaPlayer gameOverPlayer;
    private static Media win = new Media(new File(FILE_PATH + "/win.wav").toURI().toString());
    private static MediaPlayer winPlayer;
    private static boolean muted = false;

    public static void playBombSound() {
        bombPlayer = new MediaPlayer(bomb);
        if (!muted) bombPlayer.play();
    }

    public static void playBonusSound() {
        bonusPlayer = new MediaPlayer(bonus);
        if (!muted) bonusPlayer.play();
    }

    public static void playDyingSound() {
        dyingPlayer = new MediaPlayer(dying);
        if (!muted) dyingPlayer.play();
    }

    public static void playStageStartSound() {
        stageStartPlayer = new MediaPlayer(stageStart);
        if (!muted) stageStartPlayer.play();
    }

    public static void playGameStartSound() {
        gameStartPlayer = new MediaPlayer(gameStart);
        if (!muted) gameStartPlayer.play();
    }

    public static void playGameOverSound() {
        gameOverPlayer = new MediaPlayer(gameOver);
        if (!muted) gameOverPlayer.play();
    }

    public static void playWinSound() {
        winPlayer = new MediaPlayer(win);
        if (!muted) winPlayer.play();
    }

    // Bấm S để bật tắt âm lượng
    public static void activateMuteHandler(Stage stage) {
        EventHandler<KeyEvent> muteHandler = event -> {
            if (event.getCode() == KeyCode.S) {
                muted = !muted;
            }
        };
        stage.addEventHandler(KeyEvent.KEY_PRESSED, muteHandler);
    }
}
