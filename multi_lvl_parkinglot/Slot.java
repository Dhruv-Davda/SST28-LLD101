package multi_lvl_parkinglot;

public class Slot {
    private String slotId;
    private SlotType type;
    private boolean occupied;
    private int floor;
    private double xPos;
    private double yPos;

    public Slot(String slotId, SlotType type, int floor, double xPos, double yPos) {
        this.slotId = slotId;
        this.type = type;
        this.floor = floor;
        this.xPos = xPos;
        this.yPos = yPos;
        this.occupied = false;
    }

    public String getSlotId() { return slotId; }
    public SlotType getType() { return type; }
    public boolean isOccupied() { return occupied; }
    public void markOccupied() { this.occupied = true; }
    public void markFree() { this.occupied = false; }
    public int getFloor() { return floor; }

    public double distanceTo(Gate gate) {
        double dx = this.xPos - gate.getXPos();
        double dy = this.yPos - gate.getYPos();
        double dz = (this.floor - gate.getFloorNum()) * 10.0;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
}
