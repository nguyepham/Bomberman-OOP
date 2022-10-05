package game.bomman.inputHandler.command;
import game.bomman.entity.character.Character;

public class MovingCommand extends Command {
    @Override
    public void execute(Character obj) {
        obj.beTouched();
    }
}
