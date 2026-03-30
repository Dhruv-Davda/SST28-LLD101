package movie_ticket_booking;

public class StandardPricing implements PricingStrategy {
    public double getPrice(SeatType seatType) {
        switch (seatType) {
            case REGULAR: return 200;
            case PREMIUM: return 350;
            case VIP: return 500;
            default: return 200;
        }
    }
}
