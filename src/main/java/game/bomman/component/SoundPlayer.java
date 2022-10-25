package game.bomman.component;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

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

    public static void playBombSound() {
        bombPlayer = new MediaPlayer(bomb);
        bombPlayer.play();
    }

    public static void playBonusSound() {
        bonusPlayer = new MediaPlayer(bonus);
        bonusPlayer.play();
    }

    public static void playDyingSound() {
        dyingPlayer = new MediaPlayer(dying);
        dyingPlayer.play();
    }

    public static void playStageStartSound() {
        stageStartPlayer = new MediaPlayer(stageStart);
        stageStartPlayer.play();
    }
}
