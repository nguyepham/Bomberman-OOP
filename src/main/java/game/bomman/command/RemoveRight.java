package game.bomman.command;

import game.bomman.entity.character.Character;

public class RemoveRight extends Command {
    @Override
    public void executeOn(Character obj) {
        obj.removeRight();
    }
}
