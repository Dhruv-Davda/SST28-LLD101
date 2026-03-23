package multi_lvl_parkinglot;

public class Vehicle {
    private String plateNumber;
    private VehicleType type;

    public Vehicle(String plateNumber, VehicleType type) {
        this.plateNumber = plateNumber;
        this.type = type;
    }

    public String getPlateNumber() { return plateNumber; }
    public VehicleType getType() { return type; }
}
