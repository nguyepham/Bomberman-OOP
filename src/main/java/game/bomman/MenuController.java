package game.bomman;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class MenuController {
    @FXML
    void startGameClicked(ActionEvent event) throws FileNotFoundException {
        Game.init(MainApplication.stage);
        Game.run();
    }

    @FXML
    void exitClicked(ActionEvent event) {
        MainApplication.stage.close();
    }
}
