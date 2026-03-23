import java.util.Queue;

public class Game {
    private Board board;
    private Queue<Player> playerQueue;
    private Dice dice;
    private MoveStrategy strategy;

    public Game(Board board, Queue<Player> playerQueue, Dice dice, MoveStrategy strategy) {
        this.board = board;
        this.playerQueue = playerQueue;
        this.dice = dice;
        this.strategy = strategy;
    }

    public void play() {
        boolean won = false;

        while (!won && !playerQueue.isEmpty()) {
            Player current = playerQueue.poll();

            won = strategy.playTurn(current, board, dice);

            if (won) {
                System.out.println(current.getName() + " wins the game!");
            } else {
                playerQueue.offer(current);
            }
        }
    }
}
