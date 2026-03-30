package movie_ticket_booking;

import java.util.ArrayList;
import java.util.List;

public class PricingEngine {
    private List<PricingRule> activeRules;

    public PricingEngine() {
        this.activeRules = new ArrayList<>();
        this.activeRules.add(new BasePriceRule());
    }

    public void addRule(PricingRule rule) {
        activeRules.add(rule);
        System.out.println("Pricing rule added: " + rule.getRuleName());
    }

    public void removeRule(PricingRule rule) {
        activeRules.remove(rule);
        System.out.println("Pricing rule removed: " + rule.getRuleName());
    }

    public void showActiveRules() {
        System.out.println("Active pricing rules:");
        for (PricingRule r : activeRules) {
            System.out.println("  - " + r.getRuleName());
        }
    }

    public double calculatePrice(Show show, Seat seat) {
        double price = 0;
        for (PricingRule rule : activeRules) {
            price = rule.apply(price, show, seat);
        }
        double basePrice = show.getScreen().getBasePrice(seat.getType());
        if (price < basePrice) {
            price = basePrice;
        }
        return price;
    }
}
