public class NormalMode implements MoveStrategy {
    public boolean playTurn(Player player, Board board, Dice dice) {
        boolean extraTurn = true;

        while (extraTurn) {
            int rolled = dice.roll();
            System.out.println(player.getName() + " rolled a " + rolled);

            if (rolled == 6) {
                System.out.println("Rolled a 6! Extra turn granted.");
                extraTurn = true;
            } else {
                extraTurn = false;
            }

            int newPos = player.getPosition() + rolled;

            if (newPos > board.getTotalCells()) {
                continue;
            }

            newPos = board.checkPosition(newPos);
            player.setPosition(newPos);
            System.out.println(player.getName() + " moved to " + newPos);

            if (player.getPosition() == board.getTotalCells()) {
                return true;
            }
        }
        return false;
    }
}
