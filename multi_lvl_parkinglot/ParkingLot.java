package multi_lvl_parkinglot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ParkingLot {
    private List<Slot> allSlots;
    private List<Gate> allGates;
    private SlotAssignmentStrategy assignStrategy;
    private PricingStrategy pricingStrategy;

    public ParkingLot(SlotAssignmentStrategy assignStrategy, PricingStrategy pricingStrategy) {
        this.allSlots = new ArrayList<>();
        this.allGates = new ArrayList<>();
        this.assignStrategy = assignStrategy;
        this.pricingStrategy = pricingStrategy;
    }

    public void addSlot(Slot slot) { allSlots.add(slot); }
    public void addGate(Gate gate) { allGates.add(gate); }

    public Ticket park(Vehicle vehicle, Gate gate) {
        try {
            System.out.println("Attempting to park " + vehicle.getType() + " at Gate " + gate.getGateId());
            Slot assigned = assignStrategy.findSlot(gate, allSlots, vehicle.getType());

            assigned.markOccupied();
            Ticket ticket = new Ticket(vehicle, assigned);
            System.out.println("Assigned Slot: " + assigned.getSlotId() + " on Floor " + assigned.getFloor());
            return ticket;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public double exit(Ticket ticket) {
        ticket.getSlot().markFree();
        long now = System.currentTimeMillis();
        double fee = pricingStrategy.calculateFee(ticket, now);

        System.out.println("Vehicle " + ticket.getVehicle().getPlateNumber() +
                " exited. Slot " + ticket.getSlot().getSlotId() + " is now free. Fee: $" + fee);
        return fee;
    }

    public void showStatus(SlotType filterType) {
        Map<SlotType, Long> available = allSlots.stream()
            .filter(s -> !s.isOccupied())
            .filter(s -> filterType == null || s.getType() == filterType)
            .collect(Collectors.groupingBy(Slot::getType, Collectors.counting()));

        System.out.println("\n--- Parking Lot Status ---");
        if (available.isEmpty()) {
            System.out.println("No slots available.");
        } else {
            available.forEach((type, count) ->
                System.out.println(type + " SLOTS available: " + count));
        }
        System.out.println("--------------------------\n");
    }
}
