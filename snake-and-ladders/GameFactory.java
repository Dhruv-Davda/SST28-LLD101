import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GameFactory {
    public static Game createGame(List<String> names, List<Snake> snakes,
                                  List<Ladder> ladders, String mode) {
        Board board = new Board(100, snakes, ladders);
        Dice dice = new Dice(6);

        Queue<Player> players = new LinkedList<>();
        for (String n : names) {
            players.offer(new Player(n));
        }

        MoveStrategy strategy;
        if ("HARD".equalsIgnoreCase(mode)) {
            strategy = new StrictMode();
        } else {
            strategy = new NormalMode();
        }

        return new Game(board, players, dice, strategy);
    }
}
