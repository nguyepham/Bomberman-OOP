package game.bomman.gameState.scores;

public class Score {
    private int rank;
    private long value;

    public Score(int rank, long value) {
        setRank(rank);
        setValue(value);
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
