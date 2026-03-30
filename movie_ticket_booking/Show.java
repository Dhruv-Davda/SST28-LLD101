package movie_ticket_booking;

import java.util.ArrayList;
import java.util.List;

public class Show {
    private String showId;
    private Movie movie;
    private Screen screen;
    private String startTime;
    private Theatre theatre;
    private List<String> bookedSeatIds;

    public Show(String showId, Movie movie, Screen screen, String startTime, Theatre theatre) {
        this.showId = showId;
        this.movie = movie;
        this.screen = screen;
        this.startTime = startTime;
        this.theatre = theatre;
        this.bookedSeatIds = new ArrayList<>();
    }

    public String getShowId() { return showId; }
    public Movie getMovie() { return movie; }
    public Screen getScreen() { return screen; }
    public String getStartTime() { return startTime; }
    public Theatre getTheatre() { return theatre; }

    public synchronized List<Seat> getAvailableSeats() {
        List<Seat> available = new ArrayList<>();
        for (Seat s : screen.getSeats()) {
            if (!bookedSeatIds.contains(s.getSeatId())) {
                available.add(s);
            }
        }
        return available;
    }

    public synchronized boolean bookSeats(List<String> seatIds) {
        for (String seatId : seatIds) {
            if (bookedSeatIds.contains(seatId)) {
                return false;
            }
        }
        bookedSeatIds.addAll(seatIds);
        return true;
    }

    public synchronized void releaseSeats(List<String> seatIds) {
        bookedSeatIds.removeAll(seatIds);
    }
}
