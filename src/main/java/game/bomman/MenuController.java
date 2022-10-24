package game.bomman;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class MenuController {
    public static Stage stage;

    @FXML
    void startGameClicked(ActionEvent event) throws FileNotFoundException {
        Game.init(stage);
        Game.run();
    }

    @FXML
    void exitClicked(ActionEvent event) {
        stage.close();
    }
}
