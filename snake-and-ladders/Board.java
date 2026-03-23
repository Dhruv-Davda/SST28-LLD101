import java.util.List;

public class Board {
    private int totalCells;
    private List<Snake> snakes;
    private List<Ladder> ladders;

    public Board(int totalCells, List<Snake> snakes, List<Ladder> ladders) {
        this.totalCells = totalCells;
        this.snakes = snakes;
        this.ladders = ladders;
    }

    public int getTotalCells() { return totalCells; }

    public int checkPosition(int pos) {
        for (Snake s : snakes) {
            if (s.getHead() == pos) return s.getTail();
        }
        for (Ladder l : ladders) {
            if (l.getBottom() == pos) return l.getTop();
        }
        return pos;
    }
}
