package game.bomman.command.movingCommand;

import game.bomman.command.Command;
import game.bomman.entity.character.Character;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class MoveLeft extends Command {
    public MoveLeft() { label = "l"; }

    @Override
    public void executeOn(Character obj) {
        obj.moveLeft();
    }
}
