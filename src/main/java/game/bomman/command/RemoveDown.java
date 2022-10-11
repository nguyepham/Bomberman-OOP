package game.bomman.command;

import game.bomman.entity.character.Character;

public class RemoveDown extends Command {
    @Override
    public void executeOn(Character obj) {
        obj.removeDown();
    }
}
