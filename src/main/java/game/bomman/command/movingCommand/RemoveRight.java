package game.bomman.command.movingCommand;

import game.bomman.command.Command;
import game.bomman.entity.character.Character;

public class RemoveRight extends Command {
    @Override
    public void executeOn(Character obj) {
        obj.removeRight();
    }
}
