package game.bomman.command;
import game.bomman.entity.character.Character;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Command {
    protected String label = "default";

    public String getLabel() { return label; }

    public abstract void executeOn(Character obj);
}
