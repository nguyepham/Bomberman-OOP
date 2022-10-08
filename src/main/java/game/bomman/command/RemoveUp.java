package game.bomman.command;

import game.bomman.entity.character.Character;

public class RemoveUp extends Command {
    @Override
    public void executeOn(Character obj) {
        obj.removeUp();
    }
}
