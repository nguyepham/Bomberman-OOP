package game.object;

public class Entity {
    protected int x;
    protected int y;

    /**
     * Creates an entity with initial position.
     *
     * @param x horizontal coordinate.
     * @param y vertical coordinate.
     */
    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
