package multi_lvl_parkinglot;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<SlotType, Double> rates = new HashMap<>();
        rates.put(SlotType.SMALL, 5.0);
        rates.put(SlotType.MEDIUM, 10.0);
        rates.put(SlotType.LARGE, 20.0);
        PricingStrategy pricing = new HourlyPricing(rates);

        SlotAssignmentStrategy slotFinder = new NearestSlotStrategy();

        ParkingLot lot = new ParkingLot(slotFinder, pricing);

        Gate g1 = new Gate("G1", 1, 0, 0);
        lot.addGate(g1);

        lot.addSlot(new Slot("S1", SlotType.MEDIUM, 1, 10, 10));
        lot.addSlot(new Slot("S2", SlotType.MEDIUM, 1, 50, 50));
        lot.addSlot(new Slot("S3", SlotType.SMALL, 2, 5, 5));

        lot.showStatus(null);

        Vehicle car = new Vehicle("ABC-123", VehicleType.CAR);
        Ticket t = lot.park(car, g1);

        if (t != null) {
            long twoHoursAgo = System.currentTimeMillis() - (2 * 60 * 60 * 1000) - 1000;
            t.setEntryTime(twoHoursAgo);
            lot.exit(t);
        }

        lot.showStatus(null);
    }
}
