package movie_ticket_booking;

public class Seat {
    private String seatId;
    private String row;
    private int number;
    private SeatType type;

    public Seat(String seatId, String row, int number, SeatType type) {
        this.seatId = seatId;
        this.row = row;
        this.number = number;
        this.type = type;
    }

    public String getSeatId() { return seatId; }
    public String getRow() { return row; }
    public int getNumber() { return number; }
    public SeatType getType() { return type; }

    public String toString() {
        return row + number + "(" + type + ")";
    }
}
