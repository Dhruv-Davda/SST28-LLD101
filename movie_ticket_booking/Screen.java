package movie_ticket_booking;

import java.util.List;
import java.util.Map;

public class Screen {
    private String screenId;
    private List<Seat> seats;
    private Map<SeatType, Double> basePrices;

    public Screen(String screenId, List<Seat> seats, Map<SeatType, Double> basePrices) {
        this.screenId = screenId;
        this.seats = seats;
        this.basePrices = basePrices;
    }

    public String getScreenId() { return screenId; }
    public List<Seat> getSeats() { return seats; }

    public double getBasePrice(SeatType type) {
        return basePrices.getOrDefault(type, 100.0);
    }
}
