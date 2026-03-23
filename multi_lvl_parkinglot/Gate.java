package multi_lvl_parkinglot;

public class Gate {
    private String gateId;
    private int floorNum;
    private double xPos;
    private double yPos;

    public Gate(String gateId, int floorNum, double xPos, double yPos) {
        this.gateId = gateId;
        this.floorNum = floorNum;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public String getGateId() { return gateId; }
    public int getFloorNum() { return floorNum; }
    public double getXPos() { return xPos; }
    public double getYPos() { return yPos; }
}
