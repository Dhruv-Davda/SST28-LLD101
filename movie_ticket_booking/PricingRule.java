package movie_ticket_booking;

public interface PricingRule {
    double apply(double currentPrice, Show show, Seat seat);
    String getRuleName();
}
