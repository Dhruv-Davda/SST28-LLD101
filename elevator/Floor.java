package elevator;

public class Floor {
    private int floorNumber;
    private boolean underMaintenance;

    public Floor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.underMaintenance = false;
    }

    public int getFloorNumber() { return floorNumber; }
    public boolean isUnderMaintenance() { return underMaintenance; }

    public void setUnderMaintenance(boolean status) {
        this.underMaintenance = status;
        if (status) {
            System.out.println("Floor " + floorNumber + " is now under maintenance.");
        } else {
            System.out.println("Floor " + floorNumber + " maintenance completed.");
        }
    }
}
