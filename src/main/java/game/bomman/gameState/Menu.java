package game.bomman.gameState;


import game.bomman.MainApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class Menu {
    public static Scene newMenuScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("menu.fxml"));
        return new Scene(fxmlLoader.load());
    }
}
