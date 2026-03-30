package movie_ticket_booking;

import java.time.LocalDate;
import java.time.DayOfWeek;

public class WeekendPriceRule implements PricingRule {
    private double multiplier;

    public WeekendPriceRule(double multiplier) {
        this.multiplier = multiplier;
    }

    public double apply(double currentPrice, Show show, Seat seat) {
        DayOfWeek day = LocalDate.now().getDayOfWeek();
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            return currentPrice * multiplier;
        }
        return currentPrice;
    }

    public String getRuleName() { return "Weekend Surge (x" + multiplier + ")"; }
}
