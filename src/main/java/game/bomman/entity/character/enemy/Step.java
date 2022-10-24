package game.bomman.entity.character.enemy;


public class Step {
    private int posX;
    private int posY;
    private int trace;

    public Step(int posX, int posY, int trace) {
        this.posX = posX;
        this.posY = posY;
        this.trace = trace;
    }

    public int getPosX() { return posX; }

    public int getPosY() { return posY; }

    public int getTrace() { return trace; }

    public void setTrace(int value) { trace = value; }
}
