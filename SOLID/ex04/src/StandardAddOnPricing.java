import java.util.*;

public class StandardAddOnPricing implements AddOnPricing {
    private static final Map<AddOn, Double> PRICES = Map.of(
        AddOn.MESS, 500.0,
        AddOn.LAUNDRY, 500.0,
        AddOn.GYM, 300.0
    );

    public Money priceFor(AddOn addOn) {
        return new Money(PRICES.getOrDefault(addOn, 0.0));
    }
}
