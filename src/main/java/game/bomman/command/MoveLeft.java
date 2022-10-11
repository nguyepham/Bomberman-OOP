package game.bomman.command;

import game.bomman.entity.character.Character;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class MoveLeft extends Command {
    @Override
    public void executeOn(Character obj) {
        obj.moveLeft();
    }
}
