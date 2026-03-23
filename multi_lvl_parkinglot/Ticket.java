package multi_lvl_parkinglot;

import java.util.UUID;

public class Ticket {
    private String id;
    private Vehicle vehicle;
    private Slot slot;
    private long entryTime;

    public Ticket(Vehicle vehicle, Slot slot) {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.vehicle = vehicle;
        this.slot = slot;
        this.entryTime = System.currentTimeMillis();
    }

    public String getId() { return id; }
    public Vehicle getVehicle() { return vehicle; }
    public Slot getSlot() { return slot; }
    public long getEntryTime() { return entryTime; }
    public void setEntryTime(long time) { this.entryTime = time; }
}
