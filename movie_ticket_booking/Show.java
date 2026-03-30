package movie_ticket_booking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Show {
    private String showId;
    private Movie movie;
    private Screen screen;
    private String startTime;
    private Theatre theatre;
    private List<String> bookedSeatIds;
    private Map<String, Long> lockedSeats;
    private static final long LOCK_TIMEOUT_MS = 5 * 60 * 1000;

    public Show(String showId, Movie movie, Screen screen, String startTime, Theatre theatre) {
        this.showId = showId;
        this.movie = movie;
        this.screen = screen;
        this.startTime = startTime;
        this.theatre = theatre;
        this.bookedSeatIds = new ArrayList<>();
        this.lockedSeats = new HashMap<>();
    }

    public String getShowId() { return showId; }
    public Movie getMovie() { return movie; }
    public Screen getScreen() { return screen; }
    public String getStartTime() { return startTime; }
    public Theatre getTheatre() { return theatre; }

    public synchronized List<Seat> getAvailableSeats() {
        cleanExpiredLocks();
        List<Seat> available = new ArrayList<>();
        for (Seat s : screen.getSeats()) {
            if (!bookedSeatIds.contains(s.getSeatId()) && !lockedSeats.containsKey(s.getSeatId())) {
                available.add(s);
            }
        }
        return available;
    }

    public synchronized List<Seat> getSeatsForDisplay() {
        cleanExpiredLocks();
        List<Seat> viewable = new ArrayList<>();
        for (Seat s : screen.getSeats()) {
            if (!bookedSeatIds.contains(s.getSeatId())) {
                viewable.add(s);
            }
        }
        return viewable;
    }

    public synchronized boolean lockSeats(List<String> seatIds) {
        cleanExpiredLocks();
        for (String seatId : seatIds) {
            if (bookedSeatIds.contains(seatId) || lockedSeats.containsKey(seatId)) {
                return false;
            }
        }
        long now = System.currentTimeMillis();
        for (String seatId : seatIds) {
            lockedSeats.put(seatId, now);
        }
        return true;
    }

    public synchronized boolean confirmBooking(List<String> seatIds) {
        for (String seatId : seatIds) {
            if (!lockedSeats.containsKey(seatId)) {
                return false;
            }
        }
        for (String seatId : seatIds) {
            lockedSeats.remove(seatId);
        }
        bookedSeatIds.addAll(seatIds);
        return true;
    }

    public synchronized void releaseLock(List<String> seatIds) {
        for (String seatId : seatIds) {
            lockedSeats.remove(seatId);
        }
    }

    public synchronized void releaseSeats(List<String> seatIds) {
        bookedSeatIds.removeAll(seatIds);
    }

    public synchronized int getBookedCount() {
        return bookedSeatIds.size();
    }

    public int getTotalSeatCount() {
        return screen.getSeats().size();
    }

    private void cleanExpiredLocks() {
        long now = System.currentTimeMillis();
        lockedSeats.entrySet().removeIf(entry -> (now - entry.getValue()) > LOCK_TIMEOUT_MS);
    }
}
