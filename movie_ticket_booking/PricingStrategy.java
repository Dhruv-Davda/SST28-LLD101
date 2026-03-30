package movie_ticket_booking;

public interface PricingStrategy {
    double getPrice(SeatType seatType);
}
