package game.bomman;

import game.bomman.gameState.scores.HighScore;
import game.bomman.gameState.scores.Score;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class HighScoreController implements Initializable {
    @FXML private TableView<Score> scoreTable;
    @FXML private TableColumn<Score, Integer> rankColumn;
    @FXML private TableColumn<Score, Long> valueColumn;

    /**
     * Initialises the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        scoreTable.setItems(HighScore.getHighScores());
    }
}
