package movie_ticket_booking;

public class DemandPriceRule implements PricingRule {
    private double threshold;
    private double multiplier;

    public DemandPriceRule(double threshold, double multiplier) {
        this.threshold = threshold;
        this.multiplier = multiplier;
    }

    public double apply(double currentPrice, Show show, Seat seat) {
        int total = show.getTotalSeatCount();
        int booked = show.getBookedCount();
        double bookedPercent = (booked * 100.0) / total;

        if (bookedPercent >= threshold) {
            return currentPrice * multiplier;
        }
        return currentPrice;
    }

    public String getRuleName() { return "Demand Surge (>" + threshold + "% booked -> x" + multiplier + ")"; }
}
