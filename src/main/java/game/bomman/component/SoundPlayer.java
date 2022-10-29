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
    private static final Media bomb = new Media(new File(FILE_PATH + "/bomb.wav").toURI().toString());
    private static final Media bonus = new Media(new File(FILE_PATH + "/bonus.wav").toURI().toString());
    private static final Media dying = new Media(new File(FILE_PATH + "/dying.wav").toURI().toString());
    private static final Media stageStart = new Media(new File(FILE_PATH + "/stage_start.wav").toURI().toString());
    private static final Media gameStart = new Media(new File(FILE_PATH + "/game_start.wav").toURI().toString());
    private static final Media gameOver = new Media(new File(FILE_PATH + "/game_over.wav").toURI().toString());
    private static final Media win = new Media(new File(FILE_PATH + "/win.wav").toURI().toString());
    private static boolean muted = false;

    public static void playBombSound() {
        MediaPlayer bombPlayer = new MediaPlayer(bomb);
        if (!muted) bombPlayer.play();
    }

    public static void playBonusSound() {
        MediaPlayer bonusPlayer = new MediaPlayer(bonus);
        if (!muted) bonusPlayer.play();
    }

    public static void playDyingSound() {
        MediaPlayer dyingPlayer = new MediaPlayer(dying);
        if (!muted) dyingPlayer.play();
    }

    public static void playStageStartSound() {
        MediaPlayer stageStartPlayer = new MediaPlayer(stageStart);
        if (!muted) stageStartPlayer.play();
    }

    public static void playGameStartSound() {
        MediaPlayer gameStartPlayer = new MediaPlayer(gameStart);
        if (!muted) gameStartPlayer.play();
    }

    public static void playGameOverSound() {
        MediaPlayer gameOverPlayer = new MediaPlayer(gameOver);
        if (!muted) gameOverPlayer.play();
    }

    public static void playWinSound() {
        MediaPlayer winPlayer = new MediaPlayer(win);
        if (!muted) winPlayer.play();
    }

    public static void turnSoundOnOff() {
        muted = !muted;
    }

    // Bấm S để bật tắt âm lượng
    public static void activateMuteHandler(Stage stage) {
        EventHandler<KeyEvent> muteHandler = event -> {
            if (event.getCode() == KeyCode.S) {
                turnSoundOnOff();
            }
        };
        stage.addEventHandler(KeyEvent.KEY_PRESSED, muteHandler);
    }
}
