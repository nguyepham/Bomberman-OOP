package game.bomman.component;

import game.bomman.Game;
import game.bomman.entity.Entity;
import game.bomman.gameState.scores.HighScore;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.FileNotFoundException;

public class DashboardHandler {
    public static final Image dashboardImage;
    private static Canvas dashboardCanvas;
    private static Canvas scoreCanvas;
    private static Canvas timeCanvas;
    private static Canvas lifeCountCanvas;
    private static Canvas highScoreCanvas;
    private static Group dashboardRoot;
    public static final int SCORE_MIN_X = 67;
    public static final int TIME_MIN_X = 375;
    public static final int LIFE_COUNT_MIN_X = 542;
    public static final int HIGH_SCORE_MIN_X = 631;
    public static final int TEXT_MIN_Y = 42;
    public static final int FONT_HEIGHT = 24;
    private static final Font font = Font.font("Consolas", FontWeight.BOLD, FONT_HEIGHT);
    private static final Color fontColor = Color.WHITE;


    static {
        try {
            dashboardImage = Entity.loadImage(Entity.IMAGES_PATH + "/dashboard.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void initialize() {
        dashboardCanvas = new Canvas(dashboardImage.getWidth(), dashboardImage.getHeight());
        scoreCanvas = new Canvas(dashboardImage.getWidth(), dashboardImage.getHeight());
        timeCanvas = new Canvas(dashboardImage.getWidth(), dashboardImage.getHeight());
        lifeCountCanvas = new Canvas(dashboardImage.getWidth(), dashboardImage.getHeight());
        highScoreCanvas = new Canvas(dashboardImage.getWidth(), dashboardImage.getHeight());

        setFillAndFont(dashboardCanvas);
        setFillAndFont(scoreCanvas);
        setFillAndFont(timeCanvas);
        setFillAndFont(lifeCountCanvas);
        setFillAndFont(highScoreCanvas);

        dashboardRoot = new Group(dashboardCanvas, scoreCanvas, timeCanvas, lifeCountCanvas, highScoreCanvas);
    }

    private static void setFillAndFont(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(fontColor);
        gc.setFont(font);
    }

    private static GraphicsContext rendererOf(Canvas canvas) {
        return canvas.getGraphicsContext2D();
    }

    public static void setUp() {
        // draw initial things
        rendererOf(dashboardCanvas).drawImage(dashboardImage, 0, 0);
        clearDashboard();
    }

    public static Group getDashboardRoot() {
        return dashboardRoot;
    }

    public static void updateScore() {
        rendererOf(scoreCanvas).clearRect(0, 0, scoreCanvas.getWidth(), scoreCanvas.getHeight());
        rendererOf(scoreCanvas).fillText(String.valueOf(HighScore.getCurrentScore()), SCORE_MIN_X, TEXT_MIN_Y);
    }

    public static void updateTime() {
        rendererOf(timeCanvas).clearRect(0, 0, timeCanvas.getWidth(), timeCanvas.getHeight());
        rendererOf(timeCanvas).fillText(Game.getPlayingState().getTime(), TIME_MIN_X, TEXT_MIN_Y);
    }

    public static void updateLifeCount() {
        rendererOf(lifeCountCanvas).clearRect(0, 0, lifeCountCanvas.getWidth(), lifeCountCanvas.getHeight());
        rendererOf(lifeCountCanvas).fillText(String.valueOf(GamePlayComponent.getBomber().getNumOfLives()), LIFE_COUNT_MIN_X, TEXT_MIN_Y);
    }

    public static void updateHighScore() {
        rendererOf(highScoreCanvas).clearRect(0, 0, highScoreCanvas.getWidth(), highScoreCanvas.getHeight());
        rendererOf(highScoreCanvas).fillText(String.valueOf(HighScore.getHighestScore()), HIGH_SCORE_MIN_X, TEXT_MIN_Y);
    }

    public static void clearDashboard() {
        updateScore();
        updateTime();
        updateLifeCount();
        updateHighScore();
    }
}
