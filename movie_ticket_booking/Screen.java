package movie_ticket_booking;

import java.util.List;

public class Screen {
    private String screenId;
    private List<Seat> seats;

    public Screen(String screenId, List<Seat> seats) {
        this.screenId = screenId;
        this.seats = seats;
    }

    public String getScreenId() { return screenId; }
    public List<Seat> getSeats() { return seats; }
}
