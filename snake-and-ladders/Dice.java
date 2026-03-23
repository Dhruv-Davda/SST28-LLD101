import java.util.Random;

public class Dice {
    private int sides;
    private Random rand;

    public Dice(int sides) {
        this.sides = sides;
        this.rand = new Random();
    }

    public int roll() {
        return rand.nextInt(sides) + 1;
    }
}
