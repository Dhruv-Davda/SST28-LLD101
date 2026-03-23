package pen;

public class PenFactory {

    public static Pen createPen(PenType type, String color, MechanismType mech) {
        WriteStrategy ws;
        RefillStrategy rs;

        if (type == PenType.BALLPOINT) {
            ws = new BallpointWrite();
            rs = new TubeRefill();
        } else if (type == PenType.GEL) {
            ws = new GelWrite();
            rs = new TubeRefill();
        } else if (type == PenType.INK) {
            ws = new InkWrite();
            rs = new BottleRefill();
        } else {
            throw new IllegalArgumentException("Unknown Pen Type");
        }

        OpenCloseStrategy ocs;
        if (mech == MechanismType.CAP) {
            ocs = new CapMechanism();
        } else if (mech == MechanismType.CLICK) {
            ocs = new ClickMechanism();
        } else {
            throw new IllegalArgumentException("Unknown Mechanism Type");
        }

        return new Pen(color, ws, rs, ocs);
    }
}
