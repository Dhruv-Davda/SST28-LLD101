package movie_ticket_booking;

public class BasePriceRule implements PricingRule {

    public double apply(double currentPrice, Show show, Seat seat) {
        return show.getScreen().getBasePrice(seat.getType());
    }

    public String getRuleName() { return "Base Price"; }
}
