package game.bomman.gameState.scores;

import game.bomman.MainApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.LinkedList;

public class HighScore {
    private static long currentScore = 0;
    private static final int MAX_SIZE = 20;
    private static final ObservableList<Score> highScores = FXCollections.observableList(new LinkedList<>());

    public static Scene newHighScoreScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("high-score.fxml"));
        return new Scene(fxmlLoader.load());
    }

    public static void resetScore() {
        currentScore = 0;
    }

    public static void addScore(long amount) {
        currentScore += amount;
        if (highScores.isEmpty() || currentScore > highScores.get(0).getValue()) {
            highScores.add(0, new Score(0, currentScore));
            if (highScores.size() > MAX_SIZE) highScores.remove(highScores.size() - 1);
            for (Score score: highScores) {
                score.setRank(score.getRank() + 1);
            }
        }
    }

    public static ObservableList<Score> getHighScores() {
        return highScores;
    }
}
