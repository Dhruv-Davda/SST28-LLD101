package multi_lvl_parkinglot;

import java.util.Map;

public class HourlyPricing implements PricingStrategy {
    private Map<SlotType, Double> rates;

    public HourlyPricing(Map<SlotType, Double> rates) {
        this.rates = rates;
    }

    public double calculateFee(Ticket ticket, long exitTime) {
        long duration = exitTime - ticket.getEntryTime();
        double hrs = Math.ceil(duration / (1000.0 * 60 * 60));
        if (hrs == 0) hrs = 1;

        double rate = rates.getOrDefault(ticket.getSlot().getType(), 10.0);
        return hrs * rate;
    }
}
