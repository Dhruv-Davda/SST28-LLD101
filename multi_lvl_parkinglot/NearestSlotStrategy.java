package multi_lvl_parkinglot;

import java.util.List;

public class NearestSlotStrategy implements SlotAssignmentStrategy {

    private SlotType mapVehicleToSlot(VehicleType vt) {
        if (vt == VehicleType.TWO_WHEELER) return SlotType.SMALL;
        if (vt == VehicleType.CAR) return SlotType.MEDIUM;
        if (vt == VehicleType.BUS) return SlotType.LARGE;
        throw new IllegalArgumentException("Unknown vehicle type");
    }

    public Slot findSlot(Gate entryGate, List<Slot> slots, VehicleType vehicleType) throws Exception {
        SlotType needed = mapVehicleToSlot(vehicleType);

        Slot best = null;
        double bestDist = Double.MAX_VALUE;

        for (Slot s : slots) {
            if (!s.isOccupied() && s.getType() == needed) {
                double d = s.distanceTo(entryGate);
                if (d < bestDist) {
                    bestDist = d;
                    best = s;
                }
            }
        }

        if (best == null) {
            throw new Exception("Parking Lot is Full for type: " + needed);
        }
        return best;
    }
}
