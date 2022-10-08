package game.bomman.command;
import game.bomman.entity.character.Character;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Command {
    public abstract void executeOn(Character obj);
}
